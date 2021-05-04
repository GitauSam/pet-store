package com.zenza.pets.auth.userdetails

import com.zenza.pets.store.repository.UserRepository
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class UserDetailsImpl(val userRepository: UserRepository): UserDetailsService {
    override fun loadUserByUsername(username: String?): UserDetails {
        val user = userRepository.findByEmail(username!!)
        user
            ?.let {
                return user as UserDetails
            }?: run {
                throw UsernameNotFoundException("User with email $username not found")
            }
    }
}