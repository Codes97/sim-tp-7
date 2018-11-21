package utn.frc.sim.simulation;

public enum Events {
    INICIO("Inicio."),
    INICIO_DEL_DIA("Inicio de la simulacion."),
    LLEGADA_CLIENTE("Llegada de Trabajo."),
    FIN_ATENCION_A("Fin atencion A."),
    FIN_ATENCION_B("Fin atencion B."),
    FIN_ATENCION_SECADO1("Fin atencion Secado 1."),
    FIN_ATENCION_SECADO2("Fin atencion Secado 2."),
    FIN_ATENCION_SECADO3("Fin atencion Secado 3."),
    FIN_ATENCION_SECADO4("Fin atencion Secado 4."),
    FIN_ATENCION_SECADO5("Fin atencion Secado 5.");

    private final String text;

    Events(final String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }
}
