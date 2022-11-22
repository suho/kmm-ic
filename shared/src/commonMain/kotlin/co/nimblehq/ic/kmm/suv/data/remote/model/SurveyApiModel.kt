package co.nimblehq.ic.kmm.suv.data.remote.model

import co.nimblehq.ic.kmm.suv.data.local.model.SurveyRealmObject
import co.nimblehq.ic.kmm.suv.domain.model.Survey
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SurveyApiModel(
    @SerialName("id")
    val id: String,
    @SerialName("type")
    val type: String,
    @SerialName("title")
    val title: String,
    @SerialName("description")
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

fun SurveyApiModel.toSurveyRealmObject() = SurveyRealmObject(
    id = id,
    type = type,
    title = title,
    description = description,
    isActive = isActive,
    coverImageUrl = coverImageUrl
)
