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
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author Patrick
 */
public interface DAOInterface {

    Set<AutomodellDTO> getAutomodelle(String hersteller,
            String bezeichnung,
            int art,
            int sitzpl,
            String treibstoff);

    Set<String> getBezeichnungByHersteller(String hersteller);

    Set<String> getTitles(String title);

    Set<AutoartDTO> getAllAutoarten();

    Set<KundeDTO> getAllKunden();

    Set<KundeDTO> getKundeDTObyName(String vorname, String nachname);

    Set<ReservierungDTO> getAllReservierungen();

    Set<ReservierungDTO> getReservierungByIDs(int k_ID, int m_ID);

    KundeDTO getKundeDTObyID(int id);

    void addKundeDTO(KundeDTO kundeDTO);

    void addReservierung(int kID, int aID, Timestamp startDate, Timestamp endDate);

    void deleteKundeDTOByID(int id);

    void deleteReservierungByID(int id);

}
