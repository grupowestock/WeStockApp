package br.com.fiap.westockmobile.service

import br.com.fiap.westockmobile.model.Produto
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ProdutoService {

    @GET("produtos")
    fun getProdutos(): Call<List<Produto>>

    @GET("produtos/{id}")
    fun getProduto(@Path("id") id: Long): Call<Produto>

    @POST("produtos")
    fun createProduto(@Body produto: Produto): Call<Produto>

    @GET("produtos/ultimos/{userId}")
    fun getUltimosProdutos(@Path("userId") userId: Long): Call<List<Produto>>

    @GET("produtos/usuario/{userId}")
    fun getProdutosByUser(@Path("userId") userId: Long): Call<List<Produto>>
}
