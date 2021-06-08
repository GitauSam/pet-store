package com.zenza.pets.ipc.activator.file

import com.zenza.pets.ipc.utils.Util
import com.zenza.pets.ipc.utils.aws.s3.S3StoreFile
import com.zenza.pets.ipc.utils.exceptions.DocumentStorageException
import com.zenza.pets.store.domain.PetImageProperties
import com.zenza.pets.store.repository.PetImagePropertiesRepository
import org.apache.http.entity.ContentType.*
import org.springframework.core.io.Resource
import org.springframework.core.io.UrlResource
import org.springframework.stereotype.Service
import org.springframework.util.StringUtils
import org.springframework.web.multipart.MultipartFile
import java.io.FileNotFoundException
import java.io.IOException
import java.net.MalformedURLException
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.util.*
import java.util.UUID
import java.util.logging.Level
import java.util.logging.Logger


@Service
class FileService(
        val petImagePropertiesRepository: PetImagePropertiesRepository,
        val s3StoreFile: S3StoreFile
) {

    lateinit var fileStorageLocation: Path

    init {
        fileStorageLocation = Paths.get(PetImageProperties().path!!).toAbsolutePath().normalize()

        try {
            Files.createDirectories(fileStorageLocation)
        } catch (e: Exception) {
            throw DocumentStorageException("Could not create pet images storage directory. Cause: ${e.message}")
        }
    }

    fun storeFileS3(
            file: MultipartFile,
            pet: Long,
            docType: String
    ): String {
        val originalFilename = StringUtils.cleanPath(file.originalFilename!!)

        try {

            if (file.isEmpty()) {
                throw IllegalStateException("Cannot upload empty file");
            }

            // Check if the file's name contains invalid characters
            if (originalFilename.contains("..")) {
                throw DocumentStorageException("Sorry! Filename contains invalid path sequence $originalFilename")
            }

            //Check if the file is an image
            if (!Arrays.asList(IMAGE_PNG.getMimeType(),
                            IMAGE_BMP.getMimeType(),
                            IMAGE_GIF.getMimeType(),
                            IMAGE_JPEG.getMimeType()).contains(file.getContentType())) {
                throw IllegalStateException("FIle uploaded is not an image");
            }

            var fileExtension = "";

            try {
                fileExtension = originalFilename.substring(originalFilename.lastIndexOf("."))
            } catch (e: Exception) {
                fileExtension = ""
            }

            // get file metadata
            val metadata: MutableMap<String?, String?>? = HashMap()
            metadata?.set("Content-Type", file.contentType)
            metadata?.set("Content-Length", java.lang.String.valueOf(file.size))

            //Save Image in S3
            val path = "phipetsbucket/${UUID.randomUUID()}"
            val fileName = file.originalFilename

            s3StoreFile.upload(path, fileName, Optional.of(metadata!!), file.inputStream)

            val petImage = PetImageProperties()
            petImage.pet = pet
            petImage.documentFormat = file.contentType
            petImage.fileName = fileName
            petImage.documentType = docType
            petImage.path = path
            petImagePropertiesRepository.save(petImage)

            return fileName!!
        } catch (e: IOException) {
            throw DocumentStorageException("Could not store file $originalFilename. Cause: ${e.message}")
        }
    }

    fun storeFile(
            file: MultipartFile,
            pet: Long
    ): String {
        try {
            Files.copy(file.inputStream, fileStorageLocation.resolve(file.originalFilename))

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