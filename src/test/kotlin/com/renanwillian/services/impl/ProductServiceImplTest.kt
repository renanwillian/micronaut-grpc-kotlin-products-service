package com.renanwillian.services.impl

import com.renanwillian.domain.Product
import com.renanwillian.dto.ProductReq
import com.renanwillian.repository.ProductRepository
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.mockito.Mockito.`when`

internal class ProductServiceImplTest {
    private val productRepository = Mockito.mock(ProductRepository::class.java)
    private val productService = ProductServiceImpl(productRepository)

    @Test
    fun `when create method is call with valid data a ProductRes is returned`() {
        val productInput = Product(id = null, name = "product name", price = 10.0, quantityInStock = 5)
        val productOutput = Product(id = 1, name = "product name", price = 10.0, quantityInStock = 5)

        `when`(productRepository.save(productInput)).thenReturn(productOutput)

        val productReq = ProductReq(name = "product name", price = 10.0, quantityInStock = 5)

        val productRes = productService.create(productReq)

        assertEquals(productOutput.id, productRes.id)
        assertEquals(productReq.name, productRes.name)
        assertEquals(productReq.price, productRes.price)
        assertEquals(productReq.quantityInStock, productRes.quantityInStock)
    }
}