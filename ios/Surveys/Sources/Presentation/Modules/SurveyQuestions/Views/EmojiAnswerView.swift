//
//  EmojiAnswerView.swift
//  Surveys
//
//  Created by Su Ho on 09/01/2023.
//  Copyright © 2023 Nimble. All rights reserved.
//

import SwiftUI

// MARK: - EmojiAnswerView

struct EmojiAnswerView: View {

    let emojis: [String]
    var highlightStyle: EmojiHighlightStyle = .leftItems

    @ObservedObject var viewModel: AnswerViewModel

    var currentIndex: Int {
        viewModel.answers.firstIndex(where: { $0.id == viewModel.input?.id }) ?? -1
    }

    var body: some View {
        HStack(spacing: 16.0) {
            Spacer()
            ForEach(0 ..< emojis.count, id: \.self) { index in
                Button {
                    let currentInput = AnswerInput.select(id: viewModel.answers[index].id)
                    viewModel.input = currentInput
                } label: {
                    Text(emojis[index])
                        .font(Typography.neuzeitSLTStdBookHeavy.font(size: 28.0))
                        .opacity(isHighlighted(for: index) ? 1.0 : 0.5)
                }
            }
            Spacer()
        }
    }

    func isHighlighted(for index: Int) -> Bool {
        switch highlightStyle {
        case .leftItems: return index <= currentIndex
        case .one: return index == currentIndex
        }
    }
}

// MARK: EmojiAnswerView.EmojiHighlightStyle

extension EmojiAnswerView {

    enum EmojiHighlightStyle {

        case leftItems
        case one
    }
}

// MARK: - ThumpsUpEmojiAnswerView_Previews

struct ThumpsUpEmojiAnswerView_Previews: PreviewProvider {

    static var previews: some View {
        EmojiAnswerView(
            emojis: Array(repeating: Constants.Emoji.thumpsUp, count: 5),
            highlightStyle: .leftItems,
            viewModel: .init(
                answers: [.init(id: "1"), .init(id: "2"), .init(id: "3"), .init(id: "4"), .init(id: "5")]
            )
        )
    }
}

// MARK: - StarEmojiAnswerView_Previews

struct StarEmojiAnswerView_Previews: PreviewProvider {

    static var previews: some View {
        EmojiAnswerView(
            emojis: Array(repeating: Constants.Emoji.star, count: 5),
            highlightStyle: .leftItems,
            viewModel: .init(
                answers: [.init(id: "1"), .init(id: "2"), .init(id: "3"), .init(id: "4"), .init(id: "5")]
            )
        )
    }
}

// MARK: - EmojiAnswerView_Previews

struct EmojiAnswerView_Previews: PreviewProvider {

    static var previews: some View {
        EmojiAnswerView(
            emojis: [
                Constants.Emoji.poutingFace,
                Constants.Emoji.confusedFace,
                Constants.Emoji.neutralFace,
                Constants.Emoji.slightlySmilingFace,
                Constants.Emoji.grinningFaceWithSmilingEyes
            ],
            highlightStyle: .one,
            viewModel: .init(
                answers: [.init(id: "1"), .init(id: "2"), .init(id: "3"), .init(id: "4"), .init(id: "5")]
            )
        )
    }
}
