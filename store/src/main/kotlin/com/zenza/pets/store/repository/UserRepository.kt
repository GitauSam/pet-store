package com.zenza.pets.store.repository

import com.zenza.pets.store.domain.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository: JpaRepository<User, Long?> {
    fun findByEmail(email: String): User?
    fun findByUsername(email: String): User?
}