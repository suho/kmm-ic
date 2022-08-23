package co.nimblehq.suvkmmsurveys.ui

import android.content.Context
import co.nimblehq.suvkmmsurveys.R

fun Throwable.userReadableMessage(context: Context): String {
    return context.getString(R.string.error_generic)
}
