package ru.a5x5retail.frontproductmanagement;

public class Version {

    public Version() {
        realise = -1;
        build = -1;
    }

    public Version(int realise, int build) {
        this.realise = realise;
        this.build = build;
    }

    private int realise;

    public int getRealise() {
        return realise;
    }

    public void setRealise(int realise) {
        this.realise = realise;
    }

    public int getBuild() {
        return build;
    }

    public void setBuild(int build) {
        this.build = build;
    }

    private int build;

    @Override
    public String toString() {
        return String.format("%d.%d",realise,build);
    }
}
