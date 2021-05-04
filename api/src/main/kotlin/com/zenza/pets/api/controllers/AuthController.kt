package com.zenza.pets.api.controllers

import com.zenza.pets.api.domain.ApiResponse
import com.zenza.pets.store.domain.User
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RequestMapping("/auth")
@RestController
class AuthController(
) {

    @PostMapping("/login")
    fun login(@RequestBody user: User): ResponseEntity<ApiResponse> {
        try {

            return ResponseEntity.ok()
                    .body(ApiResponse(
                            "200",
                            "login successful",
                            null
                    ))
        } catch (e: BadCredentialsException) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build()
        }
    }
}