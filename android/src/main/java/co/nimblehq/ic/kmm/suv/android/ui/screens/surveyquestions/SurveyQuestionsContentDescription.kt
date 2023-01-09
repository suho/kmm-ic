package co.nimblehq.ic.kmm.suv.android.ui.screens.surveyquestions

object SurveyQuestionsContentDescription {
    const val BUTTON_CLOSE = "BUTTON_CLOSE"
    const val BUTTON_NEXT_OR_SUBMIT = "BUTTON_NEXT_OR_SUBMIT"
    private const val LABEL_PROGRESS = "LABEL_PROGRESS"
    private const val LABEL_QUESTION_TITLE = "LABEL_QUESTION_TITLE"

    fun progressLabel(index: Int): String = "$LABEL_PROGRESS-$index"
    fun questionTitleLabel(index: Int): String = "$LABEL_QUESTION_TITLE-$index"
}
