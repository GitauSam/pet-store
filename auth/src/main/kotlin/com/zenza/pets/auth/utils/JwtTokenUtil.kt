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

    private final val jwtSecret = "zdtlD3JK56m6wTTgsNFhqzjqP"
    private  final val jwtIssuer = "pets.io"
    private final val TAG = this::class.java.name

    fun generateAccessToken(user: User): String {
        return Jwts.builder()
                .setSubject("${user.id},${user.email}")
                .setIssuer(jwtIssuer)
                .setIssuedAt(Date())
                .setExpiration(Date(System.currentTimeMillis() + 7 * 24 * 60 * 60 * 1000))
                .signWith(SignatureAlgorithm.ES512, jwtSecret)
                .compact()
    }

    fun getUserId(token: String): String {
        val claims = Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .body
        return claims.subject.split(",")[0]
    }

    fun getEmail(token: String): String {
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
            Logger.getLogger(TAG).log(Level.SEVERE, "Invalid JWT signature - {${e.message}}")
        } catch (e: MalformedJwtException) {
            Logger.getLogger(TAG).log(Level.SEVERE, "Invalid JWT token - {${e.message}}")
        } catch (e: ExpiredJwtException) {
            Logger.getLogger(TAG).log(Level.SEVERE, "Expired JWT token - {${e.message}}")
        } catch (e: UnsupportedJwtException) {
            Logger.getLogger(TAG).log(Level.SEVERE, "Unsupported JWT token - {${e.message}}")
        } catch (e: IllegalArgumentException) {
            Logger.getLogger(TAG).log(Level.SEVERE, "JWT claims strinf is empty - {${e.message}}")
        }

        return false
    }
}



















