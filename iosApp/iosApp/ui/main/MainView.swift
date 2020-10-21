import SwiftUI
import shared

struct MainView: View {

    @State private var selection = 0
    
    var body: some View {
        return NavigationView {
            TabView(selection: $selection) {
                HomeView()
                    .tabItem {
                        Image("ic_home")
                    }
                    .tag(0)
                
                ProfileView()
                    .tabItem {
                        Image("ic_profile")
                    }
                    .tag(1)
            }
            .accentColor(.black)
            .onAppear() {
                UITabBar.appearance().barTintColor = .white
                UITabBar.appearance().clipsToBounds = true
            }
        }
    }
}

#if DEBUG
struct MainView_Previews: PreviewProvider {
    static var previews: some View {
        MainView()
    }
}
#endif
