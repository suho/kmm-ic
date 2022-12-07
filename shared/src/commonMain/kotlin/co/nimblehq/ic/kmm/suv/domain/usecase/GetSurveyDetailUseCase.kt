package co.nimblehq.ic.kmm.suv.domain.usecase

import co.nimblehq.ic.kmm.suv.domain.model.Survey
import co.nimblehq.ic.kmm.suv.domain.repository.SurveyRepository
import kotlinx.coroutines.flow.Flow

interface GetSurveyDetailUseCase {
    operator fun invoke(id: String): Flow<Survey>
}

class GetSurveyDetailUseCaseImpl(
    private val surveyRepository: SurveyRepository
) : GetSurveyDetailUseCase {

    override operator fun invoke(id: String): Flow<Survey> {
        return surveyRepository.getSurveyDetail(id)
    }
}
