package application;

import model.Szotar;
import model.Szopar;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class SzotarControllerTest {

    private SzotarController controller;

    @BeforeEach
    public void setUp() {
        controller = new SzotarController(new Szotar());
    }

    @Test
    public void testAddSzoparToController() {
        Szopar szopar = new Szopar("apple", "alma");
        controller.addWordPair("apple", "alma");

        assertEquals(1, controller.getSzotar().getSzoparok().size());
        assertEquals("apple", controller.getSzotar().getSzoparok().get(0).getAngol());
        assertEquals("alma", controller.getSzotar().getSzoparok().get(0).getMagyar());
    }

    @Test
    public void testCheckCorrectAnswer() {
        Szopar szopar = new Szopar("dog", "kutya");
        controller.addWordPair("dog", "kutya");

        boolean result = controller.checkAnswer(szopar, "kutya");
        assertTrue(result);
    }

    @Test
    public void testCheckIncorrectAnswer() {
        Szopar szopar = new Szopar("dog", "kutya");
        controller.addWordPair("dog", "kutya");

        boolean result = controller.checkAnswer(szopar, "macska");
        assertFalse(result);
    }
}
