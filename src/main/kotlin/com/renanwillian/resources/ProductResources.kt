package com.renanwillian.resources

import com.renanwillian.ProductServiceRequest
import com.renanwillian.ProductServiceResponse
import com.renanwillian.ProductsServiceGrpc
import com.renanwillian.dto.ProductReq
import com.renanwillian.services.ProductService
import com.renanwillian.util.ValidationUtils
import io.grpc.stub.StreamObserver
import io.micronaut.grpc.annotation.GrpcService

@GrpcService
class ProductResources(private val productService: ProductService) : ProductsServiceGrpc.ProductsServiceImplBase() {

    override fun create(request: ProductServiceRequest?, responseObserver: StreamObserver<ProductServiceResponse>?) {
        val payload = ValidationUtils.validatePayload(request)
        val productReq = ProductReq(name = payload.name, price = payload.price, quantityInStock = payload.quantityInStock)
        val productRes = productService.create(productReq)

        val productResponse = ProductServiceResponse.newBuilder()
            .setId(productRes.id)
            .setName(productRes.name)
            .setPrice(productRes.price)
            .setQuantityInStock(productRes.quantityInStock)
            .build()

        responseObserver?.onNext(productResponse)
        responseObserver?.onCompleted()
    }
}