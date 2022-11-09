package co.nimblehq.ic.kmm.suv.helper.date

import kotlinx.datetime.*

interface DateTime {

    fun today(): LocalDate
}

class DateTimeImpl: DateTime {

    override fun today(): LocalDate {
        return Clock.System.todayIn(TimeZone.currentSystemDefault())
    }
}


