package land.moka.kmm.shared.app.preference

import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import land.moka.kmm.shared.app.model.User
import land.moka.kmm.shared.lib.preferecne.Preference


class MokaPref {

    private val preference: Preference = Preference("hello_moka")

    private val KEY_USER = "KEY_USER"
    var user: User
        get() = Json.decodeFromString(string = preference.getString(KEY_USER, "{}"))
        set(value) = preference.setString(KEY_USER, Json.encodeToString(value))

}
