package com.zenza.pets.ipc.activator

import com.zenza.pets.ipc.utils.exceptions.InvalidInputException
import com.zenza.pets.ipc.utils.exceptions.InvalidParameterException
import com.zenza.pets.store.domain.Pet
import com.zenza.pets.store.repository.PetRepository
import lombok.AllArgsConstructor
import org.springframework.stereotype.Service

@Service
@AllArgsConstructor
class DeactivatePet(val petRepository: PetRepository) {

    fun deactivate(pet: Pet?): Pet {
        pet
            ?.let {
                pet.id
                    ?.let {
                        pet.status
                            ?.let {
                                pet.status = 0
                                return petRepository.save(pet)
                            }?: run {
                                throw InvalidParameterException("Expected param status of input pet is null")
                            }
                    }?: run {
                        throw InvalidParameterException("Expected param id of input pet is null")
                    }
            }?: run {
                throw InvalidInputException("Expected input pet is null")
            }
    }
}