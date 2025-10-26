package br.com.fiap.challengewtcfiap


import br.com.fiap.challengewtcfiap.OperatorDashboard
import br.com.fiap.challengewtcfiap.ClientDashboard
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

// Definição das rotas (pode ser um Enum ou Sealed Class em projetos maiores)
object Routes {
    const val LOGIN = "login"
    const val OPERATOR_DASHBOARD = "operator_dashboard/{userType}"
    const val CLIENT_DASHBOARD = "client_dashboard"
}

/**
 * Componente principal para gerenciar a navegação no App.
 */
@Composable
fun AppNavigation(
    navController: NavHostController
) {
    NavHost(navController = navController, startDestination = Routes.LOGIN) {

        // Rota de Login (Eduarda)
        composable(Routes.LOGIN) {
            LoginScreen(
                onLoginSuccess = { userType ->
                    // Após o login, navega para o Dashboard correspondente
                    if (userType == UserType.OPERATOR) {
                        navController.navigate(Routes.OPERATOR_DASHBOARD.replace("{userType}", userType.name)) {
                            // Limpa a pilha para que o usuário não volte para o Login
                            popUpTo(Routes.LOGIN) { inclusive = true }
                        }
                    } else {
                        navController.navigate(Routes.CLIENT_DASHBOARD) {
                            popUpTo(Routes.LOGIN) { inclusive = true }
                        }
                    }
                }
            )
        }

        // Rota do Dashboard do Operador (Visão CRM e Campanha Express) (Pablo + Eduarda)
        composable(Routes.OPERATOR_DASHBOARD) { backStackEntry ->
            // Você pode até extrair o tipo de usuário se precisar:
            // val userType = backStackEntry.arguments?.getString("userType")

            OperatorDashboard() // Implementação de Pablo e Eduarda
        }

        // Rota do Dashboard do Cliente (Visão Chat) (Gustavo)
        composable(Routes.CLIENT_DASHBOARD) {
            ClientDashboard() // Implementação de Gustavo
        }
    }
}