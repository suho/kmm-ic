//
//  FormAnswerView.swift
//  Surveys
//
//  Created by Su Ho on 11/01/2023.
//  Copyright © 2023 Nimble. All rights reserved.
//

import SwiftUI

// MARK: - FormAnswerView

struct FormAnswerView: View {

    let answers: [Answer]

    @State var input: [AnswerInput]

    var body: some View {
        VStack(spacing: 16.0) {
            ForEach(0 ..< answers.count, id: \.self) { index in
                TextField(answers[index].placeholder.string, text: content(of: index))
                    .primaryFieldStyle()
                    .padding(.horizontal, 12.0)
            }
        }
    }

    func content(of index: Int) -> Binding<String> {
        let currentInput = input[index]
        return Binding<String>(
            get: { currentInput.content.string },
            set: { input[index] = AnswerInput.content(id: currentInput.id, value: $0) }
        )
    }
}

// MARK: - FormAnswerView_Previews

struct FormAnswerView_Previews: PreviewProvider {
    static var previews: some View {
        let answers = [
            Answer(id: "1"),
            Answer(id: "2"),
            Answer(id: "3"),
            Answer(id: "4")
        ]
        FormAnswerView(answers: answers, input: answers.map { AnswerInput.content(id: $0.id, value: "") })
    }
}
