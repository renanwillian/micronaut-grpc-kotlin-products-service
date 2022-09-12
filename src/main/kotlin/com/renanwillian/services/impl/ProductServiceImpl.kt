package com.renanwillian.services.impl

import com.renanwillian.dto.ProductReq
import com.renanwillian.dto.ProductRes
import com.renanwillian.exceptions.AlreadyExistsException
import com.renanwillian.exceptions.ProductNotFoundException
import com.renanwillian.repository.ProductRepository
import com.renanwillian.services.ProductService
import com.renanwillian.util.toDomain
import com.renanwillian.util.toProductRes
import jakarta.inject.Singleton

@Singleton
class ProductServiceImpl(private val productRepository: ProductRepository) : ProductService {

    override fun create(req: ProductReq): ProductRes {
        verifyName(req.name)
        val product = productRepository.save(req.toDomain())
        return product.toProductRes()
    }

    override fun findById(id: Long): ProductRes {
        val product = productRepository.findById(id)
            .orElseThrow { ProductNotFoundException(id) }
        return product.toProductRes()
    }

    private fun verifyName(name: String) {
        productRepository.findByNameIgnoreCase(name)?.let {
            throw AlreadyExistsException(name)
        }
    }
}