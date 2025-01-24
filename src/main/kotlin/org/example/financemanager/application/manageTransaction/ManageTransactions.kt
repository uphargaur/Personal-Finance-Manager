package org.example.financemanager.application.manageTransaction

import org.example.financemanager.domain.Request.TransactionRequest
import org.example.financemanager.domain.transactions.TransactionPageResponse

interface ManageTransactions {
    fun addTransaction(token: String, userTransactions: TransactionRequest): TransactionRequest
    fun getTransactionsWithPagination(token: String, page: Int, size: Int, type: String?): TransactionPageResponse
    fun updateTransaction(token: String, transactionId: String, updatedUserTransactions: TransactionRequest): TransactionRequest
    fun deleteTransaction(token: String, transactionId: String)
}