package br.com.fiap.challengewtcfiap.data.remote

import br.com.fiap.challengewtcfiap.model.Anotacao
import br.com.fiap.challengewtcfiap.model.Cliente
import com.google.firebase.Firebase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.ktx.snapshots
import com.google.firebase.firestore.ktx.toObjects // Removido (não usado mais no getClientesFlow)
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.tasks.await

class FirestoreRepository {

    private val db: FirebaseFirestore = Firebase.firestore
    private val clientesCollection = db.collection("clientes")
    private val anotacoesCollection = db.collection("anotacoes")

    // --- FUNÇÃO CORRIGIDA ---
    fun getClientesFlow(): Flow<List<Cliente>> {
        return clientesCollection.snapshots()
            .map { querySnapshot ->
                // Itera sobre os documentos individuais
                querySnapshot.documents.mapNotNull { document ->
                    // Ao converter um Documento individual,
                    // o @DocumentId é preenchido corretamente.
                    document.toObject(Cliente::class.java)
                }
            }
    }
    // --- FIM DA CORREÇÃO ---

    fun getAnotacoesFlow(clienteId: String): Flow<List<Anotacao>> {
        return anotacoesCollection
            .whereEqualTo("clientId", clienteId)
            .orderBy("timestamp", Query.Direction.DESCENDING)
            .snapshots()
            .map { querySnapshot ->
                // Aqui .toObjects() funciona pois Anotacao não usa @DocumentId
                querySnapshot.toObjects(Anotacao::class.java)
            }
    }

    suspend fun addAnotacao(anotacao: Anotacao) {
        try {
            anotacoesCollection.add(anotacao).await()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    // Esta função JÁ ESTAVA CORRETA
    // pois .toObject() em um DocumentSnapshot preenche o @DocumentId
    suspend fun getClienteById(clienteId: String): Cliente? {
        return try {
            clientesCollection.document(clienteId)
                .get()
                .await()
                .toObject(Cliente::class.java)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}