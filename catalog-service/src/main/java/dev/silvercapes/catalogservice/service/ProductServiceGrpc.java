package dev.silvercapes.catalogservice.service;

import catalogService.ProductRequest;
import catalogService.ProductResponse;
import dev.silvercapes.catalogservice.dto.ProductResponseDTO;
import dev.silvercapes.catalogservice.exceptions.ProductNotFoundException;
import io.github.resilience4j.timelimiter.TimeLimiter;
import io.github.resilience4j.timelimiter.TimeLimiterConfig;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.server.service.GrpcService;
import io.github.resilience4j.retry.*;

import java.time.Duration;
import java.util.concurrent.*;

@GrpcService
@RequiredArgsConstructor
public class ProductServiceGrpc extends catalogService.ProductServiceGrpc.ProductServiceImplBase {

    private final ProductService productService;

    private final TimeLimiter timeLimiter = TimeLimiter.of(
            TimeLimiterConfig.custom()
                    .timeoutDuration(Duration.ofSeconds(5))
                    .cancelRunningFuture(true)
                    .build()
    );

    private final Retry retry = Retry.of(
            "productService",
            RetryConfig.custom()
                    .maxAttempts(3)
                    .waitDuration(Duration.ofMillis(500))
                    .retryExceptions(RuntimeException.class)
                    .ignoreExceptions(ProductNotFoundException.class)
                    .build()
    );

    private final ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor();

    @Override
    public void getProductByCode(ProductRequest req, StreamObserver<ProductResponse> obs) {
        String code = req.getCode();

        CompletableFuture<ProductResponse> future = CompletableFuture.supplyAsync(
                Retry.decorateSupplier(retry, () -> {
                    ProductResponseDTO res = productService.getProductByCode(code);

                    if (res == null) {
                        throw new ProductNotFoundException("Product not found for code: " + code);
                    }

                    return ProductResponse.newBuilder()
                            .setCode(res.getCode())
                            .setName(res.getName())
                            .setImageUrl(res.getImageUrl())
                            .setDescription(res.getDescription())
                            .setPrice(res.getPrice())
                            .build();
                }),
                executor
        );

        try {
            ProductResponse response = timeLimiter.executeFutureSupplier(() -> future);
            obs.onNext(response);
            obs.onCompleted();

        } catch (TimeoutException e) {
            future.cancel(true);
            obs.onError(
                    io.grpc.Status.DEADLINE_EXCEEDED
                            .withDescription("Request timed out for product code: " + code)
                            .asRuntimeException()
            );

        } catch (ExecutionException e) {                        // ← unwrap CompletableFuture exceptions
            Throwable cause = e.getCause();
            if (cause instanceof ProductNotFoundException) {
                obs.onError(
                        io.grpc.Status.NOT_FOUND
                                .withDescription(cause.getMessage())
                                .asRuntimeException()
                );
            } else {
                obs.onError(
                        io.grpc.Status.INTERNAL
                                .withDescription("Failed to fetch product")
                                .withCause(cause)
                                .asRuntimeException()
                );
            }

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