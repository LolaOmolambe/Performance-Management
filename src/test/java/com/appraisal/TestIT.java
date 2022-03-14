package com.appraisal;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = TestContextConfiguration.class)
public class TestIT extends AppraisalPostgreSQLContainer{

    @Test
    public void test(){
    }
}
