package co.nimblehq.ic.kmm.suv.domain.usecase

import co.nimblehq.ic.kmm.suv.domain.model.Survey
import co.nimblehq.ic.kmm.suv.domain.repository.SurveyRepository
import kotlinx.coroutines.flow.Flow

interface GetSurveysUseCase {
    operator fun invoke(pageNumber: Int, pageSize: Int): Flow<List<Survey>>
}

class GetSurveysUseCaseImpl(
    private val surveyRepository: SurveyRepository
) : GetSurveysUseCase {

    override operator fun invoke(pageNumber: Int, pageSize: Int): Flow<List<Survey>> {
        return surveyRepository.getSurveys(pageNumber, pageSize)
    }
}
