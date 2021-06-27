package com.zenza.pets.ipc.activator.pet

import com.zenza.pets.ipc.activator.file.FileService
import com.zenza.pets.ipc.utils.exceptions.InvalidInputException
import com.zenza.pets.ipc.utils.exceptions.InvalidParameterException
import com.zenza.pets.store.domain.Pet
import com.zenza.pets.store.repository.PetRepository
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.sql.Timestamp
import java.time.Instant

@Service
class CreatePet(
        val fileService: FileService,
        val petRepository: PetRepository
) {
//    fun save(pet: Pet?): Pet {
    fun save(file: MultipartFile, pet: Pet?): Pet {
        pet
            ?.let {
                pet.type
                        ?.let {
                            pet.age
                                    ?.let {
                                        pet.colour
                                                ?.let {
                                                    val savedPet = petRepository.save(pet.apply {
                                                        createdAt = Timestamp.from(Instant.now())
                                                    })
                                                    fileService.storeFile(file, savedPet.id!!)
                                                    return savedPet
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