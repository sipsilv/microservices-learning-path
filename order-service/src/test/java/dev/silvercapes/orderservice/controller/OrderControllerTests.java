package dev.silvercapes.orderservice.controller;
import dev.silvercapes.orderservice.clients.ProductDTO;

import dev.silvercapes.orderservice.AbstractIntegrationTests;
import dev.silvercapes.orderservice.clients.ProductServiceClientGrpc;
import dev.silvercapes.orderservice.properties.ApplicationProperties;
import io.restassured.http.ContentType;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.util.Optional;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.Mockito.when;

@Disabled
@Tag("order-tests")
class OrderControllerTests extends AbstractIntegrationTests {
    @Autowired
    private ApplicationProperties applicationProperties;

    @MockitoBean
    private ProductServiceClientGrpc productServiceClientGrpc;


    @Nested
    class CreateOrderTests{
        @Test
        void shouldCreateOrderSuccessfully() {

            when(productServiceClientGrpc.getProductByCode("P100"))
                    .thenReturn(Optional.of(
                            ProductDTO.builder()
                                    .code("P100")
                                    .name("Product 1")
                                    .price(34.0)
                                    .build()
                    ));
            String payload = """
                    {
                        "items": [
                          {
                            "code": "P100",
                            "name": "Wireless Mouse",
                            "price": 34,
                            "quantity": 2
                          }
                        ],
                        "customer": {
                          "name": "Santhosh Jallu",
                          "phone": "9876543210",
                          "email": "santhosh@example.com"
                        },
                        "address": {
                          "addressLine1": "123 Main Street",
                          "addressLine2": "Near Park",
                          "city": "Hyderabad",
                          "state": "Telangana",
                          "zipCode": "500001",
                          "country": "India"
                        }
                      }
                    """;

            given()
                    .contentType(ContentType.JSON)
                    .body(payload)
                    .when()
                    .post("/api/orders")
                    .then()
                    .statusCode(201)
                    .body("order_number", notNullValue())
                    .log().all();
        }
    }
}