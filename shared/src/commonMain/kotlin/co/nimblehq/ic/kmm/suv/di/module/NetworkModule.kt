package co.nimblehq.ic.kmm.suv.di.module

import co.nimblehq.ic.kmm.suv.data.network.core.provideHttpClient
import co.nimblehq.ic.kmm.suv.data.network.service.UserService
import co.nimblehq.ic.kmm.suv.data.network.service.UserServiceImpl
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val networkModule = module {
    singleOf(::provideHttpClient)
    single<UserService> { UserServiceImpl(get()) }
}
