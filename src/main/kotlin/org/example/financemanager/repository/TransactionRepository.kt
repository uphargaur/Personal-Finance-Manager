package org.example.financemanager.repository

import org.example.financemanager.domain.transactions.UserTransactions
import org.springframework.data.mongodb.repository.MongoRepository

interface TransactionRepository : MongoRepository<UserTransactions, String> {
    fun findByUserId(userId: String): UserTransactions?
}
