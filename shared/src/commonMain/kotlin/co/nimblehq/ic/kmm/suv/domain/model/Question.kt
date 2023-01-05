package co.nimblehq.ic.kmm.suv.domain.model

const val QUESTION_DISPLAY_TYPE_INTRO = "intro"
const val QUESTION_DISPLAY_TYPE_STAR = "star"
const val QUESTION_DISPLAY_TYPE_HEART = "heart"
const val QUESTION_DISPLAY_TYPE_SMILEY = "smiley"
const val QUESTION_DISPLAY_TYPE_CHOICE = "choice"
const val QUESTION_DISPLAY_TYPE_NPS = "nps"
const val QUESTION_DISPLAY_TYPE_TEXTAREA = "textarea"
const val QUESTION_DISPLAY_TYPE_TEXTFIELD = "textfield"
const val QUESTION_DISPLAY_TYPE_DROPDOWN = "dropdown"
const val QUESTION_DISPLAY_TYPE_OUTRO = "outro"

data class Question(
    val id: String,
    val text: String,
    val displayOrder: Int,
    val displayType: String,
    val pick: String,
    val coverImageUrl: String,
    val answers: List<Answer>
) {
    val sortedAnswers: List<Answer>
        get() = answers.sortedBy { it.displayOrder }
}

sealed class QuestionDisplayType(
    open val answers: List<Answerable> = emptyList(),
    open var input: List<AnswerInput> = emptyList()
) {

    object Intro : QuestionDisplayType()

    data class Star(
        override val answers: List<Answerable>
    ) : QuestionDisplayType(answers = answers)

    data class Heart(
        override val answers: List<Answerable>
    ) : QuestionDisplayType(answers = answers)

    data class Smiley(
        override val answers: List<Answerable>
    ) : QuestionDisplayType(answers = answers)

    data class Choice(
        override val answers: List<Answerable>
    ) : QuestionDisplayType(answers = answers)

    data class Nps(
        override val answers: List<Answerable>
    ) : QuestionDisplayType(answers = answers)

    data class Textarea(
        override val answers: List<Answerable>
    ) : QuestionDisplayType(answers = answers)

    data class Textfield(
        override val answers: List<Answerable>
    ) : QuestionDisplayType(answers = answers)

    data class Dropdown(
        override val answers: List<Answerable>
    ) : QuestionDisplayType(answers = answers)

    object Outro : QuestionDisplayType()
    object Unsupported : QuestionDisplayType()
}

fun Question.displayType(): QuestionDisplayType {
    return when (displayType) {
        QUESTION_DISPLAY_TYPE_INTRO -> QuestionDisplayType.Intro
        QUESTION_DISPLAY_TYPE_STAR -> QuestionDisplayType.Star(answers = sortedAnswers)
        QUESTION_DISPLAY_TYPE_HEART -> QuestionDisplayType.Heart(answers = sortedAnswers)
        QUESTION_DISPLAY_TYPE_SMILEY -> QuestionDisplayType.Smiley(answers = sortedAnswers)
        QUESTION_DISPLAY_TYPE_CHOICE -> QuestionDisplayType.Choice(answers = sortedAnswers)
        QUESTION_DISPLAY_TYPE_NPS -> QuestionDisplayType.Nps(answers = sortedAnswers)
        QUESTION_DISPLAY_TYPE_TEXTAREA -> QuestionDisplayType.Textarea(answers = sortedAnswers)
        QUESTION_DISPLAY_TYPE_TEXTFIELD -> QuestionDisplayType.Textfield(answers = sortedAnswers)
        QUESTION_DISPLAY_TYPE_DROPDOWN -> QuestionDisplayType.Dropdown(answers = sortedAnswers)
        QUESTION_DISPLAY_TYPE_OUTRO -> QuestionDisplayType.Outro
        else -> QuestionDisplayType.Unsupported
    }
}
