package com.renanwillian.services.impl

import com.renanwillian.domain.Product
import com.renanwillian.dto.ProductReq
import com.renanwillian.dto.ProductUpdateReq
import com.renanwillian.exceptions.AlreadyExistsException
import com.renanwillian.exceptions.ProductNotFoundException
import com.renanwillian.repository.ProductRepository
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
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
        assertThrowsExactly(ProductNotFoundException::class.java) { productService.findById(productId) }
    }


    @Test
    fun `when update method is called with valid data a ProductRes is returned`() {
        val oldProduct = Product(id = 1, name = "product name", price = 10.0, quantityInStock = 5)
        val newProduct = Product(id = 1, name = "updated name", price = 10.0, quantityInStock = 5)

        `when`(productRepository.findById(1)).thenReturn(Optional.of(oldProduct))
        `when`(productRepository.update(newProduct)).thenReturn(newProduct)

        val productReq = ProductUpdateReq(id = 1, name = "updated name", price = 10.0, quantityInStock = 5)

        val productRes = productService.update(productReq)

        assertEquals(oldProduct.id, productRes.id)
        assertEquals(productReq.name, productRes.name)
        assertEquals(productReq.price, productRes.price)
        assertEquals(productReq.quantityInStock, productRes.quantityInStock)
    }

    @Test
    fun `when update method is called with duplicated product-name, throws AlreadyExistsException`() {
        val productInput = Product(id = 1, name = "product name", price = 10.0, quantityInStock = 5)
        val productOutput = Product(id = 1, name = "product name", price = 10.0, quantityInStock = 5)

        `when`(productRepository.findByNameIgnoreCase(productInput.name)).thenReturn(productOutput)

        val productReq = ProductUpdateReq(id = 1, name = "product name", price = 10.0, quantityInStock = 5)

        assertThrowsExactly(AlreadyExistsException::class.java) { productService.update(productReq) }
    }

    @Test
    fun `when update method is called with invalid id, throws ProductNotFoundException`() {
        val productReq = ProductUpdateReq(id = 1L, name = "product name", price = 10.0, quantityInStock = 5)
        assertThrowsExactly(ProductNotFoundException::class.java) { productService.update(productReq) }
    }

    @Test
    fun `when delete method is called with valid id the product is deleted`() {
        val productOutput = Product(id = 1, name = "product name", price = 10.0, quantityInStock = 5)

        `when`(productRepository.findById(1)).thenReturn(Optional.of(productOutput))

        assertDoesNotThrow { productService.delete(1) }
    }

    @Test
    fun `when delete method is called with invalid id, throws ProductNotFoundException`() {
        `when`(productRepository.findById(1)).thenReturn(Optional.empty())
        assertThrowsExactly(ProductNotFoundException::class.java) { productService.delete(1) }
    }

    @Test
    fun `when findAll method is called a list of ProductRes is returned`() {
        val products = listOf(
            Product(id = 1, name = "product 1", price = 10.0, quantityInStock = 5)
        )

        `when`(productRepository.findAll()).thenReturn(products)

        val productRes = productService.findAll()

        assertEquals(products.size, productRes.size)
        assertEquals(products[0].id, productRes[0].id)
        assertEquals(products[0].name, productRes[0].name)
        assertEquals(products[0].price, productRes[0].price)
        assertEquals(products[0].quantityInStock, productRes[0].quantityInStock)
    }

    @Test
    fun `when findAll method is called without products a empty list of ProductRes is returned`() {
        `when`(productRepository.findAll()).thenReturn(emptyList<Product>())

        val productRes = productService.findAll()

        assertTrue(productRes.isEmpty())
    }
}