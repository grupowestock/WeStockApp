package br.com.fiap.westockmobile.service

import br.com.fiap.westockmobile.model.Produto
import retrofit2.Call
import retrofit2.http.*

interface ProdutoService {

    @GET("produtos")
    fun getProdutos(): Call<List<Produto>>

    @GET("produtos/{id}")
    fun getProduto(@Path("id") id: Long): Call<Produto>

    @POST("produtos/usuario/{userId}")  // Endpoint atualizado para criar produto com userId na URL
    fun createProduto(@Path("userId") userId: Long, @Body produto: Produto): Call<Produto>

    @GET("produtos/ultimos/{userId}")
    fun getUltimosProdutos(@Path("userId") userId: Long): Call<List<Produto>>

    @GET("produtos/usuario/{userId}")
    fun getProdutosByUser(@Path("userId") userId: Long): Call<List<Produto>>

    // Outros endpoints (exemplo)
    @PUT("produtos/{id}")
    fun updateProduto(@Path("id") id: Long, @Body produto: Produto): Call<Produto>

    @DELETE("produtos/{id}")
    fun deleteProduto(@Path("id") id: Long): Call<Void>
}
