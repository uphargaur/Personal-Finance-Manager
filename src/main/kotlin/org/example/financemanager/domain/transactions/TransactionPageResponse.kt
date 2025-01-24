package org.example.financemanager.domain.transactions

import org.example.financemanager.domain.Request.TransactionRequest

data class TransactionPageResponse(
    val pageNo: Int,
    val pageSize: Int,
    val totalPages: Int,
    val totalTransactions: Int,
    val transactions: List<TransactionRequest>
)
