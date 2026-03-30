package dev.silvercapes.orderservice.controller;
import dev.silvercapes.orderservice.AbstractIntegrationTests;
import dev.silvercapes.orderservice.properties.ApplicationProperties;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.notNullValue;

class OrderControllerTests extends AbstractIntegrationTests {
    @Autowired
    private ApplicationProperties applicationProperties;

    @Nested
    class CreateOrderTests{
        @Test
        void shouldCreateOrderSuccessfully(){
            String payload = """
                    {
                      "items": [
                        {
                          "code": "P1001",
                          "name": "Wireless Mouse",
                          "price": 499.0,
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
                    .body("order_number", notNullValue());
        }


    }

}