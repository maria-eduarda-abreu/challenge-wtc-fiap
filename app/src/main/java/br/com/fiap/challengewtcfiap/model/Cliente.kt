package br.com.fiap.challengewtcfiap.model

import com.google.firebase.firestore.DocumentId

data class Cliente(

    @DocumentId
    val id: String = "",
    val nome: String = "",
    val telefone: String? = null,
    val email: String? = null,
    val status: String? = null,
    val score: Int = 0,
    val tags: List<String> = emptyList()
) {
    constructor() : this(
        id = "",
        nome = "",
        telefone = null,
        email = null,
        status = null,
        score = 0,
        tags = emptyList()
    )
}