package utn.frc.sim.util;


import javafx.beans.property.SimpleStringProperty;

public class Fila {

    private final SimpleStringProperty event;
    private final SimpleStringProperty clock;
    private final SimpleStringProperty clientes;
    private final SimpleStringProperty nextArrival;
    private final SimpleStringProperty day;
    private final SimpleStringProperty estadoAreaA;
    private final SimpleStringProperty estadoAreaB;
    private final SimpleStringProperty estadoSecado1;
    private final SimpleStringProperty estadoSecado2;
    private final SimpleStringProperty estadoSecado3;
    private final SimpleStringProperty estadoSecado4;
    private final SimpleStringProperty estadoSecado5;
    private final SimpleStringProperty colaAreaA;
    private final SimpleStringProperty colaAreaB;
    private final SimpleStringProperty colaSecado;
    private final SimpleStringProperty clienteAreaA;
    private final SimpleStringProperty clienteAreaB;
    private final SimpleStringProperty clienteSecado1;
    private final SimpleStringProperty clienteSecado2;
    private final SimpleStringProperty clienteSecado3;
    private final SimpleStringProperty clienteSecado4;
    private final SimpleStringProperty clienteSecado5;
    private final SimpleStringProperty endAreaA;
    private final SimpleStringProperty endAreaB;
    private final SimpleStringProperty endS1;
    private final SimpleStringProperty endS2;
    private final SimpleStringProperty endS3;
    private final SimpleStringProperty endS4;
    private final SimpleStringProperty endS5;

    public Fila(String event, String clock, String clientes, String nextArrival, String stAreaA, String stAreaB,
                String stSecado1, String stSecado2, String stSecado3, String stSecado4, String stSecado5, String colaA,
                String colaB, String colaSecado, String clienteA, String clienteB, String clienteS1, String clienteS2,
                String clienteS3, String clienteS4, String clienteS5, String day, String endA, String endB, String endS1,
                String endS2, String endS3, String endS4, String endS5) {
        this.event =  new SimpleStringProperty(event);
        this.clock = new SimpleStringProperty(clock);
        this.clientes = new SimpleStringProperty(clientes);
        this.nextArrival = new SimpleStringProperty(nextArrival);
        this.day = new SimpleStringProperty(day);
        this.estadoAreaA = new SimpleStringProperty(stAreaA);
        this.estadoAreaB = new SimpleStringProperty(stAreaB);
        this.estadoSecado1 = new SimpleStringProperty(stSecado1);
        this.estadoSecado2 = new SimpleStringProperty(stSecado2);
        this.estadoSecado3 = new SimpleStringProperty(stSecado3);
        this.estadoSecado4 = new SimpleStringProperty(stSecado4);
        this.estadoSecado5 = new SimpleStringProperty(stSecado5);
        this.colaAreaA = new SimpleStringProperty(colaA);
        this.colaAreaB = new SimpleStringProperty(colaB);
        this.colaSecado = new SimpleStringProperty(colaSecado);
        this.clienteAreaA = new SimpleStringProperty(clienteA);
        this.clienteAreaB = new SimpleStringProperty(clienteB);
        this.clienteSecado1 = new SimpleStringProperty(clienteS1);
        this.clienteSecado2 = new SimpleStringProperty(clienteS2);
        this.clienteSecado3 = new SimpleStringProperty(clienteS3);
        this.clienteSecado4 = new SimpleStringProperty(clienteS4);
        this.clienteSecado5 = new SimpleStringProperty(clienteS5);
        this.endAreaA = new SimpleStringProperty(endA);
        this.endAreaB = new SimpleStringProperty(endB);
        this.endS1 = new SimpleStringProperty(endS1);
        this.endS2 = new SimpleStringProperty(endS2);
        this.endS3 = new SimpleStringProperty(endS3);
        this.endS4 = new SimpleStringProperty(endS4);
        this.endS5 = new SimpleStringProperty(endS5);
    }

    public String getEvent() {
        return event.get();
    }

    public SimpleStringProperty eventProperty() {
        return event;
    }

    public void setEvent(String event) {
        this.event.set(event);
    }

    public String getClock() {
        return clock.get();
    }

    public SimpleStringProperty clockProperty() {
        return clock;
    }

    public void setClock(String clock) {
        this.clock.set(clock);
    }

    public String getClientes() {
        return clientes.get();
    }

    public SimpleStringProperty clientesProperty() {
        return clientes;
    }

    public void setClientes(String clientes) {
        this.clientes.set(clientes);
    }

    public String getNextArrival() {
        return nextArrival.get();
    }

    public SimpleStringProperty nextArrivalProperty() {
        return nextArrival;
    }

    public void setNextArrival(String nextArrival) {
        this.nextArrival.set(nextArrival);
    }

    public String getDay() {
        return day.get();
    }

    public SimpleStringProperty dayProperty() {
        return day;
    }

    public void setDay(String day) {
        this.day.set(day);
    }

    public String getEstadoAreaA() {
        return estadoAreaA.get();
    }

    public SimpleStringProperty estadoAreaAProperty() {
        return estadoAreaA;
    }

    public void setEstadoAreaA(String estadoAreaA) {
        this.estadoAreaA.set(estadoAreaA);
    }

    public String getEstadoAreaB() {
        return estadoAreaB.get();
    }

    public SimpleStringProperty estadoAreaBProperty() {
        return estadoAreaB;
    }

    public void setEstadoAreaB(String estadoAreaB) {
        this.estadoAreaB.set(estadoAreaB);
    }

    public String getEstadoSecado1() {
        return estadoSecado1.get();
    }

    public SimpleStringProperty estadoSecado1Property() {
        return estadoSecado1;
    }

