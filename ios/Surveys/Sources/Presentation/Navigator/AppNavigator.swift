//
//  AppNavigator.swift
//  Surveys
//
//  Created by Su Ho on 19/10/2022.
//  Copyright © 2022 Nimble. All rights reserved.
//

import FlowStacks
import SwiftUI

struct AppNavigator: View {

    @StateObject var navigator = Navigator()

    var body: some View {
        Router($navigator.routes) { screen, _ in
            switch screen {
            case .login:
                LoginView()
            case .home:
                HomeView()
            case let .surveyDetail(viewModel):
                SurveyDetailView(viewModel: viewModel)
            case let .surveyQuestions(viewModel):
                SurveyQuestionsView(viewModel: viewModel)
            }
        }
        .environmentObject(navigator)
    }
}
