package land.moka.kmm.shared.model

data class Profile(
    var name: String,
    var avatarUrl: String,
    var bio: String,
    var status: Status?
) {

    data class Status(
        var message: String
    )

    companion object {
        fun init() = Profile("", "", "", null)
    }

}

data class Pinned(
    var id: String,
    var name: String,
    var description: String
)

data class Organizer(
    var name: String,
    var avatarUrl: String,
    var description: String,
)

data class Repository(
    var name: String,
    var description: String
)