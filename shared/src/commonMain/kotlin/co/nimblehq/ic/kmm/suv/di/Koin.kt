package co.nimblehq.ic.kmm.suv.di

import co.nimblehq.ic.kmm.suv.di.module.networkModule
import co.nimblehq.ic.kmm.suv.di.module.platformModule
import co.nimblehq.ic.kmm.suv.di.module.repositoryModule
import co.nimblehq.ic.kmm.suv.di.module.useCaseModule
import org.koin.core.KoinApplication
import org.koin.core.context.startKoin

fun initKoin() : KoinApplication {
    val dataModules = listOf(networkModule, repositoryModule)
    val domainModules = listOf(useCaseModule)
    return startKoin {
        modules(
            domainModules + dataModules + platformModule()
        )
    }
}
