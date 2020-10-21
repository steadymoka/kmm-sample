import SwiftUI

struct ProfileView: View {
    
    var body: some View {
        ZStack {
            VStack {
                Spacer()
                Rectangle()
                    .fill(Color.white)
                    .frame(width: .infinity, height: 10.0)
                    .shadow(color: Color(red: 0, green: 0, blue: 0, opacity: 0.1), radius: 4, x: 0.0, y: -2)
            }
            
            Text("Hello, World!\nProfile")
                .scaledToFill()
        }
        .onAppear() {
        }
    }
}

#if DEBUG
struct ProfileView_Previews: PreviewProvider {
    static var previews: some View {
        ProfileView()
    }
}
#endif
