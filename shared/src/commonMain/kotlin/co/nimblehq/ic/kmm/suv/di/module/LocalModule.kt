package co.nimblehq.ic.kmm.suv.di.module

import co.nimblehq.ic.kmm.suv.data.local.datasource.SurveyLocalDataSource
import co.nimblehq.ic.kmm.suv.data.local.datasource.SurveyLocalDataSourceImpl
import co.nimblehq.ic.kmm.suv.data.local.datasource.TokenLocalDataSource
import co.nimblehq.ic.kmm.suv.data.local.datasource.TokenLocalDataSourceImpl
import co.nimblehq.ic.kmm.suv.data.local.realm.realm
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val localModule = module {
    single { realm }
    singleOf(::TokenLocalDataSourceImpl) { bind<TokenLocalDataSource>() }
    singleOf(::SurveyLocalDataSourceImpl) { bind<SurveyLocalDataSource>() }
}
