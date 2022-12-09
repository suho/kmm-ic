package co.nimblehq.ic.kmm.suv.android.ui.screens.surveyquestions

import androidx.lifecycle.viewModelScope
import co.nimblehq.ic.kmm.suv.android.ui.base.BaseViewModel
import co.nimblehq.ic.kmm.suv.domain.model.sortedQuestions
import co.nimblehq.ic.kmm.suv.domain.usecase.GetSurveyDetailUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class SurveyQuestionsViewModel(
    private val getSurveyDetailUseCase: GetSurveyDetailUseCase
) : BaseViewModel() {

    private val _coverImageUrl = MutableStateFlow("")
    val coverImageUrl: StateFlow<String> = _coverImageUrl.asStateFlow()

    private val _questionContentUiModels: MutableStateFlow<List<QuestionContentUiModel>> =
        MutableStateFlow(
            emptyList()
        )
    val questionContentUiModels: StateFlow<List<QuestionContentUiModel>> =
        _questionContentUiModels.asStateFlow()

    fun loadSurveyDetail(surveyQuestionsArgument: SurveyQuestionsArgument?) {
        surveyQuestionsArgument?.let {
            _coverImageUrl.value = it.coverImageUrl
            showLoading()
            viewModelScope.launch {
                getSurveyDetailUseCase(it.id)
                    .catch { e ->
                        showError(e.message)
                    }
                    .collect { survey ->
                        survey.sortedQuestions()?.let { questions ->
                            val totalOfQuestions = questions.size
                            _questionContentUiModels.value =
                                questions.mapIndexed { index, question ->
                                    QuestionContentUiModel(
                                        order = "${index + 1}/$totalOfQuestions",
                                        title = question.text
                                    )
                                }
                        }
                    }
                hideLoading()
            }
        }
    }
}
