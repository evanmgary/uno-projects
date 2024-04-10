import java.io.Serializable;

/**
 * Created by emg2 on 3/12/2018.
 */
public class Team implements Serializable{

    private String name;
    private int region;
    private int seed;


    private double bpi;
    private double rpi;
    private double five38num;
    private double kenpomeff;
    private double vb;

    public Team(String name, int region, int seed, double five38num) {
        this.name = name;
        this.region = region;
        this.seed = seed;
        this.five38num = five38num;
    }

    public String getName() {
        return name;
    }

    public void setBpi(double bpi) {
        this.bpi = bpi;
    }

    public void setRpi(double rpi) {
        this.rpi = rpi;
    }

    public void setKenpomeff(double kenpomeff) {
        this.kenpomeff = kenpomeff;
    }

    public double getVb() {
        return vb;
    }

    public void setVb(double vb) {
        this.vb = vb;
    }
    public int getRegion() {
        return region;
    }

    public int getSeed() {
        return seed;
    }

    public double getBpi() {
        return bpi;
    }

    public double getRpi() {
        return rpi;
    }

    public double getFive38num() {
        return five38num;
    }

    public double getKenpomeff() {
        return kenpomeff;
    }
}
