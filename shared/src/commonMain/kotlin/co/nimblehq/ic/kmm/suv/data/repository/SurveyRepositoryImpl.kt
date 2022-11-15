package co.nimblehq.ic.kmm.suv.data.repository

import co.nimblehq.ic.kmm.suv.data.local.datasource.SurveyLocalDataSource
import co.nimblehq.ic.kmm.suv.data.local.model.toSurvey
import co.nimblehq.ic.kmm.suv.data.remote.datasource.SurveyRemoteDataSource
import co.nimblehq.ic.kmm.suv.data.remote.model.toSurvey
import co.nimblehq.ic.kmm.suv.data.remote.model.toSurveyRealmObject
import co.nimblehq.ic.kmm.suv.data.remote.parameter.GetSurveysApiQueryParams
import co.nimblehq.ic.kmm.suv.domain.model.Survey
import co.nimblehq.ic.kmm.suv.domain.repository.SurveyRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.last

class SurveyRepositoryImpl(
    private val surveyRemoteDataSource: SurveyRemoteDataSource,
    private val surveyLocalDataSource: SurveyLocalDataSource
) : SurveyRepository {

    override fun getSurveys(pageNumber: Int, pageSize: Int): Flow<List<Survey>> {
        // TODO: Check this logic again when we have pull to refresh
        return flow {
            val localSurveys = surveyLocalDataSource.getSurveys().map { it.toSurvey() }
            if (localSurveys.isNotEmpty()) {
                emit(localSurveys)
            }

            val apiSurveys = surveyRemoteDataSource
                .getSurveys(GetSurveysApiQueryParams(pageNumber, pageSize))
                .last()

            val latestSurveys = apiSurveys.map { it.toSurvey() }

            if (localSurveys != latestSurveys) {
                emit(latestSurveys)
            }

            surveyLocalDataSource.saveSurveys(apiSurveys.map { it.toSurveyRealmObject() })
        }
    }
}
