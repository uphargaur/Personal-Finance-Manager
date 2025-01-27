package org.example.financemanager.application.savingsGoalServices

import org.example.financemanager.domain.saving.SavingsGoal


interface SavingsGoalService {
    fun createSavingsGoal(userId: Long, targetAmount: Double, targetDate: String): SavingsGoal
    fun getAllSavingsGoals(userId: Long): List<SavingsGoal>
    fun updateCurrentAmount(userId: Long, savingsGoalId: String, amount: Double): SavingsGoal
    fun checkGoalStatus(userId: Long, savingsGoalId: String): SavingsGoal
}
