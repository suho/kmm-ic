package co.nimblehq.ic.kmm.suv.di.module

import co.nimblehq.ic.kmm.suv.data.repository.SurveyRepositoryImpl
import co.nimblehq.ic.kmm.suv.data.repository.TokenRepositoryImpl
import co.nimblehq.ic.kmm.suv.data.repository.UserRepositoryImpl
import co.nimblehq.ic.kmm.suv.domain.repository.SurveyRepository
import co.nimblehq.ic.kmm.suv.domain.repository.TokenRepository
import co.nimblehq.ic.kmm.suv.domain.repository.UserRepository
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val repositoryModule = module {
    singleOf(::TokenRepositoryImpl) bind TokenRepository::class
    singleOf(::UserRepositoryImpl) bind UserRepository::class
    singleOf(::SurveyRepositoryImpl) bind SurveyRepository::class
}
