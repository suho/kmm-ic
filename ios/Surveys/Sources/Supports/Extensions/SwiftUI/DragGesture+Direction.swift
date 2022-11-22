// swiftlint:disable identifier_name
//  DragGesture+LeftRight.swift
//  Surveys
//
//  Created by Su Ho on 18/11/2022.
//  Copyright © 2022 Nimble. All rights reserved.
//

import SwiftUI

extension DragGesture.Value {

    enum Direction {

        case left
        case right
        case up
        case down
    }
}

extension DragGesture.Value {

    func detectDirection(_ tolerance: Double = 24.0) -> Direction? {
        if startLocation.x < location.x - tolerance { return .left }
        if startLocation.x > location.x + tolerance { return .right }
        if startLocation.y > location.y + tolerance { return .up }
        if startLocation.y < location.y - tolerance { return .down }
        return nil
    }
}

extension View {

    func leftOrRightGesture(action: @escaping (DragGesture.Value.Direction) -> Void) -> some View {
        gesture(
            DragGesture()
                .onEnded { value in
                    guard let direction = value.detectDirection() else { return }
                    if direction == .left || direction == .right {
                        action(direction)
                    }
                }
        )
    }
}
