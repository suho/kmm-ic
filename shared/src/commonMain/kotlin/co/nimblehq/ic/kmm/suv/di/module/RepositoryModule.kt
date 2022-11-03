package co.nimblehq.ic.kmm.suv.di.module

import co.nimblehq.ic.kmm.suv.data.repository.TokenRepositoryImpl
import co.nimblehq.ic.kmm.suv.domain.repository.TokenRepository
import org.koin.dsl.module

val repositoryModule = module {
    single<TokenRepository> { TokenRepositoryImpl(get(), get()) }
}
