//
//  QuestionDisplayType.swift
//  Surveys
//
//  Created by Su Ho on 11/01/2023.
//  Copyright © 2023 Nimble. All rights reserved.
//

import Combine
import Shared

// MARK: - AnswerViewModel

final class AnswerViewModel: ObservableObject {

    let answers: [Answer]
    @Published var inputs: Set<AnswerInput>
    @Published var input: AnswerInput?

    init(answers: [Answer], inputs: Set<AnswerInput> = [], input: AnswerInput? = nil) {
        self.answers = answers
        self.inputs = inputs
        self.input = input
    }
}

// MARK: Equatable

extension AnswerViewModel: Equatable {

    static func == (lhs: AnswerViewModel, rhs: AnswerViewModel) -> Bool {
        return lhs.answers == rhs.answers && lhs.input == rhs.input
    }
}
