/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.*/
package test;

import Database_Resources.AutomodellDTO;
import MyBatisDAO.DAOInterface;
import MyBatisDAO.MyBatisConnectionFactory;
import java.util.List;
import java.util.Set;
import org.apache.ibatis.session.SqlSession;
import org.junit.Before;
import org.apache.ibatis.session.SqlSessionFactory;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 *
 * @author Patrick
 */
public class testMyBatis {

    private SqlSessionFactory connection;

    @Before
    public void setUp() {
        System.out.println("Session setup");
        this.connection = MyBatisConnectionFactory.getSqlSessionFactory();
        System.out.println("Session available");
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
    @Test
    public void testgetAutomodelle() {
        SqlSession session = connection.openSession();
        int art = 0;
        int sitzplaetze = 0;
        String bezeichnung = null;
        String treibstoff = null;
        String hersteller = "VW";
        try {
            DAOInterface dao = session.getMapper(DAOInterface.class);
           List<AutomodellDTO> res = dao.getAutomodelle(hersteller, bezeichnung, art, sitzplaetze, treibstoff);
           
           for(AutomodellDTO auto : res){
               System.out.println("Ergebnis: " + auto.getHersteller());
           }
       
           assertEquals(4, res.size());
           
           /*
           *String hersteller,
            String bezeichnung,
            int art,
            int sitzpl,
            String treibstoff);
    
           */

        } finally {

            session.close();

        }
    }

}
