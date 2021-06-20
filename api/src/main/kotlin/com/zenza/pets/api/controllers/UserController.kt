package com.zenza.pets.api.controllers

import com.zenza.pets.api.domain.ApiResponse
import com.zenza.pets.ipc.activator.user.IndexUserButCurrent
import com.zenza.pets.store.repository.UserRepository
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.logging.Level
import java.util.logging.Logger
import javax.transaction.Transactional


@RequestMapping("/user")
@RestController
class UserController(
        val userRepository: UserRepository,
        val indexUserButCurrent: IndexUserButCurrent
) {

    @Transactional
    @PreAuthorize("hasAuthority('READ_ALL_USERS_PRIVILEGE')")
    @CrossOrigin(origins = ["http://localhost:3000"])
    @GetMapping("/")
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

    @Transactional
    @PreAuthorize("hasAuthority('WRITE_PET_PRIVILEGE')")
    @CrossOrigin(origins = ["http://localhost:3000"])
    @GetMapping("/all")
    fun fetchAllButCurrent(): ApiResponse = try {
        val principal = SecurityContextHolder
                            .getContext()
                            .authentication
                            .principal

        var username = ""

        if (principal is UserDetails) {
            username = principal.username
        } else {
            username = principal.toString()
        }

        Logger.getLogger(this.javaClass.name).log(Level.WARNING, "username: $username")

        ApiResponse(
                "200",
                "Fetched users successfully",
                indexUserButCurrent.fetchAllUsersButCurrent(username)
        )
    } catch (e: Exception) {
        ApiResponse(
                "99",
                "Unable to fetch all users",
                "Cause: ${e.message}"
        )
    }


}