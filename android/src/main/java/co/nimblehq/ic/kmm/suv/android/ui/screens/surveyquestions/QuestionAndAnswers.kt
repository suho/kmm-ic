package co.nimblehq.ic.kmm.suv.android.ui.screens.surveyquestions

data class QuestionAndAnswers(
    val questionIndex: Int,
    val answerInputs: List<AnswerInput>
)

sealed class AnswerInput {

    data class Index(val index: Int) : AnswerInput()
    data class Content(val index: Int, val content: String) : AnswerInput()
}
