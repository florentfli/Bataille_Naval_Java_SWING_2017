import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;

import java.awt.*;
import java.util.Arrays;
import java.util.Scanner;

public class Map {

    static final Scanner input = new Scanner(System.in);

    private int widthX;
    private int lengthY;

    private Case[][] tabMapCase;

    /**
     * Constructors
     */
    public Map(){
        initMap();
    }

    public Map(int tailleMap){
        this.widthX = tailleMap;
        this.lengthY = tailleMap;
        initMap();
    }

    public void initMap(){
        tabMapCase = new Case[widthX][lengthY];
        for(int y = 0; y < this.lengthY; y++) {
            for (int x = 0; x < this.widthX; x++) {
                tabMapCase[x][y] = new Case(false, Color.blue, x, y); // a Verif
            }
        }
    }

    @Override
    public String toString() {
        String s = "";
        for (int i = 0; i < this.getWidthX(); i++){
            for (int j = 0; j < this.getLengthY(); j++){
                if (this.getTabMapCase()[i][j].getIsOccupied()){

                    s += " x";
                }else
                    s += " o";
            }
            s += "\n";
        }
        return "Map{\n" +
                "tabMapCase=\n" + s +
                '}';
    }

    /**
     * Getter & Setter
     *
     * @return
     */
    public int getLengthY() {
        return lengthY;
    }
    public void setLengthY(int lengthY) {
        this.lengthY = lengthY;
    }
    public int getWidthX() {
        return widthX;
    }
    public void setWidthX(int widthX) {
        this.widthX = widthX;
    }
    public Case[][] getTabMapCase() {
        return tabMapCase;
    }
    public void setTabMapCase(Case[][] tabMapCase) {
        this.tabMapCase = tabMapCase;
    }
}
