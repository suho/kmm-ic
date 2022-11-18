//
//  KFImage+Image.swift
//  Surveys
//
//  Created by Su Ho on 18/11/2022.
//  Copyright © 2022 Nimble. All rights reserved.
//

import Kingfisher

typealias URLImage = KFImage

extension KFImageProtocol {

    public static func urlString(_ string: String) -> Self {
        Self(URL(string: string))
    }
}
