package io.samjingwen.example;

import io.samjingwen.HttpStatus;
import org.junit.jupiter.api.Test;

class DemoApplicationTest {

	@Test
	void testStuff(){
		assert HttpStatus.BAD_REQUEST == 400;
		assert HttpStatus.UNAUTHORIZED == 401;
		assert HttpStatus.INTERNAL_SERVER_ERROR == 500;
	}

}
