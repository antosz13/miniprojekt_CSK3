package model;

public class Szopar {
    private String angol;
    private String magyar;

    public Szopar(String angol, String magyar) {
        this.angol = angol;
        this.magyar = magyar;
    }

    public String getAngol() {
        return angol;
    }

    public String getMagyar() {
        return magyar;
    }

    public void setAngol(String angol) {
        this.angol = angol;
    }

    public void setMagyar(String magyar) {
        this.magyar = magyar;
    }
}