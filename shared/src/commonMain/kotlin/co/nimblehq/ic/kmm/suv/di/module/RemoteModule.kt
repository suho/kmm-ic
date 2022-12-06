package co.nimblehq.ic.kmm.suv.di.module

import co.nimblehq.ic.kmm.suv.data.remote.apiclient.core.ApiClient
import co.nimblehq.ic.kmm.suv.data.remote.datasource.*
import org.koin.core.module.dsl.singleOf
import org.koin.core.qualifier.named
import org.koin.dsl.bind
import org.koin.dsl.module

private const val UNAUTHORIZED_API_CLIENT = "UNAUTHORIZED_API_CLIENT"

val remoteModule = module {
    singleOf(::ApiClient)
    single(named(UNAUTHORIZED_API_CLIENT)) { ApiClient(get()) }
    single<TokenRemoteDataSource> { TokenRemoteDataSourceImpl(get(named(UNAUTHORIZED_API_CLIENT))) }
    singleOf(::UserRemoteDataSourceImpl) bind UserRemoteDataSource::class
    singleOf(::SurveyRemoteDataSourceImpl) bind SurveyRemoteDataSource::class
}
