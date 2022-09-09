package com.renanwillian.resources

import com.renanwillian.ProductsServiceReply
import com.renanwillian.ProductsServiceRequest
import com.renanwillian.ProductsServiceServiceGrpc
import io.grpc.stub.StreamObserver
import io.micronaut.grpc.annotation.GrpcService

@GrpcService
class ProductResources : ProductsServiceServiceGrpc.ProductsServiceServiceImplBase() {
    override fun send(request: ProductsServiceRequest?, responseObserver: StreamObserver<ProductsServiceReply>?) {
        val toSend = "Hello, ${request?.name}"

        val reply = ProductsServiceReply.newBuilder()
                                        .setMessage(toSend)
                                        .build()

        responseObserver?.onNext(reply)
        responseObserver?.onCompleted()
    }
}