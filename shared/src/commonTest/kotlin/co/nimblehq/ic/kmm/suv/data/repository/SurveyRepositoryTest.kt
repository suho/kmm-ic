package co.nimblehq.ic.kmm.suv.data.repository

import app.cash.turbine.test
import co.nimblehq.ic.kmm.suv.data.local.datasource.SurveyLocalDataSource
import co.nimblehq.ic.kmm.suv.data.local.model.SurveyRealmObject
import co.nimblehq.ic.kmm.suv.data.local.model.toSurvey
import co.nimblehq.ic.kmm.suv.data.remote.datasource.SurveyRemoteDataSource
import co.nimblehq.ic.kmm.suv.data.remote.model.QuestionApiModel
import co.nimblehq.ic.kmm.suv.data.remote.model.SurveyApiModel
import co.nimblehq.ic.kmm.suv.data.remote.model.Url
import co.nimblehq.ic.kmm.suv.data.remote.model.toSurvey
import co.nimblehq.ic.kmm.suv.domain.model.SurveySubmission
import co.nimblehq.ic.kmm.suv.domain.repository.SurveyRepository
import io.kotest.matchers.shouldBe
import io.mockative.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import kotlin.test.BeforeTest
import kotlin.test.Test

@ExperimentalCoroutinesApi
class SurveyRepositoryTest {

    @Mock
    private val mockSurveyRemoteDataSource = mock(classOf<SurveyRemoteDataSource>())

    @Mock
    private val mockSurveyLocalDataSource = mock(classOf<SurveyLocalDataSource>())

    private val mockThrowable = Throwable("mock")
    private val mockSurveyRealmObject = SurveyRealmObject()
        .apply { id = "id-survey-realm-object" }
    private val mockSurveyApiModel = SurveyApiModel(
        "id-survey-api-model",
        "type",
        "title",
        "description",
        true,
        Url("coverImageUrl"),
        listOf(
            QuestionApiModel(
                id = "id",
                text = "text",
                displayOrder = 0,
                displayType = "intro",
                pick = "pick",
                coverImageUrl = Url("string"),
                answers = emptyList()
            )
        )
    )

    private lateinit var repository: SurveyRepository

    @BeforeTest
    fun setUp() {
        repository = SurveyRepositoryImpl(mockSurveyRemoteDataSource, mockSurveyLocalDataSource)
    }

    @Test
    fun `when there is no cached data and get surveys is succeeded - it returns surveys one time`() =
        runTest {
            given(mockSurveyLocalDataSource)
                .function(mockSurveyLocalDataSource::getSurveys)
                .whenInvoked()
                .thenReturn(listOf())
            given(mockSurveyRemoteDataSource)
                .function(mockSurveyRemoteDataSource::getSurveys)
                .whenInvokedWith(any())
                .thenReturn(
                    flow {
                        emit(listOf(mockSurveyApiModel))
                    }
                )

            repository.getSurveys(1, 1).test {
                this.awaitItem() shouldBe listOf(mockSurveyApiModel.toSurvey())
                this.awaitComplete()
            }
        }

    @Test
    fun `when there is cached data and get surveys is succeeded - it returns surveys two times`() =
        runTest {
            given(mockSurveyLocalDataSource)
                .function(mockSurveyLocalDataSource::getSurveys)
                .whenInvoked()
                .thenReturn(listOf(mockSurveyRealmObject))

            given(mockSurveyRemoteDataSource)
                .function(mockSurveyRemoteDataSource::getSurveys)
                .whenInvokedWith(any())
                .thenReturn(
                    flow {
                        emit(listOf(mockSurveyApiModel))
                    }
                )

            repository.getSurveys(1, 1).test {
                this.awaitItem() shouldBe listOf(mockSurveyRealmObject.toSurvey())
                this.awaitItem() shouldBe listOf(mockSurveyApiModel.toSurvey())
                this.awaitComplete()
            }
        }

