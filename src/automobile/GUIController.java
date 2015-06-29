/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 Test commit
 */
package automobile;

import Database_Resources.AutomodellDTO;
import Database_DBAccess.DAO;
import Database_DBAccess.DAOInterface;
import Database_Resources.AutoartenSingleton;
import Database_Resources.KundeDTO;
import java.net.URL;
import java.sql.Connection;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;

/**
 * FXML Controller class
 *
 * Projekt für Wahlfach Datenbankprogrammierung
 *
 * @author Sven Nagel und Patrick Treiber
 *
 */
public class GUIController implements Initializable {

    private DBConn connection;
    private Connection conn;
    private DAOInterface dao;

    private ObservableList<String> bez_list;
    private ObservableList<String> her_list;
    private ObservableList<String> autoart_list;
    private ObservableList<String> sitz_list;
    private ObservableList<String> treib_list;
    private ObservableList<String> kunden_vorname_list;
    private ObservableList<String> kunden_nachname_list;

    private ObservableList<AutomodellDTO> modell_list;

    private String her;
    private String bez;
    private String hersteller;
    private int aid;

    private String k_nachname;
    private String k_vorname;
    private int kid;
    
    private AutoartenSingleton autoarten;

    @FXML
    private DatePicker start_date;

    @FXML
    private DatePicker ende_date;

    @FXML
    private TextField her_txt;

    @FXML
    private TableView<AutomodellDTO> resultView;

    @FXML
    private ComboBox<String> cb_time;

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
    private Button btn_kunden_add;

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
        this.autoarten = AutoartenSingleton.getInstance();
        this.autoarten.addAutoarten(dao.getAllAutoarten());
        //Create and fill comboboxes with data from database-layer
        bez_list = FXCollections.observableArrayList();
        her_list = FXCollections.observableArrayList();
        autoart_list = FXCollections.observableArrayList();
        sitz_list = FXCollections.observableArrayList();
        treib_list = FXCollections.observableArrayList();
        kunden_vorname_list = FXCollections.observableArrayList();
        kunden_nachname_list = FXCollections.observableArrayList();

        her_list.addAll(this.dao.getTitles("Hersteller"));
        bez_list.addAll(this.dao.getTitles("Bezeichnung"));
        autoart_list.addAll(this.autoarten.getStringTitles());
        sitz_list.addAll(this.dao.getTitles("Sitzplaetze"));
        treib_list.addAll(this.dao.getTitles("Treibstoff"));

        this.start_date.setValue(LocalDate.now());
        this.ende_date.setValue(LocalDate.now());

        this.start_date.setDayCellFactory(getCallBack(start_date));
        this.ende_date.setDayCellFactory(getCallBack(ende_date));

        cb_bezeichnung.setItems(bez_list);
        cb_hersteller.setItems(her_list);
        cb_autoart.setItems(autoart_list);
        cb_sitzplaetze.setItems(sitz_list);
        cb_treibstoff.setItems(treib_list);

        // Timestamp for reservation
        ArrayList<String> myTime = new ArrayList<String>();
        myTime.add("08:00:00");
        myTime.add("09:00:00");
        myTime.add("10:00:00");
        myTime.add("11:00:00");
        myTime.add("12:00:00");
        myTime.add("13:00:00");
        myTime.add("14:00:00");
        myTime.add("15:00:00");
        myTime.add("16:00:00");
        myTime.add("17:00:00");
        cb_time.getItems().addAll(myTime);

        //Tabellenbezeichnungen erzeugen
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

        //Listenerfür die Abhängigkeit bei der Auswahl Hersteller -> Bezeichnung  
        cb_hersteller.setOnAction((event) -> {
            String herst = cb_hersteller.getSelectionModel().getSelectedItem();
            bez_list.clear();
            bez_list.addAll(this.dao.getBezeichnungByHersteller(herst));
            cb_bezeichnung.setItems(bez_list);

        });
    }

    // Listener für die Suche von Automodellen 
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
        treibstoff = cb_treibstoff.getValue();

        if (cb_autoart.getValue() != null) {
            String sArt = cb_autoart.getValue();
            art = this.autoarten.getIDbyArt(sArt);
        }
        if (cb_sitzplaetze.getValue() != null) {
            String sSitzPl = cb_sitzplaetze.getValue();
            sitzpl = Integer.parseInt(sSitzPl);
        }

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

    //Auswahl der ComboBoxen zurücksetzen
    @FXML
    public void buttonRefresh() {

        cb_hersteller.getSelectionModel().clearSelection();
        cb_bezeichnung.getSelectionModel().clearSelection();
        cb_autoart.getSelectionModel().clearSelection();
        cb_sitzplaetze.getSelectionModel().clearSelection();
        cb_treibstoff.getSelectionModel().clearSelection();
    }

    // ausgewählte row in der resultView in die Reservierung übernehmen
    @FXML
    public void getChoose() {

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

    // Kunden aus der Datenbank auslesen 
    @FXML
    public void getKD() {

        cb_kunde_vorname.getItems().clear();

        Set<KundeDTO> kunde_set = new HashSet<KundeDTO>();
        kunde_set = this.dao.getAllKunden();

        for (KundeDTO k : kunde_set) {

            k_nachname = k.getNachname();
            k_vorname = k.getVorname();
            kid = k.getId();

            System.out.println(kid);

        }

        kunden_nachname_list.add(k_nachname);
        kunden_vorname_list.add(k_vorname);

        cb_kunde_nachname.setItems(kunden_nachname_list);
        cb_kunde_vorname.setItems(kunden_vorname_list);

    }

    @FXML
    public void reserverationPrepare() {
        LocalDate sdate = start_date.getValue();
        System.out.println(sdate);

        LocalDate edate = ende_date.getValue();
        System.out.println(edate);

        String time = this.cb_time.getValue();

        String startdate = sdate.toString();
        String endedate = edate.toString();

        System.out.println("Reservierung" + "\n" + "Datum: " + startdate + "\n" + "Uhrzeit ab: " + time + "Uhr" + "\n");

        String datetime_s = sdate + " " + time;
        String datetime_e = edate + " " + time;

        System.out.println(datetime_s);
        System.out.println(datetime_e);

        Timestamp tstart = Timestamp.valueOf(datetime_s);
        Timestamp tende = Timestamp.valueOf(datetime_e);

        if (tstart.after(tende)) {

            System.err.println("ABBRUCH: Start-Datum nach Ende-Datum!");
            return;

        }

        System.out.println(kid);
        System.out.println(aid);

        this.dao.addReservierung(kid, aid, tstart, tende);

    }

    @FXML
    public void kundenAdd() {

        NewFrameKunde kd;
        kd = new NewFrameKunde();

    }

    public Callback<DatePicker, DateCell> getCallBack(DatePicker dP) {

        final Callback<DatePicker, DateCell> dayCellFactory
                = new Callback<DatePicker, DateCell>() {
                    @Override
                    public DateCell call(final DatePicker datePicker) {
                        return new DateCell() {
                            @Override
                            public void updateItem(LocalDate item, boolean empty) {
                                super.updateItem(item, empty);
                                if (item.isBefore(
                                        dP.getValue().plusDays(1))) {
                                    setDisable(true);
                                    setStyle("-fx-background-color: #ffc0cb;");
                                }
                            }
                        };

                    }
                };

        return dayCellFactory;
    }

}
