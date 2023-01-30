package co.nimblehq.ic.kmm.suv.di.extensions

import co.nimblehq.ic.kmm.suv.di.initKoin
import co.nimblehq.ic.kmm.suv.domain.usecase.*
import org.koin.core.Koin
import org.koin.core.KoinApplication

fun KoinApplication.Companion.start(): KoinApplication = initKoin()

// Define all UseCases here for iOS
val Koin.logInUseCase: LogInUseCase
    get() = get()

val Koin.getProfileUseCase: GetProfileUseCase
    get() = get()

val Koin.getSurveysUseCase: GetSurveysUseCase
    get() = get()

val Koin.getSurveyDetailUseCase: GetSurveyDetailUseCase
    get() = get()

val Koin.submitSurveyResponseUseCase: SubmitSurveyResponseUseCase
    get() = get()
