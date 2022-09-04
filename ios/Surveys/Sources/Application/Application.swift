// swiftlint:disable weak_delegate let_var_whitespace
import FirebaseCore
import SwiftUI

@main
struct Application: App {

    @UIApplicationDelegateAdaptor(AppDelegate.self)
    var delegate: AppDelegate

    var body: some Scene {
        WindowGroup {
            LoginView()
        }
    }
}
