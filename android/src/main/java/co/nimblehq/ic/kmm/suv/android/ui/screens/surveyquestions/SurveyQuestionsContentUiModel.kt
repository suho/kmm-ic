package co.nimblehq.ic.kmm.suv.android.ui.screens.surveyquestions

import co.nimblehq.ic.kmm.suv.domain.model.QuestionDisplayType

data class SurveyQuestionsContentUiModel(
    val isLoading: Boolean,
    val backgroundUrl: String,
    val questions: List<QuestionContentUiModel>
)

data class QuestionContentUiModel(
    val progress: String,
    val title: String,
    val displayType: QuestionDisplayType
)
