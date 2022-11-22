//
//  Optional+Unwrap.swift
//

import Foundation

extension Optional where Wrapped == String {

    var string: String { self ?? "" }
}

extension Optional {

    func `let`(_ unwrapped: (Wrapped) -> Void) {
        if let self {
            unwrapped(self)
        }
    }
}
