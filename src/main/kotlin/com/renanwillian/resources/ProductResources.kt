package com.renanwillian.resources

import com.renanwillian.ProductServiceRequest
import com.renanwillian.ProductServiceResponse
import com.renanwillian.ProductsServiceGrpc
import io.grpc.stub.StreamObserver
import io.micronaut.grpc.annotation.GrpcService

@GrpcService
class ProductResources : ProductsServiceGrpc.ProductsServiceImplBase() {

    override fun create(request: ProductServiceRequest?, responseObserver: StreamObserver<ProductServiceResponse>?) {
        super.create(request, responseObserver)
    }
}