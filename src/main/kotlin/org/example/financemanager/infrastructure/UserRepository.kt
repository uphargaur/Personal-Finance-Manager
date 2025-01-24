package org.example.financemanager.infrastructure

import org.example.financemanager.domain.Client
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : MongoRepository<Client, String> {
    fun findByEmail(email: String): Client?
    fun existsByEmail(email: String): Boolean
}