package com.fpo.sieventtest.data.api

import com.fpo.sieventtest.data.model.EventCheckData
import com.fpo.sieventtest.data.model.EventData
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface RetrofitService {
    @GET("/api/events")
    suspend fun getAllEvents(): Response<List<EventData>>

    @POST("/api/checkin")
    suspend fun checkInEvents(@Body eventCheckData: EventCheckData): Response<Any>

    companion object {
        var retrofitService: RetrofitService? = null
        fun getInstance() : RetrofitService {
            if (retrofitService == null) {
                val retrofit = Retrofit.Builder()
                    .baseUrl("https://5f5a8f24d44d640016169133.mockapi.io")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                retrofitService = retrofit.create(RetrofitService::class.java)
            }
            return retrofitService!!
        }

    }
}