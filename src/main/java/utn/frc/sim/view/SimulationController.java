package utn.frc.sim.view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import utn.frc.sim.simulation.SimulationFinishedException;
import utn.frc.sim.simulation.SimulationWrapper;
import utn.frc.sim.util.Fila;

public class SimulationController {

    private static final Logger logger = LogManager.getLogger(SimulationController.class);
    private static final int MAX_SIMULATION = 30;
    private SimulationWrapper simulation;
    private ObservableList<Fila> data;

    @FXML
    private TableColumn<Fila, String> event;
    @FXML
    private TableColumn<Fila, String> clock;
    @FXML
    private TableColumn<Fila, String> clientes;
    @FXML
    private TableColumn<Fila, String> nextArrival;
    @FXML
    private TableColumn<Fila, String> stateAreaA;
    @FXML
    private TableColumn<Fila, String> clienteAreaA;
    @FXML
    private TableColumn<Fila, String> endAreaA;
    @FXML
    private TableColumn<Fila, String> colaAreaA;
    @FXML
    private TableColumn<Fila, String> stateAreaB;
    @FXML
    private TableColumn<Fila, String> clienteAreaB;
    @FXML
    private TableColumn<Fila, String> endAreaB;
    @FXML
    private TableColumn<Fila, String> colaAreaB;
    @FXML
    private TableColumn<Fila, String> colaSecado;
    @FXML
    private TableColumn<Fila, String> stateS1;
    @FXML
    private TableColumn<Fila, String> clienteS1;
    @FXML
    private TableColumn<Fila, String> endS1;
    @FXML
    private TableColumn<Fila, String> stateS2;
    @FXML
    private TableColumn<Fila, String> clienteS2;
    @FXML
    private TableColumn<Fila, String> endS2;
    @FXML
    private TableColumn<Fila, String> stateS3;
    @FXML
    private TableColumn<Fila, String> clienteS3;
    @FXML
    private TableColumn<Fila, String> endS3;
    @FXML
    private TableColumn<Fila, String> stateS4;
    @FXML
    private TableColumn<Fila, String> clienteS4;
    @FXML
    private TableColumn<Fila, String> endS4;
    @FXML
    private TableColumn<Fila, String> stateS5;
    @FXML
    private TableColumn<Fila, String> clienteS5;
    @FXML
    private TableColumn<Fila, String> endS5;
    @FXML
    private TableColumn<Fila, String> day;

    @FXML
    private TableView<Fila> tvSim;
    @FXML
    private AnchorPane panelSim1;
    @FXML
    private Button semiautomatic;
    @FXML
    private TextField txtFromDay;
    @FXML
    private TextField txtToDay;
    @FXML
    private TextField txtFromHour;
    @FXML
    private TextField txtToHour;
    @FXML
    private Text txAvgDurationService;
    @FXML
    private Text txAvgStopA;
    @FXML
    private Text txMaxTrabajos;
    @FXML
    private Text txCamionesTotales;

    @FXML
    private TextField txt_Numeric;
    @FXML
    private TextField txt_Letters;



    @FXML
    public void initialize() {
        resetSimulation();
    }

    @FXML
    void btnRunClick(ActionEvent event) {
        resetSimulation();
        runSimulationToEnd();
    }

    @FXML
    void btnRunSemiClick(ActionEvent event) {
        runOneStepOfSimulation();
    }

    @FXML
    void btnReset(ActionEvent event) {
        resetSimulation();
    }

    private void runSimulationToEnd() {
        disableSemiautomaticButton();
        runSimulation(Boolean.TRUE);
        setStats();
    }

    private void setStats() {
        txMaxTrabajos.setText(simulation.getMaxWorksAtTime());
        txAvgStopA.setText(simulation.getStoppedTime());
        txAvgDurationService.setText(simulation.getAverageDurationOfService());
        txCamionesTotales.setText(simulation.getNumberOfClientsServed());
    }

    private void runOneStepOfSimulation() {
        runSimulation(Boolean.FALSE);
    }

    private void resetSimulation() {
        txAvgDurationService.setText("0");
        txMaxTrabajos.setText("0");
        txAvgStopA.setText("0");
        txCamionesTotales.setText("0");
        enableSemiautomaticButton();
        clearItemsInTableView();
        initializeNewSimulation();
    }

    private void clearItemsInTableView() {
        tvSim.getItems().clear();
    }

    private void disableSemiautomaticButton() {
        semiautomatic.setDisable(true);
    }

    private void enableSemiautomaticButton() {
        semiautomatic.setDisable(false);
    }

    private void initializeNewSimulation() {
        logger.debug("Initializing simulation of {} days.", MAX_SIMULATION);
        simulation = SimulationWrapper.ofType(MainMenuController.getType(), MAX_SIMULATION);
        data = FXCollections.observableArrayList();
    }

    private void runSimulation(boolean auto) {

        if (auto) {
            while (true) {
                try {
                    runOneStepAndAddToTable();
                } catch (SimulationFinishedException e) {
                    calculateStats();
                    logger.debug("Simulation finished.");
                    break;
                }
            }
        } else {
            try {
                runOneStepAndAddToTable();
            } catch (SimulationFinishedException e) {
                calculateStats();
                logger.debug("Simulation finished.");
            }
        }
    }

