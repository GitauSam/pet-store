package com.zenza.pets.ipc.activator

import com.zenza.pets.ipc.utils.exceptions.InvalidInputException
import com.zenza.pets.ipc.utils.exceptions.InvalidParameterException
import com.zenza.pets.store.domain.Pet
import com.zenza.pets.store.repository.PetRepository
import lombok.AllArgsConstructor
import org.springframework.stereotype.Service

@Service
class CreatePet(val petRepository: PetRepository) {

    fun save(pet: Pet?): Pet {
        pet
            ?.let {
                pet.type
                        ?.let {
                            pet.age
                                    ?.let {
                                        pet.colour
                                                ?.let {
                                                    val p = petRepository.save(pet)
                                                    return p
                                                }?: run {
                                                    throw InvalidParameterException("Expected param colour of input pet is null")
                                                }
                                    }?: run {
                                        throw InvalidParameterException("Expected param age of input pet is null")
                                    }
                        }?:run {
                            throw InvalidParameterException("Expected param type of input pet is null")
                        }
            }?: run {
                throw InvalidInputException("Expected input pet is null");
            }
    }
}