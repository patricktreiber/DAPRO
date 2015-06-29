/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Database_Resources;

/**
 *
 * @author Patrick
 */
public class AutoartDTO {

    private int id;

    private String art;

    public AutoartDTO(int id, String art) {
        this.id = id;
        this.art = art;
    }

    public int getID() {
        return id;
    }

    public String getArt() {
        return art;
    }

}
