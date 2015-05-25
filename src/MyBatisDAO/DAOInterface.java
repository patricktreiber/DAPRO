/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MyBatisDAO;

import Database.Resources.KundeDTO;

/**
 *
 * @author Patrick
 */
public interface DAOInterface {
    
    public KundeDTO getKundebyID(int id);
    
}
