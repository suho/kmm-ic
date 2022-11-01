package co.nimblehq.ic.kmm.suv.helper.date

import kotlinx.datetime.LocalDate

actual class DateTimeFormatterImpl: DateTimeFormatter {

    actual override fun getFormattedString(
        localDate: LocalDate,
        format: DateFormat
    ): String {
        val date = DateFormatter.yearMonthDay.dateFromString(localDate.toString()) ?: return ""
        return DateFormatter.getBy(format).stringFromDate(date)
    }
}
