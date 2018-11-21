package utn.frc.sim.model;

public class EulerRow {

    private final String time;
    private final String Z;
    private final String Z1;
    private final String Yn1;
    private final String Z3;
    private final String fxyn1;

    public EulerRow(String time, String z, String z1, String yn1, String z3, String fxyn1) {
        this.time = time;
        Z = z;
        Z1 = z1;
        Yn1 = yn1;
        Z3 = z3;
        this.fxyn1 = fxyn1;
    }

    public String getTime() {
        return time;
    }

    public String getZ() {
        return Z;
    }

    public String getZ1() {
        return Z1;
    }

    public String getYn1() {
        return Yn1;
    }

    public String getZ3() {
        return Z3;
    }

    public String getFxyn1() {
        return fxyn1;
    }
}
