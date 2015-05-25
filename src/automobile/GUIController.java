/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 Test commit
 */
package automobile;

import Database.Resources.AutomodellDTO;
import Database.DBAccess.DAO;
import Database.DBAccess.DAOInterface;
import java.net.URL;
import java.sql.Connection;
import java.util.ResourceBundle;
import java.util.Set;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * FXML Controller class
 *
 * @author Sven
 */
public class GUIController implements Initializable {

    private DBConn connection;
    private Connection conn;
    private String hersteller;
    private DAOInterface dao;
    private ObservableList<String> bez_list;
    private ObservableList<String> her_list;
    private ObservableList<AutomodellDTO> modell_list;

    @FXML
    private TableView<AutomodellDTO> resultView;

    @FXML
    private ComboBox<String> cb_bezeichnung;

    @FXML
    private ComboBox<String> cb_hersteller;

    @FXML
    private ComboBox<String> cb_autoart;

    @FXML
    private ComboBox<String> cb_sitzplaetze;

    @FXML
    private ComboBox<String> cb_treibstoff;

    @FXML
    private Button btn_search;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        //Connection to database-layer
        this.dao = new DAO();
        //Create and fill comboboxes with data from database-layer
        bez_list = FXCollections.observableArrayList();
        her_list = FXCollections.observableArrayList();
        her_list.addAll(this.dao.getTitles("Hersteller"));
        bez_list.addAll(this.dao.getTitles("Bezeichung"));
        cb_bezeichnung.setItems(bez_list);
        cb_hersteller.setItems(her_list);

        //Tabellenspalten anlegen
        TableColumn<AutomodellDTO, String> col1 = new TableColumn<AutomodellDTO, String>("ID");
        col1.setCellValueFactory(new PropertyValueFactory<AutomodellDTO, String>("id"));
        TableColumn<AutomodellDTO, String> col2 = new TableColumn<AutomodellDTO, String>("Hersteller");
        col2.setCellValueFactory(new PropertyValueFactory<AutomodellDTO, String>("hersteller"));
        TableColumn<AutomodellDTO, String> col3 = new TableColumn<AutomodellDTO, String>("Bezeichnung");
        col3.setCellValueFactory(new PropertyValueFactory<AutomodellDTO, String>("bezeichnung"));
        TableColumn<AutomodellDTO, String> col4 = new TableColumn<AutomodellDTO, String>("PreisTag");
        col4.setCellValueFactory(new PropertyValueFactory<AutomodellDTO, String>("preisTag"));
        resultView.getColumns().addAll(col1, col2, col3, col4);

        //Listener für die Combobox-Auswahl    
        cb_hersteller.setOnAction((event) -> {
            String herst = cb_hersteller.getSelectionModel().getSelectedItem();
            bez_list.clear();
            bez_list.addAll(this.dao.getBezeichnungByHersteller(herst));
            cb_bezeichnung.setItems(bez_list);

        });
    }

    @FXML
    public void buttonSearch() {

        String bezeichnung;
        String hersteller;
        int art = 0;
        int sitzpl = 0;
        String treibstoff = null;

        //Werte für Suche aus Combobox holen
        hersteller = cb_hersteller.getValue();
        bezeichnung = cb_bezeichnung.getValue();

        //Modelle aus DB holen
        Set<AutomodellDTO> modelle = dao.getAutomodelle(hersteller, bezeichnung, art, sitzpl, treibstoff);

        //HashSet mit Automodellen in observableList parsen
        modell_list = FXCollections.observableArrayList();
        for (AutomodellDTO a : modelle) {
            modell_list.add(a);
            //System.out.println("ID vor View: " + a.getID());
        }

        //TableView updaten
        resultView.setItems(modell_list);

    }

}
