package utn.frc.sim.simulation;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import utn.frc.sim.generators.distributions.DistributionRandomGenerator;
import utn.frc.sim.generators.distributions.NegativeExponentialDistributionGenerator;
import utn.frc.sim.generators.distributions.NormalDistributionGenerator;
import utn.frc.sim.generators.distributions.UniformDistributionGenerator;
import utn.frc.sim.model.Event;
import utn.frc.sim.model.clients.Client;
import utn.frc.sim.model.clients.ClientGenerator;
import utn.frc.sim.model.servers.Server;
import utn.frc.sim.model.servers.ServerWithDistribution;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.LinkedList;
import java.util.Optional;
import java.util.Queue;

public class Simulation {

    private static final Logger logger = LogManager.getLogger(Simulation.class);
    private LocalDateTime clock;
    private LocalDateTime dayFirstEvent;
    private ClientGenerator clientGenerator;
    private double avgMinutesPerClient;
    private double avgMinutesStopped;
    private int maxClientsAtTime;
    private int attendingWorks;
    private int clientsServed;
    private Events lastEventDescription;
    private Client clientOfEvent;
    private int day;
    private int limitOfSimulations;
    private DistributionRandomGenerator capacityGenerator;
    private Server areaA;
    private Server areaB;
    private Server secado1;
    private Server secado2;
    private Server secado3;
    private Server secado4;
    private Server secado5;
    private Queue<Client> colaA;
    private Queue<Client> colaB;
    private Queue<Client> colaSecado;

    private Simulation(SimulationType type, int days) {
        initSimulation(type, days);
    }

    public static Simulation ofType(SimulationType type, int days) {
        return new Simulation(type, days);
    }

    private void initSimulation(SimulationType type, int days) {
        limitOfSimulations = days;
        initFirstEventOfDay();
        initStatisticsValues();
        initServers();
        initColas();
        initClientGenerator(type);
        initEvent();
    }

    private void initServers(){
        DistributionRandomGenerator aGenerator = UniformDistributionGenerator.createOf(1, 10);
        DistributionRandomGenerator bGenerator = NormalDistributionGenerator.createOf(8, Math.sqrt(5));
        DistributionRandomGenerator secadoGenerator = NormalDistributionGenerator.createOf(20, Math.sqrt(5));

        areaA = new Server("Area A", aGenerator);
        areaB = new Server("Area B", bGenerator);
        secado1 = new Server("Secado 1", secadoGenerator);
        secado2 = new Server("Secado 2", secadoGenerator);
        secado3 = new Server("Secado 3", secadoGenerator);
        secado4 = new Server("Secado 4", secadoGenerator);
        secado5 = new Server("Secado 5", secadoGenerator);
    }
    private void initColas(){
        colaA = new LinkedList();
        colaB = new LinkedList();
        colaSecado = new LinkedList();
    }

    private void initEvent() {
        lastEventDescription = Events.INICIO;
    }

    private void initFirstEventOfDay() {
        day = 0;
        dayFirstEvent = LocalDateTime.of(2018, 1, 1, 0, 0);
    }

    private void initStatisticsValues() {
        avgMinutesPerClient = 0;
        avgMinutesStopped = 0;
        maxClientsAtTime = 0;
        attendingWorks = 0;
        clientsServed = 0;
    }

    private Server createDarsenaForNumber(int number) {
        DistributionRandomGenerator generator = UniformDistributionGenerator.createOf(15, 20);
        NormalDistributionGenerator generatorForInterruptions = NormalDistributionGenerator.createOf(10, Math.sqrt(1.2));
        return new ServerWithDistribution("Darsena " + number, generator, 15, generatorForInterruptions);
    }

    private void initClientGenerator(SimulationType type) {
        DistributionRandomGenerator generator;
        generator = NegativeExponentialDistributionGenerator.createOf(5);
        LocalDateTime clientsInitial = LocalDateTime.of(2018, 1, 1, 0, 0);
        clientGenerator = new ClientGenerator(clientsInitial, generator);
    }

    public void step() throws SimulationFinishedException {
        clock = getNextEvent();
        handleEventFromFirstEvent(clock);
    }

    private void handleEventFromFirstEvent(LocalDateTime clock) throws SimulationFinishedException {
        if (dayFirstEvent.isEqual(clock)) {
            day++;
            if (day > limitOfSimulations) {
                throw new SimulationFinishedException();
            }
            logger.debug("{} - Day start.", clock);
            lastEventDescription = Events.INICIO_DEL_DIA;
            dayFirstEvent = dayFirstEvent.plus(1, ChronoUnit.DAYS);
            clientOfEvent = null;

            if(areaA.isFree() && !colaA.isEmpty()){
                Client client = colaA.poll();
                logger.debug("{} - Ingesting outside client. Client: {}.", clock, client);
                client.setInTime(clock);
                areaA.serveToClient(clock, client);
            }

        } else {
            handleEventFromClients(clock);
        }
    }

