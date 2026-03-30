package dev.silvercapes.catalogservice.service;

import catalogService.ProductRequest;

import catalogService.ProductResponse;

import dev.silvercapes.catalogservice.dto.ProductResponseDTO;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.server.service.GrpcService;

@GrpcService
@RequiredArgsConstructor
public class ProductServiceGrpc extends catalogService.ProductServiceGrpc.ProductServiceImplBase {
    private final ProductService productService;
    @Override
    public void getProductByCode(ProductRequest req, StreamObserver<ProductResponse> obs){
        try {
            String code = req.getCode();
            ProductResponseDTO res = productService.getProductByCode(code);
            if (res == null) {
                obs.onError(
                        io.grpc.Status.NOT_FOUND
                                .withDescription("Product not found for code: " + code)
                                .asRuntimeException()
                );
                return;
            }
            ProductResponse response = ProductResponse.newBuilder()
                    .setCode(res.getCode())
                    .setName(res.getName())
                    .setImageUrl(res.getImageUrl())
                    .setDescription(res.getDescription())
                    .setPrice(res.getPrice())
                    .build();

            obs.onNext(response);
            obs.onCompleted();

        } catch (Exception e) {
            obs.onError(
                    io.grpc.Status.INTERNAL
                            .withDescription("Failed to fetch product")
                            .withCause(e)
                            .asRuntimeException()
            );
        }
    }
}


