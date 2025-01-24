package org.example.financemanager.application.ManageTransactions

import org.example.financemanager.domain.Transaction

class ManageTransactionsImpl : ManageTransactions {
    private val transactions = mutableListOf<Transaction>()

    override fun createTransaction(transaction: Transaction) {
        transactions.add(transaction) // Replace with repository interaction
    }

    override fun getTransactionsByUser(userId: String): List<Transaction> {
        return transactions.filter { it.userId == userId }
    }
}