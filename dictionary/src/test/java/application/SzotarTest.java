package application;

import model.Szotar;
import model.Szopar;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

public class SzotarTest {

    @Test
    public void testAddSzopar() {
        Szotar szotar = new Szotar();
        Szopar szopar = new Szopar("apple", "alma");
        szotar.addSzopar(szopar);

        assertEquals(1, szotar.getSzoparok().size());
        assertEquals("apple", szotar.getSzoparok().get(0).getAngol());
        assertEquals("alma", szotar.getSzoparok().get(0).getMagyar());
    }

    @Test
    public void testRemoveSzopar() {
        Szotar szotar = new Szotar();
        Szopar szopar = new Szopar("dog", "kutya");
        szotar.addSzopar(szopar);

        szotar.getSzoparok().remove(szopar);
        assertTrue(szotar.getSzoparok().isEmpty());
    }

    @Test
    public void testGetSzoparok() {
        Szotar szotar = new Szotar();
        Szopar szopar1 = new Szopar("dog", "kutya");
        Szopar szopar2 = new Szopar("cat", "macska");

        szotar.addSzopar(szopar1);
        szotar.addSzopar(szopar2);

        List<Szopar> szoparok = szotar.getSzoparok();
        assertEquals(2, szoparok.size());
    }

    @Test
    public void testClearSzotar() {
        Szotar szotar = new Szotar();
        Szopar szopar1 = new Szopar("apple", "alma");
        Szopar szopar2 = new Szopar("tree", "fa");

        szotar.addSzopar(szopar1);
        szotar.addSzopar(szopar2);

        szotar.getSzoparok().clear();
        assertTrue(szotar.getSzoparok().isEmpty());
    }
}
