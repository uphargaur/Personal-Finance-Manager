package org.example.financemanager.repository

import org.example.financemanager.domain.saving.SavingsGoal
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface SavingsGoalRepository : MongoRepository<SavingsGoal, String> {
    fun findByUserId(userId: Long): List<SavingsGoal>
    fun existsByUserIdAndId(userId: Long, savingsGoalId: String): Boolean
}
