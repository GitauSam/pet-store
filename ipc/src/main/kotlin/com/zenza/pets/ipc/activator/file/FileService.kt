package com.zenza.pets.ipc.activator.file

import com.zenza.pets.ipc.utils.exceptions.DocumentStorageException
import com.zenza.pets.store.domain.PetImageProperties
import com.zenza.pets.store.repository.PetImagePropertiesRepository
import org.springframework.core.io.Resource
import org.springframework.core.io.UrlResource
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.io.FileNotFoundException
import java.net.MalformedURLException
import java.nio.file.Files
import java.nio.file.Path
import java.util.logging.Level
import java.nio.file.Paths
import java.util.logging.Logger


@Service
class FileService(
        val petImagePropertiesRepository: PetImagePropertiesRepository
) {

    final var fileStorageLocation: Path = Paths.get(PetImageProperties().path!!).toAbsolutePath().normalize()

    init {
        try {
            Files.createDirectories(fileStorageLocation)
        } catch (e: Exception) {
            throw DocumentStorageException("Could not create pet images storage directory. Cause: ${e.message}")
        }
    }

    fun storeFile(
            file: MultipartFile,
            pet: Long
    ): String {
        try {
            Files.copy(file.inputStream, fileStorageLocation.resolve(file.originalFilename!!))

            val petImage = PetImageProperties()
            petImage.pet = pet
            petImage.documentFormat = file.contentType
            petImage.fileName = "fileName"
            petImage.documentType = "Pet Image"
            petImage.path = "path"
            petImagePropertiesRepository.save(petImage)

            return "1"
        } catch (e: Exception) {
            Logger.getLogger(this.javaClass.name)
                    .log(Level.SEVERE, "Unable to store image for pet with id: $pet. Error: ${e.message}")
            e.printStackTrace()
            return "0"
        }
    }

    fun loadFileAsResource(filename: String): Resource {
        try {
            val filePath = fileStorageLocation.resolve(filename).normalize()
            val resource = UrlResource(filePath.toUri())

            if (resource.exists()) {
                return resource
            } else {
                throw FileNotFoundException("File not found: $filename")
            }
        } catch (e: MalformedURLException) {
            throw FileNotFoundException("File not found: $filename")
        }
    }

    fun getDocumentName(pet: Long, docType: String): String {
        return petImagePropertiesRepository.findByPetAndDocumentType(pet, docType)!!.path!!
    }
}