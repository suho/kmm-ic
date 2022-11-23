//
//  GeometryProxy+Size.swift
//  Surveys
//
//  Created by Su Ho on 18/11/2022.
//  Copyright © 2022 Nimble. All rights reserved.
//

import SwiftUI

extension GeometryProxy {

    var maxWidth: CGFloat {
        size.width + safeAreaInsets.leading + safeAreaInsets.trailing
    }

    var maxHeight: CGFloat {
        size.height + safeAreaInsets.top + safeAreaInsets.bottom
    }
}
