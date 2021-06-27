package com.zenza.pets.ipc.activator.user

import com.zenza.pets.ipc.utils.exceptions.ExceptionHandler
import com.zenza.pets.ipc.utils.exceptions.InvalidInputException
import com.zenza.pets.ipc.utils.exceptions.InvalidParameterException
import com.zenza.pets.store.domain.User
import com.zenza.pets.store.repository.UserRepository
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import java.sql.Timestamp
import java.time.Instant

@Service
class CreateUser(
       private val userRepository: UserRepository,
       private val bCryptPasswordEncoder: BCryptPasswordEncoder
) {

    fun save(user: User?): User? {

        var savedUser: User? = null

         user
                ?.let {
                    user.firstName
                            ?.let {
                                user.lastName
                                        ?.let {
                                            user.password
                                                    ?.let {
                                                        user.email
                                                                ?.let {
                                                                    user.phoneNumber
                                                                            ?.let {
                                                                                user.status = 1
                                                                                user.createdAt = Timestamp.from(Instant.now())
                                                                                user.password = bCryptPasswordEncoder.encode(user.password)
                                                                                 savedUser = userRepository.save(user)
                                                                            }?: run {
                                                                                ExceptionHandler.throwInvalidParameterException(InvalidParameterException::class.java.constructors[0], "phone number", "user")
                                                                            }
                                                                }?: run {
                                                                    ExceptionHandler.throwInvalidParameterException(InvalidParameterException::class.java.constructors[0], "email", "user")
                                                                }
                                                    }?: run {
                                                        ExceptionHandler.throwInvalidParameterException(InvalidParameterException::class.java.constructors[0], "password", "user")
                                                    }
                                        }?: run {
                                            ExceptionHandler.throwInvalidParameterException(InvalidParameterException::class.java.constructors[0], "last name", "user")
                                        }
                            }?: run {
                                ExceptionHandler.throwInvalidParameterException(InvalidParameterException::class.java.constructors[0], "first name", "user")
                            }
                }?: run {
                    ExceptionHandler.throwInvalidInputException(InvalidInputException::class.java.constructors[0], "user")
                }

        return savedUser
    }

}