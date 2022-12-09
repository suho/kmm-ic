package co.nimblehq.ic.kmm.suv.android.ui.screens.surveydetail

import co.nimblehq.ic.kmm.suv.android.ui.base.BaseViewModel
import co.nimblehq.ic.kmm.suv.android.ui.screens.home.SurveyArgument
import co.nimblehq.ic.kmm.suv.android.ui.screens.surveyquestions.SurveyQuestionsArgument
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class SurveyDetailViewModel : BaseViewModel() {

    private lateinit var surveyArgument: SurveyArgument
    private val _contentUiModel = MutableStateFlow<SurveyDetailContentUiModel?>(null)

    val contentUiModel: StateFlow<SurveyDetailContentUiModel?>
        get() = _contentUiModel

    val surveyQuestionsArgument: SurveyQuestionsArgument?
        get() {
            return if (this::surveyArgument.isInitialized) {
                SurveyQuestionsArgument(
                    id = surveyArgument.id,
                    coverImageUrl = surveyArgument.coverImageUrl
                )
            } else {
                null
            }
        }

    fun set(surveyArgument: SurveyArgument?) {
        surveyArgument?.let {
            this.surveyArgument = surveyArgument
            _contentUiModel.value = SurveyDetailContentUiModel(
                it.title,
                it.description,
                it.coverImageUrl
            )
        }

    }
}
