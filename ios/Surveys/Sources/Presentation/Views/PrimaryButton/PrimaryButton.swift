//
//  PrimaryButton.swift
//  Surveys
//
//  Created by Su Ho on 04/09/2022.
//  Copyright © 2022 Nimble. All rights reserved.
//

import SwiftUI

struct PrimaryButton: View {

    private let title: String
    private let action: () -> Void

    var body: some View {
        GeometryReader { geometry in
            Button(action: action) {
                Text(title)
                    .frame(height: geometry.size.height, alignment: .center)
                    .font(R.font.neuzeitSLTStdBookHeavy.font(size: 17.0))
            }
            .frame(maxWidth: .infinity)
            .background(Color.white)
            .foregroundColor(Color.black)
            .cornerRadius(10.0)
        }
    }

    init(title: String, action: @escaping () -> Void) {
        self.title = title
        self.action = action
    }
}
