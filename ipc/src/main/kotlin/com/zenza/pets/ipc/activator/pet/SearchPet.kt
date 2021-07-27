package com.zenza.pets.ipc.activator.pet

import com.zenza.pets.store.domain.Pet
import com.zenza.pets.store.repository.PetRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service

@Service
class SearchPet(
    private val petRepository: PetRepository
) {
    fun search(filter: String, page: Int, size: Int): Page<Pet> {
//        return petRepository.findPetsByType(filter, PageRequest.of(page, size))
        return petRepository.findPetsByTypeContainingOrColourContaining(filter, filter, PageRequest.of(page, size))
    }
}