package model;

import java.util.ArrayList;
import java.util.List;

public class Szotar {
    private List<Szopar> szoparok;

    public Szotar() {
        this.szoparok = new ArrayList<>();
    }

    public void addSzopar(Szopar szopar) {
        szoparok.add(szopar);
    }

    public List<Szopar> getSzoparok() {
        return szoparok;
    }
}