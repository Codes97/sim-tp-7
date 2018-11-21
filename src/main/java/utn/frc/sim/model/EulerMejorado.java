package utn.frc.sim.model;

import utn.frc.sim.util.DoubleUtils;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class EulerMejorado {

    private static double h = 0.1d;
    private static double time = 0d;
    private static double m = 1d;

    private static List<EulerRow> filas = new LinkedList<>();

    public static List<EulerRow> calculateEuler(){
        filas.clear();
        double z = 1;
        double z1 = 0;
        double Yn1 = 0;
        double fxyn1 = 0;
        double corchetes = 0;

        String timestring = "";
        String zstring = "";
        String z1string = "";
        String yn1string = "";
        String fxyn1string = "";
        String corchetesstring = "";

        while(m > 0.1){
            time += h;
            z = z + h*z1;
            z1 = -0.05d*z-0.0001d*time;
            Yn1 = z+h*z1;
            fxyn1 = -0.05d*Yn1-0.001*time;
            corchetes = (z1+fxyn1)/2;

            m=z;

            zstring = DoubleUtils.roundString(z, 4);
            z1string = DoubleUtils.roundString(z1, 4);
            yn1string = DoubleUtils.roundString(Yn1, 4);
            fxyn1string = DoubleUtils.roundString(fxyn1, 4);
            corchetesstring = DoubleUtils.roundString(corchetes, 4);
            timestring = DoubleUtils.roundString(time, 2);

            filas.add(new EulerRow(timestring, zstring, z1string, yn1string, corchetesstring, fxyn1string));
        }
        return filas;
    }

    public static double getTime() {
        return time;
    }

    public static double getH() {
        return h;
    }
}
