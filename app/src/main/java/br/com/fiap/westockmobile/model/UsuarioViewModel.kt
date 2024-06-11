package br.com.fiap.westockmobile.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.fiap.westockmobile.repository.UsuarioRepository
import kotlinx.coroutines.launch
import retrofit2.Response

class UsuarioViewModel(private val usuarioRepository: UsuarioRepository) : ViewModel() {

    private val _usuario = MutableLiveData<Usuario?>()
    val usuario: LiveData<Usuario?> get() = _usuario

    fun fetchUsuario(id: Long) {
        viewModelScope.launch {
            val response: Response<Usuario> = usuarioRepository.getUsuario(id)
            if (response.isSuccessful) {
                _usuario.postValue(response.body())
            }
        }
    }

    fun updateUsuario(usuario: Usuario) {
        viewModelScope.launch {
            val response: Response<Usuario> = usuarioRepository.updateUsuario(usuario)
            if (response.isSuccessful) {
                _usuario.postValue(response.body())
            }
        }
    }
}
