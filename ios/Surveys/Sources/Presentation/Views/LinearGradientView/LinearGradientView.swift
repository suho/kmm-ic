//
//  LinearGradientView.swift
//  Surveys
//
//  Created by Su Ho on 06/01/2023.
//  Copyright © 2023 Nimble. All rights reserved.
//

import SwiftUI

struct LinearGradientView: View {

    var body: some View {
        Rectangle()
            .foregroundColor(.clear)
            .background(
                LinearGradient(colors: [.clear, .black], startPoint: .top, endPoint: .bottom)
            )
            .opacity(0.6)
            .ignoresSafeArea()
    }
}
