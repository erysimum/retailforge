package com.amitshahi.retailforge.order;

import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.options;

import com.github.tomakehurst.wiremock.WireMockServer;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Import(TestcontainersConfiguration.class)
public abstract class AbstractIntegrationTest {
    public static final WireMockServer wireMock = new WireMockServer(options().dynamicPort());

    static {
        wireMock.start();
        Runtime.getRuntime().addShutdownHook(new Thread(wireMock::stop));
    }

    @DynamicPropertySource
    static void overrideCatalogServiceUrl(DynamicPropertyRegistry registry) {
        registry.add("catalog.service.url", wireMock::baseUrl);
    }

    @LocalServerPort
    int port;

    @BeforeEach
    void setUpRestAssured() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = port;
    }
}
