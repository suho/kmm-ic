package co.nimblehq.ic.kmm.suv.android.ui.screens.surveyquestions

sealed class AnswerInput {

    data class Index(val index: Int) : AnswerInput()
    data class Content(val index: Int, val content: String) : AnswerInput()
}
