package com.renanwillian.exceptions

import io.grpc.Status

class ProductNotFoundException(private val productId: Long) : BaseBusinessException() {
    override fun errorMessage() = "Produto com ID $productId não encontrado."
    override fun statusCode() = Status.Code.NOT_FOUND
}