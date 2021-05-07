package com.zenza.pets.api.controllers

import com.zenza.pets.api.domain.ApiResponse
import com.zenza.pets.ipc.activator.CreatePet
import com.zenza.pets.ipc.activator.DeactivatePet
import com.zenza.pets.ipc.activator.EditPet
import com.zenza.pets.ipc.activator.FetchPet
import com.zenza.pets.ipc.utils.exceptions.InvalidInputException
import com.zenza.pets.ipc.utils.exceptions.InvalidParameterException
import com.zenza.pets.store.domain.Pet
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController()
@RequestMapping("/pet")
class PetController(
        val createPet: CreatePet,
        val editPet: EditPet,
        val deactivatePet: DeactivatePet,
        val fetchPet: FetchPet
) {

    @PostMapping("/create")
    fun create(@RequestBody pet: Pet): ResponseEntity<ApiResponse> = try {
        ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse(
                        "200",
                        "pet saved successfully",
                        createPet.save(pet)
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

    @PostMapping("/delete")
    fun delete(@RequestBody pet: Pet): ResponseEntity<ApiResponse> = try {
        ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse(
                        "200",
                        "pet saved successfully",
                        deactivatePet.deactivate(pet)
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

    @GetMapping("/fetch-all")
    fun fetchAll(): ResponseEntity<ApiResponse> = try {
        ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse(
                        "200",
                        "pets fetched successfully",
                        fetchPet.all()
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

}