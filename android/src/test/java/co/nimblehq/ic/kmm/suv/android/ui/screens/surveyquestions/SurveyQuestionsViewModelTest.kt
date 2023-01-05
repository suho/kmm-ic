package co.nimblehq.ic.kmm.suv.android.ui.screens.surveyquestions

import co.nimblehq.ic.kmm.suv.android.rule.MainCoroutinesRule
import co.nimblehq.ic.kmm.suv.domain.model.*
import co.nimblehq.ic.kmm.suv.domain.usecase.GetSurveyDetailUseCase
import co.nimblehq.ic.kmm.suv.domain.usecase.SubmitSurveyResponseUseCase
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class SurveyQuestionsViewModelTest {

    private lateinit var viewModel: SurveyQuestionsViewModel

    private val mockGetSurveyDetailUseCase: GetSurveyDetailUseCase = mockk()
    private val mockSubmitSurveyResponseUseCase: SubmitSurveyResponseUseCase = mockk()
    private val mockSurveyQuestionsArgument = SurveyQuestionsArgument(
        id = "id",
        coverImageUrl = "coverImageUrl"
    )
    private val mockSurvey = Survey(
        id = "id",
        title = "firstTitle",
        description = "firstDescription",
        isActive = true,
        coverImageUrl = "coverImageUrl",
        questions = listOf(
            Question(
                id = "firstId",
                text = "firstQuestion",
                displayOrder = 0,
                displayType = "star",
                pick = "pick",
                coverImageUrl = "coverImageUrl",
                answers = listOf(
                    Answer(
                        id = "1",
                        text = "choice 1",
                        displayOrder = 0,
                        inputMaskPlaceholder = null
                    ),
                    Answer(
                        id = "2",
                        text = "choice 2",
                        displayOrder = 1,
                        inputMaskPlaceholder = null
                    ),
                )
            ),
            Question(
                id = "secondId",
                text = "secondQuestion",
                displayOrder = 1,
                displayType = "intro",
                pick = "pick",
                coverImageUrl = "coverImageUrl",
                answers = listOf(
                    Answer(
                        id = "1",
                        text = "choice 1",
                        displayOrder = 0,
                        inputMaskPlaceholder = null
                    ),
                    Answer(
                        id = "2",
                        text = "choice 2",
                        displayOrder = 1,
                        inputMaskPlaceholder = null
                    ),
                )
            )
        )
    )

    @ExperimentalCoroutinesApi
    @get:Rule
    val mainCoroutinesRule = MainCoroutinesRule()

    @Before
    fun setup() {
        every { mockGetSurveyDetailUseCase(any()) } returns flowOf(mockSurvey)
        every { mockSubmitSurveyResponseUseCase(any()) } returns flowOf(Unit)
        viewModel =
            SurveyQuestionsViewModel(mockGetSurveyDetailUseCase, mockSubmitSurveyResponseUseCase)
    }

    @Test
    fun `When load survey, the cover url should be right`() = runTest {
        viewModel.loadSurveyDetail(mockSurveyQuestionsArgument)
        viewModel.coverImageUrl.value shouldBe "coverImageUrl"
    }

    @Test
    fun `When load survey successfully, the question content ui models should be right as expected`() =
        runTest {
            viewModel.loadSurveyDetail(mockSurveyQuestionsArgument)
            advanceUntilIdle()

            val questions = mockSurvey.questions!!
            for ((index, uiModel) in viewModel.questionContentUiModels.value.withIndex()) {
                verifySurveyQuestion(
                    uiModel = uiModel,
                    question = questions[index],
                    questionIndex = index,
                    questionsCount = questions.size
                )
            }
        }

    @Test
    fun `When load survey failed, error message should not be null`() =
        runTest {
            val expectedError = AppError("Load profile failed!")
            every { mockGetSurveyDetailUseCase(any()) } returns flow { throw expectedError }
            viewModel.loadSurveyDetail(mockSurveyQuestionsArgument)
            advanceUntilIdle()

            viewModel.errorMessage.value shouldBe expectedError.message
        }

    @Test
    fun `When answer a question, the ui model's display type updated to reflect the input`() =
        runTest {
            viewModel.loadSurveyDetail(mockSurveyQuestionsArgument)
            advanceUntilIdle()

            val input = AnswerInput.Select("1")
            viewModel.answerQuestion(QuestionAndAnswers("firstId", listOf(input)))
            viewModel.questionContentUiModels.value[0].displayType.input.first() shouldBe input
        }

    @Test
    fun `When submit answers successfully, the isSubmitSuccess is true as expected`() =
        runTest {
            viewModel.loadSurveyDetail(mockSurveyQuestionsArgument)
            advanceUntilIdle()
            val input = AnswerInput.Select("1")
            viewModel.answerQuestion(QuestionAndAnswers("firstId", listOf(input)))

            viewModel.submitSurveyResponse()
            advanceUntilIdle()

            viewModel.submittingAnswerState.value shouldBe SubmittingAnswerState.SUCCESS
        }

    @Test
    fun `When submit answers failed, error message should not be null`() =
        runTest {
            val expectedError = AppError("Load profile failed!")
            every { mockSubmitSurveyResponseUseCase(any()) } returns flow { throw expectedError }

            viewModel.loadSurveyDetail(mockSurveyQuestionsArgument)
            advanceUntilIdle()
            val input = AnswerInput.Select("1")
            viewModel.answerQuestion(QuestionAndAnswers("firstId", listOf(input)))

            viewModel.submitSurveyResponse()
            advanceUntilIdle()

            viewModel.errorMessage.value shouldBe expectedError.message
        }

    private fun verifySurveyQuestion(
        uiModel: QuestionContentUiModel,
        question: Question,
        questionIndex: Int,
        questionsCount: Int
    ) {
        uiModel.progress shouldBe "${questionIndex + 1}/$questionsCount"
        uiModel.title shouldBe question.text
    }
}

