package land.moka.kmm.shared.app.model

sealed class LayoutCommander {

    object ShowNetworkError : LayoutCommander()
    object ShowAppError : LayoutCommander()

}

