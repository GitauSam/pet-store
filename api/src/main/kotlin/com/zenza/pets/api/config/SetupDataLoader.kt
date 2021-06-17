package com.zenza.pets.api.config

import com.zenza.pets.store.domain.Privilege
import com.zenza.pets.store.domain.Role
import com.zenza.pets.store.domain.User
import com.zenza.pets.store.repository.PrivilegeRepository
import com.zenza.pets.store.repository.RoleRepository
import com.zenza.pets.store.repository.UserRepository
import lombok.AllArgsConstructor
import lombok.NoArgsConstructor
import org.springframework.context.ApplicationListener
import org.springframework.context.event.ContextRefreshedEvent
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class SetupDataLoader(
    val userRepository: UserRepository,
    val roleRepository: RoleRepository,
    val privilegeRepository: PrivilegeRepository,
    val bCryptPasswordEncoder: BCryptPasswordEncoder
): ApplicationListener<ContextRefreshedEvent> {
    
    var alreadySetup = false

    @Transactional
    override fun onApplicationEvent(p0: ContextRefreshedEvent) {

        if (alreadySetup) return

        val readAllPetsPrivilege = _createPrivilegeIfNotFound("READ_ALL_PETS_PRIVILEGE")
        val readPetPrivilege = _createPrivilegeIfNotFound("READ_PET_PRIVILEGE")
        val writePetPrivilege = _createPrivilegeIfNotFound("WRITE_PET_PRIVILEGE")

        val adminPrivileges = listOf(
            readPetPrivilege,
            readAllPetsPrivilege,
            writePetPrivilege
        )

        val userPrivileges = listOf(
            readPetPrivilege,
            readAllPetsPrivilege
        )

        _createRoleIfNotFound("ROLE_ADMIN", adminPrivileges)
        _createRoleIfNotFound("ROLE_USER", userPrivileges)

        val adminRole = roleRepository.findByName("ROLE_ADMIN")

        userRepository.findByUsername("johndoe")?.let {

        }?: run {
            userRepository.save(User().apply {
                firstName = "John"
                lastName = "Doe"
                email = "johndoe@pets.com"
                username = "johndoe"
                password = bCryptPasswordEncoder.encode("JohnDoe1234")
                phoneNumber = "254700000000"
                roles = listOf(adminRole!!)
            })
        }
    }

    @Transactional
    fun _createPrivilegeIfNotFound(p: String): Privilege {
        privilegeRepository.findByName(p)
            ?.let {
                return it
            }?: run {
                val privilege = Privilege().apply {
                    name = p
                }
                return privilegeRepository.save(privilege)
        }
    }

    @Transactional
    fun _createRoleIfNotFound(r: String, ps: Collection<Privilege>): Role {
        roleRepository.findByName(r)
            ?.let {
                return it
            }?: run {
            val role = Role().apply {
                name = r
                privileges = ps
            }
            return roleRepository.save(role)
        }
    }

}