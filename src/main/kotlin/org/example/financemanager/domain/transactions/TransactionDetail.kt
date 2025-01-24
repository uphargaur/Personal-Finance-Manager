package org.example.financemanager.domain.transactions

data class TransactionDetail(
    val transactionId: String? = null,
    val amount: Double,
    val date: String,
    val description: String? = null
)