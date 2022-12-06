package co.nimblehq.ic.kmm.suv.android.ui.screens.home

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class SurveyArgument(
    val id: String,
    val title: String,
    val description: String,
    val coverImageUrl: String
) : Parcelable
