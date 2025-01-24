package org.example.financemanager.utils


import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.MalformedJwtException
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.security.Keys
import org.springframework.stereotype.Component
import java.util.*

@Component
class JwtUtil {
    private val secretKey = "yourSuperSecretKeyWhichShouldBeAtLeast32BytesLong!"
    private val expirationMs = 3600000 // 1 hour

    private val key = Keys.hmacShaKeyFor(secretKey.toByteArray())

    fun generateToken(email: String): String {
        return Jwts.builder()
            .setSubject(email)
            .setIssuedAt(Date())
            .setExpiration(Date(System.currentTimeMillis() + expirationMs))
            .signWith(key)
            .compact()
    }
    fun validateTokenAndGetUserId(token: String): String {
        val tokenWithoutBearer = token.replace("Bearer ", "").trim()
        try {
            val claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(tokenWithoutBearer)

            return claims.body.subject ?: throw IllegalArgumentException("Invalid token: missing user ID")

        } catch (e: ExpiredJwtException) {
            throw IllegalArgumentException("Token has expired")
        } catch (e: MalformedJwtException) {
            throw IllegalArgumentException("Malformed token")
        } catch (e: Exception) {
            throw IllegalArgumentException("Invalid token")
        }
    }
}