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

// MARK: - TextEditor

private struct TextEditor: View {

    let placeholder: String?

    @Binding var text: String

    var body: some View {
        ZStack(alignment: .topLeading) {
            if text.isEmpty {
                Text(placeholder ?? "")
                    .foregroundColor(Color.primary.opacity(0.25))
                    .padding()
                    .padding(.leading, 2.0) // Extra padding to matching the cursor and the label
                    .padding(.top, 4.0)
            }
            if #available(iOS 16.0, *) {
                SwiftUI.TextEditor(text: $text)
                    .scrollContentBackground(.hidden)
                    .primaryFieldStyle()
            } else {
                SwiftUI.TextEditor(text: $text)
                    .primaryFieldStyle()
            }
        }
        .onAppear {
            UITextView.appearance().backgroundColor = .clear
        }
        .onDisappear {
            UITextView.appearance().backgroundColor = nil
        }
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
