//
//  DropdownAnswerView.swift
//  Surveys
//
//  Created by Su Ho on 29/12/2022.
//  Copyright © 2022 Nimble. All rights reserved.
//

import SwiftUI

// MARK: - DropdownAnswerView

struct DropdownAnswerView: View {

    @ObservedObject var viewModel: AnswerViewModel

    private var selection: Binding<String> {
        Binding<String>(
            get: { viewModel.input?.id ?? "-" },
            set: { viewModel.input = AnswerInput.select(id: $0) }
        )
    }

    private lazy var ids: [String] = viewModel.answers.map { $0.id }

    var body: some View {
        Picker(String(describing: Self.self), selection: selection) {
            ForEach(0 ..< viewModel.answers.count, id: \.self) { index in
                let font = selection.wrappedValue == viewModel.answers[index].id
                    ? Typography.neuzeitSLTStdBookHeavy.font(size: 20.0)
                    : Typography.neuzeitSLTStdBook.font(size: 20.0)
                Text(viewModel.answers[index].content.string)
                    .font(font)
                    .foregroundColor(Color.white)
            }
        }
        .pickerStyle(.wheel)
        .padding(20.0)
    }

    init(viewModel: AnswerViewModel) {
        self.viewModel = viewModel
    }
}

// MARK: - DropdownAnswerView_Previews

struct DropdownAnswerView_Previews: PreviewProvider {

    static var previews: some View {
        DropdownAnswerView(
            viewModel: .init(
                questionId: "-",
                answers: [
                    .init(id: "1", content: "Choice 1", placeholder: nil),
                    .init(id: "2", content: "Choice 2", placeholder: nil),
                    .init(id: "3", content: "Choice 3", placeholder: nil)
                ],
                input: AnswerInput.select(id: "1")
            )
        )
        .background(Color.black)
    }
}
