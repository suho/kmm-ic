package co.nimblehq.ic.kmm.suv.data.remote.parameter

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GetSurveysApiQueryParams(
    @SerialName("page[number]")
    val pageNumber: Int,
    @SerialName("page[size]")
    val pageSize: Int
)
