package com.renanwillian.resources

import com.renanwillian.FindByIdServiceRequest
import com.renanwillian.ProductServiceRequest
import com.renanwillian.ProductsServiceGrpc
import com.renanwillian.exceptions.ProductNotFoundException
import io.grpc.Status
import io.grpc.StatusRuntimeException
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test

@MicronautTest
internal class ProductResourcesTestIT(
    private val productsServiceBlockingStub: ProductsServiceGrpc.ProductsServiceBlockingStub
) {

    @Test
    fun `when ProductsServiceGrpc create method is called with valid data a success is returned`() {
        val request = ProductServiceRequest.newBuilder()
            .setName("product name")
            .setPrice(20.99)
            .setQuantityInStock(10)
            .build()

        val response = productsServiceBlockingStub.create(request)

        assertEquals(2, response.id)
        assertEquals("product name", response.name)
        assertEquals(20.99, response.price)
        assertEquals(10, response.quantityInStock)
    }

    @Test
    fun `when ProductsServiceGrpc create method is called with invalid data a AlreadyExistsException is returned`() {
        val request = ProductServiceRequest.newBuilder()
            .setName("Product A")
            .setPrice(20.99)
            .setQuantityInStock(10)
            .build()

        val response = assertThrows(StatusRuntimeException::class.java) {
            productsServiceBlockingStub.create(request)
        }

        assertEquals(Status.ALREADY_EXISTS.code, response.status.code)
        assertEquals("Produto ${request.name} já cadastrado no sistema.", response.status.description)
    }

    @Test
    fun `when ProductsServiceGrpc findById method is called with valid id a success is returned`() {
        val request = FindByIdServiceRequest.newBuilder().setId(1).build()
        val response = productsServiceBlockingStub.findById(request)

        assertEquals(1, response.id)
        assertEquals("Product A", response.name)
        assertEquals(10.99, response.price)
        assertEquals(10, response.quantityInStock)
    }

    @Test
    fun `when ProductsServiceGrpc findById method is called with invalid id a ProductNotFoundException is returned`() {
        val request = FindByIdServiceRequest.newBuilder().setId(10).build()

        val response = assertThrows(StatusRuntimeException::class.java) {
            productsServiceBlockingStub.findById(request)
        }

        assertEquals(Status.NOT_FOUND.code, response.status.code)
        assertEquals("Produto com ID ${request.id} não encontrado.", response.status.description)
    }
}