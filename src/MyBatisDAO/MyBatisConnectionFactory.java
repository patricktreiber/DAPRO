/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MyBatisDAO;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Reader;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

/**
 *
 * @author Patrick
 */
public class MyBatisConnectionFactory {

    private static SqlSessionFactory sqlSessionFactory;

    static {

        try {
            String resource = "MyBatisDAO/config.xml";
            Reader reader = null;
            reader = Resources.getResourceAsReader(resource);
            if (sqlSessionFactory == null) {
                sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
            }
        } catch (IOException ex) {
            Logger.getLogger(MyBatisConnectionFactory.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public static SqlSessionFactory getSqlSessionFactory() {
        return sqlSessionFactory;
    }
}
