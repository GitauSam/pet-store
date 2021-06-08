package com.zenza.pets.api.domain

import org.springframework.web.multipart.MultipartFile

class CreatePetRequest {
    val type: String? = null
    val age: Double? = null
    val colour: String? = null
    val file: ArrayList<MultipartFile>? = null
}