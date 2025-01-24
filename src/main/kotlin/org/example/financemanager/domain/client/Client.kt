package org.example.financemanager.domain.client

import jakarta.persistence.*
import org.springframework.data.mongodb.core.mapping.Document
import java.util.*

@Document(collection = "clients")
data class Client(
    @Id
    val id: String? = null,
    val name: String,
    val email: String,
    val password: String,
    val createdAt: Date = Date()
)
