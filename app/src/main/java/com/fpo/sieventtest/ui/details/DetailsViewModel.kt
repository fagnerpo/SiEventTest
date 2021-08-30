package com.fpo.sieventtest.ui.details

import android.content.Intent
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.fpo.sieventtest.data.model.EventData
import com.fpo.sieventtest.data.model.EventDataManager

class DetailsViewModel: ViewModel() {

    val eventManager = MutableLiveData<EventDataManager>()
    val dataToShare = MutableLiveData<Intent>()
    val checkin = MutableLiveData<EventData>()

    fun setEvent(event: EventData) {
        eventManager.postValue(EventDataManager(event))
    }

    fun shareEvent() {
        val shareData = "${eventManager.value?.title} - ${eventManager.value?.data}  \n\n\n" +
                "${eventManager.value?.description}"

        val intent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, shareData)
            type = "text/plain"
        }

        dataToShare.postValue(intent)
    }

    fun checkin() {
        checkin.postValue(eventManager.value?.eventData)
    }
}