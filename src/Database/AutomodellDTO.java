/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Database;

/**
 *
 * @author Patrick
 */
public class AutomodellDTO {
    
    private int id;
    private String bezeichnung;
    private String hersteller;
    private int autoart;
    private int sitzplaetze;
    private int kw;
    private String treibstoff;
    private double preisTag;
    private double preisKM;
    private int achsen;
    private int ladevolumen;
    private int zuladung;
    private String fuehrerscheinKlasse;
    
/**
 * 
 * Constructor for AutoDTO Class 
 */    
    AutomodellDTO(
            int id,
            String bezeichnung, 
            String hersteller, 
            int autoart, 
            int sitzplaetze, 
            int kw, 
            String treibstoff,
            double preisTag,
            double preisKM,
            int achsen,
            int ladevolumen,
            int zuladung,
            String fuehrerscheinKlasse)
        {
        this.id = id;
        this.bezeichnung = bezeichnung; 
        this.hersteller = hersteller; 
        this.autoart = autoart; 
        this.sitzplaetze = sitzplaetze; 
        this.kw = kw; 
        this.treibstoff = treibstoff;
        this.preisTag = preisTag;
        this.preisKM = preisKM;
        this.achsen = achsen;
        this.ladevolumen = ladevolumen;
        this.zuladung = zuladung;
        this.fuehrerscheinKlasse = fuehrerscheinKlasse;
        
    }

    AutomodellDTO() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    /**
     *Ausgabe der Automodelle auf der Konsole
     */
    public void showAutoModellOnConsole(){
    System.out.println("Hersteller: " + this.hersteller); 
    System.out.println("Bezeichnung: " + this.bezeichnung); 
    System.out.println("Autoart: " + this.autoart); 
    System.out.println("Sitzplaetze: " + this.sitzplaetze); 
    System.out.println("KW: " + this.kw); 
    System.out.println("Treibstoff: " + this.treibstoff);
    System.out.println("PreisTag: " + this.preisTag);
    System.out.println("PreisKM: " + this.preisKM);
    System.out.println("Achsen: " + this.achsen);
    System.out.println("Ladevolumen: " + this.ladevolumen);
    System.out.println("Zuladung: " + this.zuladung);
    System.out.println("Fuehrerscheinklasse: " + this.fuehrerscheinKlasse);
    System.out.println(".............................................");
    }

    /**
     * @return the id
     */
    public int getID() {
        return id;
    }
    
    /**
     * @return the bezeichnung
     */
    public String getBezeichnung() {
        return bezeichnung;
    }

    /**
     * @param bezeichnung the bezeichnung to set
     */
    public void setBezeichnung(String bezeichnung) {
        this.bezeichnung = bezeichnung;
    }

    /**
     * @return the hersteller
     */
    public String getHersteller() {
        return hersteller;
    }

    /**
     * @param hersteller the hersteller to set
     */
    public void setHersteller(String hersteller) {
        this.hersteller = hersteller;
    }

    /**
     * @return the autoart
     */
    public int getAutoart() {
        return autoart;
    }

    /**
     * @param autoart the autoart to set
     */
    public void setAutoart(int autoart) {
        this.autoart = autoart;
    }

    /**
     * @return the sitzplaetze
     */
    public int getSitzplaetze() {
        return sitzplaetze;
    }

    /**
     * @param sitzplaetze the sitzplaetze to set
     */
    public void setSitzplaetze(int sitzplaetze) {
        this.sitzplaetze = sitzplaetze;
    }

    /**
     * @return the kw
     */
    public int getKw() {
        return kw;
    }

    /**
     * @param kw the kw to set
     */
    public void setKw(int kw) {
        this.kw = kw;
    }

    /**
     * @return the treibstoff
     */
    public String getTreibstoff() {
        return treibstoff;
    }

    /**
     * @param treibstoff the treibstoff to set
     */
    public void setTreibstoff(String treibstoff) {
        this.treibstoff = treibstoff;
    }

    /**
     * @return the preisTag
     */
    public double getPreisTag() {
        return preisTag;
    }

    /**
     * @param preisTag the preisTag to set
     */
    public void setPreisTag(double preisTag) {
        this.preisTag = preisTag;
    }

    /**
     * @return the preisKM
     */
    public double getPreisKM() {
        return preisKM;
    }

    /**
     * @param preisKM the preisKM to set
     */
    public void setPreisKM(double preisKM) {
        this.preisKM = preisKM;
    }

    /**
     * @return the achsen
     */
    public int getAchsen() {
        return achsen;
    }

    /**
     * @param achsen the achsen to set
     */
    public void setAchsen(int achsen) {
        this.achsen = achsen;
    }

    /**
     * @return the ladevolumen
     */
    public int getLadevolumen() {
        return ladevolumen;
    }

    /**
     * @param ladevolumen the ladevolumen to set
     */
    public void setLadevolumen(int ladevolumen) {
        this.ladevolumen = ladevolumen;
    }

    /**
     * @return the zuladung
     */
    public int getZuladung() {
        return zuladung;
    }

    /**
     * @param zuladung the zuladung to set
     */
    public void setZuladung(int zuladung) {
        this.zuladung = zuladung;
    }

    /**
     * @return the fuehrerscheinKlasse
     */
    public String getFuehrerscheinKlasse() {
        return fuehrerscheinKlasse;
    }

    /**
     * @param fuehrerscheinKlasse the fuehrerscheinKlasse to set
     */
    public void setFuehrerscheinKlasse(String fuehrerscheinKlasse) {
        this.fuehrerscheinKlasse = fuehrerscheinKlasse;
    }
    
}
