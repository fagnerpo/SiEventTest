package com.fpo.sieventtest.data.repository

import com.fpo.sieventtest.data.model.EventCheckData
import com.fpo.sieventtest.data.model.EventData
import retrofit2.Response

interface EventsRepositoryInterface {
    suspend fun getAllEvents():  Response<List<EventData>>
    suspend fun checkInEvents(eventCheckData: EventCheckData): Response<Any>
}