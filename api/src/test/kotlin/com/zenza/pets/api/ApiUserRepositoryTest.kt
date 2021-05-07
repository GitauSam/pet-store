package com.zenza.pets.api

import com.zenza.pets.store.repository.UserRepository
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class ApiUserRepositoryTest {

    @Autowired lateinit var userRepository: UserRepository

    @Test
    fun findAll() {
        val users = userRepository.findAll()

        println(users.size)

        for (u in users) {
            println(u.email)
            println(u.password)
        }

    }

    @Test
    fun deleteAll() {
        userRepository.deleteAll()
    }

}