package com.renanwillian.services

import com.renanwillian.dto.ProductReq
import com.renanwillian.dto.ProductRes
import com.renanwillian.dto.ProductUpdateReq

interface ProductService {
    fun create(req: ProductReq): ProductRes
    fun findById(id: Long): ProductRes
    fun update(req: ProductUpdateReq): ProductRes
    fun delete(id: Long)
}