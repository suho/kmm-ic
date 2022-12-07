package co.nimblehq.ic.kmm.suv.domain.usecase

import app.cash.turbine.test
import co.nimblehq.ic.kmm.suv.domain.model.Survey
import co.nimblehq.ic.kmm.suv.domain.repository.SurveyRepository
import io.kotest.matchers.shouldBe
import io.mockative.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import kotlin.test.BeforeTest
import kotlin.test.Test

@ExperimentalCoroutinesApi
class GetSurveyDetailUseCaseTest {

    @Mock
    private val mockSurveyRepository = mock(classOf<SurveyRepository>())

    private val mockThrowable = Throwable("mock")
    private val mockSurvey = Survey(
        id = "id",
        title = "title",
        description = "description",
        isActive = true,
        coverImageUrl = "coverImageUrl",
        questions = emptyList()
    )

    private lateinit var useCase: GetSurveyDetailUseCase

    @BeforeTest
    fun setUp() {
        useCase = GetSurveyDetailUseCaseImpl(mockSurveyRepository)
    }

    @Test
    fun `when the use case is succeeded - it returns survey`() = runTest {
        given(mockSurveyRepository)
            .function(mockSurveyRepository::getSurveyDetail)
            .whenInvokedWith(any())
            .thenReturn(flow { emit(mockSurvey) })

        useCase("id").test {
            this.awaitItem() shouldBe mockSurvey
            this.awaitComplete()
        }
    }

    @Test
    fun `when the use case is failed - it returns error`() = runTest {
        given(mockSurveyRepository)
            .function(mockSurveyRepository::getSurveyDetail)
            .whenInvokedWith(any())
            .thenReturn(flow { throw mockThrowable })

        useCase("id").test {
            this.awaitError().message shouldBe mockThrowable.message
        }
    }
}
