package org.example.financemanager.domain

data class Transaction(
    val id: String,
    val userId: String,
    val amount: Double,
    val date: String,
    val category: String,
    val description: String
)
