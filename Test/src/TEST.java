import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TEST {

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	    
	
	}

	@BeforeEach
	void setUp() throws Exception {
	}

	@Test
	void testLower() {
	   LowerP lp=new LowerP();
	   lp.print(5, "A");
	}

}
