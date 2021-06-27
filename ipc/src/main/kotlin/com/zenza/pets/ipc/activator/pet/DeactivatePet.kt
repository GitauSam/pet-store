package com.zenza.pets.ipc.activator.pet

import com.zenza.pets.ipc.utils.exceptions.InvalidInputException
import com.zenza.pets.ipc.utils.exceptions.InvalidParameterException
import com.zenza.pets.ipc.utils.exceptions.MissingEntityException
import com.zenza.pets.store.domain.Pet
import com.zenza.pets.store.repository.PetRepository
import lombok.AllArgsConstructor
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import java.sql.Timestamp
import java.time.Instant

@Service
@AllArgsConstructor
class DeactivatePet(val petRepository: PetRepository) {

    fun deactivate(id: Long): Pet = petRepository
            .findByIdOrNull(id)
            ?.let {
                it.status = 0
                it.deletedAt = Timestamp.from(Instant.now())
                petRepository.save(it)
            }?: run {
                throw MissingEntityException("pet", "id", id.toString())
            }
}