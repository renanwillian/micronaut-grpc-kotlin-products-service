package com.renanwillian.services.impl

import com.renanwillian.dto.ProductReq
import com.renanwillian.dto.ProductRes
import com.renanwillian.repository.ProductRepository
import com.renanwillian.services.ProductService
import com.renanwillian.util.toDomain
import com.renanwillian.util.toProductRes
import jakarta.inject.Singleton

@Singleton
class ProductServiceImpl(private val productRepository: ProductRepository) : ProductService {

    override fun create(req: ProductReq): ProductRes {
        val product = productRepository.save(req.toDomain())
        return product.toProductRes()
    }
}