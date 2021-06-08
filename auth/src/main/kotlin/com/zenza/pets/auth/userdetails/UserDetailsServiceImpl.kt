package com.zenza.pets.auth.userdetails

import com.zenza.pets.store.repository.UserRepository
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Component

@Component
class UserDetailsServiceImpl(private val userRepository: UserRepository): UserDetailsService {

    override fun loadUserByUsername(username: String?): UserDetails {

        val user = userRepository.findByUsername(username!!)

        user?.let {
            return User(user.email, user.password, listOf<SimpleGrantedAuthority>())
        }?: run {
            throw UsernameNotFoundException("Invalid user. Did not find user with username {$username}")
        }

    }

}