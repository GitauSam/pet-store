package com.zenza.pets.ipc.activator.pet

import com.zenza.pets.ipc.utils.exceptions.MissingEntityException
import com.zenza.pets.store.domain.PetAdoption
import com.zenza.pets.store.repository.PetAdoptionRepository
import com.zenza.pets.store.repository.PetRepository
import com.zenza.pets.store.repository.UserRepository
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service
import java.sql.Timestamp
import java.time.Instant

@Service
class AdoptPet(
    val petRepository: PetRepository,
    val userRepository: UserRepository,
    val petAdoptionRepository: PetAdoptionRepository
) {

    fun requestAdoption(id: Long): PetAdoption {
        val p = petRepository.findPetById(id)

        return p?.let {
            val principal = SecurityContextHolder
                .getContext()
                .authentication
                .principal

            var username = ""

            if (principal is UserDetails) {
                username = principal.username
            } else {
                username = principal.toString()
            }

            userRepository.findByUsername(username)
                ?.let {
                    val petAdoption = PetAdoption().apply {
                        pet_id = p.id
                        user_id = it.id
                        status = 1
                        approvedAt = Timestamp.from(Instant.now())
                        approvedBy = "admin"
                        createdAt = Timestamp.from(Instant.now())
                        createdBy = "admin"
                        modifiedBy = "admin"
                    }

                    petAdoptionRepository.save(petAdoption)
                }?: run {
                    throw MissingEntityException("User", "username", username)
                }
        }?: run {
            throw MissingEntityException("Pet", "ID", id.toString())
        }
    }
}