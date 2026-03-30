package dev.silvercapes.orderservice.clients;

import catalogService.ProductRequest;
import catalogService.ProductResponse;
import catalogService.ProductServiceGrpc;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class ProductServiceClientGrpc {
    @GrpcClient("product-service")
    private ProductServiceGrpc.ProductServiceBlockingStub stub;

    public Optional<ProductDTO> getProductByCode(String code){
        try{
            ProductRequest req = ProductRequest.newBuilder()
                    .setCode(code)
                    .build();

            ProductResponse res = stub.getProductByCode(req);

            return Optional.of(
                    ProductDTO.builder()
                            .code(res.getCode())
                            .price(res.getPrice())
                            .name(res.getName())
                            .description(res.getDescription())
                            .imageUrl(res.getImageUrl())
                            .build()
            );
        } catch (io.grpc.StatusRuntimeException e){
            if (e.getStatus().getCode() == io.grpc.Status.Code.NOT_FOUND) {
                return Optional.empty();
            }

            throw e;
        }

    }
}
