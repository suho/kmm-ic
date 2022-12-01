package co.nimblehq.ic.kmm.suv.domain.repository

import co.nimblehq.ic.kmm.suv.domain.model.Survey
import co.nimblehq.ic.kmm.suv.domain.model.SurveySubmission
import kotlinx.coroutines.flow.Flow

interface SurveyRepository {

    fun getSurveys(
        pageNumber: Int,
        pageSize: Int,
        isForceLatestData: Boolean = false
    ): Flow<List<Survey>>

    fun getSurvey(id: String): Flow<Survey>

    fun submitSurvey(submission: SurveySubmission): Flow<Unit>
}
