package co.nimblehq.ic.kmm.suv.domain.usecase

import co.nimblehq.ic.kmm.suv.domain.model.SurveySubmission
import co.nimblehq.ic.kmm.suv.domain.repository.SurveyRepository
import kotlinx.coroutines.flow.Flow

interface SubmitSurveyResponseUseCase {
    operator fun invoke(submission: SurveySubmission): Flow<Unit>
}

class SubmitSurveyResponseUseCaseImpl(private val repository: SurveyRepository) :
    SubmitSurveyResponseUseCase {

    override operator fun invoke(submission: SurveySubmission): Flow<Unit> {
        return repository.submitSurvey(submission)
    }
}
