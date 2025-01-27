package org.example.financemanager.application.savingsGoalServices

import org.example.financemanager.domain.saving.GoalStatus
import org.example.financemanager.domain.saving.SavingsGoal
import org.example.financemanager.repository.SavingsGoalRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Service
class SavingsGoalServiceImpl @Autowired constructor(
    private val savingsGoalRepository: SavingsGoalRepository
) : SavingsGoalService {

    override fun createSavingsGoal(userId: Long, targetAmount: Double, targetDate: String): SavingsGoal {
        val savingsGoal = SavingsGoal(
            userId = userId,
            targetAmount = targetAmount,
            targetDate = targetDate
        )
        return savingsGoalRepository.save(savingsGoal)
    }

    override fun getAllSavingsGoals(userId: Long): List<SavingsGoal> {
        return savingsGoalRepository.findByUserId(userId)
    }

    override fun updateCurrentAmount(userId: Long, savingsGoalId: String, amount: Double): SavingsGoal {
        val goal = savingsGoalRepository.findById(savingsGoalId).orElseThrow { IllegalArgumentException("Goal not found.") }
        if (goal.userId == userId) {
            goal.currentAmount += amount
            if (goal.currentAmount >= goal.targetAmount) {
                goal.goalStatus = GoalStatus.COMPLETED
            }
            return savingsGoalRepository.save(goal)
        } else {
            throw IllegalArgumentException("Unauthorized access to this goal.")
        }
    }

    override fun checkGoalStatus(userId: Long, savingsGoalId: String): SavingsGoal {
        val goal = savingsGoalRepository.findById(savingsGoalId).orElseThrow { IllegalArgumentException("Goal not found.") }
        if (goal.userId == userId) {
            // Converting targetDate (String) to LocalDate
            val targetDate = LocalDate.parse(goal.targetDate, DateTimeFormatter.ISO_DATE)

            if (targetDate.isBefore(LocalDate.now())) {
                goal.goalStatus = GoalStatus.EXPIRED
            }
            return goal
        } else {
            throw IllegalArgumentException("Unauthorized access to this goal.")
        }
    }
}
