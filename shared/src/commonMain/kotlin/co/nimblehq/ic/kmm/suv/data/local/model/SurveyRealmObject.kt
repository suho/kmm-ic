package co.nimblehq.ic.kmm.suv.data.local.model

import co.nimblehq.ic.kmm.suv.domain.model.Survey
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey

class SurveyRealmObject : RealmObject {
    @PrimaryKey
    var id: String = ""
    var type: String = ""
    var title: String = ""
    var description: String = ""
    var isActive: Boolean = false
    var coverImageUrl: String = ""
}

fun SurveyRealmObject.toSurvey() = Survey(
    title,
    description,
    isActive,
    coverImageUrl
)
