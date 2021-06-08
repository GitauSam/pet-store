package com.zenza.pets.ipc.activator

import com.zenza.pets.ipc.utils.exceptions.ExceptionHandler
import com.zenza.pets.ipc.utils.exceptions.InvalidInputException
import com.zenza.pets.ipc.utils.exceptions.InvalidParameterException
import com.zenza.pets.store.domain.Pet
import com.zenza.pets.store.repository.PetRepository
import lombok.AllArgsConstructor
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service

@Service
@AllArgsConstructor
class FetchPet(val petRepository: PetRepository) {

    fun byId(pet: Pet?): Pet? {
        var p: Pet? = null

        pet
            ?.let {
                it.id
                    ?.let {
                        p = petRepository.findById(pet.id!!).get()
                    }?: run {
                        ExceptionHandler.throwInvalidParameterException(InvalidParameterException::class.java.constructors[0], "id", "pet")
                    }
            }?: run {
                ExceptionHandler.throwInvalidInputException(InvalidInputException::class.java.constructors[0], "pet")
            }

        return p
    }

    fun all(page: Int, size: Int): Page<Pet> {
        return petRepository.findAll(PageRequest.of(page, size, Sort.by("createdAt").descending()))
    }

}