package co.nimblehq.ic.kmm.suv.android.ui.screens.surveyquestions

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class SurveyQuestionsArgument(
    val id: String,
    val coverImageUrl: String
) : Parcelable
