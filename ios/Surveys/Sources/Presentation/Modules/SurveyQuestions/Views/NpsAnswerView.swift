//
//  NpsAnswerView.swift
//  Surveys
//
//  Created by Su Ho on 10/01/2023.
//  Copyright © 2023 Nimble. All rights reserved.
//

import SwiftUI

// MARK: - NpsAnswerView

struct NpsAnswerView: View {

    @ObservedObject var viewModel: AnswerViewModel

    var currentIndex: Int {
        viewModel.answers.firstIndex(where: { $0.id == viewModel.input?.id }) ?? -1
    }

    var body: some View {
        let numberOfAnswers = viewModel.answers.prefix(10).count
        VStack(spacing: 16.0) {
            HStack(spacing: 0.0) {
                ForEach(0 ..< numberOfAnswers, id: \.self) { index in
                    Button {
                        viewModel.input = AnswerInput.select(id: viewModel.answers[index].id)
                    } label: {
                        Text("\(index + 1)")
                            .font(
                                isHighlight(for: index)
                                    ? Typography.neuzeitSLTStdBookHeavy.font(size: 20.0)
                                    : Typography.neuzeitSLTStdBook.font(size: 20.0)
                            )
                            .foregroundColor(Color.white)
                    }
                    .frame(width: 34.0)
                    .opacity(isHighlight(for: index) ? 1.0 : 0.5)
                    if index != numberOfAnswers - 1 {
                        Divider()
                            .frame(minWidth: 0.5)
                            .background(Color.white)
                    }
                }
            }
            .frame(height: 56.0)
            .cornerRadius(10.0)
            .overlay(RoundedRectangle(cornerRadius: 10.0).stroke(.white, lineWidth: 1.0))

            HStack {
                let leftRangeAlpha = (currentIndex < numberOfAnswers / 2) ? 1.0 : 0.5
                let isInvalidIndex = currentIndex < 0

                Text(Localize.surveyQuestionsNpsUnlike())
                    .foregroundColor(Color.white)
                    .opacity(isInvalidIndex ? 0.5 : leftRangeAlpha)
                    .font(Typography.neuzeitSLTStdBookHeavy.font(size: 17.0))
                Spacer()
                Text(Localize.surveyQuestionsNpsLike())
                    .foregroundColor(Color.white)
                    .opacity(isInvalidIndex ? 0.5 : 1.5 - leftRangeAlpha)
                    .font(Typography.neuzeitSLTStdBookHeavy.font(size: 17.0))
            }
            .frame(width: 345.5)
        }
    }

    func isHighlight(for index: Int) -> Bool {
        index <= currentIndex
    }
}

// MARK: - NpsAnswerView_Previews

struct NpsAnswerView_Previews: PreviewProvider {

    static var previews: some View {
        NpsAnswerView(viewModel: .init(answers: (1 ... 10).map { Answer(id: "\($0)") }))
            .background(Color.black)
    }
}
