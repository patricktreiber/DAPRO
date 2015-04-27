/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package automobile;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.fxml.FXML;


/**
 *
 * @author Sven
 */
public class DBConn {
    
    private Connection connection;
    private GUIController gui;

    
    // Diese Eintraege werden zum
    // Verbindungsaufbau benoetigt.
    private static String hostname = "localhost";
    private static String port = "3306";
    // final String url = "fbe-neptun.hs-weingarten.de";
    private static String dbname = "automobile";
    private static String user = "root";
    private static String password = "0000";
    
    String dbURL = "jdbc:mysql://" + hostname + ":" + port + "/" + dbname;

    
    DBConn(){
        createConnection();
        gui = new GUIController();
    }
    
    public Connection createConnection() {

		try {
			System.out.println("* Treiber laden");
			Class.forName("com.mysql.jdbc.Driver");
			System.out.println("* Verbindung aufbauen");
			String url = "jdbc:mysql://" + hostname + ":" + port + "/" + dbname;
			connection = DriverManager.getConnection(url, user, password);
			System.out.println("* Verbindung aufgebaut");
		} catch (Exception e) {
			System.err.println("Unable to load driver.");
			e.printStackTrace();
		}

		return  connection;
	}
	
	public Connection closeConnection(){
		try {
			connection.close();
			System.out.println("Verbindung geschlossen");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return connection;
	}
        
        public void selectAutomodell(String hersteller) {
		
		String sql = "SELECT * FROM Automodell WHERE Hersteller = ? AND Bezeichnung = ?";

		try {
			PreparedStatement stmt = connection.prepareStatement(sql);
                        
			stmt.setString(1, hersteller);
                        //stmt.setString(2, bezeichnung);
			ResultSet rs = stmt.executeQuery();
			
			while(rs.next()){
				String res = rs.getString("Bezeichnung");
				System.out.println("Test"+res);
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 

	}
        

        
   
        

}