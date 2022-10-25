package co.nimblehq.ic.kmm.suv.android

import android.app.Application
import co.nimblehq.ic.kmm.suv.android.di.viewModelModule
import co.nimblehq.ic.kmm.suv.di.initKoin
import org.koin.android.ext.koin.androidContext
import timber.log.Timber

class MainApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        initKoin {
            androidContext(applicationContext)
            modules(viewModelModule)
        }
        setupLogging()
    }

    private fun setupLogging() {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}
