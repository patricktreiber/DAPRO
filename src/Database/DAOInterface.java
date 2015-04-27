/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Database;

import java.util.Date;
import java.util.Set;

/**
 *
 * @author Patrick
 */
public interface DAOInterface {
    
   Set<AutomodellDTO> getAutomodelle( String bezeichnung, 
                                          String hersteller, 
                                          int art, 
                                          int sitzpl, 
                                          String treibstoff);
   Set<String> getBezeichnungByHersteller(String hersteller);
   Set<String> getBezeichnungTitles();
   Set<String> getHerstellerTitles();
   Set<String> getAutoartTitles();
   Set<String> getTreibstoffTitles();
   void setReservierung(int kID, int aID, String startDate, String endDate);
    
}
