package co.nimblehq.ic.kmm.suv.data.remote.body

import co.nimblehq.ic.kmm.suv.domain.model.AnswerSubmission
import co.nimblehq.ic.kmm.suv.domain.model.QuestionSubmission
import co.nimblehq.ic.kmm.suv.domain.model.SurveySubmission
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SurveySubmissionApiBody(
    @SerialName("survey_id")
    val surveyId: String,
    @SerialName("questions")
    val questions: List<QuestionSubmissionApiBody>
) {
    constructor(surveySubmission: SurveySubmission) : this(
        surveySubmission.id,
        surveySubmission.questions.map(::QuestionSubmissionApiBody)
    )
}

@Serializable
data class QuestionSubmissionApiBody(
    @SerialName("id")
    val id: String,
    @SerialName("answers")
    val answers: List<AnswerSubmissionApiBody>
) {
    constructor(questionSubmission: QuestionSubmission) : this(
        questionSubmission.id,
        questionSubmission.answers.map(::AnswerSubmissionApiBody)
    )
}

@Serializable
data class AnswerSubmissionApiBody(
    @SerialName("id")
    val id: String,
    @SerialName("answer")
    val answer: String?
) {
    constructor(answerSubmission: AnswerSubmission) : this(
        answerSubmission.id,
        answerSubmission.answer
    )
}
