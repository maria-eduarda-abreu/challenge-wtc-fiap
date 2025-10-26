package br.com.fiap.challengewtcfiap

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import br.com.fiap.challengewtcfiap.ui.theme.ChallengewtcfiapTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ChallengewtcfiapTheme {
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
// E crie arquivos separados para eles no futuro.