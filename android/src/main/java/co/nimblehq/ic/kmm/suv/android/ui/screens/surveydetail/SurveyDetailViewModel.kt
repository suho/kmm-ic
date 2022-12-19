package co.nimblehq.ic.kmm.suv.android.ui.screens.surveydetail

import co.nimblehq.ic.kmm.suv.android.ui.base.BaseViewModel
import co.nimblehq.ic.kmm.suv.android.ui.screens.home.SurveyArgument
import co.nimblehq.ic.kmm.suv.android.ui.screens.surveyquestions.SurveyQuestionsArgument
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class SurveyDetailViewModel : BaseViewModel() {

    private var surveyArgument: SurveyArgument? = null
    private val _contentUiModel = MutableStateFlow<SurveyDetailContentUiModel?>(null)

    val contentUiModel: StateFlow<SurveyDetailContentUiModel?>
        get() = _contentUiModel

    val surveyQuestionsArgument: SurveyQuestionsArgument?
        get() = surveyArgument?.let {
            SurveyQuestionsArgument(
                id = it.id,
                coverImageUrl = it.coverImageUrl
            )
        }

    fun set(surveyArgument: SurveyArgument?) {
        this.surveyArgument = surveyArgument
        surveyArgument?.let {
            _contentUiModel.value = SurveyDetailContentUiModel(
                it.title,
                it.description,
                it.coverImageUrl
            )
        }

    }
}
