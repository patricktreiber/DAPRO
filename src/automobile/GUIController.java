/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package automobile;

import Database.AutomodellDTO;
import Database.DAO;
import Database.DAOInterface;
import java.awt.event.ActionEvent;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.Set;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;

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
    
    @FXML
    private ComboBox<String> cb_bezeichnung;
    
    @FXML
    private ComboBox<String> cb_hersteller;
    
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
        her_list.addAll(this.dao.getHerstellerTitles());
        bez_list.addAll(this.dao.getBezeichnungTitles());
        cb_bezeichnung.setItems(bez_list);
        cb_hersteller.setItems(her_list);
        
    //Listener für die Combobox-Auswahl    
    cb_hersteller.setOnAction((event) -> {
        String herst = cb_hersteller.getSelectionModel().getSelectedItem();
        System.out.println("ComboBox Action (selected: " + herst + ")");
        bez_list.clear();
        bez_list.addAll(this.dao.getBezeichnungByHersteller(herst));
        cb_bezeichnung.setItems(bez_list);
        
    });
    } 
       
        @FXML
        public void buttonSearch(){
           
        String bezeichnung; 
        String hersteller;
        int art = 0; 
        int sitzpl = 0; 
        String treibstoff = "leer";
        
        
        hersteller = cb_hersteller.getValue();
        System.out.println("Hersteller :" + hersteller); 
        bezeichnung = cb_bezeichnung.getValue();
        System.out.println("Bezeichnung :" + bezeichnung); 
        
        Set<AutomodellDTO> modelle = dao.getAutomodelle(bezeichnung, hersteller, art, sitzpl, treibstoff);
        
        for(AutomodellDTO a : modelle){
            a.showAutoModellOnConsole();
        }
        
        System.out.println(".............................");
        System.out.println("Titel der Autohersteller");
        Set<String> titles = dao.getHerstellerTitles();
        for(String s : titles)
            System.out.println(s);
        System.out.println("Ende");
            
                       
        }
        
        
    
    
    
    
}
