package com.renanwillian.dto

data class ProductUpdateReq(
    val id: Long,
    val name: String,
    val price: Double,
    val quantityInStock: Int
)
