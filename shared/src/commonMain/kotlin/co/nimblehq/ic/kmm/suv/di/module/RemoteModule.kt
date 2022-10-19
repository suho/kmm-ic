package co.nimblehq.ic.kmm.suv.di.module

import co.nimblehq.ic.kmm.suv.data.remote.datasource.TokenRemoteDataSource
import co.nimblehq.ic.kmm.suv.data.remote.datasource.TokenRemoteDataSourceImpl
import co.nimblehq.ic.kmm.suv.data.remote.httpclient.core.provideHttpClient
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val remoteModule = module {
    singleOf(::provideHttpClient)
    single<TokenRemoteDataSource> { TokenRemoteDataSourceImpl(get()) }
}
