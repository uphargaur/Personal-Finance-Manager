package org.example.financemanager.presentation


import org.example.financemanager.application.UserService.UserService
import org.example.financemanager.domain.Request.LoginRequest
import org.example.financemanager.domain.Request.RegisterRequest
import org.example.financemanager.utils.JwtUtil
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*



@RestController
@RequestMapping("/auth")
class AuthController(
    private val userService: UserService,
    private val jwtUtil: JwtUtil
) {
    @PostMapping("/register")
    fun registerUser(@RequestBody registerRequest: RegisterRequest): ResponseEntity<String> {
        userService.registerUser(registerRequest.name, registerRequest.email, registerRequest.password)
        return ResponseEntity.ok("Registration successful")
    }

    @PostMapping("/login")
    fun loginUser(@RequestBody loginRequest: LoginRequest): ResponseEntity<String> {
        val user = userService.authenticateUser(loginRequest.email, loginRequest.password)
        val token = jwtUtil.generateToken(user.email)
        return ResponseEntity.ok(token)
    }
}