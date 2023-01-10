//
//  AnswerInput.swift
//  Surveys
//
//  Created by Su Ho on 03/01/2023.
//  Copyright © 2023 Nimble. All rights reserved.
//

import Shared

// MARK: - AnswerInput

enum AnswerInput: Hashable, Equatable {

    case select(id: String)
    case content(id: String, value: String)

    var id: String {
        switch self {
        case let .select(id): return id
        case let .content(id, _): return id
        }
    }

    init?(_ answerInput: Shared.AnswerInput) {
        switch answerInput {
        case let select as Shared.AnswerInput.Select:
            self = AnswerInput.select(id: select.id)
        case let content as Shared.AnswerInput.Content:
            self = AnswerInput.content(id: content.id, value: content.content)
        default:
            return nil
        }
    }
}

extension Collection where Element: Shared.AnswerInput {

    func callAsFunction() -> [AnswerInput] {
        compactMap { input in
            AnswerInput(input)
        }
    }
}
