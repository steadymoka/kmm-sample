import SwiftUI
import shared

struct MainView: View {

    var body: some View {
        TabView {
            HomeView()
                .tabItem {
                    Image("ic_home")
                }
            
            ProfileView()
                .tabItem {
                    Image("ic_profile")
                }
        }
    }
}

struct MainView_Previews: PreviewProvider {
    static var previews: some View {
        MainView()
    }
}