    public void setEstadoSecado1(String estadoSecado1) {
        this.estadoSecado1.set(estadoSecado1);
    }

    public String getEstadoSecado2() {
        return estadoSecado2.get();
    }

    public SimpleStringProperty estadoSecado2Property() {
        return estadoSecado2;
    }

    public void setEstadoSecado2(String estadoSecado2) {
        this.estadoSecado2.set(estadoSecado2);
    }

    public String getEstadoSecado3() {
        return estadoSecado3.get();
    }

    public SimpleStringProperty estadoSecado3Property() {
        return estadoSecado3;
    }

    public void setEstadoSecado3(String estadoSecado3) {
        this.estadoSecado3.set(estadoSecado3);
    }

    public String getEstadoSecado4() {
        return estadoSecado4.get();
    }

    public SimpleStringProperty estadoSecado4Property() {
        return estadoSecado4;
    }

    public void setEstadoSecado4(String estadoSecado4) {
        this.estadoSecado4.set(estadoSecado4);
    }

    public String getEstadoSecado5() {
        return estadoSecado5.get();
    }

    public SimpleStringProperty estadoSecado5Property() {
        return estadoSecado5;
    }

    public void setEstadoSecado5(String estadoSecado5) {
        this.estadoSecado5.set(estadoSecado5);
    }

    public String getColaAreaA() {
        return colaAreaA.get();
    }

    public SimpleStringProperty colaAreaAProperty() {
        return colaAreaA;
    }

    public void setColaAreaA(String colaAreaA) {
        this.colaAreaA.set(colaAreaA);
    }

    public String getColaAreaB() {
        return colaAreaB.get();
    }

    public SimpleStringProperty colaAreaBProperty() {
        return colaAreaB;
    }

    public void setColaAreaB(String colaAreaB) {
        this.colaAreaB.set(colaAreaB);
    }

    public String getColaSecado() {
        return colaSecado.get();
    }

    public SimpleStringProperty colaSecadoProperty() {
        return colaSecado;
    }

    public void setColaSecado(String colaSecado) {
        this.colaSecado.set(colaSecado);
    }

    public String getClienteAreaA() {
        return clienteAreaA.get();
    }

    public SimpleStringProperty clienteAreaAProperty() {
        return clienteAreaA;
    }

    public void setClienteAreaA(String clienteAreaA) {
        this.clienteAreaA.set(clienteAreaA);
    }

    public String getClienteAreaB() {
        return clienteAreaB.get();
    }

    public SimpleStringProperty clienteAreaBProperty() {
        return clienteAreaB;
    }

    public void setClienteAreaB(String clienteAreaB) {
        this.clienteAreaB.set(clienteAreaB);
    }

    public String getClienteSecado1() {
        return clienteSecado1.get();
    }

    public SimpleStringProperty clienteSecado1Property() {
        return clienteSecado1;
    }

    public void setClienteSecado1(String clienteSecado1) {
        this.clienteSecado1.set(clienteSecado1);
    }

    public String getClienteSecado2() {
        return clienteSecado2.get();
    }

    public SimpleStringProperty clienteSecado2Property() {
        return clienteSecado2;
    }

    public void setClienteSecado2(String clienteSecado2) {
        this.clienteSecado2.set(clienteSecado2);
    }

    public String getClienteSecado3() {
        return clienteSecado3.get();
    }

    public SimpleStringProperty clienteSecado3Property() {
        return clienteSecado3;
    }

    public void setClienteSecado3(String clienteSecado3) {
        this.clienteSecado3.set(clienteSecado3);
    }

    public String getClienteSecado4() {
        return clienteSecado4.get();
    }

    public SimpleStringProperty clienteSecado4Property() {
        return clienteSecado4;
    }

    public void setClienteSecado4(String clienteSecado4) {
        this.clienteSecado4.set(clienteSecado4);
    }

    public String getClienteSecado5() {
        return clienteSecado5.get();
    }

    public SimpleStringProperty clienteSecado5Property() {
        return clienteSecado5;
    }

    public void setClienteSecado5(String clienteSecado5) {
        this.clienteSecado5.set(clienteSecado5);
    }

    public String getEndAreaA() {
        return endAreaA.get();
    }

    public SimpleStringProperty endAreaAProperty() {
        return endAreaA;
    }

    public void setEndAreaA(String endAreaA) {
        this.endAreaA.set(endAreaA);
    }

    public String getEndAreaB() {
        return endAreaB.get();
    }

    public SimpleStringProperty endAreaBProperty() {
        return endAreaB;
    }

    public void setEndAreaB(String endAreaB) {
        this.endAreaB.set(endAreaB);
    }

    public String getEndS1() {
        return endS1.get();
    }

    public SimpleStringProperty endS1Property() {
        return endS1;
    }

    public void setEndS1(String endS1) {
        this.endS1.set(endS1);
    }

    public String getEndS2() {
        return endS2.get();
    }

    public SimpleStringProperty endS2Property() {
        return endS2;
    }

    public void setEndS2(String endS2) {
        this.endS2.set(endS2);
    }

    public String getEndS3() {
        return endS3.get();
    }

    public SimpleStringProperty endS3Property() {
        return endS3;
    }

    public void setEndS3(String endS3) {
        this.endS3.set(endS3);
    }

    public String getEndS4() {
        return endS4.get();
    }

    public SimpleStringProperty endS4Property() {
        return endS4;
    }

    public void setEndS4(String endS4) {
        this.endS4.set(endS4);
    }

    public String getEndS5() {
        return endS5.get();
    }

    public SimpleStringProperty endS5Property() {
        return endS5;
    }

    public void setEndS5(String endS5) {
        this.endS5.set(endS5);
    }
}
