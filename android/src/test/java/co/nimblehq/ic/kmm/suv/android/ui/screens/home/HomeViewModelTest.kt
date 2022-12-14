package co.nimblehq.ic.kmm.suv.android.ui.screens.home

import co.nimblehq.ic.kmm.suv.android.rule.MainCoroutinesRule
import co.nimblehq.ic.kmm.suv.domain.model.AppError
import co.nimblehq.ic.kmm.suv.domain.model.Survey
import co.nimblehq.ic.kmm.suv.domain.model.User
import co.nimblehq.ic.kmm.suv.domain.usecase.GetProfileUseCase
import co.nimblehq.ic.kmm.suv.domain.usecase.GetSurveysUseCase
import co.nimblehq.ic.kmm.suv.helper.date.DateTime
import co.nimblehq.ic.kmm.suv.helper.date.DateTimeFormatterImpl
import io.kotest.matchers.nulls.beNull
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNot
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import kotlinx.datetime.LocalDate
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class HomeViewModelTest {

    private lateinit var viewModel: HomeViewModel

    private val mockGetProfileUseCase: GetProfileUseCase = mockk()
    private val mockGetSurveysUseCase: GetSurveysUseCase = mockk()
    private val mockDateTime: DateTime = mockk()
    private val mockUser = User("email", "name", "avatarUrl")
    private val mockFirstSurvey = Survey(
        "id",
        "firstTitle",
        "firstDescription",
        true,
        "coverImageUrl",
        emptyList()
    )
    private val mockSecondSurvey = Survey(
        "id",
        "secondTitle",
        "secondTitle",
        true,
        "coverImageUrl",
        emptyList()
    )

    @ExperimentalCoroutinesApi
    @get:Rule
    val mainCoroutinesRule = MainCoroutinesRule()

    @Before
    fun setup() {
        every { mockDateTime.today() } returns LocalDate(2022, 11, 8)
        every { mockGetProfileUseCase() } returns flowOf(mockUser)
        every { mockGetSurveysUseCase(any(), any(), any()) } returns flowOf(
            listOf(
                mockFirstSurvey,
                mockSecondSurvey
            )
        )
        viewModel = HomeViewModel(
            mockGetProfileUseCase,
            mockGetSurveysUseCase,
            mockDateTime,
            DateTimeFormatterImpl()
        )
    }

    @Test
    fun `When its init, today should be right`() {
        viewModel.currentDate.value shouldBe "TUESDAY, NOVEMBER 8"
    }

    @Test
    fun `When load profile and surveys successfully, the avatar url should not be null and the survey ui model should not be null`() =
        runTest {
            viewModel.loadProfileAndSurveys()
            advanceUntilIdle()

            viewModel.avatarUrlString.value shouldBe mockUser.avatarUrl

            verifyCurrentSurveyPage(mockFirstSurvey)
            verifyTotalPagesAndIndex(2, 0)
        }

    @Test
    fun `When load profile and refresh survey refresh successfully, the avatar url should not be null and the survey ui model should not be null`() =
        runTest {
            viewModel.loadProfileAndSurveys(isRefresh = true)
            advanceUntilIdle()

            viewModel.avatarUrlString.value shouldBe mockUser.avatarUrl

            verifyCurrentSurveyPage(mockFirstSurvey)
            verifyTotalPagesAndIndex(2, 0)
        }

    @Test
    fun `When load profile and refresh survey refresh successfully, isRefreshing should change from false to true`() =
        runTest {
            Dispatchers.setMain(StandardTestDispatcher())
            viewModel.loadProfileAndSurveys(isRefresh = true)

            viewModel.isRefreshing.value shouldBe true
            advanceUntilIdle()
            viewModel.isRefreshing.value shouldBe false
        }

    @Test
    fun `When load profile failed, error message should not be null`() = runTest {
        val expectedError = AppError("Load profile failed!")
        every { mockGetProfileUseCase() } returns flow { throw expectedError }
        viewModel.loadProfileAndSurveys()
        advanceUntilIdle()

        viewModel.errorMessage.value shouldBe expectedError.message
    }

    @Test
    fun `When load surveys failed, error message should not be null`() = runTest {
        val expectedError = AppError("Load surveys failed!")
        every { mockGetSurveysUseCase(any(), any(), any()) } returns flow { throw expectedError }
        viewModel.loadProfileAndSurveys()
        advanceUntilIdle()

        viewModel.errorMessage.value shouldBe expectedError.message
    }

    @Test
    fun `When load profile, isLoading should change from false to true`() = runTest {
        Dispatchers.setMain(StandardTestDispatcher())
        viewModel.loadProfileAndSurveys()

        viewModel.isLoading.value shouldBe true
        advanceUntilIdle()
        viewModel.isLoading.value shouldBe false
    }

    @Test
    fun `When show next survey, the survey ui model should be updated`() = runTest {
        viewModel.loadProfileAndSurveys()
        advanceUntilIdle()
        viewModel.showNextSurvey()

        verifyCurrentSurveyPage(mockSecondSurvey)
        verifyTotalPagesAndIndex(2, 1)
    }

    @Test
    fun `When show previous survey, the survey ui model should be updated`() = runTest {
        viewModel.loadProfileAndSurveys()
        advanceUntilIdle()

        viewModel.showNextSurvey()
        verifyTotalPagesAndIndex(2, 1)

        viewModel.showPreviousSurvey()
        verifyCurrentSurveyPage(mockFirstSurvey)
        verifyTotalPagesAndIndex(2, 0)
    }

    @Test
    fun `When get current survey argument, it should return the right survey argument`() =
        runTest {
            viewModel.loadProfileAndSurveys()
            advanceUntilIdle()

            val firstCurrentSurveyArgument = viewModel.getCurrentSurveyArgument()
            verifyCurrentSurveyArgument(firstCurrentSurveyArgument, mockFirstSurvey)

            viewModel.showNextSurvey()
            val secondCurrentSurveyArgument = viewModel.getCurrentSurveyArgument()
            verifyCurrentSurveyArgument(secondCurrentSurveyArgument, mockSecondSurvey)
        }

    private fun verifyCurrentSurveyPage(survey: Survey) {
        viewModel.surveysUiModel.value.apply {
            this shouldNot beNull()
            this?.currentSurveyUiModel?.title shouldBe survey.title
            this?.currentSurveyUiModel?.description shouldBe survey.description
        }
    }

    private fun verifyTotalPagesAndIndex(totalPages: Int, currentIndex: Int) {
        viewModel.surveysUiModel.value.apply {
            this shouldNot beNull()
            this?.totalPages shouldBe totalPages
            this?.currentPageIndex shouldBe currentIndex
        }
    }

    private fun verifyCurrentSurveyArgument(surveyArgument: SurveyArgument, survey: Survey) {
        surveyArgument shouldBe survey.run { SurveyArgument(id, title, description, coverImageUrl) }
    }
}
