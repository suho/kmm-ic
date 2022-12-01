package co.nimblehq.ic.kmm.suv.domain.model

data class SurveySubmission(
    val surveyID: String,
    val questions: List<QuestionSubmission>
)

data class QuestionSubmission(
    val id: String,
    val answers: List<AnswerSubmission>
)

data class AnswerSubmission(
    val id: String,
    val answer: String? = null
)
