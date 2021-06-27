package com.zenza.pets.ipc.activator.user

import com.zenza.pets.ipc.utils.exceptions.MissingUserException
import com.zenza.pets.store.domain.User
import com.zenza.pets.store.repository.UserRepository
import org.springframework.stereotype.Service

@Service
class ActivateUser(private val userRepository: UserRepository) {
    fun activate(id: Long): User {
        val user: User? = userRepository.findById(id).get()

        return user?.let {
            it.status = 1
            userRepository.save(it)
        }?: run {
            throw MissingUserException("id", id.toString())
        }
    }
}