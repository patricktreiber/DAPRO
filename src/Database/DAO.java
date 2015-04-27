/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Patrick
 */
public class DAO implements DAOInterface{
    
    private Connection connection;
    // Diese Eintraege werden zum
    // Verbindungsaufbau benoetigt.
    private static final String hostname = "localhost";
    private static final String port = "3306";
    // final String url = "fbe-neptun.hs-weingarten.de";
    private static final String dbname = "automobile";
    private static final String user = "root";
    private static final String password = "0000";
    
    
    public DAO(){
        this.connection = createConnection();
    }
    
    private Connection createConnection(){
        try {
                        System.out.println("* Treiber laden");
			Class.forName("com.mysql.jdbc.Driver");
			System.out.println("* Verbindung aufbauen");
			String url = "jdbc:mysql://" + hostname + ":" + port + "/" + dbname;
			this.connection = DriverManager.getConnection(url, user, password);
			System.out.println("* Verbindung aufgebaut");
                        return this.connection;
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    return null;
    }
   
    @Override
    public Set<AutomodellDTO> getAutomodelle(String bezeichnung, 
                                                 String hersteller, 
                                                 int art, 
                                                 int sitzpl, 
                                                 String treibstoff)
    {
        HashSet modelle = new HashSet<AutomodellDTO>();
        //Build String for SQL Statement
        String sql = "SELECT * FROM automodell WHERE ";
        
        if(bezeichnung != null){
            sql = sql + "Bezeichnung = ? ";
        }
        if(hersteller != null){
            if(sql.equals("SELECT * FROM automodell WHERE ")){
            sql = sql + "Hersteller = ? ";
            }else{
                sql = sql + "AND Hersteller = ? ";
            }
        }
        if(art > 0){
            if(sql.equals("SELECT * FROM automodell WHERE ")){
            sql = sql + "Autoart = ? ";
            }else{
                sql = sql + "AND Autoart = ? ";
            }
        }
        if(sitzpl > 0){
            if(sql.equals("SELECT * FROM automodell WHERE ")){
            sql = sql + "Sitzplaetze = ? ";
            }else{
                sql = sql + "AND Sitzplaetze = ? ";
            }
        }
        if(!treibstoff.equals("leer")){
            if(sql.equals("SELECT * FROM automodell WHERE ")){
            sql = sql + "Treibstoff = ? ";
            }else{
                sql = sql + "AND Treibstoff = ? ";
            }
        }
        System.out.println("SQL-Statement: " + sql);
        //Set parameter for PreparedStatement
        try {
            PreparedStatement stmt = this.connection.prepareStatement(sql);
            int count = 0;
            if(bezeichnung != null){
            stmt.setString(++count, bezeichnung);
            }
            if(hersteller != null){
            stmt.setString(++count, hersteller);
            System.out.println("PrepStmt-Index = " + count);
            }
            if(art > 0){
            stmt.setInt(++count, art);
            }
            if(sitzpl > 0){
            stmt.setInt(++count, sitzpl);
            }
            if(!treibstoff.equals("leer")){
            stmt.setString(++count, treibstoff);
            }
            
            ResultSet rs = stmt.executeQuery();
            
            while(rs.next()){
                
                modelle.add(new AutomodellDTO(rs.getInt("ID"),
                                                rs.getString("Bezeichnung"), 
                                                rs.getString("Hersteller"),
                                                rs.getInt("Autoart"),
                                                rs.getInt("Sitzplaetze"),
                                                rs.getInt("kw"),
                                                rs.getString("Treibstoff"),
                                                rs.getDouble("PreisTag"),
                                                rs.getDouble("PreisKM"),
                                                rs.getInt("Achsen"),
                                                rs.getInt("Ladevolumen"),
                                                rs.getInt("Zuladung"),
                                                rs.getString("Fuehrerschein")));
            }
            
            return modelle;
            
        } catch (SQLException ex) {
            Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return null;
    }
    
    @Override
   public Set<String> getBezeichnungTitles(){
       HashSet titles = new HashSet<String>();
       titles = (HashSet) executeTitlesQuery("Bezeichnung");
       return titles;
   }
   
    @Override
   public Set<String> getHerstellerTitles(){
       HashSet titles = new HashSet<String>();
       titles = (HashSet) executeTitlesQuery("Hersteller");
       return titles;
   }
   
    @Override
   public Set<String> getAutoartTitles(){
       HashSet titles = new HashSet<String>();
       titles = (HashSet) executeTitlesQuery("Autoart");
       return titles;
   }
   
    @Override
   public Set<String> getTreibstoffTitles(){
       HashSet titles = new HashSet<String>();
       titles = (HashSet) executeTitlesQuery("Treibstoff");
       return titles;
   }
   
   public Set<String> executeTitlesQuery(String title){
            HashSet titles = new HashSet<String>();
       String sql = "SELECT " + title + " FROM automodell";
        try {
            ResultSet rs = this.connection.createStatement().executeQuery(sql);
            while(rs.next()){
                titles.add(rs.getString(title));
            }
            
            return titles;
           
        } catch (SQLException ex) {
            Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);
        }
   
    return null;
   }

    @Override
    public Set<String> getBezeichnungByHersteller(String hersteller) {
       HashSet titles = new HashSet<String>();
        
       String sql = "SELECT Bezeichnung FROM automodell WHERE Hersteller = '" + hersteller +"'";
      
        try {
           
            ResultSet rs = this.connection.createStatement().executeQuery(sql);
            while(rs.next()){
                titles.add(rs.getString("Bezeichnung"));
            }
            
            return titles;
           
        } catch (SQLException ex) {
            Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);
        }
   
    return null;
    }

    /*
    * Under construction
    * 
    */
    @Override
    public void addReservierung(int kID, int aID, String startDate, String endDate) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        
        //Date date = new Date();
        //Timestamp tstp = new Timestamp(date.);
        
        String sql = "INSERT INTO `automobile`.`reservierung` (`ID`, `KundeID`, `ModellID`, `Beginn`, `Ende`) "
                    + "VALUES (NULL, ?, ?, ?, ?)";
        
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, aID);
            stmt.setInt(2, aID);
           // stmt.setTimestamp(3, new Timestamp());
        } catch (SQLException ex) {
            Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    
    }

    @Override
    public Set<KundeDTO> getKundeDTObyName(String vorname, String nachname) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        String sql = "SELECT * FROM `kunde` WHERE Vorname = ? AND Nachname = ?";
        Set<KundeDTO> k = new HashSet<KundeDTO>();
        try {
            PreparedStatement stmt = this.connection.prepareStatement(sql);
            stmt.setString(1, vorname);
            stmt.setString(2, nachname);
            ResultSet rs = stmt.executeQuery();
            
            while(rs.next()){
                k.add(new KundeDTO(rs.getInt("ID"),
                                   rs.getString("Vorname"),
                                   rs.getString("Nachname"),
                                   rs.getString("PLZ"),
                                   rs.getString("Ort"),
                                   rs.getString("Strasse"),
                                   rs.getString("EMail"),
                                   rs.getString("TelNr")));
            }
            return k;
        } catch (SQLException ex) {
            Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public KundeDTO getKundebyID(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void addKundeDTO(KundeDTO kundeDTO) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Set<KundeDTO> getAllKunden() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
