package org.example.financemanager.application.userServices


import org.example.financemanager.domain.client.Client

interface UserService {
    fun registerUser(name: String, email: String, password: String): Client
    fun authenticateUser(email: String, password: String): Client
}
