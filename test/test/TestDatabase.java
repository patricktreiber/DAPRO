/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import Database_Resources.AutomodellDTO;
import Database_DBAccess.DAO;
import Database_DBAccess.DAOInterface;
import Database_Resources.KundeDTO;
import Database_Resources.ReservierungDTO;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Patrick
 */
public class TestDatabase {

    private DAOInterface dao;
    private Timestamp start_ts;
    private Timestamp stop_ts;

    @Before
    public void setUp() {

        this.dao = new DAO();

        String time = "18:00";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate ldate = LocalDate.parse("2015-07-01", formatter);
        String sdate = ldate.toString();
        ldate = LocalDate.parse("2015-07-02", formatter);
        String edate = ldate.toString();

        String start_datetime = sdate + " " + time + ":00.0";
        String stop_datetime = edate + " " + time + ":00.0";
        //String -> Timestamp
        this.start_ts = Timestamp.valueOf(start_datetime);
        this.stop_ts = Timestamp.valueOf(stop_datetime);

    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
    @Test
    public void testgetAutomodelleVW() {
        String hersteller = "VW";
        String bezeichung = null;
        String treibstoff = null;
        int art = 0;
        int sitzpl = 0;
        Set<AutomodellDTO> set = this.dao.getAutomodelle(hersteller, bezeichung, art, sitzpl, treibstoff);

        assertEquals(4, set.size());
    }

    @Test
    public void testgetAutomodelle() {
        String hersteller = "VW";
        String bezeichung = null;
        String treibstoff = "Diesel";
        int art = 0;
        int sitzpl = 0;
        Set<AutomodellDTO> set = this.dao.getAutomodelle(hersteller, bezeichung, art, sitzpl, treibstoff);

        assertEquals(1, set.size());
    }

    @Test
    public void testgetTitlesGolf() {
        String hersteller = null;
        String bezeichung = "Golf";
        String treibstoff = null;
        int art = 0;
        int sitzpl = 0;
        Set<AutomodellDTO> set = this.dao.getAutomodelle(hersteller, bezeichung, art, sitzpl, treibstoff);

        assertEquals(3, set.size());

    }

    @Test
    public void testgetAllKunden() {
        Set<KundeDTO> set = this.dao.getAllKunden();
        assertEquals(1, set.size());
    }

    @Test
    public void testgetBezeichnungByHersteller() {
        Set<String> set = new HashSet(Arrays.asList("Golf FSI", "Golf Variant TDI", "Golf", "Sharan"));
        assertEquals(set, this.dao.getBezeichnungByHersteller("VW"));
    }

    @Test
    public void testKundeDTObyName() {
        assertEquals(1, this.dao.getKundeDTObyName("Patrick", "Treiber").size());
    }

    @Test
    public void testKundeDTObyID() {
        assertEquals("Patrick", this.dao.getKundeDTObyID(7).getVorname());
    }

    @Test
    public void testaddKundeDTOAndDelete() {

        KundeDTO neu_kunde = new KundeDTO("paddy", "treiber", "88250", "Weingarten", "Str", "a@b", "12345679");
        this.dao.addKundeDTO(neu_kunde);

        Set<KundeDTO> set = this.dao.getKundeDTObyName("paddy", "treiber");
        assertTrue(set.size() > 0);

        int id = 0;
        for (KundeDTO k : set) {
            id = k.getId();
        }
        this.dao.deleteKundeDTOByID(id);
        set = this.dao.getKundeDTObyName("paddy", "treiber");
        KundeDTO delete_kunde = null;
        for (KundeDTO k : set) {
            assertFalse(k.getVorname().equals("paddy"));
        }
    }

    @Test
    public void testaddReservierungAndDelete() {
        this.dao.addReservierung(1, 2, this.start_ts, this.stop_ts);
        Set<ReservierungDTO> set = this.dao.getReservierungByIDs(1, 2);
        assertEquals(1, set.size());
        int id = 0;
        for(ReservierungDTO res : set){
            id = res.getR_ID();
        }
        
        this.dao.deleteReservierungByID(id);
        set = this.dao.getReservierungByIDs(1, 2);
        assertTrue(set.isEmpty());
    }
}
