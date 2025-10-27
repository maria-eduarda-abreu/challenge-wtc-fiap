package br.com.fiap.challengewtcfiap.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.fiap.challengewtcfiap.data.remote.FirestoreRepository
import br.com.fiap.challengewtcfiap.model.Anotacao
import br.com.fiap.challengewtcfiap.model.Cliente
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class ClientDetailViewModel(
    private val repository: FirestoreRepository
) : ViewModel() {

    private val _cliente = MutableStateFlow<Cliente?>(null)
    val cliente: StateFlow<Cliente?> = _cliente.asStateFlow()

    private val _anotacoes = MutableStateFlow<List<Anotacao>>(emptyList())
    val anotacoes: StateFlow<List<Anotacao>> = _anotacoes.asStateFlow()

    private val _novaAnotacaoTexto = MutableStateFlow("")
    val novaAnotacaoTexto: StateFlow<String> = _novaAnotacaoTexto.asStateFlow()


    private var clienteIdAtual: String = ""


    fun carregarDadosDoCliente(clienteId: String) {

        if (clienteId.isBlank() || clienteId == clienteIdAtual) return

        clienteIdAtual = clienteId


        buscarDetalhes(clienteId)
        buscarAnotacoes(clienteId)
    }

    private fun buscarDetalhes(clienteId: String) {
        viewModelScope.launch {

            _cliente.value = repository.getClienteById(clienteId)
        }
    }

    private fun buscarAnotacoes(clienteId: String) {
        viewModelScope.launch {
            repository.getAnotacoesFlow(clienteId)
                .catch { e ->
                    e.printStackTrace()
                    _anotacoes.value = emptyList()
                }
                .collect { lista ->
                    _anotacoes.value = lista
                }
        }
    }


    fun onNovaAnotacaoChanged(texto: String) {
        _novaAnotacaoTexto.value = texto
    }

    fun addAnotacao() {
        val texto = _novaAnotacaoTexto.value

        if (texto.isBlank() || clienteIdAtual.isBlank()) return

        viewModelScope.launch {
            val novaAnotacao = Anotacao(
                clientId = clienteIdAtual,
                text = texto

            )
            repository.addAnotacao(novaAnotacao)

            _novaAnotacaoTexto.value = ""
        }
    }
}