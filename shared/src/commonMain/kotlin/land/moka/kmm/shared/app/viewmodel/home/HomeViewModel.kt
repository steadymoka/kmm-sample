package land.moka.kmm.shared.app.viewmodel.home

import land.moka.kmm.shared.app.model.User
import land.moka.kmm.shared.app.preference.MokaPref
import land.moka.kmm.shared.app.viewmodel.ViewModel

class HomeViewModel(
    private val mokaPref: MokaPref
) : ViewModel {

    fun saveUser(id: String, name: String) {
        mokaPref.user = User(id, name)
    }

    fun getUser(): User {
        return mokaPref.user
    }


}
