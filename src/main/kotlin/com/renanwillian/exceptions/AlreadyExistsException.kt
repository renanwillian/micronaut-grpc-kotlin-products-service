package com.renanwillian.exceptions

import io.grpc.Status

class AlreadyExistsException(private val productName: String) : BaseBusinessException() {
    override fun errorMessage() = "Produto $productName já cadastrado no sistema."
    override fun statusCode() = Status.Code.ALREADY_EXISTS
}