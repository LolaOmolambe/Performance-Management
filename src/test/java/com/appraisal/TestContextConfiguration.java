package com.appraisal;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.test.context.TestPropertySource;

@SpringBootApplication
@TestPropertySource(value = {
        "classpath:application-test.properties"
})
public class TestContextConfiguration {
}
