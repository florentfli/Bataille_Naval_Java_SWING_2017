import java.util.Scanner;

public class Player {

    static final Scanner input = new Scanner(System.in);

    private int idPlayer = 0;
    private int nbTorpille;
    private String name;
    private Map map;
    private Map mapOponment;
    private Map mapCheck;
    private Boat[] tabBoatPlayer;

    public Player(String name) {
        this.name = name;
    }

    public Player() {
        System.out.println("Name player : ");
        this.name = input.next();
    }

    public void initPlayer(int sizeMap) {
        map = new Map(sizeMap);
        mapOponment = new Map(sizeMap);

        sizeMap += 2;
        mapCheck = new Map(sizeMap);
    }

    public void convertToTrueMap() {
        for (int i = 1; i < mapCheck.getWidthX() - 1; i++) {
            for (int j = 1; j < mapCheck.getLengthY() - 1; j++) {
                map.getTabMapCase()[i - 1][j - 1] = mapCheck.getTabMapCase()[i][j];
            }
        }
    }

    public int getNbTorpille() {
        return nbTorpille;
    }

    public void setNbTorpille(int nbTorpille) {
        this.nbTorpille = nbTorpille;
    }

    public Map getMapCheck() {
        return mapCheck;
    }

    public void setMapCheck(Map mapCheck) {
        this.mapCheck = mapCheck;
    }

    public String toString() {
        return "\n\t\t" + this.name;
    }

    public Map getMap() {
        return map;
    }

    public void setMap(Map map) {
        this.map = map;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boat[] getTabBoatPlayer() {
        return tabBoatPlayer;
    }

    public void setTabBoatPlayer(Boat[] tabBoatPlayer) {
        this.tabBoatPlayer = tabBoatPlayer;
    }

    public static Scanner getInput() {
        return input;
    }

    public int getIdPlayer() {
        return idPlayer;
    }

    public void setIdPlayer(int idPlayer) {
        this.idPlayer = idPlayer;
    }

    public Map getMapOponment() {
        return mapOponment;
    }

    public void setMapOponment(Map mapOponment) {
        this.mapOponment = mapOponment;
    }


}

