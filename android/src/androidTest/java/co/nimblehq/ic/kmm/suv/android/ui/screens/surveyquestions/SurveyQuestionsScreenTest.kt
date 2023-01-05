package co.nimblehq.ic.kmm.suv.android.ui.screens.surveyquestions

import androidx.activity.compose.setContent
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import co.nimblehq.ic.kmm.suv.android.MainActivity
import co.nimblehq.ic.kmm.suv.android.ui.components.*
import co.nimblehq.ic.kmm.suv.domain.model.*
import co.nimblehq.ic.kmm.suv.domain.usecase.GetSurveyDetailUseCase
import co.nimblehq.ic.kmm.suv.domain.usecase.SubmitSurveyResponseUseCase
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalTestApi
class SurveyQuestionsScreenTest {

    @get:Rule
    val composeRule = createAndroidComposeRule<MainActivity>()

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
            dummyQuestion(QUESTION_DISPLAY_TYPE_STAR, displayOrder = 0),
            dummyQuestion(QUESTION_DISPLAY_TYPE_HEART, displayOrder = 1),
            dummyQuestion(QUESTION_DISPLAY_TYPE_SMILEY, displayOrder = 2),
            dummyQuestion(QUESTION_DISPLAY_TYPE_CHOICE, displayOrder = 3),
            dummyQuestion(QUESTION_DISPLAY_TYPE_NPS, displayOrder = 4),
            dummyQuestion(QUESTION_DISPLAY_TYPE_TEXTAREA, displayOrder = 5),
            dummyQuestion(QUESTION_DISPLAY_TYPE_TEXTFIELD, displayOrder = 6),
            dummyQuestion(QUESTION_DISPLAY_TYPE_DROPDOWN, displayOrder = 7),
        )
    )

    @Before
    fun setup() {
        every { mockGetSurveyDetailUseCase(any()) } returns flowOf(mockSurvey)
        every { mockSubmitSurveyResponseUseCase(any()) } returns flowOf(Unit)
        viewModel = SurveyQuestionsViewModel(
            mockGetSurveyDetailUseCase,
            mockSubmitSurveyResponseUseCase
        )
        composeRule.activity.setContent {
            SurveyQuestionsScreen(
                surveyQuestionsArgument = mockSurveyQuestionsArgument,
                onCloseClick = {},
                onSubmitSuccess = {},
                viewModel = viewModel
            )
        }
    }

    @Test
    fun when_enter_the_survey_questions_screen__it_shows_its_ui_components() {
        composeRule.apply {
            onNodeWithContentDescription(SurveyQuestionsContentDescription.BUTTON_CLOSE).assertIsDisplayed()
            onNodeWithContentDescription(SurveyQuestionsContentDescription.BUTTON_NEXT_OR_SUBMIT).assertIsDisplayed()
            onNodeWithContentDescription(SurveyQuestionsContentDescription.progressLabel(0)).assertIsDisplayed()
            onNodeWithContentDescription(SurveyQuestionsContentDescription.questionTitleLabel(0)).assertIsDisplayed()

            // Star question
            onNodeWithContentDescription(
                EmojiRatingAnswerContentDescription.emoji(
                    0,
                    0
                )
            ).assertIsDisplayed()
            onNodeWithContentDescription(
                EmojiRatingAnswerContentDescription.emoji(
                    0,
                    1
                )
            ).assertIsDisplayed()
        }
    }

    @Test
    fun when_answer_questions_and_submit_answers__is_submit_success_is_true() {
        composeRule.apply {
            val nextButtonNode =
                onNodeWithContentDescription(SurveyQuestionsContentDescription.BUTTON_NEXT_OR_SUBMIT)

            // star
            onNodeWithContentDescription(
                EmojiRatingAnswerContentDescription.emoji(
                    0,
                    0
                )
            ).performClick()
            nextButtonNode.performClick()

            // heart
            onNodeWithContentDescription(
                EmojiRatingAnswerContentDescription.emoji(
                    1,
                    0
                )
            ).performClick()
            nextButtonNode.performClick()

            // smiley
            onNodeWithContentDescription(
                EmojiRatingAnswerContentDescription.emoji(
                    2,
                    0
                )
            ).performClick()
            nextButtonNode.performClick()

            // choice
            onNodeWithContentDescription(
                MultipleChoicesAnswerContentDescription.choice(0)
            ).performClick()
            nextButtonNode.performClick()

            // nps
            onNodeWithContentDescription(
                NpsAnswerContentDescription.nps(0)
            ).performClick()
            nextButtonNode.performClick()

            // textarea
            onNodeWithContentDescription(
                TextareaAnswerContentDescription.TEXTAREA
            ).performTextInput("text area")
            nextButtonNode.performClick()

            // textfield
            onNodeWithContentDescription(
                FormAnswerContentDescription.field(0)
            ).performTextInput("first field")
            onNodeWithContentDescription(
                FormAnswerContentDescription.field(1)
            ).performTextInput("second field")
            nextButtonNode.performClick()

            // dropdown
            onRoot().performTouchInput {
                swipeUp()
            }
            nextButtonNode.performClick()

            Assert.assertEquals(
                SubmittingAnswerState.SUCCESS,
                viewModel.submittingAnswerState.value
            )
        }
    }

    private fun dummyQuestion(displayType: String, displayOrder: Int): Question = Question(
        id = "$displayType-id",
        text = "$displayType-text",
        displayOrder = displayOrder,
        displayType = displayType,
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
}
