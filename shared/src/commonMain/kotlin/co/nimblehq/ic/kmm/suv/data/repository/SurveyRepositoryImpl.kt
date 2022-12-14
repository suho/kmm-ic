package co.nimblehq.ic.kmm.suv.data.repository

import co.nimblehq.ic.kmm.suv.data.local.datasource.SurveyLocalDataSource
import co.nimblehq.ic.kmm.suv.data.local.model.toSurvey
import co.nimblehq.ic.kmm.suv.data.remote.body.SurveySubmissionApiBody
import co.nimblehq.ic.kmm.suv.data.remote.datasource.SurveyRemoteDataSource
import co.nimblehq.ic.kmm.suv.data.remote.model.toSurvey
import co.nimblehq.ic.kmm.suv.data.remote.model.toSurveyRealmObject
import co.nimblehq.ic.kmm.suv.data.remote.parameter.GetSurveysApiQueryParams
import co.nimblehq.ic.kmm.suv.domain.model.Survey
import co.nimblehq.ic.kmm.suv.domain.model.SurveySubmission
import co.nimblehq.ic.kmm.suv.domain.repository.SurveyRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.flow.map

private const val FIRST_PAGE_NUMBER = 1

class SurveyRepositoryImpl(
    private val surveyRemoteDataSource: SurveyRemoteDataSource,
    private val surveyLocalDataSource: SurveyLocalDataSource
) : SurveyRepository {

    override fun getSurveys(
        pageNumber: Int,
        pageSize: Int,
        isForceLatestData: Boolean
    ): Flow<List<Survey>> {
        return flow {
            if (isForceLatestData) {
                surveyLocalDataSource.deleteAllSurveys()
            } else if (pageNumber == FIRST_PAGE_NUMBER) {
                val localSurveys = surveyLocalDataSource.getSurveys().map { it.toSurvey() }
                if (localSurveys.isNotEmpty()) {
                    emit(localSurveys)
                }
            }

            val apiSurveys = surveyRemoteDataSource
                .getSurveys(GetSurveysApiQueryParams(pageNumber, pageSize))
                .last()

            val latestSurveys = apiSurveys.map { it.toSurvey() }

            emit(latestSurveys)

            surveyLocalDataSource.saveSurveys(apiSurveys.map { it.toSurveyRealmObject() })
        }
    }

    override fun getSurvey(id: String): Flow<Survey> {
        return surveyRemoteDataSource.getSurvey(id).map { it.toSurvey() }
    }

    override fun submitSurvey(submission: SurveySubmission): Flow<Unit> {
        return surveyRemoteDataSource.submitSurvey(SurveySubmissionApiBody(submission))
    }
}
