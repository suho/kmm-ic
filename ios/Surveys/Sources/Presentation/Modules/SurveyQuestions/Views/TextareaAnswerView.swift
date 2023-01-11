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

    let answer: Answer

    @State var input: AnswerInput?

    var content: Binding<String> {
        Binding<String>(
            get: { input?.content ?? "" },
            set: { input = AnswerInput.content(id: input?.id ?? "", value: $0) }
        )
    }

    var body: some View {
        TextEditor(placeholder: answer.placeholder, text: content)
    }
}

// MARK: - TextareaAnswerView_Previews

struct TextareaAnswerView_Previews: PreviewProvider {

    static var previews: some View {
        TextareaAnswerView(
            answer: Answer(id: "1", content: "Email", placeholder: "Email"),
            input: nil
        )
        .frame(width: 327.0, height: 168.0)
    }
}
