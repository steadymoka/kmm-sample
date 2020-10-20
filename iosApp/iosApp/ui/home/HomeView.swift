import SwiftUI
import shared

struct HomeView: View {
    var body: some View {
        Text("Hello, World!\nHome")
                .onAppear {
                    aaaa()
                }
    }

    func observe( _ action: () -> Unit) {

    }
    
    func aaaa() {
        let container = AppContainer()
        ProfileScope().onCreate(container: container)
        let viewModel = container.profileContainer!.viewModel

        viewModel.profile.observe { (value) in
            debugPrint("aaaaaaaaaaaaa value: \(value)")
        }
        viewModel.loadProfileData { unit, error in }
    }
}

struct HomeView_Previews: PreviewProvider {
    static var previews: some View {
        HomeView()
    }
}

extension Kotlinx_coroutines_coreFlow {

    func observe( _ action: @escaping (Any?) -> Void) {
        ApiUtilKt.observe(self, action: action)
    }

}
