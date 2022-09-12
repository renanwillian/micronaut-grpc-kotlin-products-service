package com.renanwillian.exceptions

import io.grpc.Status

class InvalidArgumentException(private val argumentName: String) : BaseBusinessException() {
    override fun errorMessage() = "Argumento $argumentName inválido."
    override fun statusCode() = Status.Code.INVALID_ARGUMENT
}