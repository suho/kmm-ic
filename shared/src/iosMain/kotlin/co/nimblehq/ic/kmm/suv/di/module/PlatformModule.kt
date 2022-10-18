package co.nimblehq.ic.kmm.suv.di.module

import io.ktor.client.engine.darwin.*
import org.koin.core.module.Module
import org.koin.dsl.module

actual fun platformModule(): Module = module {
    single {  Darwin.create() }
}
