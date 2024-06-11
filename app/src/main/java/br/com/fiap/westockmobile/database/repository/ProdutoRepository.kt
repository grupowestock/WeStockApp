package br.com.fiap.westockmobile.database.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import br.com.fiap.westockmobile.model.Produto
import br.com.fiap.westockmobile.service.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProdutoRepository {

    private val _produtos = MutableLiveData<List<Produto>>()
    val produtos: LiveData<List<Produto>> get() = _produtos

    private val produtoService = RetrofitInstance.produtoService

    fun fetchProdutosByUser(userId: Long) {
        produtoService.getProdutosByUser(userId).enqueue(object : Callback<List<Produto>> {
            override fun onResponse(call: Call<List<Produto>>, response: Response<List<Produto>>) {
                if (response.isSuccessful) {
                    _produtos.postValue(response.body())
                }
            }

            override fun onFailure(call: Call<List<Produto>>, t: Throwable) {
                // Trate o erro
            }
        })
    }
}
