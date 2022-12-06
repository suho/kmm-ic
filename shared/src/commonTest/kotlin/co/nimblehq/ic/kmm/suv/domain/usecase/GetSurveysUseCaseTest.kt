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
class GetSurveysUseCaseTest {

    @Mock
    private val mockSurveyRepository = mock(classOf<SurveyRepository>())

    private val mockThrowable = Throwable("mock")
    private val mockSurvey = Survey(
        "title",
        "description",
        true,
        "coverImageUrl"
    )

    private lateinit var useCase: GetSurveysUseCase

    @BeforeTest
    fun setUp() {
        useCase = GetSurveysUseCaseImpl(mockSurveyRepository)
    }

    @Test
    fun `when the use case is succeeded - it returns surveys`() = runTest {
        given(mockSurveyRepository)
            .function(mockSurveyRepository::getSurveys)
            .whenInvokedWith(any(), any(), any())
            .thenReturn(flow { emit(listOf(mockSurvey)) })

        useCase(1, 1).test {
            this.awaitItem() shouldBe listOf(mockSurvey)
            this.awaitComplete()
        }
    }

    @Test
    fun `when the use case is failed - it returns error`() = runTest {
        given(mockSurveyRepository)
            .function(mockSurveyRepository::getSurveys)
            .whenInvokedWith(any(), any(), any())
            .thenReturn(flow { throw mockThrowable })

        useCase(1, 1).test {
            this.awaitError().message shouldBe mockThrowable.message
        }
    }
}
