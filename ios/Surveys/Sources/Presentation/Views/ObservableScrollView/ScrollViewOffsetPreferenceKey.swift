//
//  ScrollViewOffsetPreferenceKey.swift
//  Surveys
//
//  Created by Su Ho on 16/01/2023.
//  Copyright © 2023 Nimble. All rights reserved.
//

import SwiftUI

struct ScrollViewOffsetPreferenceKey: PreferenceKey {

    static var defaultValue = CGFloat.zero

    static func reduce(value: inout CGFloat, nextValue: () -> CGFloat) {
        value += nextValue()
    }
}
