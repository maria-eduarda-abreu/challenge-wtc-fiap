package br.com.fiap.challengewtcfiap

/**
 * Serviço de Autenticação Mockado (simulado) para a Sprint 1.
 * Na Sprint 2, este será substituído pela chamada à API em Java/C#.
 */
object AuthService {

    // Login mockado 1: Operador (para testar a visão CRM/Campanha Express)
    private const val OPERATOR_USER = "operador@wtc.com"
    private const val OPERATOR_PASS = "12345"

    // Login mockado 2: Cliente (para testar a visão Chat/Recebimento de Mensagens)
    private const val CLIENT_USER = "cliente@wtc.com"
    private const val CLIENT_PASS = "senha123"

    /**
     * Simula a tentativa de login.
     * @return O tipo de usuário logado (UserType) ou null se as credenciais forem inválidas.
     */
    fun login(email: String, pass: String): UserType? {
        return when {
            email == OPERATOR_USER && pass == OPERATOR_PASS -> UserType.OPERATOR
            email == CLIENT_USER && pass == CLIENT_PASS -> UserType.CLIENT
            else -> null
        }
    }
}