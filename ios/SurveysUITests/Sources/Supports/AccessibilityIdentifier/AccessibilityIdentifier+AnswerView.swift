//
//  AccessibilityIdentifier+AnswerView.swift
//  Surveys
//
//  Created by Su Ho on 12/01/2023.
//  Copyright © 2023 Nimble. All rights reserved.
//

extension AccessibilityIdentifier {

    enum AnswerView {

        static func emoji(questionId: String, index: Int) -> String { "emoji-\(questionId)-\(index)" }
        static func choice(questionId: String, index: Int) -> String { "choice-\(questionId)-\(index)" }
        static func nps(questionId: String, index: Int) -> String { "choice-\(questionId)-\(index)" }
        static func textField(questionId: String, index: Int) -> String { "textField-\(questionId)-\(index)" }
    }
}
