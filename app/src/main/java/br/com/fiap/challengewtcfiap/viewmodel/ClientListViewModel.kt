package br.com.fiap.challengewtcfiap.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.fiap.challengewtcfiap.data.remote.FirestoreRepository
import br.com.fiap.challengewtcfiap.model.Cliente
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ClientListViewModel(
    private val repository: FirestoreRepository
) : ViewModel() {

    private val _clientesFromRepo = MutableStateFlow<List<Cliente>>(emptyList())

    private val _searchQuery = MutableStateFlow("")
    val searchQuery = _searchQuery.asStateFlow()

    private val _statusFilter = MutableStateFlow("")
    val statusFilter = _statusFilter.asStateFlow()


    val clientes: StateFlow<List<Cliente>> = combine(
        _clientesFromRepo,
        _searchQuery,
        _statusFilter
    ) { listaCompleta, query, status ->


        val listaFiltradaPorStatus = if (status.isBlank()) {
            listaCompleta // Se o filtro de status estiver vazio, retorna a lista toda
        } else {
            listaCompleta.filter { it.status == status }
        }

        val listaFiltradaFinal = if (query.isBlank()) {
            listaFiltradaPorStatus // Se a busca estiver vazia, retorna a lista filtrada pelo status
        } else {
            listaFiltradaPorStatus.filter {
                it.nome.contains(query, ignoreCase = true) ||
                        it.email?.contains(query, ignoreCase = true) == true
            }
        }

        listaFiltradaFinal // O resultado final da combinação

    }.stateIn( // Converte o Flow combinado em um StateFlow
        scope = viewModelScope,
        started = kotlinx.coroutines.flow.SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )


    init {
        buscarClientes()
    }

    private fun buscarClientes() {

        viewModelScope.launch {
            repository.getClientesFlow()
                // 7. Se der erro (ex: sem internet), emite uma lista vazia
                .catch { e ->
                    e.printStackTrace()
                    _clientesFromRepo.value = emptyList()
                }
                // 8. "Coleta" a lista do Flow
                .collect { lista ->
                    _clientesFromRepo.value = lista // Atualiza a lista completa
                }
        }
    }


    fun onSearchQueryChanged(query: String) {
        _searchQuery.value = query
    }

    fun onStatusFilterChanged(status: String) {
        // Lógica de "toggle": se clicar no mesmo filtro, desativa
        if (_statusFilter.value == status) {
            _statusFilter.value = ""
        } else {
            _statusFilter.value = status
        }
    }
}