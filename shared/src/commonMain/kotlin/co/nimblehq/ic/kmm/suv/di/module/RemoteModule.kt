package co.nimblehq.ic.kmm.suv.di.module

import co.nimblehq.ic.kmm.suv.data.remote.datasource.TokenRemoteDataSource
import co.nimblehq.ic.kmm.suv.data.remote.datasource.TokenRemoteDataSourceImpl
import co.nimblehq.ic.kmm.suv.data.remote.apiclient.core.ApiClient
import org.koin.core.qualifier.named
import org.koin.dsl.module

private const val UNAUTHORIZED_API_CLIENT = "UNAUTHORIZED_API_CLIENT"

val remoteModule = module {
    single { ApiClient(get(), get(), get()) }
    single(named(UNAUTHORIZED_API_CLIENT)) { ApiClient(get()) }
    single<TokenRemoteDataSource> { TokenRemoteDataSourceImpl(get(named(UNAUTHORIZED_API_CLIENT))) }
}
