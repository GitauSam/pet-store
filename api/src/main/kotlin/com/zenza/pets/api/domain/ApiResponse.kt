package com.zenza.pets.api.domain

data class ApiResponse(
        var status: String,
        var message: String,
        var data: Any?
) {

}