package co.nimblehq.ic.kmm.suv.data.remote.model

import co.nimblehq.ic.kmm.suv.data.local.model.SurveyRealmObject
import co.nimblehq.ic.kmm.suv.domain.model.Survey
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SurveyApiModel(
    val id: String,
    val type: String,
    val title: String,
    val description: String,
    @SerialName("is_active")
    val isActive: Boolean,
    @SerialName("cover_image_url")
    val coverImageUrl: String
)

fun SurveyApiModel.toSurvey() = Survey(
    title,
    description,
    isActive,
    coverImageUrl
)


fun SurveyApiModel.toSurveyRealmObject(): SurveyRealmObject {
    val survey = SurveyRealmObject()
    survey.id = id
    survey.type = type
    survey.title = title
    survey.description = description
    survey.isActive = isActive
    survey.coverImageUrl = coverImageUrl
    return survey
}
