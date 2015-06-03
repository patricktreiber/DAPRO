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
import Database.Resources.KundeDTO;
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
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
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
    private ObservableList<String> autoart_list;
    private ObservableList<String> sitz_list;
    private ObservableList<String> treib_list;
    private ObservableList<String> kunden_list;
    
    private ObservableList<AutomodellDTO> modell_list;
    
    private String her;
    private String bez;
    private int aid;
    
    private Set<KundeDTO> kunde_set;
    private String k_nachname;
    private String k_vorname;
    private int kid;
    
    
    @FXML
    private TextField her_txt;

    @FXML
    private TableView<AutomodellDTO> resultView;

    @FXML
    private ComboBox<String> cb_kunde_nachname;
    
    @FXML
    private ComboBox<String> cb_kunde_vorname; 
    
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
    
    @FXML
    private Button btn_refresh;
    
    @FXML
    private Button btn_choose;
    
    @FXML
    private Button btn_reservation;
    
    @FXML
    private Button btn_kd_add;
    
    @FXML
    private Button btn_her_set;
    
    @FXML
    private Button btn_bez_set;

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
        autoart_list = FXCollections.observableArrayList();
        sitz_list = FXCollections.observableArrayList();
        treib_list = FXCollections.observableArrayList();
        kunden_list = FXCollections.observableArrayList();
        
        
        her_list.addAll(this.dao.getTitles("Hersteller"));
        bez_list.addAll(this.dao.getTitles("Bezeichnung"));
        autoart_list.addAll(this.dao.getTitles("Autoart"));
        sitz_list.addAll(this.dao.getTitles("Sitzplaetze"));
        treib_list.addAll(this.dao.getTitles("Treibstoff"));
        
        
        cb_bezeichnung.setItems(bez_list);
        cb_hersteller.setItems(her_list);
        cb_autoart.setItems(autoart_list);
        cb_sitzplaetze.setItems(sitz_list);
        cb_treibstoff.setItems(treib_list);
        

        //Tabellenspalten anlegen
       // TableColumn<AutomodellDTO, String> col1 = new TableColumn<AutomodellDTO, String>("ID");
       // col1.setCellValueFactory(new PropertyValueFactory<AutomodellDTO, String>("id"));
        TableColumn<AutomodellDTO, String> col2 = new TableColumn<AutomodellDTO, String>("Hersteller");
        col2.setCellValueFactory(new PropertyValueFactory<AutomodellDTO, String>("hersteller"));
        TableColumn<AutomodellDTO, String> col3 = new TableColumn<AutomodellDTO, String>("Bezeichnung");
        col3.setCellValueFactory(new PropertyValueFactory<AutomodellDTO, String>("bezeichnung"));
        TableColumn<AutomodellDTO, String> col1 = new TableColumn<AutomodellDTO, String>("Autoart");
        col1.setCellValueFactory(new PropertyValueFactory<AutomodellDTO, String>("autoart"));
        TableColumn<AutomodellDTO, String> col4 = new TableColumn<AutomodellDTO, String>("PreisTag");
        col4.setCellValueFactory(new PropertyValueFactory<AutomodellDTO, String>("preisTag"));
        TableColumn<AutomodellDTO, String> col5 = new TableColumn<AutomodellDTO, String>("Sitzplaetze");
        col5.setCellValueFactory(new PropertyValueFactory<AutomodellDTO, String>("sitzplaetze"));
        TableColumn<AutomodellDTO, String> col6 = new TableColumn<AutomodellDTO, String>("Treibstoff");
        col6.setCellValueFactory(new PropertyValueFactory<AutomodellDTO, String>("treibstoff"));
        
        resultView.getColumns().addAll(col2, col3, col1, col4, col5, col6);

        //Listener für die Combobox-Auswahl  Hersteller mit Abhängigkeit Bezeichnung  
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
    
    @FXML
    public void buttonRefresh(){
        
        cb_hersteller.getSelectionModel().clearSelection();
        cb_bezeichnung.getSelectionModel().clearSelection();
        cb_autoart.getSelectionModel().clearSelection();
    }
    
    @FXML
    public void getChoose(){
        
               
        for (AutomodellDTO a : resultView.getSelectionModel().getSelectedItems()) {
            
            this.her = a.getHersteller();
            this.bez = a.getBezeichnung();
            this.aid = a.getID();
            System.out.println(aid);
                    
        }
        
        System.out.println(her);
        btn_her_set.setText(her);
        btn_bez_set.setText(bez);
        
        
    }
    
    @FXML
    public void getKD(){
        
        kunde_set = this.dao.getAllKunden();
        
        for(KundeDTO k : kunde_set){
            
            k_nachname = k.getNachname();
            k_vorname = k.getVorname();
            kid = k.getId();
            
            System.out.println(kid);
            
        }
        
        kunden_list.add(k_nachname);
        kunden_list.add(k_vorname);
        
        cb_kunde_nachname.setItems(kunden_list);
        cb_kunde_vorname.setItems(kunden_list);
        
    }
       

}
