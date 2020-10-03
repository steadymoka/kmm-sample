import SwiftUI
import shared

struct ContentView: View {

    var body: some View {
        TabView {
            HomeView()
                .tabItem {
                    Image(systemName: "aa")
                    Text("Home")
                }
            
            ProfileView()
                .tabItem {
                    Image(systemName: "aa")
                    Text("Profile")
                }
        }
    }
}

#if DEBUG
struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        ContentView()
    }
}
#endif
