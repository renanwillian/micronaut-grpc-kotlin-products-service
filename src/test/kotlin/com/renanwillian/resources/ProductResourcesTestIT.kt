package com.renanwillian.resources

import com.renanwillian.*
import io.grpc.Status
import io.grpc.StatusRuntimeException
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import org.flywaydb.core.Flyway
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow

@MicronautTest
internal class ProductResourcesTestIT(
    private val flyway: Flyway,
    private val productsServiceBlockingStub: ProductsServiceGrpc.ProductsServiceBlockingStub
) {

    @BeforeEach
    fun setUp() {
        flyway.clean()
        flyway.migrate()
    }

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
        val request = RequestById.newBuilder().setId(1).build()
        val response = productsServiceBlockingStub.findById(request)

        assertEquals(1, response.id)
        assertEquals("Product A", response.name)
        assertEquals(10.99, response.price)
        assertEquals(10, response.quantityInStock)
    }

    @Test
    fun `when ProductsServiceGrpc findById method is called with invalid id a ProductNotFoundException is returned`() {
        val request = RequestById.newBuilder().setId(10).build()

        val response = assertThrows(StatusRuntimeException::class.java) {
            productsServiceBlockingStub.findById(request)
        }

        assertEquals(Status.NOT_FOUND.code, response.status.code)
        assertEquals("Produto com ID ${request.id} não encontrado.", response.status.description)
    }

    @Test
    fun `when ProductsServiceGrpc update method is called with valid data a success is returned`() {
        val request = ProductServiceUpdateRequest.newBuilder()
            .setId(1L)
            .setName("product name")
            .setPrice(20.99)
            .setQuantityInStock(10)
            .build()

        val response = productsServiceBlockingStub.update(request)

        assertEquals(1, response.id)
        assertEquals("product name", response.name)
        assertEquals(20.99, response.price)
        assertEquals(10, response.quantityInStock)
    }

    @Test
    fun `when ProductsServiceGrpc update method is called with invalid data a AlreadyExistsException is returned`() {
        val request = ProductServiceUpdateRequest.newBuilder()
            .setId(1L)
            .setName("Product A")
            .setPrice(20.99)
            .setQuantityInStock(10)
            .build()

        val response = assertThrows(StatusRuntimeException::class.java) {
            productsServiceBlockingStub.update(request)
        }

        assertEquals(Status.ALREADY_EXISTS.code, response.status.code)
        assertEquals("Produto ${request.name} já cadastrado no sistema.", response.status.description)
    }

    @Test
    fun `when ProductsServiceGrpc update method is called with invalid id a ProductNotFoundException is returned`() {
        val request = ProductServiceUpdateRequest.newBuilder()
            .setId(10L)
            .setName("Product B")
            .setPrice(20.99)
            .setQuantityInStock(10)
            .build()

        val response = assertThrows(StatusRuntimeException::class.java) {
            productsServiceBlockingStub.update(request)
        }

        assertEquals(Status.NOT_FOUND.code, response.status.code)
        assertEquals("Produto com ID ${request.id} não encontrado.", response.status.description)
    }

    @Test
    fun `when ProductsServiceGrpc delete method is called with valid id a success is returned`() {
        val request = RequestById.newBuilder().setId(1).build()

        assertDoesNotThrow {
            productsServiceBlockingStub.delete(request)
        }
    }

    @Test
    fun `when ProductsServiceGrpc delete method is called with invalid id a ProductNotFoundException is returned`() {
        val request = RequestById.newBuilder().setId(10).build()

        val response = assertThrows(StatusRuntimeException::class.java) {
            productsServiceBlockingStub.delete(request)
        }

        assertEquals(Status.NOT_FOUND.code, response.status.code)
        assertEquals("Produto com ID ${request.id} não encontrado.", response.status.description)
    }

    @Test
    fun `when ProductsServiceGrpc findAll method is called a list of ProductServiceResponse is returned`() {
        val request = Empty.newBuilder().build()

        val response = productsServiceBlockingStub.findAll(request)

        assertEquals(1, response.getProducts(0).id)
        assertEquals("Product A", response.getProducts(0).name)
        assertEquals(10.99, response.getProducts(0).price)
        assertEquals(10, response.getProducts(0).quantityInStock)
    }
}