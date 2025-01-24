package org.example.financemanager.application.ManageTransactions

import org.example.financemanager.domain.Transaction

interface ManageTransactions {
    fun createTransaction(transaction: Transaction)
    fun getTransactionsByUser(userId: String): List<Transaction>
}