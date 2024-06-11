package br.com.fiap.westockmobile.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import br.com.fiap.westockmobile.model.LoginRequest
import br.com.fiap.westockmobile.model.LoginResponse
import br.com.fiap.westockmobile.model.Usuario
import br.com.fiap.westockmobile.service.UsuarioService
import retrofit2.Response

class UsuarioRepository(private val usuarioService: UsuarioService) {

    private val _usuarios = MutableLiveData<List<Usuario>>()
    val usuarios: LiveData<List<Usuario>> get() = _usuarios

    suspend fun login(email: String, senha: String): Response<LoginResponse> {
        val loginRequest = LoginRequest(email, senha)
        return usuarioService.login(loginRequest)
    }

    suspend fun createUsuario(usuario: Usuario): Response<Usuario> {
        return usuarioService.createUsuario(usuario)
    }

    suspend fun validateEmail(email: String): Response<Boolean> {
        return usuarioService.validateEmail(email)
    }

    suspend fun redefinirSenha(email: String, senha: String): Response<Void> {
        return usuarioService.redefinirSenha(email, senha)
    }

    suspend fun getUsuario(id: Long): Response<Usuario> {
        return usuarioService.getUsuario(id)
    }

    suspend fun updateUsuario(usuario: Usuario): Response<Usuario> {
        return usuarioService.updateUsuario(usuario)
    }
}
