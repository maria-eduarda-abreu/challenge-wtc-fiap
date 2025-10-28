package br.com.fiap.challengewtcfiap.ui.navigation

import androidx.compose.runtime.Composable
// 1. --- IMPORTS ADICIONADOS ---
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import br.com.fiap.challengewtcfiap.viewmodel.ClientDetailViewModel
import br.com.fiap.challengewtcfiap.viewmodel.ClientListViewModel
import br.com.fiap.challengewtcfiap.viewmodel.ViewModelFactory

import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import br.com.fiap.challengewtcfiap.ui.screens.ClientDetailScreen
import br.com.fiap.challengewtcfiap.ui.screens.ClientListScreen

@Composable


fun AppNavigation(
    factory: ViewModelFactory,
    modifier: Modifier = Modifier
) {
    val navController = rememberNavController()


    NavHost(
        navController = navController,
        startDestination = "client_list",
        modifier = modifier
    ) {


        composable(route = "client_list") {


            val viewModel: ClientListViewModel = viewModel(factory = factory)

            ClientListScreen(
                viewModel = viewModel,
                onNavigateToDetail = { clienteId ->
                    navController.navigate("client_detail/$clienteId")
                }
            )
        }


        composable(
            route = "client_detail/{clientId}",
            arguments = listOf(navArgument("clientId") { type = NavType.StringType })
        ) { backStackEntry ->
            val clienteId = backStackEntry.arguments?.getString("clientId") ?: ""


            val viewModel: ClientDetailViewModel = viewModel(factory = factory)

            ClientDetailScreen(
                viewModel = viewModel,
                clienteId = clienteId
            )
        }
    }
}