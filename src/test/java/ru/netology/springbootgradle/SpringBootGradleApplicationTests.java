package ru.netology.springbootgradle;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.testcontainers.containers.GenericContainer;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class SpringBootGradleApplicationTests {
    @Autowired
    private TestRestTemplate restTemplate;

    private static GenericContainer<?> myapp = new GenericContainer<>("devapp:latest")
            .withExposedPorts(8080);

    private static GenericContainer<?> myapp2 = new GenericContainer<>("prodapp:latest")
            .withExposedPorts(8081);

    @BeforeAll
    public static void setUp() {
        myapp.start();
        myapp2.start();
    }

    @Test
    void contextLoads() {
        ResponseEntity<String> forEntity1 = restTemplate.getForEntity("http://localhost:" + myapp.getMappedPort(8080), String.class);
        System.out.println(forEntity1.getBody());
        assertEquals("Current profile is dev", forEntity1.getBody());

        ResponseEntity<String> forEntity2 = restTemplate.getForEntity("http://localhost:" + myapp2.getMappedPort(8081), String.class);
        System.out.println(forEntity2.getBody());
        assertEquals("Current profile is production", forEntity2.getBody());
    }
}
