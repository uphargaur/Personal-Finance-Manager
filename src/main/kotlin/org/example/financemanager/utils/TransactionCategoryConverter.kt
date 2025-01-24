package org.example.financemanager.utils

import org.example.financemanager.domain.transactions.TransactionCategory

object  TransactionCategoryConverter {

    fun convertToTransactionCategory(category: String): TransactionCategory {
        return when (category.uppercase()) {
            "FOOD" -> TransactionCategory.FOOD
            "TRAVEL" -> TransactionCategory.TRAVEL
            "STUDY" -> TransactionCategory.STUDY
            "ENTERTAINMENT" -> TransactionCategory.ENTERTAINMENT
            "MEDICAL" -> TransactionCategory.MEDICAL
            "RENT" -> TransactionCategory.RENT
            "UTILITIES" -> TransactionCategory.UTILITIES
            else -> TransactionCategory.OTHERS
        }
    }
}
