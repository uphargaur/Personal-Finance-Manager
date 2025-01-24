package org.example.financemanager.domain.transactions
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "transactions")
data class UserTransactions(
    @Id
    val userId : String,
    val year: Int,
    val month: Int,
    val transactions: MutableMap<TransactionCategory, MutableList<TransactionDetail>>
)

