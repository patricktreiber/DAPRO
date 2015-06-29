/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Database_DBAccess;

import Database_Resources.AutoartDTO;
import Database_Resources.KundeDTO;
import Database_Resources.AutomodellDTO;
import Database_Resources.ReservierungDTO;
import java.sql.Connection;
import static java.sql.Connection.TRANSACTION_SERIALIZABLE;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Patrick
 */
public class DAO implements DAOInterface {

    private Connection connection;
    // Diese Eintraege werden zum
    // Verbindungsaufbau benoetigt.
    private static final String hostname = "localhost";
    private static final String port = "3306";
    // final String url = "fbe-neptun.hs-weingarten.de";
    private static final String dbname = "automobile";
    private static final String user = "root";
    private static final String password = "0000";

    public DAO() {
        //this.connection = createConnection();
    }

    private Connection createConnection() {
        try {
            //System.out.println("* Treiber laden");
            Class.forName("com.mysql.jdbc.Driver");
            //System.out.println("* Verbindung aufbauen");
            String url = "jdbc:mysql://" + hostname + ":" + port + "/" + dbname;
            this.connection = DriverManager.getConnection(url, user, password);
            System.out.println("* Verbindung aufgebaut");
            return this.connection;
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public Set<AutomodellDTO> getAutomodelle(String hersteller,
            String bezeichnung,
            int art,
            int sitzpl,
            String treibstoff) {
        this.connection = createConnection();
        Set modelle = new HashSet<AutomodellDTO>();
        //Build String for SQL Statement
        String sql = "SELECT * FROM automodell WHERE ";

        if (bezeichnung != null) {
            sql = sql + "Bezeichnung LIKE ? ";
        }
        if (hersteller != null) {
            if (sql.equals("SELECT * FROM automodell WHERE ")) {
                sql = sql + "Hersteller = ? ";
            } else {
                sql = sql + "AND Hersteller = ? ";
            }
        }
        if (art > 0) {
            if (sql.equals("SELECT * FROM automodell WHERE ")) {
                sql = sql + "Autoart = ? ";
            } else {
                sql = sql + "AND Autoart = ? ";
            }
        }
        if (sitzpl > 0) {
            if (sql.equals("SELECT * FROM automodell WHERE ")) {
                sql = sql + "Sitzplaetze = ? ";
            } else {
                sql = sql + "AND Sitzplaetze = ? ";
            }
        }
        if (treibstoff != null) {
            if (sql.equals("SELECT * FROM automodell WHERE ")) {
                sql = sql + "Treibstoff = ? ";
            } else {
                sql = sql + "AND Treibstoff = ? ";
            }
        }
        System.out.println("SQL-Statement: " + sql);
        //Set parameter for PreparedStatement
        try {
            PreparedStatement stmt = this.connection.prepareStatement(sql);
            int count = 0;
            if (bezeichnung != null) {
                stmt.setString(++count, bezeichnung + "%");
            }
            if (hersteller != null) {
                stmt.setString(++count, hersteller);
                System.out.println("PrepStmt-Index = " + count);
            }
            if (art > 0) {
                stmt.setInt(++count, art);
            }
            if (sitzpl > 0) {
                stmt.setInt(++count, sitzpl);
            }
            if (treibstoff != null) {
                stmt.setString(++count, treibstoff);
            }
            System.out.println(stmt.toString());
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {

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
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException ex) {
                    Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

        return null;
    }

    @Override
    public Set<String> getTitles(String title) {
        this.connection = createConnection();
        HashSet titles = new HashSet<String>();
        String sql = "SELECT " + title + " FROM automodell";
        try {
            ResultSet rs = this.connection.createStatement().executeQuery(sql);
            while (rs.next()) {
                titles.add(rs.getString(title));
            }

            return titles;

        } catch (SQLException ex) {
            Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException ex) {
                    Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

        return null;
    }

    @Override
    public Set<String> getBezeichnungByHersteller(String hersteller) {
        this.connection = createConnection();
        HashSet titles = new HashSet<String>();

        String sql = "SELECT Bezeichnung FROM automodell WHERE Hersteller = '" + hersteller + "'";

        try {

            ResultSet rs = this.connection.createStatement().executeQuery(sql);
            while (rs.next()) {
                titles.add(rs.getString("Bezeichnung"));
            }

            return titles;

        } catch (SQLException ex) {
            Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException ex) {
                    Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

        return null;
    }

    /**
     * Reservierug zur Datenbank hinzufügen
     *
     * @param kID
     * @param aID
     * @param startDate
     * @param endDate
     */
    @Override
    public void addReservierung(int kID, int aID, Timestamp startDate, Timestamp endDate) {
        /**
         * **************Bsp.: String -> Timestamp**************************
         *
         * String time = this.cb_time.getValue(); LocalDate ldate =
         * datePicker.getValue(); String sdate = ldate.toString();
         * System.out.println("Reservierung" + "\n" + "Datum: " + sdate + "\n" +
         * "Uhrzeit ab: " + time + "Uhr" + "\n"); '2015-05-14 12:00:00' String
         * datetime = sdate + " " + time + ":00.0";
         *
         * String ->Timestamp: Timestamp ts = Timestamp.valueOf(datetime);
         * **************************************************************
         */
        this.connection = createConnection();
        String sql = "INSERT INTO `automobile`.`reservierung` (`ID`, `KundeID`, `ModellID`, `Beginn`, `Ende`) "
                + "VALUES (NULL, ?, ?, ?, ?)";

        try {
            this.connection.setAutoCommit(false);
            DatabaseMetaData dbmd = this.connection.getMetaData();
            if (dbmd.supportsTransactionIsolationLevel(TRANSACTION_SERIALIZABLE)) {
                this.connection.setTransactionIsolation(TRANSACTION_SERIALIZABLE);
                System.out.println("Transaction_Serializable");
            }
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, kID);
            stmt.setInt(2, aID);
            stmt.setTimestamp(3, startDate);
            stmt.setTimestamp(4, endDate);
            stmt.executeUpdate();
            this.connection.commit();
        } catch (SQLException ex) {
            Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);
            try {
                this.connection.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex1);
            }
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException ex) {
                    Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

    }

    /**
     * Returns Set of KundeDTO by vorname and nachname
     *
     * @param vorname
     * @param nachname
     * @return
     */
    @Override
    public Set<KundeDTO> getKundeDTObyName(String vorname, String nachname) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        this.connection = createConnection();
        String sql = "SELECT * FROM `kunde` WHERE Vorname = ? AND Nachname = ?";
        Set<KundeDTO> k = new HashSet<KundeDTO>();
        try {
            PreparedStatement stmt = this.connection.prepareStatement(sql);
            stmt.setString(1, vorname);
            stmt.setString(2, nachname);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
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
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException ex) {
                    Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return null;
    }

    /**
     * Returns KundeDTO by ID
     *
     * @param c_id
     * @return
     */
    @Override
    public KundeDTO getKundeDTObyID(int c_id) {
        this.connection = createConnection();
        String sql = "SELECT * FROM `kunde` WHERE ID = ?";
        try {
            PreparedStatement stmt = this.connection.prepareStatement(sql);
            stmt.setInt(1, c_id);
            KundeDTO k = null;
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                k = new KundeDTO(rs.getInt("ID"),
                        rs.getString("Vorname"),
                        rs.getString("Nachname"),
                        rs.getString("PLZ"),
                        rs.getString("Ort"),
                        rs.getString("Strasse"),
                        rs.getString("EMail"),
                        rs.getString("TelNr"));
            }
            return k;
        } catch (SQLException ex) {
            Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException ex) {
                    Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return null;
    }

    @Override
    public void addKundeDTO(KundeDTO kundeDTO) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        this.connection = createConnection();
        String sql = "INSERT INTO `automobile`.`kunde` (`ID`, `Vorname`, `Nachname`, `PLZ`, `Ort`, `Strasse`, `EMail`, `TelNr`) "
                + "VALUES (NULL, ?, ?, ?, ?, ?, ?, ?)";
        try {
            this.connection.setAutoCommit(false);
            DatabaseMetaData dbmd = this.connection.getMetaData();
            if (dbmd.supportsTransactionIsolationLevel(TRANSACTION_SERIALIZABLE)) {
                this.connection.setTransactionIsolation(TRANSACTION_SERIALIZABLE);
                System.out.println("Transaction_Serializable");
            }
            PreparedStatement stmt = this.connection.prepareStatement(sql);
            stmt.setString(1, kundeDTO.getVorname());
            stmt.setString(2, kundeDTO.getNachname());
            stmt.setString(3, kundeDTO.getPlz());
            stmt.setString(4, kundeDTO.getOrt());
            stmt.setString(5, kundeDTO.getStrasse());
            stmt.setString(6, kundeDTO.getEmail());
            stmt.setString(7, kundeDTO.getTelNr());

            stmt.executeUpdate();
            this.connection.commit();

        } catch (SQLException ex) {
            Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);
            try {
                this.connection.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex1);
            }
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException ex) {
                    Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    @Override
    public Set<KundeDTO> getAllKunden() {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        this.connection = createConnection();
        String sql = "SELECT * FROM `kunde`";
        Set<KundeDTO> kundeSet = new HashSet<KundeDTO>();
        try {
            PreparedStatement stmt = this.connection.prepareStatement(sql);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                kundeSet.add(new KundeDTO(rs.getInt("ID"),
                        rs.getString("Vorname"),
                        rs.getString("Nachname"),
                        rs.getString("PLZ"),
                        rs.getString("Ort"),
                        rs.getString("Strasse"),
                        rs.getString("EMail"),
                        rs.getString("TelNr")));
            }
            return kundeSet;
        } catch (SQLException ex) {
            Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException ex) {
                    Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return null;
    }

    @Override
    public void deleteKundeDTOByID(int id) {
        this.connection = createConnection();
        String sql = "DELETE FROM automobile.kunde WHERE kunde.ID = ?";
        try {
            this.connection.setAutoCommit(false);
            DatabaseMetaData dbmd = this.connection.getMetaData();
            if (dbmd.supportsTransactionIsolationLevel(TRANSACTION_SERIALIZABLE)) {
                this.connection.setTransactionIsolation(TRANSACTION_SERIALIZABLE);
                System.out.println("Transaction_Serializable");
            }
            PreparedStatement stmt = this.connection.prepareStatement(sql);
            stmt.setInt(1, id);
            stmt.executeUpdate();
            this.connection.commit();
        } catch (SQLException ex) {
            Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);
            try {
                this.connection.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex1);
            }
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException ex) {
                    Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    @Override
    public void deleteReservierungByID(int id) {
        this.connection = createConnection();
        String sql = "DELETE FROM automobile.reservierung WHERE reservierung.ID = ?";
        try {
            this.connection.setAutoCommit(false);
            DatabaseMetaData dbmd = this.connection.getMetaData();
            if (dbmd.supportsTransactionIsolationLevel(TRANSACTION_SERIALIZABLE)) {
                this.connection.setTransactionIsolation(TRANSACTION_SERIALIZABLE);
                System.out.println("Transaction_Serializable");
            }
            PreparedStatement stmt = this.connection.prepareStatement(sql);
            stmt.setInt(1, id);
            stmt.executeUpdate();
            this.connection.commit();
        } catch (SQLException ex) {
            Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);
            try {
                this.connection.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex1);
            }
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException ex) {
                    Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    @Override
    public Set<ReservierungDTO> getAllReservierungen() {
        this.connection = createConnection();
        String sql = "SELECT * FROM `reservierung`";
        Set<ReservierungDTO> resSet = new HashSet<ReservierungDTO>();
        try {
            PreparedStatement stmt = this.connection.prepareStatement(sql);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                resSet.add(new ReservierungDTO(rs.getInt("ID"),
                        rs.getInt("KundeID"),
                        rs.getInt("ModellID"),
                        rs.getTimestamp("Beginn"),
                        rs.getTimestamp("Ende")));
            }
            return resSet;
        } catch (SQLException ex) {
            Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException ex) {
                    Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

        return null;
    }

    @Override
    public Set<ReservierungDTO> getReservierungByIDs(int k_ID, int m_ID) {
        this.connection = createConnection();
        String sql = "SELECT * FROM `reservierung` WHERE KundeID = ? AND ModellID = ?";
        try {
            PreparedStatement stmt = this.connection.prepareStatement(sql);
            stmt.setInt(1, k_ID);
            stmt.setInt(2, m_ID);

            Set<ReservierungDTO> resSet = new HashSet<ReservierungDTO>();
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                resSet.add(new ReservierungDTO(rs.getInt("ID"),
                        rs.getInt("KundeID"),
                        rs.getInt("ModellID"),
                        rs.getTimestamp("Beginn"),
                        rs.getTimestamp("Ende")));
            }
            return resSet;
        } catch (SQLException ex) {
            Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException ex) {
                    Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

        return null;
    }

    public Set<AutoartDTO> getAllAutoarten() {
        this.connection = createConnection();
        HashSet autoarten = new HashSet<AutoartDTO>();
        
        String sql = "SELECT * FROM `autoarten`";
        try {
            PreparedStatement stmt = this.connection.prepareStatement(sql);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                autoarten.add(new AutoartDTO(rs.getInt("ID"),
                        rs.getString("Art")));
            }
            return autoarten;

        } catch (SQLException ex) {
            Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException ex) {
                    Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return null;
    }

}
