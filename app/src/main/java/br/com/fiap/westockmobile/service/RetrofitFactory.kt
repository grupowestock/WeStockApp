package br.com.fiap.westockmobile.service

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    private const val BASE_URL = "https://sua-api-url.com/api/"

    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val usuarioService: UsuarioService by lazy {
        retrofit.create(UsuarioService::class.java)
    }

    val produtoService: ProdutoService by lazy {
        retrofit.create(ProdutoService::class.java)
    }


}
