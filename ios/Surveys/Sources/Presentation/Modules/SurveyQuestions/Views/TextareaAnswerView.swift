//
//  TextareaAnswerView.swift
//  Surveys
//
//  Created by Su Ho on 10/01/2023.
//  Copyright © 2023 Nimble. All rights reserved.
//

import SwiftUI

// MARK: - TextareaAnswerView

struct TextareaAnswerView: View {

    @ObservedObject var viewModel: AnswerViewModel

    var content: Binding<String> {
        Binding<String>(
            get: { viewModel.input?.content ?? "" },
            set: { viewModel.input = AnswerInput.content(id: viewModel.answers.first?.id ?? "", value: $0) }
        )
    }

    var body: some View {
        TextEditor(placeholder: viewModel.answers.first?.placeholder ?? "", text: content)
    }
}

// MARK: - TextareaAnswerView_Previews

struct TextareaAnswerView_Previews: PreviewProvider {

    static var previews: some View {
        TextareaAnswerView(
            viewModel: .init(questionId: "-", answers: [Answer(id: "1", content: "Email", placeholder: "Email")])
        )
        .frame(width: 327.0, height: 168.0)
    }
}
