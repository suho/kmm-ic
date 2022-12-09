package co.nimblehq.ic.kmm.suv.data.remote.datasource

import co.nimblehq.ic.kmm.suv.data.remote.apiclient.builder.path
import co.nimblehq.ic.kmm.suv.data.remote.apiclient.builder.setQueryParameters
import co.nimblehq.ic.kmm.suv.data.remote.apiclient.core.ApiClient
import co.nimblehq.ic.kmm.suv.data.remote.body.SurveySubmissionApiBody
import co.nimblehq.ic.kmm.suv.data.remote.model.SurveyApiModel
import co.nimblehq.ic.kmm.suv.data.remote.parameter.GetSurveysApiQueryParams
import io.ktor.client.request.*
import io.ktor.http.*
import kotlinx.coroutines.flow.Flow

interface SurveyRemoteDataSource {
    fun getSurveys(params: GetSurveysApiQueryParams): Flow<List<SurveyApiModel>>
    fun getSurvey(id: String): Flow<SurveyApiModel>
    fun submitSurvey(body: SurveySubmissionApiBody): Flow<Unit>
}

class SurveyRemoteDataSourceImpl(private val apiClient: ApiClient) : SurveyRemoteDataSource {

    override fun getSurveys(params: GetSurveysApiQueryParams): Flow<List<SurveyApiModel>> {
        return apiClient.responseBody(
            HttpRequestBuilder().apply {
                path("/v1/surveys")
                method = HttpMethod.Get
                setQueryParameters(params)
            }
        )
    }

    override fun getSurvey(id: String): Flow<SurveyApiModel> {
        return apiClient.responseBody(
            HttpRequestBuilder().apply {
                path("/v1/surveys/${id}")
                method = HttpMethod.Get
            }
        )
    }

    override fun submitSurvey(body: SurveySubmissionApiBody): Flow<Unit> {
        return apiClient.emptyResponseBody(
            HttpRequestBuilder().apply {
                path("/v1/responses")
                method = HttpMethod.Post
                setBody(body)
            }
        )
    }
}
