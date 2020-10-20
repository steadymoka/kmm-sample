import shared

extension Kotlinx_coroutines_coreFlow {

    func observe( _ action: () -> Unit) {
        ApiUtilKt.observe(self, action)
    }

}