package utn.frc.sim.simulation;

import utn.frc.sim.model.EulerRow;
import utn.frc.sim.util.DoubleUtils;

import java.time.LocalDateTime;
import java.util.List;

public class SimulationWrapper {

    private static final String NONE_SYMBOL = "-";
    private final Simulation simulation;


    private SimulationWrapper(Simulation simulation) {
        this.simulation = simulation;
    }

    public static SimulationWrapper ofType(SimulationType type, int days) {
        return new SimulationWrapper(Simulation.ofType(type, days));
    }

    public void step() throws SimulationFinishedException {
        simulation.step();
    }


    /*
    Reloj y eventos.
     */

    public String getLastEvent() {
        return simulation.getLastEventDescription().toString();
    }

    public String getClock() {
        return simulation.getClock().toString();
    }

    public String getNumberClient(){
        return simulation.getClientOfEvent()
                .map(client -> Integer.toString(client.getClientNumber()))
                .orElse(NONE_SYMBOL);
    }
    /*
    Datos para clientes.
     */

    public String getNextClientEvent() {
        LocalDateTime nextEvent = simulation.getClientGenerator().getNextClientEvent();
        if (nextEvent == null) {
            return NONE_SYMBOL;
        }
        return nextEvent.toString();
    }
   // public  String getNumberCliente(){
    //    return Integer.toString(simulation.;
   // }




    /*
     * Datos A
     */

    public String getAreaAState(){
        return simulation.getAreaA().getState().toString();
    }
    public String getAreaAClient(){
        return simulation.getAreaA()
                .getServingClient()
                .map(client -> Integer.toString(client.getClientNumber()))
                .orElse(NONE_SYMBOL);
    }
    public String getAreaANextEvent(){
        return simulation.getAreaA().getNextEnd().map(LocalDateTime::toString).orElse(NONE_SYMBOL);
    }
    public String getAreaAQueueLength(){
        return Integer.toString(simulation.getColaA().size());
    }

    /*
     * Datos B
     */
    public String getAreaBState(){
        return simulation.getAreaB().getState().toString();
    }
    public String getAreaBClient(){
        return simulation.getAreaB()
                .getServingClient()
                .map(client -> Integer.toString(client.getClientNumber()))
                .orElse(NONE_SYMBOL);
    }
    public String getAreaBNextEvent(){
        return simulation.getAreaB().getNextEnd().map(LocalDateTime::toString).orElse(NONE_SYMBOL);
    }
    public String getAreaBQueueLength(){
        return Integer.toString(simulation.getColaB().size());
    }
    /*
     * Datos Secado
     */
    public String getSecado1State(){
        return simulation.getSecado1().getState().toString();
    }
    public String getSecado2State(){
        return simulation.getSecado2().getState().toString();
    }
    public String getSecado3State(){
        return simulation.getSecado3().getState().toString();
    }
    public String getSecado4State(){
        return simulation.getSecado4().getState().toString();
    }
    public String getSecado5State(){
        return simulation.getSecado5().getState().toString();
    }
    public String getSecado1Client(){
        return simulation.getSecado1()
                .getServingClient()
                .map(client -> Integer.toString(client.getClientNumber()))
                .orElse(NONE_SYMBOL);
    }
    public String getSecado2Client(){
        return simulation.getSecado2()
                .getServingClient()
                .map(client -> Integer.toString(client.getClientNumber()))
                .orElse(NONE_SYMBOL);
    }
    public String getSecado3Client(){
        return simulation.getSecado3()
                .getServingClient()
                .map(client -> Integer.toString(client.getClientNumber()))
                .orElse(NONE_SYMBOL);
    }
    public String getSecado4Client(){
        return simulation.getSecado4()
                .getServingClient()
                .map(client -> Integer.toString(client.getClientNumber()))
                .orElse(NONE_SYMBOL);
    }
    public String getSecado5Client(){
        return simulation.getSecado5()
                .getServingClient()
                .map(client -> Integer.toString(client.getClientNumber()))
                .orElse(NONE_SYMBOL);
    }
    public String getSecado1NextEvent(){
        return simulation.getSecado1().getNextEnd().map(LocalDateTime::toString).orElse(NONE_SYMBOL);
    }
    public String getSecado2NextEvent(){
        return simulation.getSecado2().getNextEnd().map(LocalDateTime::toString).orElse(NONE_SYMBOL);
    }
    public String getSecado3NextEvent(){
        return simulation.getSecado3().getNextEnd().map(LocalDateTime::toString).orElse(NONE_SYMBOL);
    }
    public String getSecado4NextEvent(){
        return simulation.getSecado4().getNextEnd().map(LocalDateTime::toString).orElse(NONE_SYMBOL);
    }
    public String getSecado5NextEvent(){
        return simulation.getSecado5().getNextEnd().map(LocalDateTime::toString).orElse(NONE_SYMBOL);
    }
    public String getSecadoQueueLength(){
        return Integer.toString(simulation.getColaSecado().size());
    }





    /*
    Estatisticas y conteos.
     */

    public String getNumberOfClientsServed() {
        return String.valueOf(simulation.getClientsServed());
    }

    public String getDay() {
        return Integer.toString(simulation.getDay());
    }

    public String getAverageDurationOfService() {
        return String.valueOf(DoubleUtils.round(simulation.getAvgMinutesPerClient(), 2)) + " min"; //Ya se que es una negrada gonza, no jodas =D
    }

    public String getStoppedTime(){
        return String.valueOf(DoubleUtils.round(simulation.getAvgStoppedTime(), 2)) + " hs";
    }

    public String getMaxWorksAtTime(){
        return String.valueOf(simulation.getMaxClientsAtTime());
    }

    public List<EulerRow> getEulerRows(){
        return simulation.getEulerRows();
    }

    public String getH(){
        return DoubleUtils.roundString(simulation.getH(), 4);
    }

    public boolean verifyRowToAddToTable(String txtFromDay, String txtToDay, String txtFromHour, String txtToHour){
        if(txtFromDay.equals("")) txtFromDay = "1";
        if(txtToDay.equals("")) txtToDay = "30";
        if(txtToHour.equals("")) txtToHour = "24";
        if(txtFromHour.equals("")) txtFromHour = "0";
        return  isInDayRange(Integer.parseInt(txtFromDay), Integer.parseInt(txtToDay)) &&
                isInHourRange(Integer.parseInt(txtFromHour), Integer.parseInt(txtToHour));

    }

    private boolean isInDayRange(Integer fromDay, Integer toDay){
        return simulation.getDay() >= fromDay && simulation.getDay() <= toDay;
    }
    private boolean isInHourRange(Integer fromHour, Integer toHour){
        toHour--;
        return simulation.getClock().getHour() >= fromHour && simulation.getClock().getHour() <= toHour;
    }

}
