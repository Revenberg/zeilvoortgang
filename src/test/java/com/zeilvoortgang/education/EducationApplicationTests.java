package com.zeilvoortgang.education;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Nested;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Nested
class EducationApplicationTests {

	@Test      
 	void contextLoads() {
	}

}
