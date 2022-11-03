// swiftlint:disable weak_delegate
import FirebaseCore
import FlowStacks
import SwiftUI

@main
struct Application: App {

    @UIApplicationDelegateAdaptor(AppDelegate.self) var delegate: AppDelegate

    var body: some Scene {
        WindowGroup {
            AppNavigator()
        }
    }
}
