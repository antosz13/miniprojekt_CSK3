package application;

import model.Szopar;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class SzoparTest {

    @Test
    public void testSzoparCreation() {
        Szopar szopar = new Szopar("dog", "kutya");
        assertNotNull(szopar);
        assertEquals("dog", szopar.getAngol());
        assertEquals("kutya", szopar.getMagyar());
    }

    @Test
    public void testSzoparSetAngol() {
        Szopar szopar = new Szopar("cat", "macska");
        szopar.setAngol("lion");
        assertEquals("lion", szopar.getAngol());
    }

    @Test
    public void testSzoparSetMagyar() {
        Szopar szopar = new Szopar("cat", "macska");
        szopar.setMagyar("oroszlán");
        assertEquals("oroszlán", szopar.getMagyar());
    }
}
