package org.example.financemanager.repository

import org.example.financemanager.domain.transactions.UserTransactions
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.data.mongodb.repository.Query

interface TransactionRepository : MongoRepository<UserTransactions, String> {
    fun findByUserId(userId: String): UserTransactions?

    fun findByUserIdAndYearAndMonth(userId: String, year: Int, month: Int): List<UserTransactions>
}
