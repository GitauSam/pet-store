package com.zenza.pets.api

import com.zenza.pets.store.domain.User
import com.zenza.pets.store.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

@SpringBootApplication(scanBasePackages = ["com.zenza.pets"])
class ApiApplication: ApplicationRunner {

	@Autowired
	lateinit var userRepository: UserRepository
	@Autowired
	lateinit var bCryptPasswordEncoder: BCryptPasswordEncoder

	override fun run(args: ApplicationArguments?) {
//		userRepository.save(User().apply {
//			firstName = "John"
//			lastName = "Doe"
//			email = "johndoe@pets.com"
//			username = "johndoe"
//			password = bCryptPasswordEncoder.encode("JohnDoe1234")
//			phoneNumber = "254700000000"
//		})
	}
}

fun main(args: Array<String>) {
	runApplication<ApiApplication>(*args)
}
