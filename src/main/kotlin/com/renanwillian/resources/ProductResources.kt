package com.renanwillian.resources

import com.renanwillian.*
import com.renanwillian.dto.ProductReq
import com.renanwillian.dto.ProductUpdateReq
import com.renanwillian.exceptions.BaseBusinessException
import com.renanwillian.services.ProductService
import com.renanwillian.util.ValidationUtils
import io.grpc.Status
import io.grpc.stub.StreamObserver
import io.micronaut.grpc.annotation.GrpcService

@GrpcService
class ProductResources(private val productService: ProductService) : ProductsServiceGrpc.ProductsServiceImplBase() {

    override fun create(request: ProductServiceRequest?, responseObserver: StreamObserver<ProductServiceResponse>?) {
        try {
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
        } catch (ex: BaseBusinessException) {
            responseObserver?.onError(ex.statusCode().toStatus()
                .withDescription(ex.errorMessage()).asRuntimeException())
        } catch (ex: Throwable) {
            responseObserver?.onError(Status.UNKNOWN.code.toStatus()
                .withDescription("Internal Server Error").asException())
        }
    }

    override fun findById(request: RequestById?, responseObserver: StreamObserver<ProductServiceResponse>?) {
        try {
            val productRes = productService.findById(request!!.id)
            val productResponse = ProductServiceResponse.newBuilder()
                .setId(productRes.id)
                .setName(productRes.name)
                .setPrice(productRes.price)
                .setQuantityInStock(productRes.quantityInStock)
                .build()

            responseObserver?.onNext(productResponse)
            responseObserver?.onCompleted()
        } catch (ex: BaseBusinessException) {
            responseObserver?.onError(
                ex.statusCode().toStatus()
                    .withDescription(ex.errorMessage()).asRuntimeException()
            )
        } catch (ex: Throwable) {
            responseObserver?.onError(Status.UNKNOWN.code.toStatus()
                .withDescription("Internal Server Error").asException())
        }
    }

    override fun update(request: ProductServiceUpdateRequest?, responseObserver: StreamObserver<ProductServiceResponse>?) {
        try {
            val payload = ValidationUtils.validateUpdatePayload(request)
            val productReq = ProductUpdateReq(
                id = payload.id,
                name = payload.name,
                price = payload.price,
                quantityInStock = payload.quantityInStock
            )
            val productRes = productService.update(productReq)

            val productResponse = ProductServiceResponse.newBuilder()
                .setId(productRes.id)
                .setName(productRes.name)
                .setPrice(productRes.price)
                .setQuantityInStock(productRes.quantityInStock)
                .build()

            responseObserver?.onNext(productResponse)
            responseObserver?.onCompleted()
        } catch (ex: BaseBusinessException) {
            responseObserver?.onError(ex.statusCode().toStatus()
                .withDescription(ex.errorMessage()).asRuntimeException())
        } catch (ex: Throwable) {
            responseObserver?.onError(Status.UNKNOWN.code.toStatus()
                .withDescription("Internal Server Error").asException())
        }
    }

    override fun delete(request: RequestById?, responseObserver: StreamObserver<Empty>?) {
        try {
            productService.delete(request!!.id)

            responseObserver?.onNext(Empty.newBuilder().build())
            responseObserver?.onCompleted()
        } catch (ex: BaseBusinessException) {
            responseObserver?.onError(
                ex.statusCode().toStatus()
                    .withDescription(ex.errorMessage()).asRuntimeException()
            )
        } catch (ex: Throwable) {
            responseObserver?.onError(Status.UNKNOWN.code.toStatus()
                .withDescription("Internal Server Error").asException())
        }
    }

    override fun findAll(request: Empty?, responseObserver: StreamObserver<ProductList>?) {
        try {
            val productResList = productService.findAll()

            val productServiceResponseList = productResList.map {
                ProductServiceResponse.newBuilder()
                    .setId(it.id)
                    .setName(it.name)
                    .setPrice(it.price)
                    .setQuantityInStock(it.quantityInStock)
                    .build()
            }

            val response = ProductList.newBuilder().addAllProducts(productServiceResponseList).build()

            responseObserver?.onNext(response)
            responseObserver?.onCompleted()
        } catch (ex: Throwable) {
            responseObserver?.onError(Status.UNKNOWN.code.toStatus()
                .withDescription("Internal Server Error").asException())
        }
    }
}