package co.nimblehq.ic.kmm.suv.data.local.datasource

import co.nimblehq.ic.kmm.suv.data.local.model.SurveyRealmObject
import io.realm.kotlin.Realm
import io.realm.kotlin.UpdatePolicy
import io.realm.kotlin.ext.query

interface SurveyLocalDataSource {
    fun saveSurveys(surveys: List<SurveyRealmObject>)
    fun getSurveys(): List<SurveyRealmObject>
    fun deleteAllSurveys()
}

class SurveyLocalDataSourceImpl(private val realm: Realm) : SurveyLocalDataSource {

    override fun saveSurveys(surveys: List<SurveyRealmObject>) {
        realm.writeBlocking {
            surveys.forEach {
                copyToRealm(it, updatePolicy = UpdatePolicy.ALL)
            }
        }
    }

    override fun getSurveys(): List<SurveyRealmObject> {
        return realm.query<SurveyRealmObject>().find()
    }

    override fun deleteAllSurveys() {
        realm.writeBlocking {
            val surveys = query<SurveyRealmObject>().find()
            delete(surveys)
        }
    }
}
