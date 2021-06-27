package com.zenza.pets.store.repository

import com.zenza.pets.store.domain.User
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

@Repository
interface UserRepository: JpaRepository<User, Long?> {
    fun findByEmail(email: String): User?
    fun findByUsername(email: String): User?

    @Query("SELECT * FROM users u WHERE u.username != ?1", nativeQuery = true)
    fun findAllUsersExceptCurrent(username: String, pageable: Pageable): Page<User>
}