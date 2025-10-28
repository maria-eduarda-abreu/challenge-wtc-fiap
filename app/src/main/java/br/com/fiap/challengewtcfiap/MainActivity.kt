package br.com.fiap.challengewtcfiap

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
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

import androidx.navigation.compose.rememberNavController
import br.com.fiap.challengewtcfiap.ui.theme.ChallengewtcfiapTheme


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

                // Instancia o controlador de navegação (NavHostController)
                val navController = rememberNavController()

                Scaffold(modifier = Modifier.fillMaxSize()) { _ ->
                    // Usa o componente de navegação centralizado
                    AppNavigation(navController = navController)
                }
            }
        }
    }
}
// Remova os placeholders OperatorDashboard e ClientDashboard daqui (ou deixe-os no Navigation.kt se preferir)
// E crie arquivos separados para eles no futuro
