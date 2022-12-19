package co.nimblehq.ic.kmm.suv.domain.model

data class QuestionAndAnswers(
    val questionIndex: Int,
    val answerInputs: List<AnswerInput>
)

sealed class AnswerInput {

    abstract val id: String

    data class Select(override val id: String) : AnswerInput()

    data class Content(override val id: String, val content: String) : AnswerInput()
}

fun AnswerInput.getContentType(): AnswerInput.Content? {
    return when (this) {
        is AnswerInput.Content -> this
        else -> null
    }
}
