package application;

import model.Szotar;
import model.Szopar;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class IntegrationTest {

    private SzotarController controller;
    private Szotar szotar;

    @BeforeEach
    public void setUp() {
        controller = new SzotarController(new Szotar());
        szotar = controller.getSzotar();
    }

    @Test
    public void testAddAndRetrieveSzopar() {
        Szopar szopar = new Szopar("banana", "banán");
        controller.addWordPair("banana", "banán");

        Szopar retrievedSzopar = szotar.getSzoparok().get(0);
        assertEquals("banana", retrievedSzopar.getAngol());
        assertEquals("banán", retrievedSzopar.getMagyar());
    }

    @Test
    public void testCheckAnswerForMultiplePairs() {
        controller.addWordPair("car", "autó");
        controller.addWordPair("plane", "repülő");

        Szopar firstPair = szotar.getSzoparok().get(0);
        Szopar secondPair = szotar.getSzoparok().get(1);

        assertTrue(controller.checkAnswer(firstPair, "autó"));
        assertTrue(controller.checkAnswer(secondPair, "repülő"));
    }

    @Test
    public void testCheckAnswerWithIncorrectInput() {
        controller.addWordPair("tree", "fa");

        Szopar szopar = szotar.getSzoparok().get(0);
        assertFalse(controller.checkAnswer(szopar, "virág"));
    }
}
