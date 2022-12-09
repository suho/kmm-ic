package co.nimblehq.ic.kmm.suv.domain.model

data class Survey(
    val id: String,
    val title: String,
    val description: String,
    val isActive: Boolean,
    val coverImageUrl: String,
    val questions: List<Question>? = null
)

fun Survey.sortedQuestions(): List<Question>? {
    return questions?.sortedBy { it.displayOrder }
}