    private void calculateStats() {

    }

    private void runOneStepAndAddToTable() throws SimulationFinishedException {
        simulation.step();
        if (simulation.verifyRowToAddToTable(txtFromDay.getText(), txtToDay.getText(), txtFromHour.getText(), txtToHour.getText())) {
            loadTable();
        }
    }

    private void loadTable() {

        String eventContent = simulation.getLastEvent();
        String clockContent = simulation.getClock();
        String trucksContent = simulation.getNumberClient();
        String nextArrivalContent = simulation.getNextClientEvent();

        String areaAState = simulation.getAreaAState();
        String areaAClient = simulation.getAreaAClient();
        String areaANextEvent = simulation.getAreaANextEvent();
        String areaAQueueLenght = simulation.getAreaAQueueLength();

        String areaBState = simulation.getAreaBState();
        String areaBClient = simulation.getAreaBClient();
        String areaBNextEvent = simulation.getAreaBNextEvent();
        String areaBQueueLenght = simulation.getAreaBQueueLength();

        String secadoQueueLenght = simulation.getSecadoQueueLength();

        String s1State = simulation.getSecado1State();
        String s1Client = simulation.getSecado1Client();
        String s1NextEvent = simulation.getSecado1NextEvent();

        String s2State = simulation.getSecado2State();
        String s2Client = simulation.getSecado2Client();
        String s2NextEvent = simulation.getSecado2NextEvent();

        String s3State = simulation.getSecado3State();
        String s3Client = simulation.getSecado3Client();
        String s3NextEvent = simulation.getSecado3NextEvent();

        String s4State = simulation.getSecado4State();
        String s4Client = simulation.getSecado4Client();
        String s4NextEvent = simulation.getSecado4NextEvent();

        String s5State = simulation.getSecado5State();
        String s5Client = simulation.getSecado5Client();
        String s5NextEvent = simulation.getSecado5NextEvent();


//        String truckServedContent = simulation.getNumberOfTrucksServed();
        String dayContent = simulation.getDay();
//        String litrosCD1 = simulation.getDarsena1ClientCapacity();
//        String litrosCD2 = simulation.getDarsena2ClientCapacity();

        data.addAll(new Fila(eventContent, clockContent, trucksContent, nextArrivalContent,areaAState,
                areaBState, s1State, s2State, s3State, s4State, s5State, areaAQueueLenght, areaBQueueLenght,
                secadoQueueLenght,areaAClient, areaBClient, s1Client,s2Client,s3Client,s4Client,s5Client, dayContent,
                areaANextEvent, areaBNextEvent, s1NextEvent,s2NextEvent,s3NextEvent,s4NextEvent,s5NextEvent));

        event.setCellValueFactory(new PropertyValueFactory<>("event"));
        clock.setCellValueFactory(new PropertyValueFactory<>("clock"));
        clientes.setCellValueFactory(new PropertyValueFactory<>("clientes"));
        nextArrival.setCellValueFactory(new PropertyValueFactory<>("nextArrival"));
        stateAreaA.setCellValueFactory(new PropertyValueFactory<>("stateAreaA"));
        clienteAreaA.setCellValueFactory(new PropertyValueFactory<>("clienteAreaA"));
        endAreaA.setCellValueFactory(new PropertyValueFactory<>("endAreaA"));
        colaAreaA.setCellValueFactory(new PropertyValueFactory<>("colaAreaA"));
        stateAreaB.setCellValueFactory(new PropertyValueFactory<>("stateAreaB"));
        clienteAreaB.setCellValueFactory(new PropertyValueFactory<>("clienteAreaB"));
        endAreaB.setCellValueFactory(new PropertyValueFactory<>("endAreaB"));
        colaAreaB.setCellValueFactory(new PropertyValueFactory<>("colaAreaB"));
        colaSecado.setCellValueFactory(new PropertyValueFactory<>("colaSecado"));
        stateS1.setCellValueFactory(new PropertyValueFactory<>("stateS1"));
        clienteS1.setCellValueFactory(new PropertyValueFactory<>("clienteS1"));
        endS1.setCellValueFactory(new PropertyValueFactory<>("endS1"));
        stateS2.setCellValueFactory(new PropertyValueFactory<>("stateS2"));
        clienteS2.setCellValueFactory(new PropertyValueFactory<>("clienteS2"));
        endS2.setCellValueFactory(new PropertyValueFactory<>("endS2"));
        stateS3.setCellValueFactory(new PropertyValueFactory<>("stateS3"));
        clienteS3.setCellValueFactory(new PropertyValueFactory<>("clienteS3"));
        endS3.setCellValueFactory(new PropertyValueFactory<>("endS3"));
        stateS4.setCellValueFactory(new PropertyValueFactory<>("stateS4"));
        clienteS4.setCellValueFactory(new PropertyValueFactory<>("clienteS4"));
        endS4.setCellValueFactory(new PropertyValueFactory<>("endS4"));
        stateS5.setCellValueFactory(new PropertyValueFactory<>("stateS5"));
        clienteS5.setCellValueFactory(new PropertyValueFactory<>("clienteS5"));
        endS5.setCellValueFactory(new PropertyValueFactory<>("endS5"));
        day.setCellValueFactory(new PropertyValueFactory<>("day"));

        tvSim.setItems(data);
    }

}
