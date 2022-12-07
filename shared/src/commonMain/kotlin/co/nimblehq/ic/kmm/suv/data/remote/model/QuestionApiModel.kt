package co.nimblehq.ic.kmm.suv.data.remote.model

import co.nimblehq.ic.kmm.suv.domain.model.Question
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class QuestionApiModel(
    @SerialName("id")
    val id: String,
    @SerialName("text")
    val text: String,
    @SerialName("display_order")
    val displayOrder: Int,
    @SerialName("display_type")
    val displayType: String,
    @SerialName("pick")
    val pick: String,
    @SerialName("cover_image_url")
    val coverImageUrl: Url,
    @SerialName("answers")
    val answers: List<AnswerApiModel>
)

fun QuestionApiModel.toQuestion(): Question = Question(
    id = id,
    text = text,
    displayOrder = displayOrder,
    displayType = displayType,
    pick = pick,
    coverImageUrl = coverImageUrl.string,
    answers = answers.map { it.toAnswer() }
)
