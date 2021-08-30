package com.fpo.sieventtest.ui.checkin

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.fpo.sieventtest.data.model.EventCheckData
import com.fpo.sieventtest.data.model.EventData
import com.fpo.sieventtest.data.repository.EventsRepository
import com.fpo.sieventtest.data.model.EventDataManager
import kotlinx.coroutines.*
import java.util.regex.Pattern

class CheckInViewModel(var eventsRepository: EventsRepository) : ViewModel() {

    private var eventManager: EventDataManager? = null
    val messageWarnings = MutableLiveData<String>()
    val messageToast = MutableLiveData<String>()
    val loading = MutableLiveData<Boolean>()
    val successCheckIn = MutableLiveData<Boolean>()

    var job: Job? = null

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        onError("Exception handled: ${throwable.localizedMessage}")
    }

    private val mailPatten: Pattern = Pattern.compile(
        "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
                "\\@" +
                "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                "(" +
                "\\." +
                "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                ")+"
    )

    private fun isValidMail(str: String): Boolean {
        val ret = mailPatten.matcher(str).matches()

        return if (ret)
            true
        else {
            messageWarnings.postValue("*E-mail inválido!")
            false
        }
    }

    fun setEvent(event: EventData) {
        eventManager = EventDataManager(event)
        loading.value = false
    }

    private fun isValidName(name: String): Boolean {
        return if (name.isEmpty()) {
            messageWarnings.postValue("*Preenchar o nome!")
            false
        } else true
    }


    fun postCheckIn(mail: String, name: String) {
        loading.value = true
        if (isValidName(name) && isValidMail(mail)) {
            job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
                val response =
                    eventsRepository.checkInEvents(EventCheckData(eventManager!!.id, name, mail))
                loading.postValue(false)
                withContext(Dispatchers.Main) {
                    if (response.isSuccessful) {
                        successCheckIn.postValue(true)
                        messageToast.postValue("Check-in realizado com sucesso!!")
                    }
                    else {
                        messageToast.postValue("Falha ao realizar check-in, tente mais tarde!!")
                    }
                }
            }
        } else {
            loading.value = false
        }
    }

    override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }

    private fun onError(message: String) {
        messageWarnings.postValue("*$message")
        loading.value = false
    }
}