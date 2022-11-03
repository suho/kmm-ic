package co.nimblehq.ic.kmm.suv.di.module

import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.russhwolf.settings.Settings
import com.russhwolf.settings.SharedPreferencesSettings
import io.ktor.client.engine.android.*
import org.koin.core.module.Module
import org.koin.dsl.module

private const val APP_SECRET_SHARED_PREFS = "app_secret_shared_prefs"

actual fun platformModule(): Module = module {
    single { Android.create() }

    single<Settings> {
        SharedPreferencesSettings(EncryptedSharedPreferences.create(
            get(),
            APP_SECRET_SHARED_PREFS,
            MasterKey.Builder(get()).setKeyScheme(MasterKey.KeyScheme.AES256_GCM).build(),
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        ))
    }
}
