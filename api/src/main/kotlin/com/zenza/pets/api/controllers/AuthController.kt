package com.zenza.pets.api.controllers

import com.zenza.pets.api.domain.ApiResponse
import com.zenza.pets.auth.utils.JwtTokenUtil
import com.zenza.pets.ipc.activator.user.CreateUser
import com.zenza.pets.store.domain.User
import com.zenza.pets.store.repository.UserRepository
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.*
import java.util.logging.Level
import java.util.logging.Logger

@CrossOrigin("http://localhost:3000")
@RequestMapping("/auth")
@RestController
class AuthController(
        private val authenticationManager: AuthenticationManager,
        private val jwtTokenUtil: JwtTokenUtil,
        private val userRepository: UserRepository,
        private val createUser: CreateUser
) {

    private val TAG = "AuthController"

    @Transactional
    @PostMapping("/login")
    fun login(@RequestBody user: User): ResponseEntity<ApiResponse> {
        try {

            authenticationManager.authenticate(UsernamePasswordAuthenticationToken(user.username, user.password))

            val authenticatedUser = userRepository.findByUsername(user.username!!)

            val token = jwtTokenUtil.generateAccessToken(authenticatedUser!!)

            Logger.getLogger(TAG).log(Level.WARNING, "token: $token")

            return ResponseEntity.ok()
                    .header(
                            HttpHeaders.AUTHORIZATION,
                            token
                    )
                    .body(ApiResponse(
                            "200",
                            "login successful",
                            hashMapOf(
                                "role" to authenticatedUser.roles!!.toList()[0].name,
                                "access_token" to token
                            )
                    ))
        } catch (e: BadCredentialsException) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build()
        }
    }

    @PostMapping("/register")
    fun register(@RequestBody user: User): ResponseEntity<ApiResponse> {
        try {
            val _user = createUser.save(user)

            val token = jwtTokenUtil.generateAccessToken(_user!!)

            Logger.getLogger(TAG).log(Level.WARNING, "token: $token")

            return ResponseEntity.ok()
                    .body(ApiResponse(
                            "200",
                            "registration successful",
                            hashMapOf("access_token" to token)
                    ))
        } catch (e: Exception) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(ApiResponse(
                            "99",
                            "registration failed",
                            e.message
                    ))
        }
    }
}