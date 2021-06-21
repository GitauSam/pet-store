package com.zenza.pets.store

import com.zenza.pets.store.domain.User
import com.zenza.pets.store.repository.UserRepository
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import java.util.logging.Level
import java.util.logging.Logger

@SpringBootTest
class UserRepositoryTests {

    @Autowired lateinit var userRepository: UserRepository

    @Test
    fun fetchAll() {
        val users = userRepository.findAll()

        println(users.size)

        for (u in users) {
            println(u.email)
        }

    }

    @Test
    fun fetchAllUsersExceptCurrent() {
        val users = userRepository
            .findAllUsersExceptCurrent(
                "mbwakni",
                PageRequest.of(
                    1,
                    5,
                    Sort.by("createdAt").descending()
                )
            )

        Logger.getLogger(this.javaClass.name).log(Level.WARNING, "${users.size}")

        for (u in users) {
            Logger.getLogger(this.javaClass.name).log(Level.WARNING, u.username)
        }
    }

}