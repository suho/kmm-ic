package co.nimblehq.ic.kmm.suv.di

import co.nimblehq.ic.kmm.suv.di.module.*
import org.koin.core.KoinApplication
import org.koin.core.context.startKoin

fun initKoin() : KoinApplication {
    val dataModules = listOf(remoteModule, repositoryModule)
    val domainModules = listOf(useCaseModule)
    return startKoin {
        modules(
            domainModules + dataModules + platformModule()
        )
    }
}
