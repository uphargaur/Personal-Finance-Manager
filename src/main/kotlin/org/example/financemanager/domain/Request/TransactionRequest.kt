package org.example.financemanager.domain.Request

data class TransactionRequest(
    val id: String? = null,
    val userId: String?,
    val category: String,
    val amount: Double,
    val date: String,
    val description: String?
)