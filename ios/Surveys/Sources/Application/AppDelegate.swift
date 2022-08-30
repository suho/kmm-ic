//
//  AppDelegate.swift
//  Surveys
//
//  Created by Su Ho on 24/08/2022.
//  Copyright © 2022 Nimble. All rights reserved.
//

import FirebaseCore
import UIKit

final class AppDelegate: NSObject, UIApplicationDelegate {

    func application(
        _ application: UIApplication,
        didFinishLaunchingWithOptions launchOptions: [UIApplication.LaunchOptionsKey: Any]? = nil
    ) -> Bool {
        FirebaseApp.configure()
        return true
    }
}
