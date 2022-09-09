package com.renanwillian.services

import com.renanwillian.dto.ProductReq
import com.renanwillian.dto.ProductRes

interface ProductService {
    fun create(req: ProductReq): ProductRes
}