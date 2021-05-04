package com.zenza.pets.store.repository

import com.zenza.pets.store.domain.User
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface UserRepository: MongoRepository<User, String> {
    fun findByEmail(email: String): User?
}