//
//  MultipleChoicesAnswerView.swift
//  Surveys
//
//  Created by Su Ho on 10/01/2023.
//  Copyright © 2023 Nimble. All rights reserved.
//

import SwiftUI

// MARK: - MultipleChoicesAnswerView

struct MultipleChoicesAnswerView: View {

    let answers: [Answer]
    @State var input: Set<AnswerInput>

    var body: some View {
        VStack {
            ForEach(0 ..< answers.count, id: \.self) { index in
                let answer = answers[index]
                let currentInput = AnswerInput.select(id: answer.id)
                let isSelectingCurrentInput = input.contains(currentInput)
                Button {
                    if isSelectingCurrentInput {
                        input.remove(currentInput)
                    } else {
                        input.insert(currentInput)
                    }
                } label: {
                    HStack {
                        Text(answer.content.string)
                            .lineLimit(1)
                            .foregroundColor(isSelectingCurrentInput ? Color.white : Color.white.opacity(0.5))
                            .font(
                                isSelectingCurrentInput
                                    ? Typography.neuzeitSLTStdBookHeavy.font(size: 20.0)
                                    : Typography.neuzeitSLTStdBook.font(size: 20.0)
                            )
                        Spacer()
                        if isSelectingCurrentInput {
                            Asset.selectedIcon.image
                        } else {
                            Asset.unselectedIcon.image
                        }
                    }
                }
                .frame(height: 56.0)
                if index != answers.count - 1 {
                    Divider()
                        .frame(minHeight: 0.5)
                        .background(Color.white)
                }
            }
        }
        .padding([.leading, .trailing], 60.0)
    }
}

// MARK: - MultipleChoicesAnswerView_Previews

struct MultipleChoicesAnswerView_Previews: PreviewProvider {

    static var previews: some View {
        MultipleChoicesAnswerView(answers: [
            Answer(id: "1", content: "email"),
            Answer(id: "2", content: "password")
        ], input: .init())
    }
}
