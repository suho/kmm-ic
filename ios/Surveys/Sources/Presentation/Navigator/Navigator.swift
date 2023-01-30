//
//  Navigator.swift
//  Surveys
//
//  Created by Su Ho on 19/10/2022.
//  Copyright © 2022 Nimble. All rights reserved.
//

import FlowStacks
import SwiftUI

// MARK: - Navigator

final class Navigator: ObservableObject {

    @Published var routes: Routes<Screen> = [.root(.login)]

    func show(screen: Screen, by transition: Transition) {
        switch transition {
        case .root:
            routes = [.root(screen, embedInNavigationView: true)]
        case .push:
            routes.push(screen)
        case .presentSheet:
            routes.presentSheet(screen)
        case .presentCover:
            routes.presentCover(screen, embedInNavigationView: true)
        }
    }

    func goBack() {
        routes.goBack()
    }

    func goBackToRoot() {
        routes.goBackToRoot()
    }

    func pop() {
        routes.pop()
    }

    func dismiss() {
        routes.dismiss()
    }

    func steps(routes: (inout Routes<Screen>) -> Void) {
        RouteSteps.withDelaysIfUnsupported(self, \.routes) { routes(&$0) }
    }
}

// MARK: Navigator.Transition

extension Navigator {

    enum Transition {

        case root
        case push
        case presentSheet
        case presentCover
    }
}
