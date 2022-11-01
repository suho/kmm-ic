package co.nimblehq.ic.kmm.suv.helper.date
import platform.Foundation.*

class DateFormatter {

    companion object {
        val weekDayMonthDay: NSDateFormatter = NSDateFormatter()
            .also {
                it.timeZone = NSTimeZone.localTimeZone
                it.locale = NSLocale.autoupdatingCurrentLocale
                it.dateFormat = DateFormat.WeekDayMonthDay.value
            }

        val yearMonthDay: NSDateFormatter = NSDateFormatter()
            .also {
                it.timeZone = NSTimeZone.localTimeZone
                it.locale = NSLocale.autoupdatingCurrentLocale
                it.dateFormat = DateFormat.YearMonthDay.value
            }
    }
}

fun DateFormatter.Companion.getBy(dateFormat: DateFormat): NSDateFormatter {
    return when (dateFormat) {
        is DateFormat.WeekDayMonthDay -> DateFormatter.weekDayMonthDay
        is DateFormat.YearMonthDay -> DateFormatter.weekDayMonthDay
    }
}
