package co.nimblehq.ic.kmm.suv.android.ui.screens.surveyquestions

data class SurveyQuestionsContentUiModel(
    val isLoading: Boolean,
    val backgroundUrl: String,
    val questions: List<QuestionContentUiModel>
)

data class QuestionContentUiModel(
    val order: String,
    val title: String
)
