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
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RequestMapping("/auth")
@RestController
class AuthController(
        private val authenticationManager: AuthenticationManager,
        private val jwtTokenUtil: JwtTokenUtil,
        private val userRepository: UserRepository,
        private val createUser: CreateUser
) {

    @PostMapping("/login")
    fun login(@RequestBody user: User): ResponseEntity<ApiResponse> {
        try {

            authenticationManager.authenticate(UsernamePasswordAuthenticationToken(user.username, user.password))

            val authenticatedUser = userRepository.findByUsername(user.username!!)

            return ResponseEntity.ok()
                    .header(
                            HttpHeaders.AUTHORIZATION,
                            jwtTokenUtil.generateAccessToken(authenticatedUser!!),
                            "token"
                    )
                    .body(ApiResponse(
                            "200",
                            "login successful",
                            null
                    ))
        } catch (e: BadCredentialsException) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build()
        }
    }

    @PostMapping("/register")
    fun register(@RequestBody user: User): ResponseEntity<ApiResponse> {
        try {
            val _user = createUser.save(user)

            return ResponseEntity.ok()
                    .header(
                            HttpHeaders.AUTHORIZATION,
                            jwtTokenUtil.generateAccessToken(_user!!)
                    ).body(ApiResponse(
                            "200",
                            "registration successful",
                            _user
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