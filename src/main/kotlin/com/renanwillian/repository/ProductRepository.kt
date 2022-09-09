package com.renanwillian.repository

import com.renanwillian.domain.Product
import io.micronaut.data.jpa.repository.JpaRepository
import jakarta.inject.Singleton

@Singleton
interface ProductRepository : JpaRepository<Product, Long>