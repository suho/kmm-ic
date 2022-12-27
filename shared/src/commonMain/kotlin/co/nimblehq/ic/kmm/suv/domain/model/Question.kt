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
const val QUESTION_DISPLAY_TYPE_UNSUPPORTED = "unsupported"

data class Question(
    val id: String,
    val text: String,
    val displayOrder: Int,
    val displayType: String,
    val pick: String,
    val coverImageUrl: String,
    val answers: List<Answer>
) {
    private val sortedAnswers: List<Answer>
        get() = answers.sortedBy { it.displayOrder }

    val answerTexts: List<String>
        get() = sortedAnswers.map { it.text.orEmpty() }

    val answerPlaceholders: List<String>
        get() = sortedAnswers.map { it.inputMaskPlaceholder.orEmpty() }
}

sealed class QuestionDisplayType {

    object Intro : QuestionDisplayType()
    object Star : QuestionDisplayType()
    object Heart : QuestionDisplayType()
    object Smiley : QuestionDisplayType()
    data class Choice(val answers: List<String>) : QuestionDisplayType()
    object Nps : QuestionDisplayType()
    data class Textarea(val placeholder: String) : QuestionDisplayType()
    data class Textfield(val placeholders: List<String>) : QuestionDisplayType()
    data class Dropdown(val answers: List<String>) : QuestionDisplayType()
    object Outro : QuestionDisplayType()
    object Unsupported : QuestionDisplayType()
}

fun Question.displayType(): QuestionDisplayType {
    return when (displayType) {
        QUESTION_DISPLAY_TYPE_INTRO -> QuestionDisplayType.Intro
        QUESTION_DISPLAY_TYPE_STAR -> QuestionDisplayType.Star
        QUESTION_DISPLAY_TYPE_HEART -> QuestionDisplayType.Heart
        QUESTION_DISPLAY_TYPE_SMILEY -> QuestionDisplayType.Smiley
        QUESTION_DISPLAY_TYPE_CHOICE -> QuestionDisplayType.Choice(answerTexts)
        QUESTION_DISPLAY_TYPE_NPS -> QuestionDisplayType.Nps
        QUESTION_DISPLAY_TYPE_TEXTAREA -> QuestionDisplayType.Textarea(answerPlaceholders.first())
        QUESTION_DISPLAY_TYPE_TEXTFIELD -> QuestionDisplayType.Textfield(answerTexts)
        QUESTION_DISPLAY_TYPE_DROPDOWN -> QuestionDisplayType.Dropdown(answerTexts)
        QUESTION_DISPLAY_TYPE_OUTRO -> QuestionDisplayType.Outro
        else -> QuestionDisplayType.Unsupported
    }
}
