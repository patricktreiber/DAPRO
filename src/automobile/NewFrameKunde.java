/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package automobile;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author Sven
 */
public class NewFrameKunde extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        //conn = new DBConn();
        
        Parent parent = FXMLLoader.load(getClass().getResource("/automobile/AddKunde.fxml"));
        
        Scene sence = new Scene(parent);
        stage.setTitle("Kunden verwalten");
        stage.setScene(sence);
        stage.show();
    
    
    }
    
}
