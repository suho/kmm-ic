package co.nimblehq.ic.kmm.suv.android.ui.screens.home

import androidx.activity.compose.setContent
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import co.nimblehq.ic.kmm.suv.android.MainActivity
import co.nimblehq.ic.kmm.suv.domain.model.Survey
import co.nimblehq.ic.kmm.suv.domain.model.User
import co.nimblehq.ic.kmm.suv.domain.usecase.GetProfileUseCase
import co.nimblehq.ic.kmm.suv.domain.usecase.GetSurveysUseCase
import co.nimblehq.ic.kmm.suv.helper.date.DateTime
import co.nimblehq.ic.kmm.suv.helper.date.DateTimeFormatterImpl
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.datetime.LocalDate
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalTestApi
class HomeScreenTest {

    @get:Rule
    val composeRule = createAndroidComposeRule<MainActivity>()

    private lateinit var viewModel: HomeViewModel
    private val mockGetProfileUseCase: GetProfileUseCase = mockk()
    private val mockGetSurveysUseCase: GetSurveysUseCase = mockk()
    private val mockDateTime: DateTime = mockk()
    private val user = User("email", "name", "avatarUrl")
    private val mockFirstSurvey = Survey("firstTitle", "firstDescription", true, "coverImageUrl")
    private val mockSecondSurvey = Survey("secondTitle", "secondTitle", true, "coverImageUrl")

    @Before
    fun setup() {
        every { mockGetProfileUseCase() } returns flowOf(user)
        every { mockGetSurveysUseCase(any(), any()) } returns flowOf(
            listOf(
                mockFirstSurvey,
                mockSecondSurvey
            )
        )
        every { mockDateTime.today() } returns LocalDate(2022, 11, 8)
        viewModel = HomeViewModel(
            mockGetProfileUseCase,
            mockGetSurveysUseCase,
            mockDateTime,
            DateTimeFormatterImpl()
        )
        composeRule.activity.setContent {
            HomeScreen(viewModel = viewModel)
        }
    }

    @Test
    fun when_enter_the_home_screen__it_shows_its_ui_components() {
        composeRule.apply {
            onNodeWithContentDescription(HomeContentDescription.CURRENT_DATE).assertIsDisplayed()
            onNodeWithContentDescription(HomeContentDescription.TODAY_TEXT).assertIsDisplayed()
            onNodeWithContentDescription(HomeContentDescription.AVATAR).assertIsDisplayed()
            onNodeWithContentDescription(HomeContentDescription.INDICATOR).assertIsDisplayed()
            onNodeWithContentDescription(HomeContentDescription.SURVEY_TITLE).assertIsDisplayed()
            onNodeWithContentDescription(HomeContentDescription.SURVEY_DESCRIPTION).assertIsDisplayed()
            onNodeWithContentDescription(HomeContentDescription.SURVEY_DETAIL_BUTTON).assertIsDisplayed()
        }
    }

    @Test
    fun when_enter_the_home_screen__it_shows_list_of_surveys() {
        composeRule.apply {
            onNodeWithContentDescription(HomeContentDescription.INDICATOR).assertIsDisplayed()
            onNodeWithContentDescription(HomeContentDescription.SURVEY_TITLE)
                .assertTextEquals(mockFirstSurvey.title)

            onRoot().performTouchInput {
                swipeLeft()
            }

            onNodeWithContentDescription(HomeContentDescription.SURVEY_TITLE)
                .assertTextEquals(mockSecondSurvey.title)

            onRoot().performTouchInput {
                swipeRight()
            }

            onNodeWithContentDescription(HomeContentDescription.SURVEY_TITLE)
                .assertTextEquals(mockFirstSurvey.title)
        }
    }
}
