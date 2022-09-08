//
//  View+PrimaryFieldStyle.swift
//  Surveys
//
//  Created by Su Ho on 06/09/2022.
//  Copyright © 2022 Nimble. All rights reserved.
//

import SwiftUI

extension View {

    func primaryFieldStyle() -> some View {
        font(R.font.neuzeitSLTStdBook.font(size: 17.0))
            .accentColor(Color.white)
            .padding()
            .background(Color.white.opacity(0.18))
            .cornerRadius(12.0)
    }
}
