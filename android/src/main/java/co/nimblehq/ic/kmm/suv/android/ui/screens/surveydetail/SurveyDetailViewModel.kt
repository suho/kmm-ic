package co.nimblehq.ic.kmm.suv.android.ui.screens.surveydetail

import co.nimblehq.ic.kmm.suv.android.ui.base.BaseViewModel
import co.nimblehq.ic.kmm.suv.android.ui.screens.home.SurveyArgument

class SurveyDetailViewModel(
    val survey: SurveyArgument
) : BaseViewModel() {

    val contentUiModel = SurveyDetailContentUiModel(
        survey.title,
        survey.description,
        survey.coverImageUrl
    )
}
