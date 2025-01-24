package org.example.financemanager.application.UserService

import org.example.financemanager.domain.Client
import org.example.financemanager.infrastructure.UserRepository
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service

@Service
class UserService(
    private val userRepository: UserRepository,
    private val passwordEncoder: BCryptPasswordEncoder
) {
    fun registerUser(name: String, email: String, password: String): Client {
        println("Registering user $name")  // Change this line
        if (userRepository.existsByEmail(email)) {
            throw IllegalArgumentException("Email already registered")
        }
        val encodedPassword = passwordEncoder.encode(password)
        val client = Client(name = name, email = email, password = encodedPassword)
        return userRepository.save(client)
    }

    fun authenticateUser(email: String, password: String): Client {
        val user = userRepository.findByEmail(email)
            ?: throw IllegalArgumentException("Invalid email or password")
        if (!passwordEncoder.matches(password, user.password)) {
            throw IllegalArgumentException("Invalid email or password")
        }
        return user
    }
}
