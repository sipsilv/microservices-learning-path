package dev.silvercapes.orderservice.validators;

import dev.silvercapes.orderservice.clients.ProductDTO;

import dev.silvercapes.orderservice.clients.ProductServiceClientGrpc;
import dev.silvercapes.orderservice.dto.CreateOrderRequestDTO;
import dev.silvercapes.orderservice.dto.OrderItemDTO;
import dev.silvercapes.orderservice.exceptions.InvalidOrderException;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class OrderValidator {
    private static final Logger log = LoggerFactory.getLogger(OrderValidator.class);
    private final ProductServiceClientGrpc productServiceClientGrpc;

    void validate(CreateOrderRequestDTO req){
        Set<OrderItemDTO> items = req.items();
        for(OrderItemDTO item : items){
            ProductDTO product = productServiceClientGrpc.getProductByCode(item.code())
                    .orElseThrow(() -> new InvalidOrderException("Invalid order code: " + item.code()));
            if(item.price().compareTo(BigDecimal.valueOf(product.getPrice())) != 0){
                log.error(
                        "Product price is not matching. Actual price: {}, received price: {}"
                        , product.getPrice(), item.price()
                );
                throw new InvalidOrderException("Product price is not matching");
            }
        }
    }
}
