package co.nimblehq.ic.kmm.suv.domain.model

data class Question(
    val id: String,
    val text: String,
    val displayOrder: Int,
    val displayType: String,
    val pick: String,
    val coverImageUrl: String,
    val answers: List<Answer>
)

fun Question.sortedAnswers(): List<Answer> {
    return answers.sortedBy { displayOrder }
}
