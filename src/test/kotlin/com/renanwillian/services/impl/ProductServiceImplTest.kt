package com.renanwillian.services.impl

import com.renanwillian.domain.Product
import com.renanwillian.dto.ProductReq
import com.renanwillian.exceptions.AlreadyExistsException
import com.renanwillian.exceptions.ProductNotFoundException
import com.renanwillian.repository.ProductRepository
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrowsExactly
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import java.util.*

internal class ProductServiceImplTest {
    private val productRepository = Mockito.mock(ProductRepository::class.java)
    private val productService = ProductServiceImpl(productRepository)

    @Test
    fun `when create method is called with valid data a ProductRes is returned`() {
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

    @Test
    fun `when create method is called with duplicated product-name, throws AlreadyExistsException`() {
        val productInput = Product(id = null, name = "product name", price = 10.0, quantityInStock = 5)
        val productOutput = Product(id = 1, name = "product name", price = 10.0, quantityInStock = 5)

        `when`(productRepository.findByNameIgnoreCase(productInput.name)).thenReturn(productOutput)

        val productReq = ProductReq(name = "product name", price = 10.0, quantityInStock = 5)

        assertThrowsExactly(AlreadyExistsException::class.java) {
            productService.create(productReq)
        }
    }

    @Test
    fun `when findById method is called with valid id a ProductRes is returned`() {
        val productId = 1L
        val productOutput = Product(id = 1, name = "product name", price = 10.0, quantityInStock = 5)

        `when`(productRepository.findById(productId)).thenReturn(Optional.of(productOutput))

        val productRes = productService.findById(productId)

        assertEquals(productOutput.id, productRes.id)
        assertEquals(productOutput.name, productRes.name)
        assertEquals(productOutput.price, productRes.price)
        assertEquals(productOutput.quantityInStock, productRes.quantityInStock)
    }

    @Test
    fun `when findById method is called with invalid id, throws ProductNotFoundException`() {
        val productId = 1L

        `when`(productRepository.findById(productId)).thenReturn(Optional.empty())

        assertThrowsExactly(ProductNotFoundException::class.java) {
            productService.findById(productId)
        }
    }
}