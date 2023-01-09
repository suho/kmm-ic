package co.nimblehq.ic.kmm.suv.di.module

import co.nimblehq.ic.kmm.suv.domain.usecase.*
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val useCaseModule = module {
    singleOf(::LogInUseCaseImpl) bind LogInUseCase::class
    singleOf(::GetProfileUseCaseImpl) bind GetProfileUseCase::class
    singleOf(::GetSurveysUseCaseImpl) bind GetSurveysUseCase::class
    singleOf(::GetSurveyDetailUseCaseImpl) bind GetSurveyDetailUseCase::class
    singleOf(::SubmitSurveyResponseUseCaseImpl) bind SubmitSurveyResponseUseCase::class
}
