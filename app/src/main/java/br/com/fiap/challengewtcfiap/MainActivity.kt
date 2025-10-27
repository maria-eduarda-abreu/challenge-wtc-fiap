package br.com.fiap.challengewtcfiap

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier

// 1. --- IMPORTS ADICIONADOS ---
// Importa o seu repositório
import br.com.fiap.challengewtcfiap.data.remote.FirestoreRepository
// Importa o "mapa" de navegação (que vamos ajustar a seguir)
import br.com.fiap.challengewtcfiap.ui.navigation.AppNavigation
// Importa sua Factory
import br.com.fiap.challengewtcfiap.viewmodel.ViewModelFactory
// Importa seu Tema
import br.com.fiap.challengewtcfiap.ui.theme.ChallengewtcfiapTheme // Corrigido para o seu nome de tema

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val repository = FirestoreRepository()
        
        val viewModelFactory = ViewModelFactory(repository)


        enableEdgeToEdge()
        setContent {

            ChallengewtcfiapTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->

                    AppNavigation(
                        factory = viewModelFactory,

                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

