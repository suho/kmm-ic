package co.nimblehq.ic.kmm.suv.di

import co.nimblehq.ic.kmm.suv.di.module.*
import org.koin.core.KoinApplication
import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration

fun initKoin(appDeclaration: KoinAppDeclaration = {}): KoinApplication {
    val dataModules = listOf(localModule, remoteModule, repositoryModule)
    val domainModules = listOf(useCaseModule)
    return startKoin {
        appDeclaration()
        modules(
            domainModules + dataModules + helperModule + platformModule()
        )
    }
}
