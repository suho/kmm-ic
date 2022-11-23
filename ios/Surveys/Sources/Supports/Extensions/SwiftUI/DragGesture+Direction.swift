// swiftlint:disable identifier_name
//  DragGesture+LeftRight.swift
//  Surveys
//
//  Created by Su Ho on 18/11/2022.
//  Copyright © 2022 Nimble. All rights reserved.
//

import SwiftUI

extension DragGesture.Value {

    struct Direction: OptionSet {

        let rawValue: Int

        static let left = Direction(rawValue: 1 << 0)
        static let right = Direction(rawValue: 1 << 1)
        static let up = Direction(rawValue: 1 << 2)
        static let down = Direction(rawValue: 1 << 3)

        static let horizontal: Direction = [.left, .right]

        static let all: Direction = [.left, .right, .up, .down]
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

    func swipe(
        _ direction: DragGesture.Value.Direction = .all,
        action: @escaping (DragGesture.Value.Direction) -> Void
    ) -> some View {
        gesture(
            DragGesture()
                .onEnded { value in
                    guard let detectedDirection = value.detectDirection() else { return }
                    if direction.contains(detectedDirection) {
                        action(detectedDirection)
                    }
                }
        )
    }
}
