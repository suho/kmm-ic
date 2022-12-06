package co.nimblehq.ic.kmm.suv.di.module

import co.nimblehq.ic.kmm.suv.helper.date.DateTime
import co.nimblehq.ic.kmm.suv.helper.date.DateTimeFormatter
import co.nimblehq.ic.kmm.suv.helper.date.DateTimeFormatterImpl
import co.nimblehq.ic.kmm.suv.helper.date.DateTimeImpl
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val helperModule = module {
    singleOf(::DateTimeImpl) { bind<DateTime>() }
    singleOf(::DateTimeFormatterImpl) { bind<DateTimeFormatter>() }
}
