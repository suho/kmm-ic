package co.nimblehq.ic.kmm.suv.android

import android.app.Application
import co.nimblehq.ic.kmm.suv.di.initKoin

class MainApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        initKoin()
    }
}
