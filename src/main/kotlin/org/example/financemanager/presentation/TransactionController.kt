package org.example.financemanager.presentation


import org.example.financemanager.application.manageTransaction.ManageTransactions
import org.example.financemanager.application.report.ReportService
import org.example.financemanager.domain.Report.ReportResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.example.financemanager.domain.Request.TransactionRequest
import org.example.financemanager.domain.transactions.TransactionPageResponse
import org.example.financemanager.utils.JwtUtil

@RestController
@RequestMapping("/transactions")
class
TransactionController(
    private val manageTransactions: ManageTransactions,
    private val reportService: ReportService,
    private val jwtUtil: JwtUtil
) {

    @PostMapping
    fun addTransaction(
        @RequestHeader("Authorization") token: String,
        @RequestBody transactionRequest: TransactionRequest
    ): ResponseEntity<TransactionRequest> {
        try {
            val transaction = manageTransactions.addTransaction(token, transactionRequest)
            return ResponseEntity.ok(transaction)
        } catch (ex: Exception) {
            return ResponseEntity.badRequest().build()
        }
    }

    @GetMapping("/generate")
    fun generateReport(
        @RequestHeader("Authorization") token: String,
        @RequestParam("month", required = false) month: Int?,
        @RequestParam("year", required = false) year: Int?
    ): ResponseEntity<ReportResponse> {
        return try {
            val userId = jwtUtil.validateTokenAndGetUserId(token)
            val report = reportService.getReport(userId, month, year)
            ResponseEntity.ok(report)
        } catch (e: IllegalArgumentException) {
            ResponseEntity.badRequest().body(null)
        } catch (e: Exception) {
            ResponseEntity.internalServerError().build()
        }
    }

    @GetMapping
    fun getTransactions(
        @RequestHeader("Authorization") token: String,
        @RequestParam("page", defaultValue = "0") page: Int,
        @RequestParam("size", defaultValue = "10") size: Int,
        @RequestParam("type", required = false) type: String?
    ): ResponseEntity<TransactionPageResponse> {
        try {
            val transactions = manageTransactions.getTransactionsWithPagination(token, page, size,type)
            return ResponseEntity.ok(transactions)
        } catch (ex: Exception) {
            return ResponseEntity.notFound().build()
        }
    }

    @PutMapping("/{transactionId}")
    fun updateTransaction(
        @RequestHeader("Authorization") token: String,
        @PathVariable transactionId: String,
        @RequestBody updatedTransaction: TransactionRequest
    ): ResponseEntity<TransactionRequest> {
        return try {
            val updatedTxn = manageTransactions.updateTransaction(token, transactionId, updatedTransaction)
            ResponseEntity.ok(updatedTxn)
        } catch (ex: IllegalArgumentException) {
            ResponseEntity.notFound().build()
        } catch (ex: Exception) {
            ResponseEntity.badRequest().build()
        }
    }

    @DeleteMapping("/{transactionId}")
    fun deleteTransaction(
        @RequestHeader("Authorization") token: String,
        @PathVariable transactionId: String
    ): ResponseEntity<Void> {
        return try {
            manageTransactions.deleteTransaction(token, transactionId)
            ResponseEntity.noContent().build()
        } catch (ex: IllegalArgumentException) {
            ResponseEntity.notFound().build()
        } catch (ex: Exception) {
            ResponseEntity.badRequest().build()
        }
    }
}
