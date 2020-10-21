import SwiftUI
import shared

struct HomeView: View {
    
    var body: some View {
        ZStack {
            VStack {
                Spacer()
                Rectangle()
                    .fill(Color.white)
                    .frame(width: .infinity, height: 10.0)
                    .shadow(color: Color(red: 0, green: 0, blue: 0, opacity: 0.1), radius: 4, x: 0.0, y: -2)
            }
            
            Text("Hello, World!\nHome")
                .scaledToFill()
        }
        .onAppear() {
            aaaa()
        }
    }

    func aaaa() {
        let container = AppContainer.Companion().start()
        ProfileContainer.Companion().onCreate(container: container)
        let viewModel = (container.getContainer(name: "ProfileContainer") as! ProfileContainer).viewModel

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
