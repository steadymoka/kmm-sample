import SwiftUI
import shared

struct ContentView: View {

    var body: some View {
        TabView {
            HomeView()
                .tabItem {
                    Image(systemName: "aa")
                }
            
            ProfileView()
                .tabItem {
                    Image(systemName: "aa")
                    Text("ac")
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
