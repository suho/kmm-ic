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
    private let isLoading: Bool
    private let action: () -> Void

    var body: some View {
        Button(action: action) {
            if isLoading {
                ProgressView()
                    .progressViewStyle(CircularProgressViewStyle(tint: .black))
                    .frame(maxHeight: .infinity, alignment: .center)
            } else {
                Text(title)
                    .frame(maxWidth: .infinity, maxHeight: .infinity, alignment: .center)
                    .font(Typography.neuzeitSLTStdBookHeavy.font(size: 17.0))
            }
        }
        .frame(maxWidth: .infinity, maxHeight: .infinity)
        .background(Color.white)
        .foregroundColor(Color.black)
        .cornerRadius(10.0)
    }

    init(title: String, isLoading: Bool, action: @escaping () -> Void) {
        self.title = title
        self.isLoading = isLoading
        self.action = action
    }
}
