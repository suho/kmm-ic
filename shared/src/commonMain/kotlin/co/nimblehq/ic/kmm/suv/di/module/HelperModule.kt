package co.nimblehq.ic.kmm.suv.di.module

import co.nimblehq.ic.kmm.suv.helper.date.DateTime
import co.nimblehq.ic.kmm.suv.helper.date.DateTimeFormatter
import co.nimblehq.ic.kmm.suv.helper.date.DateTimeFormatterImpl
import co.nimblehq.ic.kmm.suv.helper.date.DateTimeImpl
import org.koin.dsl.module

val helperModule = module {
    single<DateTime> { DateTimeImpl() }
    single<DateTimeFormatter> { DateTimeFormatterImpl() }
}
