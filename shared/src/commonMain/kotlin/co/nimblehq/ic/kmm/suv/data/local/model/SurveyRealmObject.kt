package co.nimblehq.ic.kmm.suv.data.local.model

import co.nimblehq.ic.kmm.suv.domain.model.Survey
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey

open class SurveyRealmObject : RealmObject {

    @PrimaryKey
    var id: String = ""
    var type: String = ""
    var title: String = ""
    var description: String = ""
    var isActive: Boolean = false
    var coverImageUrl: String = ""

    constructor(
        id: String,
        type: String,
        title: String,
        description: String,
        isActive: Boolean,
        coverImageUrl: String
    ) : this() {
        this.id = id
        this.type = type
        this.title = title
        this.description = description
        this.isActive = isActive
        this.coverImageUrl = coverImageUrl
    }

    constructor()
}

fun SurveyRealmObject.toSurvey() = Survey(
    id,
    title,
    description,
    isActive,
    coverImageUrl,
    emptyList()
)
