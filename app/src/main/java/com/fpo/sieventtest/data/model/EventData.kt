package com.fpo.sieventtest.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class EventData (
    var people: List<String> ?=null,
    var date: Long,
    var description: String,
    var image: String,
    var longitude: Double,
    val latitude: Double,
    var price: Double,
    var title: String,
    var id: Int
) : Parcelable