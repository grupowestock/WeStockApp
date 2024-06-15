package br.com.fiap.westockmobile.model

data class Produto(
    val userId: Long? = null,
    val id: Long? = null,
    val categoria: String,
    val descricao: String,
    val dataAquisicao: String,
    val cor: String,
    val tamanho: Double,
    val quantidade: Int,
    val codigoSku: String,
    val preco: Double,
    val peso: Double,
    val imagem: String,
    val imagemUrl: String,
    val nome: String,
    val dataCadastro: String
)
