package utn.frc.sim.view;

import javafx.beans.property.SimpleStringProperty;
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
import utn.frc.sim.model.EulerRow;
import utn.frc.sim.simulation.SimulationFinishedException;
import utn.frc.sim.simulation.SimulationType;
import utn.frc.sim.simulation.SimulationWrapper;
import utn.frc.sim.util.Fila;
import utn.frc.sim.util.FilaEuler;

import java.util.List;

public class EulerController {

    private static final Logger logger = LogManager.getLogger(EulerController.class);
    private static final int MAX_SIMULATION = 30;
    private SimulationWrapper simulation;
    private ObservableList<FilaEuler> data;

    @FXML
    TextField tfH;

    @FXML
    TableView<FilaEuler> tbEuler;

    @FXML
    private TableColumn<FilaEuler, String> tbT;
    @FXML
    private TableColumn<FilaEuler, String> tbZ;
    @FXML
    private TableColumn<FilaEuler, String> tbZ1;
    @FXML
    private TableColumn<FilaEuler, String> tbYn1;
    @FXML
    private TableColumn<FilaEuler, String> tbFxyn1;
    @FXML
    private TableColumn<FilaEuler, String> tbZ3;

    @FXML
    public void initialize() {
        simulation = SimulationWrapper.ofType(SimulationType.Type1, MAX_SIMULATION);
        data = FXCollections.observableArrayList();
        tfH.setText(simulation.getH());
        List<EulerRow> lst = simulation.getEulerRows();
        for(int i = 0; i<lst.size(); i++){
            loadTable(lst.get(i));
        }
    }

    private void loadTable(EulerRow e) {


        data.addAll(new FilaEuler(e.getTime(),e.getZ(), e.getZ1(), e.getYn1(), e.getZ3(), e.getFxyn1()));

        tbT.setCellValueFactory(new PropertyValueFactory<>("T"));
        tbZ.setCellValueFactory(new PropertyValueFactory<>("Z"));
        tbZ1.setCellValueFactory(new PropertyValueFactory<>("Z1"));
        tbZ3.setCellValueFactory(new PropertyValueFactory<>("Z3"));
        tbYn1.setCellValueFactory(new PropertyValueFactory<>("Yn1"));
        tbFxyn1.setCellValueFactory(new PropertyValueFactory<>("Fxyn1"));

        tbEuler.setItems(data);
    }

}
