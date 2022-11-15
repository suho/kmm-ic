package co.nimblehq.ic.kmm.suv.domain.repository

import co.nimblehq.ic.kmm.suv.domain.model.Survey
import kotlinx.coroutines.flow.Flow

interface SurveyRepository {

    fun getSurveys(pageNumber: Int, pageSize: Int): Flow<List<Survey>>
}
