package com.example.fridge.api

import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {
    @POST("/recommend")
    suspend fun orderFood(@Body request: ApiRequest): ApiResponse
}