    private void handleEventFromClients(LocalDateTime clock) {
        if (clientGenerator.isEventFrom(clock)) {

            Client nextClient = clientGenerator.getNextClient();
            clientOfEvent = nextClient;
            lastEventDescription = Events.LLEGADA_CLIENTE;

            logger.debug("{} - New client into the system. Client: {}.", clock, nextClient);

            attendingWorks++;
            checkWorksAtTime();

            if (areaA.isFree() && colaB.size() < 3) {
                nextClient.setInTime(clock);
                areaA.serveToClient(clock, nextClient);
            } else {
                colaA.add(nextClient);
            }
        } else {
            handleEventFromAreaA(clock);
        }
    }

    private void handleEventFromAreaA(LocalDateTime clock) {
        if (areaA.isEventFrom(clock)) {
            lastEventDescription = Events.FIN_ATENCION_A;
            Event event = areaA.getEvent();
            if (event.hasClient()) {
                Client finishedClient = event.getClient();
                logger.debug("{} - Area A finished. Client: {}.", clock, finishedClient);
                clientOfEvent = finishedClient;
                if (areaB.isFree()) {
                    areaB.serveToClient(clock, finishedClient);
                } else {
                    colaB.add(finishedClient);
                }

                if (!colaA.isEmpty() && colaB.size() < 3) {
                    Client nextClientForRecepcion = colaA.poll();
                    nextClientForRecepcion.setInTime(clock);
                    areaA.serveToClient(clock, nextClientForRecepcion);
                } else{
                    if(colaB.size()>= 3){
                        areaA.stop(clock);
                    }
                }
            }
        } else {
            handleEventFromAreaB(clock);
        }

    }

    private void handleEventFromAreaB(LocalDateTime clock) {

        if (areaB.isEventFrom(clock)) {
            lastEventDescription = Events.FIN_ATENCION_B;
            Event event = areaB.getEvent();
            if (event.hasClient()) {
                Client finishedClient = event.getClient();
//                finishedClient.setCapacity(capacityGenerator.random());
                logger.debug("{} - Area B finished. Client: {}.", clock, finishedClient);
                clientOfEvent = finishedClient;
                if (secado1.isFree()) {
                    secado1.serveToClient(clock, finishedClient);
                } else if (secado2.isFree()) {
                    secado2.serveToClient(clock, finishedClient);
                } else if(secado3.isFree()){
                    secado3.serveToClient(clock, finishedClient);
                } else if(secado4.isFree()){
                    secado4.serveToClient(clock, finishedClient);
                } else if(secado4.isFree()){
                    secado4.serveToClient(clock, finishedClient);
                } else {
                    colaSecado.add(finishedClient);
                }
            }

            if (!colaB.isEmpty()) {
                Client nextClientForBalanza = colaB.poll();
                areaB.serveToClient(clock, nextClientForBalanza);
            }
            if(areaA.isStopped()){
                calculateAvgMinutesStopped(clock, areaA.reanude());
            }
        } else {
            handleEventFromSecado(clock);
        }
    }

    private void handleEventFromSecado(LocalDateTime clock) {
        if (secado1.isEventFrom(clock)) {
            handleEventForSecado(clock, secado1, 1);
        } else if (secado2.isEventFrom(clock)) {
            handleEventForSecado(clock, secado2, 2);
        } else if (secado3.isEventFrom(clock)) {
            handleEventForSecado(clock, secado3, 3);
        } else if (secado4.isEventFrom(clock)) {
            handleEventForSecado(clock, secado4, 4);
        } else if (secado5.isEventFrom(clock)) {
            handleEventForSecado(clock, secado5, 5);
        } else {
            throw new RuntimeException();
        }
    }

    private void handleEventForSecado(LocalDateTime clock, Server secado, int secadoNumber) {
        Event event = secado.getEvent();
        if (event.hasClient()) {
            Client finishedClient = event.getClient();
            finishedClient.setOutTime(clock);
            clientsServed++;
            attendingWorks--;
            calculateAvgMinutesForClients(finishedClient);
            clientOfEvent = finishedClient;
            logger.debug("{} - Secado {} finished. Client out: {}.", clock, secadoNumber, finishedClient);

            switch (secadoNumber){
                case 1:{
                    lastEventDescription = Events.FIN_ATENCION_SECADO1;
                    break;
                }
                case 2:{
                    lastEventDescription = Events.FIN_ATENCION_SECADO2;
                    break;
                }
                case 3:{
                    lastEventDescription = Events.FIN_ATENCION_SECADO3;
                    break;
                }

                case 4:{
                    lastEventDescription = Events.FIN_ATENCION_SECADO4;
                    break;
                }

                case 5:{
                    lastEventDescription = Events.FIN_ATENCION_SECADO5;
                    break;
                }
            }

        }
        if (!colaSecado.isEmpty() && secado.isFree()) {
            Client nextClient = colaSecado.poll();
            secado.serveToClient(clock, nextClient);
        }
    }

