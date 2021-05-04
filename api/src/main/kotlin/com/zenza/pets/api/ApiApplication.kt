package com.zenza.pets.api

import com.zenza.pets.store.domain.User
import com.zenza.pets.store.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.security.crypto.password.PasswordEncoder

@SpringBootApplication(scanBasePackages = ["com.zenza.pets"])
class ApiApplication: ApplicationRunner {

	@Autowired
	lateinit var userRepository: UserRepository
	@Autowired
	lateinit var passwordEncoder: PasswordEncoder

	override fun run(args: ApplicationArguments?) {
//		userRepository.save(User(
//				"John",
//				"Doe",
//				"johndoe@pets.com",
//				"2547123456789",
//				passwordEncoder.encode("JohnDoe1234")
//		))
	}
}

fun main(args: Array<String>) {
	runApplication<ApiApplication>(*args)
}
