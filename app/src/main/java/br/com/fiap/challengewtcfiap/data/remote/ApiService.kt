package br.com.fiap.challengewtcfiap.data.remote

import br.com.fiap.challengewtcfiap.model.Cliente

interface ApiService {
    @GET("/clients")
    suspend fun getClientes(): List<Cliente>

}

annotation class GET(val value: String)
