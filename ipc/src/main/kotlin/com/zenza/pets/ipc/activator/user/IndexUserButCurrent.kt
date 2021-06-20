package com.zenza.pets.ipc.activator.user

import com.zenza.pets.store.domain.User
import com.zenza.pets.store.repository.UserRepository
import org.springframework.stereotype.Service

@Service
class IndexUserButCurrent(val userRepository: UserRepository) {

    fun fetchAllUsersButCurrent(username: String): List<User>? {
        return userRepository.findAllUsersExceptCurrent(username)
    }

}