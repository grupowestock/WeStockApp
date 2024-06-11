package br.com.fiap.westockmobile.model

data class ProdutoVendido(
    val id: Long? = null,
    val produto: Produto,
    val quantidadeVendida: Int
)
