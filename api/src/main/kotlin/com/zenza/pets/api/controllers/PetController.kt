package com.zenza.pets.api.controllers

import com.zenza.pets.api.domain.ApiResponse
import com.zenza.pets.ipc.activator.pet.*
import com.zenza.pets.ipc.utils.exceptions.InvalidInputException
import com.zenza.pets.ipc.utils.exceptions.InvalidParameterException
import com.zenza.pets.store.domain.Pet
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import java.util.logging.Level
import java.util.logging.Logger

@RestController()
@RequestMapping("/pet")
class PetController(
        val createPet: CreatePet,
        val editPet: EditPet,
        val deactivatePet: DeactivatePet,
        val activatePet: ActivatePet,
        val fetchPet: FetchPet
) {

    @PreAuthorize("hasAuthority('WRITE_PET_PRIVILEGE')")
    @CrossOrigin(origins = ["http://localhost:3000"])
    @PostMapping("/create")
    fun create(
            @RequestParam("type") t: String,
            @RequestParam("age") a: Double,
            @RequestParam("colour") c: String,
            @RequestParam("pet_img") file: MultipartFile
    ): ResponseEntity<ApiResponse> = try {

        val pet = createPet.save(
                file,
                Pet().apply {
                    type = t
                    age = a
                    colour = c
                }
        )

        Logger.getLogger(this.javaClass.name).log(Level.WARNING, "pet created successfully")

        ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse(
                        "200",
                        "pet saved successfully",
                        arrayOf(pet.id, pet.type, pet.age, pet.colour, pet.status)
                ))
    } catch (e: InvalidParameterException) {
        Logger.getLogger(this.javaClass.name).log(Level.SEVERE, "error occurred while creating pet ${e.message}")
        ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(
                        ApiResponse(
                                "25",
                                e.message!!,
                                null
                        )
                )
    } catch (e: InvalidInputException) {
        Logger.getLogger(this.javaClass.name).log(Level.SEVERE, "error occurred while creating pet ${e.message}")
        ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(
                        ApiResponse(
                                "25",
                                e.message!!,
                                null
                        )
                )
    } catch (e: Exception) {
        Logger.getLogger(this.javaClass.name).log(Level.SEVERE, "error occurred while creating pet ${e.message}")
        ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(
                        ApiResponse(
                                "99",
                                e.message!!,
                                null
                        )
                )
    }

    @PreAuthorize("hasAuthority('WRITE_PET_PRIVILEGE')")
    @PostMapping("/edit")
    fun edit(@RequestBody pet: Pet): ResponseEntity<ApiResponse> = try {
        ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse(
                        "200",
                        "pet saved successfully",
                        editPet.update(pet)
                ))
    } catch (e: InvalidParameterException) {
        ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(
                        ApiResponse(
                                "25",
                                e.message!!,
                                null
                        )
                )
    } catch (e: InvalidInputException) {
        ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(
                        ApiResponse(
                                "25",
                                e.message!!,
                                null
                        )
                )
    } catch (e: Exception) {
        ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(
                        ApiResponse(
                                "99",
                                e.message!!,
                                null
                        )
                )
    }

    @PreAuthorize("hasAuthority('READ_PET_PRIVILEGE')")
    @GetMapping("/fetch")
    fun fetchById(@RequestParam("pet") petId: Long): ResponseEntity<ApiResponse> = try {
        ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse(
                        "200",
                        "pet fetched successfully",
                        fetchPet.byId(Pet().apply { id = petId })
                ))
    } catch (e: InvalidParameterException) {
        ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(
                        ApiResponse(
                                "25",
                                e.message!!,
                                null
                        )
                )
    } catch (e: InvalidInputException) {
        ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(
                        ApiResponse(
                                "25",
                                e.message!!,
                                null
                        )
                )
    } catch (e: Exception) {
        ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(
                        ApiResponse(
                                "99",
                                e.message!!,
                                null
                        )
                )
    }

    @PreAuthorize("hasAuthority('READ_ALL_PETS_PRIVILEGE')")
    @GetMapping("/fetch-all")
    fun fetchAll(
            @RequestParam("page", defaultValue = "0") page: Int,
            @RequestParam("size", defaultValue = "5") size: Int
    ): ResponseEntity<ApiResponse> = try {
        ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse(
                        "200",
                        "pets fetched successfully",
                        fetchPet.all(page, size)
                ))
    } catch (e: Exception) {
        ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(
                        ApiResponse(
                                "99",
                                e.message!!,
                                null
                        )
                )
    }

    @PreAuthorize("hasAuthority('WRITE_PET_PRIVILEGE')")
    @GetMapping("/delete/{id}")
    fun deactivatePet(@PathVariable("id") id: Long): ResponseEntity<ApiResponse> = try {
        ResponseEntity
                .status(HttpStatus.OK)
                .body(
                    ApiResponse(
                            "200",
                            "Successfully deleted pet",
                            deactivatePet.deactivate(id)
                    )
                )
    } catch (e: Exception) {
        ResponseEntity
                .status(HttpStatus.EXPECTATION_FAILED)
                .body(
                    ApiResponse(
                            "99",
                            "Error occurred while deleting pet",
                            e.message
                    )
                )
    }

    @PreAuthorize("hasAuthority('WRITE_PET_PRIVILEGE')")
    @GetMapping("/activate/{id}")
    fun activatePet(@PathVariable("id") id: Long): ResponseEntity<ApiResponse> = try {
        ResponseEntity
                .status(HttpStatus.OK)
                .body(
                        ApiResponse(
                                "200",
                                "Successfully activated pet",
                                activatePet.activate(id)
                        )
                )
    } catch (e: Exception) {
        ResponseEntity
                .status(HttpStatus.EXPECTATION_FAILED)
                .body(
                        ApiResponse(
                                "99",
                                "Error occurred while activating pet",
                                e.message
                        )
                )
    }

}