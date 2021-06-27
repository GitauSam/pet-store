package com.zenza.pets.ipc.activator.pet

import com.zenza.pets.ipc.utils.exceptions.MissingEntityException
import com.zenza.pets.store.domain.Pet
import com.zenza.pets.store.repository.PetRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class ActivatePet(
        private val petRepository: PetRepository
) {
    fun activate(id: Long): Pet = petRepository
            .findByIdOrNull(id)
            ?. let {
                it.status = 1
                petRepository.save(it)
            }?: run {
                throw MissingEntityException("pet", "id", id.toString())
            }
}