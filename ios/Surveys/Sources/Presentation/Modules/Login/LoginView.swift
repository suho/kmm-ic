//
//  LoginView.swift
//  Surveys
//
//  Created by Su Ho on 31/08/2022.
//  Copyright © 2022 Nimble. All rights reserved.
//

import SwiftUI

struct LoginView: View {

    @State var animated = false
    @State private var email: String = ""
    @State private var password: String = ""

    var body: some View {
        ZStack {
            R.image.splashBackground.image
                .resizable()
                .blur(radius: animated ? 20 : 0)
                .animation(.easeInOut(duration: 0.5))

            R.image.logoWhite.image
                .offset(x: 0, y: animated ? -229 : 0)
                .scaleEffect(animated ? (1.0 / 1.2) : 1.0)
                .animation(.easeInOut(duration: 0.5))
                .onAppear {
                    DispatchQueue.main.asyncAfter(deadline: .now() + 0.5) {
                        self.animated.toggle()
                    }
                }

            VStack(alignment: .center, spacing: 20.0) {
                TextField(R.string.localizable.authenticationFieldsEmail(), text: $email)
                    .primaryFieldStyle()
                    .padding([.leading, .trailing])
                    .frame(height: 56.0, alignment: .center)

                SecureField(R.string.localizable.authenticationFieldsPassword(), text: $password)
                    .primaryFieldStyle()
                    .padding([.leading, .trailing])
                    .frame(height: 56.0, alignment: .center)

                PrimaryButton(title: R.string.localizable.authenticationFieldsLogin()) {
                    // TODO: - Implement on integrate task
                    print("Log in button did tap")
                }
                .padding([.leading, .trailing])
                .frame(height: 56.0, alignment: .center)
            }
            .frame(maxWidth: .infinity)
            .opacity(animated ? 1.0 : 0)
            .animation(.easeInOut(duration: 0.5))
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
