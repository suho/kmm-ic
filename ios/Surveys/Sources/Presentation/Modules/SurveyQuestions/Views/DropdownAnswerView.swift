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

    @State private var selectedInput: String

    private let ids: [String]
    private let answers: [Answer]
    private var input: AnswerInput
    private let inputDidChange: (AnswerInput) -> Void

    var body: some View {
        Picker(String(describing: Self.self), selection: $selectedInput) {
            ForEach(ids, id: \.self) { id in
                let font = selectedInput == id
                    ? Typography.neuzeitSLTStdBookHeavy.font(size: 20.0)
                    : Typography.neuzeitSLTStdBook.font(size: 20.0)
                Text(answers.first(where: { $0.id == id })?.content ?? "")
                    .font(font)
                    .foregroundColor(Color.white)
            }
        }
        .pickerStyle(.wheel)
        .padding(20.0)
        .onChange(of: selectedInput) {
            print($0)
        }
    }

    init(answers: [Answer], input: AnswerInput?, inputDidChange: @escaping (AnswerInput) -> Void) {
        self.answers = answers
        self.input = input ?? .select(id: answers.first?.id ?? "")
        self.inputDidChange = inputDidChange
        ids = answers.map(\.id)
        selectedInput = self.input.id
    }
}

// MARK: - DropdownAnswerView_Previews

struct DropdownAnswerView_Previews: PreviewProvider {

    static var previews: some View {
        DropdownAnswerView(
            answers: [
                .init(id: "1", content: "Choice 1", placeholder: nil),
                .init(id: "2", content: "Choice 2", placeholder: nil),
                .init(id: "3", content: "Choice 3", placeholder: nil)
            ],
            input: nil,
            inputDidChange: { _ in }
        )
        .background(Color.black)
    }
}
