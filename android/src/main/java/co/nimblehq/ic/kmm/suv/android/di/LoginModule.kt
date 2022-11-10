package co.nimblehq.ic.kmm.suv.android.di

import co.nimblehq.ic.kmm.suv.android.ui.screens.login.LoginViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val loginModule = module {
    viewModelOf(::LoginViewModel)
}
