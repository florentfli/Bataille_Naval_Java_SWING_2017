public class Marker {

    private int posX;
    private int poxY;
    private int type;

    /**
     * Constructors
     */
    public Marker(){}

    public Marker(int posX, int poxY, int type) {
        this.posX = posX;
        this.poxY = poxY;
        this.type = type;
    }

    /**
     * Getter & Setter
     * @return
     */
    public int getPosX() {
        return posX;
    }
    public void setPosX(int posX) {
        this.posX = posX;
    }
    public int getPoxY() {
        return poxY;
    }
    public void setPoxY(int poxY) {
        this.poxY = poxY;
    }
    public int getType() {
        return type;
    }
    public void setType(int type) {
        this.type = type;
    }
}
