/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 
package test;

import Database.Resources.KundeDTO;
import MyBatisDAO.DAOInterface;
import java.io.InputStream;
import javax.annotation.Resources;
import org.apache.ibatis.session.SqlSession;
import org.junit.Before;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 *
 * @author Patrick
 
public class testMyBatis {

    private static SqlSessionFactory sessionFac = null;
    private static InputStream reader;
    private static String CONFIGURATION_FILE = "MyBatisDAO/config.xml";

    @Before
    public void setUp() {
        System.out.println("Session setup");
        reader = Resources.class.getResourceAsStream(CONFIGURATION_FILE);
        sessionFac = new SqlSessionFactoryBuilder().build(reader);
        System.out.println("Session available");
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}

    @Test
    public void testgetKundebyID() {
        SqlSession session = sessionFac.openSession();
        try {
            DAOInterface mapper = session.getMapper(DAOInterface.class);
            KundeDTO k = mapper.getKundebyID(1);
            assertEquals("Patrick", k.getVorname());
        } finally {
            session.close();
        }
    }

}*/
