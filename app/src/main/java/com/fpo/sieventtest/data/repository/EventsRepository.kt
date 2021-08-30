package com.fpo.sieventtest.data.repository

import com.fpo.sieventtest.data.api.RetrofitService
import com.fpo.sieventtest.data.model.EventCheckData

class EventsRepository constructor(private val retrofitService: RetrofitService): EventsRepositoryInterface {

    override suspend fun getAllEvents() = retrofitService.getAllEvents()
    override suspend fun checkInEvents(eventCheckData: EventCheckData) = retrofitService.checkInEvents(eventCheckData)
}