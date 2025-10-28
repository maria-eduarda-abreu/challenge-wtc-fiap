package br.com.fiap.challengewtcfiap.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import br.com.fiap.challengewtcfiap.model.Anotacao
import br.com.fiap.challengewtcfiap.viewmodel.ClientDetailViewModel

@Composable
fun ClientDetailScreen(
    viewModel: ClientDetailViewModel,
    clienteId: String // 1. A tela recebe o ID da navegação
) {

    LaunchedEffect(clienteId) {
        viewModel.carregarDadosDoCliente(clienteId)
    }

    val cliente by viewModel.cliente.collectAsState()
    val anotacoes by viewModel.anotacoes.collectAsState()
    val novaAnotacaoTexto by viewModel.novaAnotacaoTexto.collectAsState()


    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
    ) {

        if (cliente == null) {
            Text("Carregando detalhes do cliente...")
        } else {

            Text(
                text = cliente!!.nome,
                style = MaterialTheme.typography.headlineMedium
            )
            Text(
                text = cliente!!.email ?: "Sem email",
                style = MaterialTheme.typography.bodyLarge
            )
            Text(
                text = "Status: ${cliente!!.status} | Score: ${cliente!!.score}",
                style = MaterialTheme.typography.bodyMedium
            )

            Spacer(modifier = Modifier.height(24.dp))


            Text(
                text = "Anotações Rápidas",
                style = MaterialTheme.typography.titleLarge
            )
            Row(verticalAlignment = Alignment.CenterVertically) {
                OutlinedTextField(
                    value = novaAnotacaoTexto,
                    onValueChange = { viewModel.onNovaAnotacaoChanged(it) },
                    label = { Text("Nova anotação") },
                    modifier = Modifier.weight(1f)
                )
                Button(
                    onClick = { viewModel.addAnotacao() },
                    modifier = Modifier.padding(start = 8.dp)
                ) {
                    Text("Salvar")
                }
            }

            Spacer(modifier = Modifier.height(16.dp))


            LazyColumn {
                items(anotacoes) { anotacao ->
                    AnotacaoItem(anotacao = anotacao)
                }
            }
        }
    }
}

@Composable
fun AnotacaoItem(anotacao: Anotacao) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Text(
            text = anotacao.text,
            modifier = Modifier.padding(12.dp),
            style = MaterialTheme.typography.bodyMedium
        )
    }
}