package co.nimblehq.ic.kmm.suv.domain.usecase

import co.nimblehq.ic.kmm.suv.domain.model.SurveySubmission
import co.nimblehq.ic.kmm.suv.domain.repository.SurveyRepository
import io.kotest.matchers.shouldBe
import io.mockative.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import kotlin.test.BeforeTest
import kotlin.test.Test

@ExperimentalCoroutinesApi
class SubmitSurveyResponseUseCaseTest {

    @Mock
    private val mockRepository = mock(classOf<SurveyRepository>())

    private val mockThrowable = Throwable("mock")

    private lateinit var useCase: SubmitSurveyResponseUseCase

    @BeforeTest
    fun setUp() {
        useCase = SubmitSurveyResponseUseCaseImpl(mockRepository)
    }

    @Test
    fun `when the use case is succeeded - it returns unit`() = runTest {
        given(mockRepository)
            .function(mockRepository::submitSurvey)
            .whenInvokedWith(any())
            .thenReturn(flow { emit(Unit) })

        useCase(SurveySubmission("survey_id", emptyList())).collect {
            it shouldBe Unit
        }
    }

    @Test
    fun `when the use case is failed - it returns error`() = runTest {
        given(mockRepository)
            .function(mockRepository::submitSurvey)
            .whenInvokedWith(any())
            .thenReturn(flow { throw mockThrowable })

        useCase(SurveySubmission("survey_id", emptyList())).catch {
            it.message shouldBe mockThrowable.message
        }.collect()
    }
}
