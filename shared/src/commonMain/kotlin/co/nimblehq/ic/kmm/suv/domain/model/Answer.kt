package co.nimblehq.ic.kmm.suv.domain.model

data class Answer(
    val id: String,
    val text: String?,
    val displayOrder: Int,
    var inputMaskPlaceholder: String?
)
