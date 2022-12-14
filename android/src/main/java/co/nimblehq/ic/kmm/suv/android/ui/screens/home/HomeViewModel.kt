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
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
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
        loadProfile()
        loadSurveys()
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

    fun getCurrentSurveyArgument(): SurveyArgument {
        return surveys[currentSurveyIndex].run {
            SurveyArgument(
                id,
                title,
                description,
                coverImageUrl
            )
        }
    }

    private fun loadProfile() {
        showLoading()
        viewModelScope.launch {
            getProfileUseCase()
                .catch { e ->
                    showError(e.message)
                }
                .collect {
                    _avatarUrlString.value = it.avatarUrl
                }
            hideLoading()
        }
    }

    private fun loadSurveys() {
        showLoading()
        viewModelScope.launch {
            // TODO: Improve this when we have paging implementation
            getSurveysUseCase(1, 5)
                .catch { e ->
                    showError(e.message)
                }
                .collect {
                    surveys = it
                    if (surveys.isNotEmpty()) updateCurrentSurvey()
                }
            hideLoading()
        }
    }

    private fun updateCurrentSurvey() {
        val surveysUiModel = surveys[currentSurveyIndex].run {
            HomeSurveysUiModel(
                HomeSurveyUiModel(
                    title,
                    description,
                    coverImageUrl
                ),
                totalPages = surveys.size,
                currentPageIndex = currentSurveyIndex
            )
        }
        _surveysUiModel.value = surveysUiModel
    }
}
