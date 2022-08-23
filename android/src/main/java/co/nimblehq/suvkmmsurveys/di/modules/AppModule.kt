package co.nimblehq.suvkmmsurveys.di.modules

import co.nimblehq.suvkmmsurveys.util.DispatchersProvider
import co.nimblehq.suvkmmsurveys.util.DispatchersProviderImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    @Provides
    fun provideDispatchersProvider(): DispatchersProvider {
        return DispatchersProviderImpl()
    }
}
