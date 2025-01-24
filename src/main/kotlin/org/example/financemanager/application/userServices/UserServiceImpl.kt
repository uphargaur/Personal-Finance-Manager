package org.example.financemanager.application.userServices


import org.example.financemanager.domain.client.Client
import org.example.financemanager.repository.UserRepository
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service

@Service
class UserServiceImpl(
    private val userRepository: UserRepository,
    private val passwordEncoder: BCryptPasswordEncoder
) : UserService {

    override fun registerUser(name: String, email: String, password: String): Client {
        println("user $name")
        if (userRepository.existsByEmail(email)) {
            throw IllegalArgumentException("Email already registered")
        }
        val encodedPassword = passwordEncoder.encode(password)
        val client = Client(name = name, email = email, password = encodedPassword)
        return userRepository.save(client)
    }

    override fun authenticateUser(email: String, password: String): Client {
        val user = userRepository.findByEmail(email)
            ?: throw IllegalArgumentException("Invalid email or password")
        if (!passwordEncoder.matches(password, user.password)) {
            throw IllegalArgumentException("Invalid email or password")
        }
        return user
    }
}
