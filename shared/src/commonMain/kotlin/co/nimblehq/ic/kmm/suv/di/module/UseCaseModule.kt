package co.nimblehq.ic.kmm.suv.di.module

import co.nimblehq.ic.kmm.suv.domain.usecase.LogInUseCase
import co.nimblehq.ic.kmm.suv.domain.usecase.LogInUseCaseImpl
import org.koin.dsl.module

val useCaseModule = module {
    single<LogInUseCase> { LogInUseCaseImpl(get()) }
}