    private void checkWorksAtTime(){
        if(attendingWorks > maxClientsAtTime) maxClientsAtTime = attendingWorks;
    }
    private void calculateAvgMinutesForClients(Client client) {
        int n = clientsServed;
        long duration = client.getMinutesOfAttention();
        avgMinutesPerClient = ((double) 1 / n) * ((n - 1) * avgMinutesPerClient + duration);
    }
    private void calculateAvgMinutesStopped(LocalDateTime clock, LocalDateTime stopped) {
        int n = clientsServed;
        long duration = ChronoUnit.MINUTES.between(stopped, clock);
        avgMinutesStopped = ((double) 1 / n) * ((n - 1) * avgMinutesStopped + duration);
    }


    private LocalDateTime getNextEvent() {

        LocalDateTime firstEvent = dayFirstEvent;

        //Primer llegada de cliente
        if (clientGenerator.getNextClientEvent().isBefore(firstEvent)) {
            firstEvent = clientGenerator.getNextClientEvent();
        }

        //Atencion A
        if (areaA.getNextEnd().isPresent()) {
            LocalDateTime time = areaA.getNextEnd().get();
            if (time.isBefore(firstEvent)) {
                firstEvent = time;
            }
        }

        //Atencion B
        if (areaB.getNextEnd().isPresent()) {
            LocalDateTime time = areaB.getNextEnd().get();
            if (time.isBefore(firstEvent)) {
                firstEvent = time;
            }
        }

        //Atencion secado 1
        if (secado1.getNextEnd().isPresent()) {
            LocalDateTime time = secado1.getNextEnd().get();
            if (time.isBefore(firstEvent)) {
                firstEvent = time;
            }
        }
        //Atencion secado 2
        if (secado2.getNextEnd().isPresent()) {
            LocalDateTime time = secado2.getNextEnd().get();
            if (time.isBefore(firstEvent)) {
                firstEvent = time;
            }
        }
        //Atencion secado 3
        if (secado3.getNextEnd().isPresent()) {
            LocalDateTime time = secado3.getNextEnd().get();
            if (time.isBefore(firstEvent)) {
                firstEvent = time;
            }
        }
        //Atencion secado 4
        if (secado4.getNextEnd().isPresent()) {
            LocalDateTime time = secado4.getNextEnd().get();
            if (time.isBefore(firstEvent)) {
                firstEvent = time;
            }
        }
        //Atencion secado 5
        if (secado5.getNextEnd().isPresent()) {
            LocalDateTime time = secado5.getNextEnd().get();
            if (time.isBefore(firstEvent)) {
                firstEvent = time;
            }
        }

        return firstEvent;
    }

    public double getAvgMinutesStopped() {
        return avgMinutesStopped;
    }


    public int getMaxClientsAtTime() {
        return maxClientsAtTime;
    }


    public double getAvgMinutesPerClient() {
        return avgMinutesPerClient;
    }

    public double getTrucksServedPerDay() {
        return (double) clientsServed / (day - 1);
    }

    public int getDay() {
        return day;
    }

    public int getClientsServed() {
        return clientsServed;
    }

    public LocalDateTime getClock() {
        return clock;
    }

    public Events getLastEventDescription() {
        return lastEventDescription;
    }

    public ClientGenerator getClientGenerator() {
        return clientGenerator;
    }

    public Optional<Client> getClientOfEvent() {
        return Optional.ofNullable(clientOfEvent);
    }

    public LocalDateTime getDayFirstEvent() {
        return dayFirstEvent;
    }

    public int getLimitOfSimulations() {
        return limitOfSimulations;
    }

    public DistributionRandomGenerator getCapacityGenerator() {
        return capacityGenerator;
    }

    public Server getAreaA() {
        return areaA;
    }
    public Server getAreaB() {
        return areaB;
    }


    public Server getSecado1() {
        return secado1;
    }


    public Server getSecado2() {
        return secado2;
    }


    public Server getSecado3() {
        return secado3;
    }


    public Server getSecado4() {
        return secado4;
    }


    public Server getSecado5() {
        return secado5;
    }


    public Queue<Client> getColaA() {
        return colaA;
    }

    public Queue<Client> getColaB() {
        return colaB;
    }

    public Queue<Client> getColaSecado() {
        return colaSecado;
    }
}
