/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MyBatisDAO;

import Database_Resources.AutomodellDTO;
import java.util.List;
import java.util.Set;

/**
 *
 * @author Patrick
 */
public interface DAOInterface {
    
    List<AutomodellDTO> getAutomodelle(String hersteller,
            String bezeichnung,
            int art,
            int sitzplaetze,
            String treibstoff);
    
}
