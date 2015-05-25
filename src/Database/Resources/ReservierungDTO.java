/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Database.Resources;

import java.sql.Timestamp;

/**
 *
 * @author Patrick
 */
public class ReservierungDTO {

    private int r_ID;
    private int kundeID;
    private int modellID;
    private Timestamp startDatum;
    private Timestamp endeDatum;
    
    /**
     *
     * Konstruktor für Instanzen die aus der DB gelesen werden.
     * @param id
     * @param kunde
     * @param modell
     * @param start
     * @param ende
     */
    public ReservierungDTO(int id,
            int kunde,
            int modell,
            Timestamp start,
            Timestamp ende) {
        this.r_ID = id;
        this.kundeID = kunde;
        this.modellID = modell;
        this.startDatum = start;
        this.endeDatum = ende;
    }
    
    /**
     *
     * Konstruktor für Instanzen die in die DB geschrieben werden.
     * @param kunde
     * @param modell
     * @param start
     * @param ende
     */
    public ReservierungDTO(int kunde,
            int modell,
            Timestamp start,
            Timestamp ende) {
        this.kundeID = kunde;
        this.modellID = modell;
        this.startDatum = start;
        this.endeDatum = ende;
    }

    /**
     * @return the r_ID
     */
    public int getR_ID() {
        return r_ID;
    }

    /**
     * @return the kunde
     */
    public int getKunde() {
        return kundeID;
    }

    /**
     * @return the modell
     */
    public int getModell() {
        return modellID;
    }

    /**
     * @return the startDatum
     */
    public Timestamp getStartDatum() {
        return startDatum;
    }

    /**
     * @return the endeDatum
     */
    public Timestamp getEndeDatum() {
        return endeDatum;
    }

}
