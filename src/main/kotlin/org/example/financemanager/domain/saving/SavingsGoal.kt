package org.example.financemanager.domain.saving

data class SavingsGoal(
    val id: Long? = null,
    val userId: Long,
    val targetAmount: Double,
    val targetDate: String,
    var currentAmount: Double = 0.0,
    var goalStatus: GoalStatus = GoalStatus.IN_PROGRESS
)

enum class GoalStatus {
    IN_PROGRESS,
    COMPLETED,
    EXPIRED
}