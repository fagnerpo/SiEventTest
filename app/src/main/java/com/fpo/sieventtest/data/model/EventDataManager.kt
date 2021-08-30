package com.fpo.sieventtest.data.model

import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*

class EventDataManager(val eventData: EventData) {

    val data: String
    get() {
        val dateFormatted = SimpleDateFormat("EEE, dd MMMM yyyy HH:mm")
        val date = Date(eventData.date)
        return "Data: ${dateFormatted.format(date)
            .replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }}"
    }

    val price: String = "Valor: ${NumberFormat.getCurrencyInstance().format(eventData.price) ?: ""}"
    val description = eventData.description
    val  latitude = eventData.latitude
    val  longitude = eventData.longitude
    val  id = eventData.id
    val  image = eventData.image
    val  title = eventData.title

}