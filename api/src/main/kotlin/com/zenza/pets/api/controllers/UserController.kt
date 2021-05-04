package com.zenza.pets.api.controllers

import com.zenza.pets.api.domain.ApiResponse
import com.zenza.pets.store.repository.UserRepository
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.lang.Exception

@RequestMapping("/user")
@RestController
class UserController(val userRepository: UserRepository) {

    @GetMapping("/all")
    fun all(): ApiResponse = try {
            ApiResponse(
                    "200",
                    "All Users Fetched Successfully",
                    userRepository.findAll()
            )
        } catch (e: Exception) {
            ApiResponse(
                    "99",
                    "Unable to fetch all users",
                    null
            )
        }


}