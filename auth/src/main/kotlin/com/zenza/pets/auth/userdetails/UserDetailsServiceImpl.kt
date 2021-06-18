package com.zenza.pets.auth.userdetails

import com.zenza.pets.store.domain.Privilege
import com.zenza.pets.store.domain.Role
import com.zenza.pets.store.repository.UserRepository
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional


@Component
class UserDetailsServiceImpl(private val userRepository: UserRepository): UserDetailsService {

    @Transactional
    override fun loadUserByUsername(username: String?): UserDetails {

        val user = userRepository.findByUsername(username!!)

        user?.let {
            return User(user.email, user.password, getAuthorities(user.roles!!))
        }?: run {
            throw UsernameNotFoundException("Invalid user. Did not find user with username {$username}")
        }

    }

    private fun getAuthorities(
        roles: Collection<Role>
    ): Collection<GrantedAuthority?> {
        return getGrantedAuthorities(getPrivileges(roles))
    }

    private fun getPrivileges(roles: Collection<Role>): MutableList<String?> {
        val privileges: MutableList<String?> = ArrayList()
        val collection: MutableList<Privilege> = ArrayList()
        for (role in roles) {
            collection.addAll(role.privileges!!)
        }
        for (item in collection) {
            privileges.add(item.name)
        }
        return privileges
    }

    private fun getGrantedAuthorities(privileges: MutableList<String?>): List<GrantedAuthority> {
        val authorities: MutableList<GrantedAuthority> = ArrayList()
        for (privilege in privileges) {
            authorities.add(SimpleGrantedAuthority(privilege))
        }
        return authorities
    }

}