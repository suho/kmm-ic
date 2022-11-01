//
//  LoginView.swift
//  Surveys
//
//  Created by Su Ho on 31/08/2022.
//  Copyright © 2022 Nimble. All rights reserved.
//

import SwiftUI

struct LoginView: View {

    @State private var animated = false

    @EnvironmentObject private var navigator: Navigator
    @StateObject private var viewModel = LoginViewModel()

    var body: some View {
        switch viewModel.state {
        case .idle:
            loginContent()
                .onAppear {
                    DispatchQueue.main.asyncAfter(deadline: .now() + .milliseconds(500)) {
                        self.animated.toggle()
                    }
                }
        case .loading:
            loginContent(isLoading: true)
        case .loginSuccess:
            loginContent()
                .onAppear {
                    navigator.show(screen: .home, by: .root)
                }
        case let .loginFail(message):
            loginContent()
                .alert(isPresented: .constant(true), content: {
                    Alert(
                        title: Text(Localize.generalTextSurveys()),
                        message: Text(message),
                        dismissButton: Alert.Button.default(Text(Localize.generalButtonGotIt()))
                    )
                })
        }
    }

    func loginContent(isLoading: Bool = false) -> some View {
        ZStack {
            Asset.splashBackground.image
                .resizable()
                .blur(radius: animated ? 20.0 : 0.0)
                .animation(.easeInOut, value: self.animated)

            Asset.logoWhite.image
                .offset(x: 0.0, y: animated ? -229.0 : 0.0)
                .scaleEffect(animated ? (1.0 / 1.2) : 1.0)
                .animation(.easeInOut, value: self.animated)

            VStack(alignment: .center, spacing: 20.0) {
                TextField(Localize.authenticationFieldsEmail(), text: $viewModel.email)
                    .primaryFieldStyle()
                    .padding([.leading, .trailing])
                    .frame(height: 56.0, alignment: .center)

                SecureField(Localize.authenticationFieldsPassword(), text: $viewModel.password)
                    .primaryFieldStyle()
                    .padding([.leading, .trailing])
                    .frame(height: 56.0, alignment: .center)

                PrimaryButton(title: Localize.authenticationButtonLogin(), isLoading: isLoading) {
                    viewModel.logIn()
                }
                .disabled(isLoading)
                .padding([.leading, .trailing])
                .frame(height: 56.0, alignment: .center)
            }
            .frame(maxWidth: .infinity)
            .opacity(animated ? 1.0 : 0.0)
            .animation(.easeInOut, value: self.animated)
        }
        .edgesIgnoringSafeArea(.all)
        .preferredColorScheme(.dark)
    }
}

struct LoginView_Previews: PreviewProvider {

    static var previews: some View {
        LoginView()
    }
}
