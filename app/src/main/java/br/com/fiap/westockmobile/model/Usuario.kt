package br.com.fiap.westockmobile.model

data class Usuario(
    val id: Long? = null,
    val nome: String,
    val sobrenome: String,
    val email: String,
    val celular: String,
    val senha: String
)

data class LoginRequest(
    val email: String,
    val senha: String
)

data class LoginResponse(
    val token: String,
    val usuario: Usuario
)
