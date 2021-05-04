package com.zenza.pets.api.controllers

import com.zenza.pets.api.domain.ApiResponse
import com.zenza.pets.auth.utils.JwtTokenUtil
import com.zenza.pets.store.domain.User
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

//@Api(tags = "Authentication")
@RequestMapping("/auth")
@RestController
class AuthController(
        val authenticationManager: AuthenticationManager,
        val jwtTokenUtil: JwtTokenUtil
) {

    @PostMapping("/login")
    fun login(@RequestBody user: User): ResponseEntity<ApiResponse> {
        try {
            val authenticate = authenticationManager.authenticate(
                    UsernamePasswordAuthenticationToken(user.email, user.password)
            )

            val authenticatedUser = authenticate.principal as User

            return ResponseEntity.ok()
                    .header(HttpHeaders.AUTHORIZATION, jwtTokenUtil.generateAccessToken(authenticatedUser))
                    .body(ApiResponse(
                            "200",
                            "login successful",
                            authenticatedUser
                    ))
        } catch (e: BadCredentialsException) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build()
        }
    }
}