package com.zenza.pets.api.controllers

import com.zenza.pets.api.domain.ApiResponse
import com.zenza.pets.ipc.activator.user.ActivateUser
import com.zenza.pets.ipc.activator.user.DeactivateUser
import com.zenza.pets.ipc.activator.user.IndexUserButCurrent
import com.zenza.pets.store.repository.UserRepository
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.web.bind.annotation.*
import java.util.logging.Level
import java.util.logging.Logger
import javax.transaction.Transactional


@RequestMapping("/user")
@RestController
class UserController(
        val userRepository: UserRepository,
        val indexUserButCurrent: IndexUserButCurrent,
        val deactivateUser: DeactivateUser,
        val activateUser: ActivateUser
) {

    @PreAuthorize("hasAuthority('READ_ALL_USERS_PRIVILEGE')")
    @GetMapping("/")
    fun all(
        @RequestParam("page", defaultValue = "1") page: Int,
        @RequestParam("size", defaultValue = "5") size: Int
    ): ApiResponse = try {
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

    @PreAuthorize("hasAuthority('READ_ALL_USERS_PRIVILEGE')")
    @GetMapping("/all")
    fun fetchAllButCurrent(
        @RequestParam("page", defaultValue = "1") page: Int,
        @RequestParam("size", defaultValue = "5") size: Int
    ): ApiResponse = try {
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
                indexUserButCurrent
                    .fetchAllUsersButCurrent(
                        username,
                        page,
                        size
                    )
        )
    } catch (e: Exception) {
        ApiResponse(
                "99",
                "Unable to fetch all users",
                "Cause: ${e.message}"
        )
    }

    @PreAuthorize("hasAuthority('WRITE_USER_PRIVILEGE')")
    @GetMapping("/delete/{id}")
    fun deactivateUser(@PathVariable("id") id: Long): ResponseEntity<ApiResponse> = try {
        val user = deactivateUser.deactivate(id)
        ResponseEntity
                .status(HttpStatus.OK)
                .body(
                        ApiResponse(
                                "200",
                                "Successfully deactivated user",
                                user
                        )
                )
    } catch (e: Exception) {
        ResponseEntity
                .status(HttpStatus.EXPECTATION_FAILED)
                .body(ApiResponse(
                        "99",
                        "Error occurred while deactivating user",
                                e.message
                ))

    }

    @PreAuthorize("hasAuthority('WRITE_USER_PRIVILEGE')")
    @GetMapping("/activate/{id}")
    fun activateUser(@PathVariable("id") id: Long): ResponseEntity<ApiResponse> = try {
        val user = activateUser.activate(id)
        ResponseEntity
                .status(HttpStatus.OK)
                .body(
                        ApiResponse(
                                "200",
                                "Successfully activated user",
                                user
                        )
                )
    } catch (e: Exception) {
        ResponseEntity
                .status(HttpStatus.EXPECTATION_FAILED)
                .body(ApiResponse(
                        "99",
                        "Error occurred while activating user",
                        e.message
                ))

    }


}