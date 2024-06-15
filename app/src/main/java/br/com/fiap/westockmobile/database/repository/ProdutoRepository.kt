package br.com.fiap.westockmobile.database.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import br.com.fiap.westockmobile.model.Produto
import br.com.fiap.westockmobile.service.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber // Importante para logs de erro

class ProdutoRepository {
    private val _produtos = MutableLiveData<List<Produto>>()
    val produtos: LiveData<List<Produto>> get() = _produtos

    private val produtoService = RetrofitInstance.produtoService

    fun fetchProdutosByUser(userId: Long) {
        produtoService.getProdutosByUser(userId).enqueue(object : Callback<List<Produto>> {
            override fun onResponse(call: Call<List<Produto>>, response: Response<List<Produto>>) {
                if (response.isSuccessful) {
                    _produtos.postValue(response.body())
                } else {
                    Timber.e("Erro ao buscar produtos: ${response.code()} - ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<Produto>>, t: Throwable) {
                Timber.e(t, "Falha na requisição de produtos")
            }
        })
    }

    fun fetchUltimosProdutos(userId: Long) {
        produtoService.getUltimosProdutos(userId).enqueue(object : Callback<List<Produto>> {
            override fun onResponse(call: Call<List<Produto>>, response: Response<List<Produto>>) {
                if (response.isSuccessful) {
                    _produtos.postValue(response.body())
                } else {
                    Timber.e("Erro ao buscar últimos produtos: ${response.code()} - ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<Produto>>, t: Throwable) {
                Timber.e(t, "Falha na requisição de últimos produtos")
            }
        })
    }

    fun createProduto(userId: Long, produto: Produto, callback: (Produto?) -> Unit) {
        // Garante que o userId esteja definido no produto (opcional se já estiver sendo feito em outro lugar)
        val produtoComUserId = produto.copy(userId = userId)

        produtoService.createProduto(userId, produtoComUserId).enqueue(object : Callback<Produto> {
            override fun onResponse(call: Call<Produto>, response: Response<Produto>) {
                if (response.isSuccessful) {
                    callback(response.body())
                    // Atualiza a lista de produtos após a criação (opcional)
                    fetchProdutosByUser(userId)
                } else {
                    Timber.e("Erro ao criar produto: ${response.code()} - ${response.message()}")
                    callback(null)
                }
            }

            override fun onFailure(call: Call<Produto>, t: Throwable) {
                Timber.e(t, "Falha na requisição de criação de produto")
                callback(null)
            }
        })
    }
}
