package co.nimblehq.ic.kmm.suv.android.ui.screens.surveydetail

import co.nimblehq.ic.kmm.suv.android.rule.MainCoroutinesRule
import co.nimblehq.ic.kmm.suv.android.ui.screens.home.SurveyArgument
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class SurveyDetailViewModelTest {

    private lateinit var viewModel: SurveyDetailViewModel

    private val mockSurveyArgument = SurveyArgument(
        id = "id",
        title = "firstTitle",
        description = "firstDescription",
        coverImageUrl = "coverImageUrl"
    )

    @ExperimentalCoroutinesApi
    @get:Rule
    val mainCoroutinesRule = MainCoroutinesRule()

    @Before
    fun setup() {
        viewModel = SurveyDetailViewModel(mockSurveyArgument)
    }

    @Test
    fun `When its init, the ui model should be right`() {
        viewModel.contentUiModel.title shouldBe mockSurveyArgument.title
        viewModel.contentUiModel.description shouldBe mockSurveyArgument.description
        viewModel.contentUiModel.imageUrl shouldBe mockSurveyArgument.coverImageUrl
    }
}
