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

    @ObservedObject var viewModel: AnswerViewModel

    var body: some View {
        VStack(spacing: 16.0) {
            ForEach(0 ..< viewModel.answers.count, id: \.self) { index in
                TextField(viewModel.answers[index].placeholder.string, text: content(of: index))
                    .primaryFieldStyle()
                    .padding(.horizontal, 12.0)
                    .accessibilityIdentifier(
                        AccessibilityIdentifier.AnswerView.textField(
                            questionId: viewModel.questionId,
                            index: index
                        )
                    )
            }
        }
    }

    func content(of index: Int) -> Binding<String> {
        let answer = viewModel.answers[index]
        let currentInput = viewModel.inputs.first(where: { $0.id == answer.id })
        return Binding<String>(
            get: { (currentInput?.content).string },
            set: {
                if let currentInput {
                    viewModel.inputs.remove(currentInput)
                    viewModel.inputs.insert(AnswerInput.content(id: currentInput.id, value: $0))
                }
            }
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
        FormAnswerView(viewModel: .init(questionId: "-", answers: answers))
    }
}
