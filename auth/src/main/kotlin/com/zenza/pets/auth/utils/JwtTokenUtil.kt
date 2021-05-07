package com.zenza.pets.auth.utils

import com.zenza.pets.store.domain.User
import io.jsonwebtoken.*
import lombok.RequiredArgsConstructor
import org.springframework.stereotype.Component
import java.util.*
import java.util.logging.Level
import java.util.logging.Logger

@Component
@RequiredArgsConstructor
class JwtTokenUtil {

    private val jwtSecret = "zdtlD3JK56m6wTTgsNFhqzjqP"
    private val jwtIssuer = "zenza.io"

    private val _TAG = this::class.java.name

    fun generateAccessToken(user: User): String {
        return Jwts.builder()
                .setSubject("${user.id},${user.email}")
                .setIssuer(jwtIssuer)
                .setIssuedAt(Date())
                .setExpiration(Date(System.currentTimeMillis() + 7 * 24 * 60 * 60 * 1000))
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact()
    }

    fun getUserId(token: String): String {
        val claims = Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .body

        return claims.subject.split(",")[0]
    }

    fun getUsername(token: String): String {
        val claims = Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .body

        return claims.subject.split(",")[1]
    }

    fun getExpirationDate(token: String): Date {
        val claims = Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .body

        return claims.expiration
    }

    fun validate(token: String): Boolean {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token)
            return true
        } catch (e: SignatureException) {
            Logger.getLogger(_TAG).log(Level.SEVERE, "Invalid JWT Signature - {${e.message}}")
        } catch (e: MalformedJwtException) {
            Logger.getLogger(_TAG).log(Level.SEVERE, "Invalid JWT Token - {${e.message}}")
        } catch (e: ExpiredJwtException) {
            Logger.getLogger(_TAG).log(Level.SEVERE, "Expired JWT Token - {${e.message}}")
        } catch (e: UnsupportedJwtException) {
            Logger.getLogger(_TAG).log(Level.SEVERE, "Unsupported JWT Token - {${e.message}}")
        } catch (e: IllegalArgumentException) {
            Logger.getLogger(_TAG).log(Level.SEVERE, "JWT claims string is empty - {${e.message}}")
        }

        return false
    }
}




















