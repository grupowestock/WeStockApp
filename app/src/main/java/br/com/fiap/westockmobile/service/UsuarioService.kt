package br.com.fiap.westockmobile.service

import br.com.fiap.westockmobile.model.LoginRequest
import br.com.fiap.westockmobile.model.LoginResponse
import br.com.fiap.westockmobile.model.Usuario
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface UsuarioService {

    @GET("usuarios")
    suspend fun getUsuarios(): Response<List<Usuario>>

    @GET("usuarios/{id}")
    suspend fun getUsuario(@Path("id") id: Long): Response<Usuario>

    @POST("usuarios")
    suspend fun createUsuario(@Body usuario: Usuario): Response<Usuario>

    @POST("usuarios/login")
    suspend fun login(@Body loginRequest: LoginRequest): Response<LoginResponse>

    @GET("usuarios/validateEmail")
    suspend fun validateEmail(@Query("email") email: String): Response<Boolean>

    @POST("usuarios/redefinirSenha")
    suspend fun redefinirSenha(@Query("email") email: String, @Query("senha") senha: String): Response<Void>

    @PUT("usuarios")
    suspend fun updateUsuario(@Body usuario: Usuario): Response<Usuario>
}