    @Test
    fun `when force latest data with cached data and get surveys is succeeded - it returns only surveys from API`() =
        runTest {
            given(mockSurveyLocalDataSource)
                .function(mockSurveyLocalDataSource::getSurveys)
                .whenInvoked()
                .thenReturn(listOf(mockSurveyRealmObject))

            given(mockSurveyRemoteDataSource)
                .function(mockSurveyRemoteDataSource::getSurveys)
                .whenInvokedWith(any())
                .thenReturn(
                    flow {
                        emit(listOf(mockSurveyApiModel))
                    }
                )

            repository.getSurveys(pageNumber = 1, pageSize = 1, isForceLatestData = true).test {
                this.awaitItem() shouldBe listOf(mockSurveyApiModel.toSurvey())
                this.awaitComplete()
            }

            verify(mockSurveyLocalDataSource).invocation { deleteAllSurveys() }
                .wasInvoked(exactly = 1.times)
        }

    @Test
    fun `when there is no cached data and get surveys is failed - it returns error`() =
        runTest {
            given(mockSurveyLocalDataSource)
                .function(mockSurveyLocalDataSource::getSurveys)
                .whenInvoked()
                .thenReturn(listOf())
            given(mockSurveyRemoteDataSource)
                .function(mockSurveyRemoteDataSource::getSurveys)
                .whenInvokedWith(any())
                .thenReturn(
                    flow {
                        throw mockThrowable
                    }
                )

            repository.getSurveys(1, 1).test {
                this.awaitError().message shouldBe mockThrowable.message
            }
        }

    @Test
    fun `when there is cached data and get surveys is failed - it returns surveys then an error`() =
        runTest {
            given(mockSurveyLocalDataSource)
                .function(mockSurveyLocalDataSource::getSurveys)
                .whenInvoked()
                .thenReturn(listOf(mockSurveyRealmObject))

            given(mockSurveyRemoteDataSource)
                .function(mockSurveyRemoteDataSource::getSurveys)
                .whenInvokedWith(any())
                .thenReturn(
                    flow {
                        throw mockThrowable
                    }
                )
            repository.getSurveys(1, 1).test {
                this.awaitItem() shouldBe listOf(mockSurveyRealmObject.toSurvey())
                this.awaitError().message shouldBe mockThrowable.message
            }
        }

    @Test
    fun `when get detail survey is succeeded - it returns survey`() =
        runTest {
            given(mockSurveyRemoteDataSource)
                .function(mockSurveyRemoteDataSource::getSurvey)
                .whenInvokedWith(any())
                .thenReturn(flow { emit(mockSurveyApiModel) })

            repository.getSurvey("id").test {
                this.awaitItem() shouldBe mockSurveyApiModel.toSurvey()
                this.awaitComplete()
            }
        }

    @Test
    fun `when get detail survey is failed - it returns error`() =
        runTest {
            given(mockSurveyRemoteDataSource)
                .function(mockSurveyRemoteDataSource::getSurvey)
                .whenInvokedWith(any())
                .thenReturn(flow { throw mockThrowable })

            repository.getSurvey("id").test {
                this.awaitError().message shouldBe mockThrowable.message
            }
        }

    @Test
    fun `when submit survey is succeeded - it returns empty response`() =
        runTest {
            given(mockSurveyRemoteDataSource)
                .function(mockSurveyRemoteDataSource::submitSurvey)
                .whenInvokedWith(any())
                .thenReturn(flow { emit(Unit) })

            repository.submitSurvey(SurveySubmission("survey_id", emptyList())).test {
                this.awaitItem() shouldBe Unit
                this.awaitComplete()
            }
        }

    @Test
    fun `when submit survey is failed - it returns error`() =
        runTest {
            given(mockSurveyRemoteDataSource)
                .function(mockSurveyRemoteDataSource::submitSurvey)
                .whenInvokedWith(any())
                .thenReturn(flow { throw mockThrowable })

            repository.submitSurvey(SurveySubmission("survey_id", emptyList())).test {
                this.awaitError().message shouldBe mockThrowable.message
            }
        }
}
