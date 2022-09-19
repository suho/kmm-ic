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
    @State private var email: String = ""
    @State private var password: String = ""

    @StateObject private var viewModel = LoginViewModel()

    var body: some View {
        ZStack {
            Asset.splashBackground.image
                .resizable()
                .blur(radius: animated ? 20.0 : 0.0)
                .animation(.easeInOut(duration: 0.5))

            Asset.logoWhite.image
                .offset(x: 0.0, y: animated ? -229.0 : 0.0)
                .scaleEffect(animated ? (1.0 / 1.2) : 1.0)
                .animation(.easeInOut(duration: 0.5))

            VStack(alignment: .center, spacing: 20.0) {
                TextField(Localize.authenticationFieldsEmail(), text: $email)
                    .primaryFieldStyle()
                    .padding([.leading, .trailing])
                    .frame(height: 56.0, alignment: .center)

                SecureField(Localize.authenticationFieldsPassword(), text: $password)
                    .primaryFieldStyle()
                    .padding([.leading, .trailing])
                    .frame(height: 56.0, alignment: .center)

                PrimaryButton(title: Localize.authenticationButtonLogin()) {
                    // TODO: - Implement on integrate task
                    viewModel.logIn()
                }
                .padding([.leading, .trailing])
                .frame(height: 56.0, alignment: .center)
            }
            .frame(maxWidth: .infinity)
            .opacity(animated ? 1.0 : 0.0)
            .animation(.easeInOut(duration: 0.5))
        }
        .edgesIgnoringSafeArea(.all)
        .preferredColorScheme(.dark)
        .onAppear {
            DispatchQueue.main.asyncAfter(deadline: .now() + 0.5) {
                self.animated.toggle()
            }
        }
    }
}

struct LoginView_Previews: PreviewProvider {

    static var previews: some View {
        LoginView()
    }
}
