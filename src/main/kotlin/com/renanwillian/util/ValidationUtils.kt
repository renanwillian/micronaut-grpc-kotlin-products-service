package com.renanwillian.util

import com.renanwillian.ProductServiceRequest
import com.renanwillian.ProductServiceUpdateRequest
import com.renanwillian.exceptions.InvalidArgumentException

class ValidationUtils {
    companion object {
        fun validatePayload(payload: ProductServiceRequest?): ProductServiceRequest {
            payload?.let {
                if (it.name.isNullOrBlank()) throw InvalidArgumentException("name")
                if (it.price.isNaN() || it.price < 0) throw InvalidArgumentException("price")
                return it
            }
            throw InvalidArgumentException("payload")
        }

        fun validateUpdatePayload(payload: ProductServiceUpdateRequest?): ProductServiceUpdateRequest {
            payload?.let {
                if (it.id <= 0L) throw InvalidArgumentException("id")
                if (it.name.isNullOrBlank()) throw InvalidArgumentException("name")
                if (it.price.isNaN() || it.price < 0) throw InvalidArgumentException("price")
                return it
            }
            throw InvalidArgumentException("payload")
        }
    }
}