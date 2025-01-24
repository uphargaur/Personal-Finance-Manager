package org.example.financemanager.application.manageTransaction

import org.example.financemanager.domain.Request.TransactionRequest
import org.example.financemanager.domain.transactions.TransactionDetail
import org.example.financemanager.domain.transactions.TransactionPageResponse
import org.example.financemanager.domain.transactions.UserTransactions
import org.example.financemanager.repository.TransactionRepository
import org.example.financemanager.utils.JwtUtil
import org.example.financemanager.utils.TransactionCategoryConverter
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class ManageTransactionsImpl(
    private val transactionsRepository: TransactionRepository,
    private val jwtUtil: JwtUtil
) : ManageTransactions {

    override fun addTransaction(token: String, transactionRequest: TransactionRequest): TransactionRequest {
        val userId = jwtUtil.validateTokenAndGetUserId(token)
        val userTransactions = transactionsRepository.findByUserId(userId)
            ?: UserTransactions(userId = userId, transactions = mutableMapOf())
        val category = TransactionCategoryConverter.convertToTransactionCategory(transactionRequest.category)
        val transactionDetail = TransactionDetail(
            transactionId = UUID.randomUUID().toString(),
            amount = transactionRequest.amount,
            date = transactionRequest.date,
            description = transactionRequest.description
        )
        userTransactions.transactions.getOrPut(category) { mutableListOf() }.add(transactionDetail)
        transactionsRepository.save(userTransactions)
        return transactionRequest.copy(id = transactionDetail.transactionId)
    }

    override fun getTransactionsWithPagination(token: String, page: Int, size: Int, type: String?): TransactionPageResponse {
        val userId = jwtUtil.validateTokenAndGetUserId(token)
        val userTransactions = transactionsRepository.findByUserId(userId)
            ?: return TransactionPageResponse(
                pageNo = page,
                pageSize = size,
                totalPages = 0,
                totalTransactions = 0,
                transactions = emptyList()
            )

        val filteredTransactions = if (!type.isNullOrEmpty()) {
            userTransactions.transactions
                .filterKeys { it.name.equals(type, ignoreCase = true) }
        } else {
            userTransactions.transactions
        }
        val allTransactions = filteredTransactions.flatMap { (category, details) ->
            details.map { detail ->
                TransactionRequest(
                    id = detail.transactionId,
                    userId = userId,
                    category = category.name,
                    amount = detail.amount,
                    date = detail.date,
                    description = detail.description
                )
            }
        }.sortedByDescending { it.date }

        val totalTransactions = allTransactions.size
        val totalPages = if (totalTransactions % size == 0) {
            totalTransactions / size
        } else {
            (totalTransactions / size) + 1
        }

        val paginatedTransactions = allTransactions
            .drop(page * size)
            .take(size)

        return TransactionPageResponse(
            pageNo = page,
            pageSize = size,
            totalPages = totalPages,
            totalTransactions = totalTransactions,
            transactions = paginatedTransactions
        )
    }

    override fun updateTransaction(token: String, transactionId: String, updatedTransaction: TransactionRequest): TransactionRequest {
        val userId = jwtUtil.validateTokenAndGetUserId(token)
        val userTransactions = transactionsRepository.findByUserId(userId)
            ?: throw IllegalArgumentException("No transactions found for user")

        val category = TransactionCategoryConverter.convertToTransactionCategory(updatedTransaction.category)

        userTransactions.transactions.forEach { (cat, details) ->
            val index = details.indexOfFirst { it.transactionId == transactionId }

            if (index != -1) {
                val updatedDetail = details[index].copy(
                    amount = updatedTransaction.amount,
                    date = updatedTransaction.date,
                    description = updatedTransaction.description
                )

                details[index] = updatedDetail

                if (cat != category) {
                    details.removeAt(index)
                    userTransactions.transactions.computeIfAbsent(category) { mutableListOf() }.add(updatedDetail)
                }

                transactionsRepository.save(userTransactions)
                return updatedTransaction
            }
        }

        throw IllegalArgumentException("Transaction not found")
    }

    override fun deleteTransaction(token: String, transactionId: String) {
        val userId = jwtUtil.validateTokenAndGetUserId(token)
        val userTransactions = transactionsRepository.findByUserId(userId)
            ?: throw IllegalArgumentException("No transactions found for user")

        userTransactions.transactions.forEach { (_, details) ->
            val index = details.indexOfFirst { it.transactionId == transactionId }

            if (index != -1) {
                details.removeAt(index)
                transactionsRepository.save(userTransactions)
                return
            }
        }

        throw IllegalArgumentException("Transaction not found")
    }
}
