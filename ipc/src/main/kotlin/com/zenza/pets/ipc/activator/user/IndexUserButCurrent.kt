package com.zenza.pets.ipc.activator.user

import com.zenza.pets.store.domain.User
import com.zenza.pets.store.repository.UserRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class IndexUserButCurrent(val userRepository: UserRepository) {

    @Transactional
    fun fetchAllUsersButCurrent(
        username: String,
        page: Int,
        size: Int
    ): Page<User>? {
        return userRepository
            .findAllUsersExceptCurrent(
                username,
                PageRequest.of(
                        page,
                        size,
                        Sort.by("created_at").descending()
                    )
            )
    }

}