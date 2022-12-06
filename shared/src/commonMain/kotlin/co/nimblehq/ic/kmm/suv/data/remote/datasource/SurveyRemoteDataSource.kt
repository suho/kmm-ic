package co.nimblehq.ic.kmm.suv.data.remote.datasource

import co.nimblehq.ic.kmm.suv.data.remote.apiclient.builder.path
import co.nimblehq.ic.kmm.suv.data.remote.apiclient.builder.setQueryParameters
import co.nimblehq.ic.kmm.suv.data.remote.apiclient.core.ApiClient
import co.nimblehq.ic.kmm.suv.data.remote.model.SurveyApiModel
import co.nimblehq.ic.kmm.suv.data.remote.parameter.GetSurveysApiQueryParams
import io.ktor.client.request.*
import io.ktor.http.*
import kotlinx.coroutines.flow.Flow

interface SurveyRemoteDataSource {
    fun getSurveys(params: GetSurveysApiQueryParams): Flow<List<SurveyApiModel>>
}

class SurveyRemoteDataSourceImpl(private val apiClient: ApiClient) : SurveyRemoteDataSource {

    override fun getSurveys(params: GetSurveysApiQueryParams): Flow<List<SurveyApiModel>> {
        return apiClient.body(
            HttpRequestBuilder().apply {
                path("/v1/surveys")
                method = HttpMethod.Get
                setQueryParameters(params)
            }
        )
    }
}
