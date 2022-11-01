package co.nimblehq.ic.kmm.suv.di.module

import co.nimblehq.ic.kmm.suv.data.local.datasource.TokenLocalDataSource
import co.nimblehq.ic.kmm.suv.data.local.datasource.TokenLocalDataSourceImpl
import org.koin.dsl.module

val localModule = module {
    single<TokenLocalDataSource> { TokenLocalDataSourceImpl(get()) }
}
