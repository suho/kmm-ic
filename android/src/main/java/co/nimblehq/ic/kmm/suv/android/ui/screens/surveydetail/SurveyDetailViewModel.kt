package co.nimblehq.ic.kmm.suv.android.ui.screens.surveydetail

import co.nimblehq.ic.kmm.suv.android.ui.base.BaseViewModel
import co.nimblehq.ic.kmm.suv.android.ui.screens.home.SurveyArgument
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class SurveyDetailViewModel : BaseViewModel() {

    private val _contentUiModel = MutableStateFlow<SurveyDetailContentUiModel?>(null)

    val contentUiModel: StateFlow<SurveyDetailContentUiModel?>
        get() = _contentUiModel

    fun set(surveyArgument: SurveyArgument?) {
        surveyArgument?.let {
            _contentUiModel.value = SurveyDetailContentUiModel(
                it.title,
                it.description,
                it.coverImageUrl
            )
        }

    }
}
