package co.nimblehq.ic.kmm.suv.android.ui.screens.surveyquestions

import androidx.lifecycle.viewModelScope
import co.nimblehq.ic.kmm.suv.android.ui.base.BaseViewModel
import co.nimblehq.ic.kmm.suv.domain.model.*
import co.nimblehq.ic.kmm.suv.domain.usecase.GetSurveyDetailUseCase
import co.nimblehq.ic.kmm.suv.domain.usecase.SubmitSurveyResponseUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

enum class SubmittingAnswerState {
    IDLE, SUBMITTING, SUCCESS
}

class SurveyQuestionsViewModel(
    private val getSurveyDetailUseCase: GetSurveyDetailUseCase,
    private val submitSurveyResponseUseCase: SubmitSurveyResponseUseCase
) : BaseViewModel() {

    private val _coverImageUrl = MutableStateFlow("")
    val coverImageUrl: StateFlow<String> = _coverImageUrl.asStateFlow()

    private val _questionContentUiModels: MutableStateFlow<List<QuestionContentUiModel>> =
        MutableStateFlow(
            emptyList()
        )
    val questionContentUiModels: StateFlow<List<QuestionContentUiModel>> =
        _questionContentUiModels.asStateFlow()

    private val _submittingAnswerState = MutableStateFlow(SubmittingAnswerState.IDLE)
    val submittingAnswerState: StateFlow<SubmittingAnswerState> =
        _submittingAnswerState.asStateFlow()

    private var _survey: Survey? = null
    private var surveySubmission: SurveySubmission? = null

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
                        _survey = survey
                        survey.sortedQuestions()?.let { questions ->
                            val totalOfQuestions = questions.size
                            _questionContentUiModels.value =
                                questions.mapIndexed { index, question ->
                                    QuestionContentUiModel(
                                        progress = "${index + 1}/$totalOfQuestions",
                                        title = question.text,
                                        displayType = question.displayType()
                                    )
                                }
                        }
                    }
                hideLoading()
            }
        }
    }

    fun answerQuestion(questionAndAnswers: QuestionAndAnswers) {
        val uiModels = _questionContentUiModels.value.toMutableList()
        val questionIndex = questionAndAnswers.questionIndex
        val changedUiModel = uiModels[questionIndex]
        changedUiModel.displayType.update(questionAndAnswers.answerInputs)
        uiModels[questionIndex] = changedUiModel
        _questionContentUiModels.value = uiModels

        // Convert answers to SurveySubmission
        _survey?.let {
            val questionSubmissions = questionContentUiModels.value
                .mapIndexed { questionIndex, uiModel ->
                    val questionId = questionId(questionIndex)
                    val answerSubmissions: List<AnswerSubmission> =
                        uiModel.displayType.input.map { input ->
                            when (input) {
                                is AnswerInput.Select -> AnswerSubmission(id = input.id)
                                is AnswerInput.Content -> AnswerSubmission(
                                    id = input.id,
                                    answer = input.content
                                )
                            }
                        }
                    QuestionSubmission(
                        questionId,
                        answerSubmissions
                    )
                }
            surveySubmission = SurveySubmission(it.id, questionSubmissions)
        }
    }

    fun submitSurveyResponse() {
        surveySubmission?.let { surveySubmission ->
            val questionSubmissions = surveySubmission.questions.filter { it.answers.isNotEmpty() }
            viewModelScope.launch {
                _submittingAnswerState.value = SubmittingAnswerState.SUBMITTING
                submitSurveyResponseUseCase(surveySubmission.copy(questions = questionSubmissions))
                    .catch { e ->
                        showError(e.message)
                        _submittingAnswerState.value = SubmittingAnswerState.IDLE
                    }
                    .collect {
                        _submittingAnswerState.value = SubmittingAnswerState.SUCCESS
                    }
            }
        }
    }

    private fun questionId(index: Int): String {
        return _survey?.sortedQuestions()
            ?.elementAtOrNull(index)
            ?.id
            .orEmpty()
    }
}
