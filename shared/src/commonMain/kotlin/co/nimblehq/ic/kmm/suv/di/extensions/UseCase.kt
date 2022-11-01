package co.nimblehq.ic.kmm.suv.di.extensions

import co.nimblehq.ic.kmm.suv.di.initKoin
import co.nimblehq.ic.kmm.suv.domain.usecase.*
import org.koin.core.Koin
import org.koin.core.KoinApplication

fun KoinApplication.Companion.start(): KoinApplication = initKoin()

// Define all UseCases here for iOS
val Koin.logInUseCase: LogInUseCase
    get() = get()

val Koin.getHomeHeaderUseCase: GetProfileUseCase
    get() = get()
