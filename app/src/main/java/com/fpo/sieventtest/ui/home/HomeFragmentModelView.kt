package com.fpo.sieventtest.ui.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.fpo.sieventtest.data.model.EventData
import com.fpo.sieventtest.data.repository.EventsRepository
import kotlinx.coroutines.*

class HomeFragmentModelView(var eventsRepository: EventsRepository) : ViewModel() {

    val errorMessage = MutableLiveData<String>()
    val eventList = MutableLiveData<List<EventData>>()
    val loading = MutableLiveData<Boolean>()
    var job: Job? = null

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        onError("Exception handled: ${throwable.localizedMessage}")
    }

    fun getAllEvents() {
        job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            val response = eventsRepository.getAllEvents()
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    val body = response.body()
                    body?.let { eventList.postValue(it) }
                    loading.value = false
                } else {
                    onError("Error : ${response.message()} ")
                }
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }

    private fun onError(message: String) {
        errorMessage.value = message
        loading.value = false
    }

}