package co.nimblehq.ic.kmm.suv.data.local.realm

import co.nimblehq.ic.kmm.suv.data.local.model.SurveyRealmObject
import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration

val realm: Realm by lazy {
    val configuration = RealmConfiguration.create(schema = setOf(SurveyRealmObject::class))
    Realm.open(configuration)
}
