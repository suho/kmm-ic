package co.nimblehq.ic.kmm.suv.domain.model

data class Question(
    val id: String,
    val text: String,
    val displayOrder: Int,
    val displayType: QuestionDisplayType,
    val pick: String,
    val coverImageUrl: String,
    val answers: List<Answer>
)

enum class QuestionDisplayType(val value: String) {
    INTRO("intro"),
    STAR("star"),
    HEART("heart"),
    SMILEY("smiley"),
    CHOICE("choice"),
    NPS("nps"),
    TEXTAREA("textarea"),
    TEXTFIELD("textfield"),
    DROPDOWN("dropdown"),
    OUTRO("outro"),
    UNSUPPORTED("unsupported"),
}

fun Question.sortedAnswers(): List<Answer> {
    return answers.sortedBy { it.displayOrder }
}
