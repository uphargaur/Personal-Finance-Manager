package org.example.financemanager.presentation

import org.example.financemanager.application.savingsGoalServices.SavingsGoalService
import org.example.financemanager.domain.saving.SavingsGoal


import org.example.financemanager.utils.JwtUtil
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@CrossOrigin("*")
@RestController
@RequestMapping("/savings-goals")
class SavingsGoalController(
    private val savingsGoalService: SavingsGoalService,
    private val jwtUtil: JwtUtil
) {

    @PostMapping("/create")
    fun createSavingsGoal(
        @RequestHeader("Authorization") token: String,
        @RequestParam("targetAmount") targetAmount: Double,
        @RequestParam("targetDate") targetDate: String
    ): ResponseEntity<SavingsGoal> {
        try {
            val userId = jwtUtil.validateTokenAndGetUserId(token)
            val savingsGoal = savingsGoalService.createSavingsGoal(userId.toLong(), targetAmount, targetDate)
            return ResponseEntity.ok(savingsGoal)
        } catch (e: Exception) {
            return ResponseEntity.badRequest().build()
        }
    }

    @GetMapping("/list")
    fun getAllSavingsGoals(
        @RequestHeader("Authorization") token: String
    ): ResponseEntity<List<SavingsGoal>> {
        try {
            val userId = jwtUtil.validateTokenAndGetUserId(token)
            val savingsGoals = savingsGoalService.getAllSavingsGoals(userId.toLong())
            return ResponseEntity.ok(savingsGoals)
        } catch (e: Exception) {
            return ResponseEntity.notFound().build()
        }
    }

    @PutMapping("/update/{goalId}")
    fun updateCurrentAmount(
        @RequestHeader("Authorization") token: String,
        @PathVariable goalId: Long,
        @RequestParam("amount") amount: Double
    ): ResponseEntity<SavingsGoal> {
        try {
            val userId = jwtUtil.validateTokenAndGetUserId(token)
            val updatedGoal = savingsGoalService.updateCurrentAmount(userId.toLong(), goalId.toString(), amount)
            return ResponseEntity.ok(updatedGoal)
        } catch (e: Exception) {
            return ResponseEntity.badRequest().build()
        }
    }

    @GetMapping("/status/{goalId}")
    fun checkGoalStatus(
        @RequestHeader("Authorization") token: String,
        @PathVariable goalId: Long
    ): ResponseEntity<SavingsGoal> {
        try {
            val userId = jwtUtil.validateTokenAndGetUserId(token)
            val goalStatus = savingsGoalService.checkGoalStatus(userId.toLong(), goalId.toString())
            return ResponseEntity.ok(goalStatus)
        } catch (e: Exception) {
            return ResponseEntity.notFound().build()
        }
    }
}
