package co.nimblehq.ic.kmm.suv.domain.model

interface Answerable {

    val id: String
    val content: String?
    val placeholder: String?
}

data class Answer(
    override val id: String,
    val text: String?,
    val displayOrder: Int,
    var inputMaskPlaceholder: String?
) : Answerable {

    override val content: String?
        get() = text

    override val placeholder: String?
        get() = inputMaskPlaceholder
}
