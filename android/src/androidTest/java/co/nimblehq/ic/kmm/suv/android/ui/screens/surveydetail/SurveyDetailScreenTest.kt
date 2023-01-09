package co.nimblehq.ic.kmm.suv.android.ui.screens.surveydetail

import androidx.activity.compose.setContent
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import co.nimblehq.ic.kmm.suv.android.MainActivity
import co.nimblehq.ic.kmm.suv.android.ui.components.BACK_BUTTON_CONTENT_DESCRIPTION
import co.nimblehq.ic.kmm.suv.android.ui.screens.home.SurveyArgument
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalTestApi
class SurveyDetailScreenTest {

    @get:Rule
    val composeRule = createAndroidComposeRule<MainActivity>()

    private lateinit var viewModel: SurveyDetailViewModel
    private val mockSurveyArgument = SurveyArgument(
        id = "firstID",
        title = "firstTitle",
        description = "firstDescription",
        coverImageUrl = "coverImageUrl"
    )

    @Before
    fun setup() {
        viewModel = SurveyDetailViewModel()
        composeRule.activity.setContent {
            SurveyDetailScreen(mockSurveyArgument, viewModel = viewModel)
        }
    }

    @Test
    fun when_enter_the_survey_detail_screen__it_shows_its_ui_components() {
        composeRule.apply {
            onNodeWithContentDescription(BACK_BUTTON_CONTENT_DESCRIPTION).assertIsDisplayed()
            onNodeWithContentDescription(SurveyDetailContentDescription.TITLE).assertIsDisplayed()
            onNodeWithContentDescription(SurveyDetailContentDescription.DESCRIPTION).assertIsDisplayed()
        }
    }
}
