//
//  Answer.swift
//  Surveys
//
//  Created by Su Ho on 03/01/2023.
//  Copyright © 2023 Nimble. All rights reserved.
//

import Shared

// MARK: - Answer

struct Answer: Equatable {

    let id: String
    let content: String?
    let placeholder: String?

    init(id: String, content: String? = nil, placeholder: String? = nil) {
        self.id = id
        self.content = content
        self.placeholder = placeholder
    }

    init(_ answer: Shared.Answerable) {
        id = answer.id
        content = answer.content
        placeholder = answer.placeholder
    }
}

extension Collection where Element: Shared.Answerable {

    func callAsFunction() -> [Answer] {
        compactMap { answerable in
            Answer(answerable)
        }
    }
}
