/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Database_Resources;

import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author Patrick
 */
public class AutoartenSingleton {

    private static AutoartenSingleton instance;
    
    private Set<AutoartDTO> autoarten;

    public void addAutoarten(Set<AutoartDTO> autoarten) {
        this.autoarten = autoarten;
    }

    public int getIDbyArt(String art) {

        for (AutoartDTO autoart : this.autoarten) {

            if (autoart.getArt().equals(art)) {
                return autoart.getID();
            }

        }
        return -1;
    }

    public Set<String> getStringTitles() {
        Set<String> titles = new HashSet<String>();
        for (AutoartDTO art : this.autoarten) {
            titles.add(art.getArt());
        }
        return titles;
    }

    public static AutoartenSingleton getInstance() {
        if (AutoartenSingleton.instance == null) {
            AutoartenSingleton.instance = new AutoartenSingleton();
        }
        return AutoartenSingleton.instance;
    }

}
