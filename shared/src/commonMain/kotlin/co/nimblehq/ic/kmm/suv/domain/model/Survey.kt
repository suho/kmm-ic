package co.nimblehq.ic.kmm.suv.domain.model

data class Survey(
    val title: String,
    val description: String,
    val isActive: Boolean,
    val coverImageUrl: String
)
