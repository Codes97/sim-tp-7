package utn.frc.sim.model.servers;

import utn.frc.sim.generators.distributions.DistributionRandomGenerator;
import utn.frc.sim.generators.distributions.NormalDistributionGenerator;
import utn.frc.sim.model.TimeEvent;
import utn.frc.sim.model.clients.Client;
import utn.frc.sim.model.Event;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

public class Server {

    private final String serverName;
    protected LocalDateTime nextEnd;
    protected Client servingClient;
    protected TimeEvent timeEvent;
    protected ServerState state;
    protected LocalDateTime stoppedTime;

    public Server(String serverName, DistributionRandomGenerator generator) {
        this.serverName = serverName;
        this.timeEvent = new TimeEvent(generator);
        this.state = ServerState.LBR;
    }

    public void serveToClient(LocalDateTime clock, Client client) {
        servingClient = client;
        nextEnd = calculateNextEventForClient(clock, client);
        state = ServerState.OCP;
    }
    public void serveToClientEuler(LocalDateTime clock, Client client) {
        servingClient = client;
        nextEnd = calculateNextEventForClientEuler(clock);
        state = ServerState.OCP;
    }

    protected LocalDateTime calculateNextEventForClient(LocalDateTime clock, Client client) {
        return timeEvent.calculateNextEventFromRandom(clock);
    }
    protected LocalDateTime calculateNextEventForClientEuler(LocalDateTime clock){
        return timeEvent.calculateNextEventFromConstant(clock);
    }

    public Event getEvent() {
        Event event = new Event(servingClient);
        state = ServerState.LBR;
        nextEnd = null;
        servingClient = null;
        return event;
    }

    public void stop(LocalDateTime clock){
        state = ServerState.OUT;
        stoppedTime = clock;
    }

    public LocalDateTime reanude(){
        state = ServerState.LBR;
        return stoppedTime;
    }


    public boolean isStopped(){
        return state == ServerState.OUT;
    }

    public Optional<LocalDateTime> getNextEnd() {
        return Optional.ofNullable(nextEnd);
    }

    public Optional<Client> getServingClient() {
        return Optional.ofNullable(servingClient);
    }

    public ServerState getState() {
        return state;
    }

    public String getServerName() {
        return serverName;
    }

    public boolean isFree() {
        return state == ServerState.LBR;
    }

    public boolean isEventFrom(LocalDateTime clock) {
        return nextEnd != null && nextEnd.equals(clock);
    }

}
