package org.example.financemanager.presentation


import org.example.financemanager.application.manageTransaction.ManageTransactions
import org.example.financemanager.application.userServices.UserService
import org.example.financemanager.domain.Request.LoginRequest
import org.example.financemanager.domain.Request.RegisterRequest
import org.example.financemanager.domain.Request.TransactionRequest
import org.example.financemanager.utils.JwtUtil
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*





@CrossOrigin("*")
@RestController
@RequestMapping("/auth")
class AuthController(
    private val userService: UserService,  // Interface Injection
    private val jwtUtil: JwtUtil,
) {

    @PostMapping("/register")
    fun registerUser(@RequestBody registerRequest: RegisterRequest): ResponseEntity<String> {
        try {
            userService.registerUser(registerRequest.name, registerRequest.email, registerRequest.password)
            return ResponseEntity.ok("Registration successful")
        } catch (e: Exception) {
            return ResponseEntity.status(400).body(e.message ?: "Registration failed")
        }
    }

    @PostMapping("/login")
    fun loginUser(@RequestBody loginRequest: LoginRequest): ResponseEntity<String> {
        try {
            val user = userService.authenticateUser(loginRequest.email, loginRequest.password)
            val token = jwtUtil.generateToken(user.email)
            return ResponseEntity.ok(token)
        } catch (e: Exception) {
            return ResponseEntity.status(400).body("Invalid login details")
        }
    }
}
