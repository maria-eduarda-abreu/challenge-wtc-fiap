package br.com.fiap.challengewtcfiap.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import br.com.fiap.challengewtcfiap.model.Cliente
import br.com.fiap.challengewtcfiap.viewmodel.ClientListViewModel

@Composable
fun ClientListScreen(
    viewModel: ClientListViewModel,
    onNavigateToDetail: (String) -> Unit // <-- 1. MUDANÇA AQUI (novo parâmetro)
) {


    val clientes by viewModel.clientes.collectAsState()
    val searchQuery by viewModel.searchQuery.collectAsState()
    val statusFilter by viewModel.statusFilter.collectAsState()



    Column(modifier = Modifier.padding(16.dp)) {


        OutlinedTextField(
            value = searchQuery,
            onValueChange = { novoValor ->
                viewModel.onSearchQueryChanged(novoValor)
            },
            label = { Text("Buscar por nome ou email") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))


        Row {
            FilterButton(
                text = "Ativo",
                isSelected = statusFilter == "Ativo",
                onClick = {
                    viewModel.onStatusFilterChanged("Ativo")
                }
            )
            FilterButton(
                text = "Inativo",
                isSelected = statusFilter == "Inativo",
                onClick = {
                    viewModel.onStatusFilterChanged("Inativo")
                }
            )
        }

        Spacer(modifier = Modifier.height(16.dp))


        LazyColumn(
            content = {
                items(clientes) { cliente ->
                    ClientItem(
                        cliente = cliente,
                        onClick = {
                            // 2. MUDANÇA AQUI (chama o novo parâmetro)
                            onNavigateToDetail(cliente.id)
                        }
                    )
                }
            }
        )
    }
}



@Composable
fun ClientItem(cliente: Cliente, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .clickable { onClick() }, // O onClick é chamado aqui
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Text(text = cliente.nome, style = MaterialTheme.typography.titleMedium)
            Text(text = cliente.email ?: "Sem email", style = MaterialTheme.typography.bodySmall)
            Text(
                text = "Status: ${cliente.status} | Score: ${cliente.score}",
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}

@Composable
fun FilterButton(text: String, isSelected: Boolean, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = if (isSelected) MaterialTheme.colorScheme.primary else Color.Gray,
            contentColor = Color.White
        ),
        modifier = Modifier.padding(end = 8.dp)
    ) {
        Text(text)
    }
}