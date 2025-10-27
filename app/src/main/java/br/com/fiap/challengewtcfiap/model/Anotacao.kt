package br.com.fiap.challengewtcfiap.model

import com.google.firebase.firestore.ServerTimestamp
import java.util.Date

data class Anotacao(
    val clientId: String = "", // ID do Cliente (do mock ou do Firestore)
    val text: String = "",


    @ServerTimestamp
    val timestamp: Date? = null

) {
    constructor() : this("", "", null)
}