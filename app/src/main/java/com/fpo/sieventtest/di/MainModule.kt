package com.fpo.sieventtest.di

import com.fpo.sieventtest.data.api.RetrofitService
import com.fpo.sieventtest.data.repository.EventsRepository
import com.fpo.sieventtest.ui.checkin.CheckInViewModel
import com.fpo.sieventtest.ui.details.DetailsViewModel
import com.fpo.sieventtest.ui.home.HomeFragmentModelView
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val mainModule= module {

    single {
        RetrofitService.getInstance()
    }

    factory {
        EventsRepository(retrofitService = get())
    }

    viewModel {
        HomeFragmentModelView(eventsRepository = get())
    }

    viewModel {
        CheckInViewModel(eventsRepository = get())
    }

    viewModel {
        DetailsViewModel()
    }
}