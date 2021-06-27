package com.zenza.pets.ipc.activator.user

import com.zenza.pets.ipc.utils.exceptions.MissingUserException
import com.zenza.pets.store.domain.User
import com.zenza.pets.store.repository.UserRepository
import org.springframework.stereotype.Service
import java.sql.Timestamp
import java.time.Instant

@Service
class DeactivateUser(
        private val userRepository: UserRepository
) {
    fun deactivate(id: Long): User {
        val user: User? = userRepository.findById(id).get()

        user?.let {
            it.status = 0
            it.deletedAt = Timestamp.from(Instant.now())
            return userRepository.save(it)
        }?: run {
            throw MissingUserException("id", id.toString())
        }
    }
}