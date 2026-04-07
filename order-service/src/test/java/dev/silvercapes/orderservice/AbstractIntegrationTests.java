package dev.silvercapes.orderservice;

import com.github.tomakehurst.wiremock.client.WireMock;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.wiremock.integrations.testcontainers.WireMockContainer;

import java.math.BigDecimal;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(
        webEnvironment = RANDOM_PORT,
        properties = {
                "grpc.server.port=0"
        }
)
@Import(TestcontainersConfiguration.class)
public abstract class AbstractIntegrationTests {

    @LocalServerPort
    int port;

    // ✅ STATIC container (NOT Spring bean)
    static WireMockContainer wireMockContainer =
            new WireMockContainer("wiremock/wiremock:3.5.2-alpine");

    // ✅ Start once
    @BeforeAll
    static void startContainer() {
        wireMockContainer.start();
    }

    // ✅ Inject dynamic property into Spring
    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("product.service.url",
                () -> "http://" + wireMockContainer.getHost() + ":" + wireMockContainer.getPort());
    }

    @BeforeEach
    void setup() {
        RestAssured.port = port;

        WireMock.configureFor(
                wireMockContainer.getHost(),
                wireMockContainer.getPort()
        );
    }

    protected void mockGetProductByCode(String code, String name, BigDecimal price) {
        stubFor(get(urlEqualTo("/api/products/" + code))
                .willReturn(aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withStatus(200)
                        .withBody("""
                                {
                                    "code": "%s",
                                    "name": "%s",
                                    "price": %f
                                }
                                """.formatted(code, name, price.doubleValue()))
                )
        );
    }
}