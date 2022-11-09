package co.nimblehq.ic.kmm.suv.helper.date

import android.annotation.SuppressLint
import kotlinx.datetime.LocalDate
import java.text.SimpleDateFormat

@SuppressLint("SimpleDateFormat")
actual class DateTimeFormatterImpl: DateTimeFormatter {

    actual override fun getFormattedString(
        localDate: LocalDate,
        format: DateFormat
    ): String {
        val date = SimpleDateFormat(DateFormat.YearMonthDay.value)
            .parse(localDate.toString()) ?: return ""
        return SimpleDateFormat(format.value).format(date)
    }
}
