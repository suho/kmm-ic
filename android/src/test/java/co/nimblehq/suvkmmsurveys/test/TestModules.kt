package co.nimblehq.suvkmmsurveys.test

import co.nimblehq.suvkmmsurveys.ui.screens.MainNavigator
import dagger.Module
import dagger.Provides
import dagger.hilt.android.components.FragmentComponent
import dagger.hilt.testing.TestInstallIn
import io.mockk.mockk

@Module
@TestInstallIn(
    components = [FragmentComponent::class],
    replaces = [NavigatorModule::class]
)
object TestNavigatorModule {
    val mockMainNavigator = mockk<MainNavigator>()

    @Provides
    fun provideMainNavigator() = mockMainNavigator
}
