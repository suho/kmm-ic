package co.nimblehq.ic.kmm.suv.di.extensions

import co.nimblehq.ic.kmm.suv.domain.usecase.GetProfileUseCase
import co.nimblehq.ic.kmm.suv.domain.usecase.LogInUseCase
import co.nimblehq.ic.kmm.suv.helper.date.DateTime
import co.nimblehq.ic.kmm.suv.helper.date.DateTimeFormatter
import org.koin.core.Koin

// Define all Helpers here for iOS
val Koin.dateTime: DateTime
    get() = get()

val Koin.dateTimeFormatter: DateTimeFormatter
    get() = get()
