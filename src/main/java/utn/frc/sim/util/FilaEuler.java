package utn.frc.sim.util;


import javafx.beans.property.SimpleStringProperty;

public class FilaEuler {

    private final SimpleStringProperty T;
    private final SimpleStringProperty Z;
    private final SimpleStringProperty Z1;
    private final SimpleStringProperty Yn1;
    private final SimpleStringProperty Z3;
    private final SimpleStringProperty Fxyn1;

    public FilaEuler(String T, String Z, String Z1, String Yn1, String Z3, String Fxyn1) {
        this.T =  new SimpleStringProperty(T);
        this.Z =  new SimpleStringProperty(Z);
        this.Z1 =  new SimpleStringProperty(Z1);
        this.Yn1 =  new SimpleStringProperty(Yn1);
        this.Z3 =  new SimpleStringProperty(Z3);
        this.Fxyn1 =  new SimpleStringProperty(Fxyn1);
    }

    public void setT(String t) {
        this.T.set(t);
    }

    public void setZ(String z) {
        this.Z.set(z);
    }

    public void setZ1(String z1) {
        this.Z1.set(z1);
    }

    public void setYn1(String yn1) {
        this.Yn1.set(yn1);
    }

    public void setZ3(String z3) {
        this.Z3.set(z3);
    }

    public void setFxyn1(String fxyn1) {
        this.Fxyn1.set(fxyn1);
    }

    public String getT() {
        return T.get();
    }

    public SimpleStringProperty tProperty() {
        return T;
    }

    public String getZ() {
        return Z.get();
    }

    public SimpleStringProperty zProperty() {
        return Z;
    }

    public String getZ1() {
        return Z1.get();
    }

    public SimpleStringProperty z1Property() {
        return Z1;
    }

    public String getYn1() {
        return Yn1.get();
    }

    public SimpleStringProperty yn1Property() {
        return Yn1;
    }

    public String getZ3() {
        return Z3.get();
    }

    public SimpleStringProperty z3Property() {
        return Z3;
    }

    public String getFxyn1() {
        return Fxyn1.get();
    }

    public SimpleStringProperty fxyn1Property() {
        return Fxyn1;
    }
}
