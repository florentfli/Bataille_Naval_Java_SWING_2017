import java.awt.*;

public class Case {
    private boolean isOccupied;
    private boolean isTargettable;
    private Color caseColor;
    private int idBoat;
    private int coordX;
    private int coordY;
    private boolean isVerif;

    public Case(){
        initCase();
    }

    public Case(boolean isOccupied, Color caseColor, int coordX, int coordY){
        this.isVerif = false;
        this.isOccupied = isOccupied;
        this.caseColor = caseColor;
        this.coordX = coordX;
        this.coordY = coordY;
        this.isTargettable = true;
    }

    public void initCase(){
        this.isVerif=false;
        this.isOccupied = false;
        this.caseColor = Color.BLUE;
        this.isTargettable = true;
    }



    /**
     * Getter & Setter
     */
    public boolean isTargettable() {
        return isTargettable;
    }
    public int getIdBoat() {
        return idBoat;
    }
    public void setIdBoat(int idBoat) {
        this.idBoat = idBoat;
    }
    public boolean getIsOccupied() {
        return isOccupied;
    }
    public void setOccupied(boolean occupied) {
        isOccupied = occupied;
    }
    public boolean getIsTargettable() {
        return isTargettable;
    }
    public void setTargettable(boolean targettable) {
        isTargettable = targettable;
    }
    public Color getCaseColor() {
        return caseColor;
    }
    public void setCaseColor(Color caseColor) {
        this.caseColor = caseColor;
    }
    public int getCoordX() {
        return coordX;
    }
    public void setCoordX(int coordX) {
        this.coordX = coordX;
    }
    public int getCoordY() {
        return coordY;
    }
    public void setCoordY(int coordY) {
        this.coordY = coordY;
    }

    public boolean isVerif() {
        return isVerif;
    }

    public void setVerif(boolean verif) {
        isVerif = verif;
    }
}
