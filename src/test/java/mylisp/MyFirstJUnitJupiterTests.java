package mylisp;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class MyFirstJUnitJupiterTests {

    @Test
    void sapmleOk() {
        assertEquals(2, 2);
    }

    @Test
    void sapmleFail() {
        assertFalse( 2 == 3);
    }


}
