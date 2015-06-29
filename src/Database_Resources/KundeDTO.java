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
public class KundeDTO {

    private int id;
    private String vorname;
    private String nachname;
    private String plz;
    private String ort;
    private String strasse;
    private String email;
    private String telNr;

    /**
     *
     * Konstruktor für Instanzen die aus der DB gelesen werden.
     * @param id
     * @param vorname
     * @param nachname
     * @param plz
     * @param ort
     * @param strasse
     * @param email
     * @param telNr
     */
    public KundeDTO(int id,
            String vorname,
            String nachname,
            String plz,
            String ort,
            String strasse,
            String email,
            String telNr) {

        this.id = id;
        this.vorname = vorname;
        this.nachname = nachname;
        this.plz = plz;
        this.ort = ort;
        this.strasse = strasse;
        this.email = email;
        this.telNr = telNr;

    }
    
    /**
     *
     * Konstruktor für Instanzen welche in die DB geschrieben werden.
     * @param vorname
     * @param nachname
     * @param plz
     * @param ort
     * @param strasse
     * @param email
     * @param telNr
     */
    public KundeDTO(String vorname,
            String nachname,
            String plz,
            String ort,
            String strasse,
            String email,
            String telNr) {

        this.vorname = vorname;
        this.nachname = nachname;
        this.plz = plz;
        this.ort = ort;
        this.strasse = strasse;
        this.email = email;
        this.telNr = telNr;

    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the vorname
     */
    public String getVorname() {
        return vorname;
    }

    /**
     * @param vorname the vorname to set
     */
    public void setVorname(String vorname) {
        this.vorname = vorname;
    }

    /**
     * @return the nachname
     */
    public String getNachname() {
        return nachname;
    }

    /**
     * @param nachname the nachname to set
     */
    public void setNachname(String nachname) {
        this.nachname = nachname;
    }

    /**
     * @return the plz
     */
    public String getPlz() {
        return plz;
    }

    /**
     * @param plz the plz to set
     */
    public void setPlz(String plz) {
        this.plz = plz;
    }

    /**
     * @return the ort
     */
    public String getOrt() {
        return ort;
    }

    /**
     * @param ort the ort to set
     */
    public void setOrt(String ort) {
        this.ort = ort;
    }

    /**
     * @return the strasse
     */
    public String getStrasse() {
        return strasse;
    }

    /**
     * @param strasse the strasse to set
     */
    public void setStrasse(String strasse) {
        this.strasse = strasse;
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return the telNr
     */
    public String getTelNr() {
        return telNr;
    }

    /**
     * @param telNr the telNr to set
     */
    public void setTelNr(String telNr) {
        this.telNr = telNr;
    }

}
