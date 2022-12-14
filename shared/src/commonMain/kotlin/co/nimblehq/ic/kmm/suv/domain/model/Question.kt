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
    val sortedAnswers: List<Answer>
        get() = answers.sortedBy { it.displayOrder }
}

sealed class QuestionDisplayType {

    object Intro : QuestionDisplayType()
    object Star : QuestionDisplayType()
    object Heart : QuestionDisplayType()
    object Smiley : QuestionDisplayType()
    data class Choice(val answers: List<String>) :
        QuestionDisplayType()

    object Nps : QuestionDisplayType()
    data class Textarea(val placeholder: String) :
        QuestionDisplayType()

    object Textfield : QuestionDisplayType()
    object Dropdown : QuestionDisplayType()
    object Outro : QuestionDisplayType()
    object Unsupported : QuestionDisplayType()
}


fun Question.displayType(): QuestionDisplayType {
    return when (displayType) {
        QUESTION_DISPLAY_TYPE_INTRO -> QuestionDisplayType.Intro
        QUESTION_DISPLAY_TYPE_STAR -> QuestionDisplayType.Star
        QUESTION_DISPLAY_TYPE_HEART -> QuestionDisplayType.Heart
        QUESTION_DISPLAY_TYPE_SMILEY -> QuestionDisplayType.Smiley
        QUESTION_DISPLAY_TYPE_CHOICE -> QuestionDisplayType.Choice(sortedAnswers.map {
            it.text.orEmpty()
        })
        QUESTION_DISPLAY_TYPE_NPS -> QuestionDisplayType.Nps
        QUESTION_DISPLAY_TYPE_TEXTAREA -> QuestionDisplayType.Textarea(
            sortedAnswers.first().inputMaskPlaceholder ?: ""
        )
        QUESTION_DISPLAY_TYPE_TEXTFIELD -> QuestionDisplayType.Textfield
        QUESTION_DISPLAY_TYPE_DROPDOWN -> QuestionDisplayType.Dropdown
        QUESTION_DISPLAY_TYPE_OUTRO -> QuestionDisplayType.Outro
        else -> QuestionDisplayType.Unsupported
    }
}
