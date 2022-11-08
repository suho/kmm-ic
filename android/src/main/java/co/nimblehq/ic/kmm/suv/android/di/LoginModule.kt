package co.nimblehq.ic.kmm.suv.android.di

import co.nimblehq.ic.kmm.suv.android.ui.screens.home.HomeViewModel
import co.nimblehq.ic.kmm.suv.android.ui.screens.login.LoginViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val loginModule = module {
    viewModel { LoginViewModel(get()) }
    viewModel { HomeViewModel(get(), get(), get()) }
}
