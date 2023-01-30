//
//  R+SwiftUI.swift
//  Surveys
//
//  Created by Su Ho on 04/09/2022.
//  Copyright © 2022 Nimble. All rights reserved.
//

import Rswift
import SwiftUI

extension ImageResource {

    var image: Image {
        Image(name)
    }
}

extension FontResource {

    func font(size: CGFloat) -> Font {
        Font.custom(fontName, size: size)
    }
}

extension ColorResource {

    func callAsFunction() -> Color {
        Color(name)
    }
}
