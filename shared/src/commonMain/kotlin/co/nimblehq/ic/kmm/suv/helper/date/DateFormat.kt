package co.nimblehq.ic.kmm.suv.helper.date

sealed class DateFormat(val value: String) {
    object WeekDayMonthDay: DateFormat("EEEE, MMMM d")
    object YearMonthDay: DateFormat("yyyy-MM-dd")
}
