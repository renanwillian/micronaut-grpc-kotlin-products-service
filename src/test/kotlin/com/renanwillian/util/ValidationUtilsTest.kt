package com.renanwillian.util

import com.renanwillian.ProductServiceRequest
import org.junit.jupiter.api.Assertions.assertDoesNotThrow
import org.junit.jupiter.api.Assertions.assertThrowsExactly
import org.junit.jupiter.api.Test

class ValidationUtilsTest {

    @Test
    fun `when validatePayload method is called with valid data, should not throw exception`() {
        val request = ProductServiceRequest.newBuilder()
            .setName("product name")
            .setPrice(20.99)
            .setQuantityInStock(10)
            .build()

        assertDoesNotThrow {
            ValidationUtils.validatePayload(request)
        }
    }

    @Test
    fun `when validatePayload method is called with invalid product name, should throw exception`() {
        val request = ProductServiceRequest.newBuilder()
            .setName("")
            .setPrice(20.99)
            .setQuantityInStock(10)
            .build()

        assertThrowsExactly(IllegalArgumentException::class.java) {
            ValidationUtils.validatePayload(request)
        }
    }

    @Test
    fun `when validatePayload method is called with invalid product price, should throw exception`() {
        val request = ProductServiceRequest.newBuilder()
            .setName("product name")
            .setPrice(-20.99)
            .setQuantityInStock(10)
            .build()

        assertThrowsExactly(IllegalArgumentException::class.java) {
            ValidationUtils.validatePayload(request)
        }
    }

    @Test
    fun `when validatePayload method is called with null payload, should throw exception`() {
        assertThrowsExactly(IllegalArgumentException::class.java) {
            ValidationUtils.validatePayload(null)
        }
    }
}