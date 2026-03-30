package dev.silvercapes.orderservice.controller;

import dev.silvercapes.orderservice.dto.CreateOrderRequestDTO;
import dev.silvercapes.orderservice.security.SecurityService;
import dev.silvercapes.orderservice.service.OrderService;
import io.restassured.specification.Argument;
import org.junit.jupiter.api.BeforeEach;
import static org.mockito.ArgumentMatchers.eq;
import org.junit.jupiter.params.provider.Arguments;
import org.springframework.http.MediaType;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.stream.Stream;

import static dev.silvercapes.orderservice.testdata.TestDataFactory.*;
import static org.junit.jupiter.params.provider.Arguments.arguments;
import static org.mockito.ArgumentMatchers.any;

import static org.mockito.BDDMockito.given;
import static org.junit.jupiter.api.Named.named;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(OrderController.class)
public class OrderControllerUnitTests {
    @MockitoBean
    private OrderService orderService;

    @MockitoBean
    private SecurityService securityService;

    @Autowired
    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setup(){
        given(securityService.getLoginUsername()).willReturn("randomUser");
    }

    @ParameterizedTest(name = "[{index}]-{0}")
    @MethodSource("createOrderRequestProvider")
    void shouldReturnBadRequestWhenOrderPayloadIsInvalid(CreateOrderRequestDTO request) throws Exception {
        given(orderService.createOrder(any(CreateOrderRequestDTO.class)))
                .willReturn(null);

        mockMvc.perform(post("/api/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    static Stream<Arguments> createOrderRequestProvider() {
        return Stream.of(
                arguments(named("Order with Invalid Customer", createOrderRequestWithInvalidCustomer())),
                arguments(named("Order with Invalid Delivery Address", createOrderRequestWithInvalidDeliveryAddress())),
                arguments(named("Order with No Items", createOrderRequestWithNoItems())));
    }


}
