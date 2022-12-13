package co.nimblehq.ic.kmm.suv.android.ui.screens.surveydetail

import co.nimblehq.ic.kmm.suv.android.rule.MainCoroutinesRule
import co.nimblehq.ic.kmm.suv.android.ui.screens.home.SurveyArgument
import io.kotest.matchers.nulls.beNull
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNot
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
        viewModel = SurveyDetailViewModel()
    }

    @Test
    fun `When its set survey argument, the ui model should be right`() {
        viewModel.set(mockSurveyArgument)
        viewModel.contentUiModel.value.let {
            it shouldNot beNull()
            it?.title shouldBe mockSurveyArgument.title
            it?.description shouldBe mockSurveyArgument.description
            it?.imageUrl shouldBe mockSurveyArgument.coverImageUrl
        }
    }
}
