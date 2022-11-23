package co.nimblehq.ic.kmm.suv.android.ui.screens.home

import androidx.lifecycle.viewModelScope
import co.nimblehq.ic.kmm.suv.android.ui.base.BaseViewModel
import co.nimblehq.ic.kmm.suv.android.ui.screens.home.views.HomeSurveyUiModel
import co.nimblehq.ic.kmm.suv.android.ui.screens.home.views.HomeSurveysUiModel
import co.nimblehq.ic.kmm.suv.domain.model.Survey
import co.nimblehq.ic.kmm.suv.domain.usecase.GetProfileUseCase
import co.nimblehq.ic.kmm.suv.domain.usecase.GetSurveysUseCase
import co.nimblehq.ic.kmm.suv.helper.date.DateFormat
import co.nimblehq.ic.kmm.suv.helper.date.DateTime
import co.nimblehq.ic.kmm.suv.helper.date.DateTimeFormatter
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class HomeViewModel(
    private val getProfileUseCase: GetProfileUseCase,
    private val getSurveysUseCase: GetSurveysUseCase,
    dateTime: DateTime,
    dateTimeFormatter: DateTimeFormatter
) : BaseViewModel() {

    private val _currentDate = MutableStateFlow("")
    val currentDate: StateFlow<String> = _currentDate.asStateFlow()

    private val _avatarUrlString = MutableStateFlow("")
    val avatarUrlString: StateFlow<String> = _avatarUrlString.asStateFlow()

    private val _surveysUiModel: MutableStateFlow<HomeSurveysUiModel?> = MutableStateFlow(null)
    val surveysUiModel: StateFlow<HomeSurveysUiModel?> = _surveysUiModel.asStateFlow()

    private lateinit var surveys: List<Survey>
    private var currentSurveyIndex: Int = 0

    init {
        _currentDate.value = dateTimeFormatter.getFormattedString(
            dateTime.today(),
            DateFormat.WeekDayMonthDay
        ).uppercase()
    }

    fun loadProfileAndSurveys() {
        showLoading()
        viewModelScope.launch {
            val getProfile = getProfileUseCase()
            // TODO: Improve this when we have paging implementation
            val getSurveys = getSurveysUseCase(1, 5)
            getProfile
                .combine(getSurveys) { profile, surveys -> Pair(profile, surveys) }
                .catch { e ->
                    showError(e.message)
                }
                .collect {
                    _avatarUrlString.value = it.first.avatarUrl
                    surveys = it.second
                    _surveysUiModel.value = HomeSurveysUiModel(
                        surveys = surveys.map { survey ->
                            HomeSurveyUiModel(
                                survey.title,
                                survey.description,
                                survey.coverImageUrl
                            )
                        },
                        currentPageIndex = 0
                    )
                }
            hideLoading()
        }
    }

    fun showPreviousSurvey() {
        if (currentSurveyIndex > 0) {
            currentSurveyIndex -= 1
            updateCurrentSurvey()
        }
    }

    fun showNextSurvey() {
        if (currentSurveyIndex < surveys.size - 1) {
            currentSurveyIndex += 1
            updateCurrentSurvey()
        }
    }

    private fun updateCurrentSurvey() {
        _surveysUiModel.value = _surveysUiModel.value?.copy(currentPageIndex = currentSurveyIndex)
    }
}
