package br.com.fiap.challengewtcfiap.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

import br.com.fiap.challengewtcfiap.data.remote.FirestoreRepository

class ViewModelFactory(private val repository: FirestoreRepository) : ViewModelProvider.Factory {


    override fun <T : ViewModel> create(modelClass: Class<T>): T {


        if (modelClass.isAssignableFrom(ClientListViewModel::class.java)) {

            @Suppress("UNCHECKED_CAST")
            return ClientListViewModel(repository) as T
        }

        if (modelClass.isAssignableFrom(ClientDetailViewModel::class.java)) {

            @Suppress("UNCHECKED_CAST")
            return ClientDetailViewModel(repository) as T
        }

        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }
}

