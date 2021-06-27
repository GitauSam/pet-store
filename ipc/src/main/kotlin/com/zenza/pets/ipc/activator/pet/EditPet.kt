package com.zenza.pets.ipc.activator.pet

import com.zenza.pets.ipc.utils.exceptions.InvalidInputException
import com.zenza.pets.ipc.utils.exceptions.InvalidParameterException
import com.zenza.pets.store.domain.Pet
import com.zenza.pets.store.repository.PetRepository
import org.springframework.stereotype.Service

@Service
class EditPet(val petRepository: PetRepository) {

    fun update(pet: Pet?): Pet {
        pet
            ?.let {
                pet.id
                        ?. let {
                            pet.type
                                    ?.let {
                                        pet.age
                                                ?.let {
                                                    pet.colour
                                                            ?.let {
                                                                return petRepository.save(pet)
                                                            }?: run {
                                                                throw InvalidParameterException("Expected param colour of input pet is null")
                                                            }
                                                }?: run {
                                                    throw InvalidParameterException("Expected param age of input pet is null")
                                                }
                                    }?: run {
                                        throw InvalidParameterException("Expected param type of input pet is null")
                                    }
                        }?: run {
                            throw InvalidParameterException("Expected param id of input pet is null")
                        }
            } ?: run {
                throw InvalidInputException("Expected input pet is null")
        }
    }

}