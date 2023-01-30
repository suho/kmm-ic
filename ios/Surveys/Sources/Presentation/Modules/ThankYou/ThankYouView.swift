//
//  ThankYouView.swift
//  Surveys
//
//  Created by Su Ho on 12/01/2023.
//  Copyright © 2023 Nimble. All rights reserved.
//

import SwiftUI

// MARK: - ThankYouView

struct ThankYouView: View {

    @EnvironmentObject private var navigator: Navigator

    var body: some View {
        ZStack {
            ColorAsset.backgroundColor()
                .edgesIgnoringSafeArea(.all)

            VStack(alignment: .center) {
                LottieView(fileName: "confetti") {
                    DispatchQueue.main.asyncAfter(deadline: .now() + 0.5) {
                        navigator.steps {
                            $0.dismiss()
                            $0.goBackToRoot()
                        }
                    }
                }
                .frame(width: 200.0, height: 200.0)
                Text(Localize.thankYouTextTitle())
                    .font(Typography.neuzeitSLTStdBookHeavy.font(size: 28.0))
                    .foregroundColor(Color.white)
                    .multilineTextAlignment(.center)
                    .padding()
            }
        }
        .navigationBarBackButtonHidden(true)
    }
}

// MARK: - ThankYouView_Previews

struct ThankYouView_Previews: PreviewProvider {
    static var previews: some View {
        ThankYouView()
    }
}
