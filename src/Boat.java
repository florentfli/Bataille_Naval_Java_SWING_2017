import java.util.Scanner;

public class Boat {

    static final Scanner input = new Scanner(System.in);

    private int posX;
    private int posY;
    private boolean isHorizontal=true;
    private boolean isPlacer = false;
    private int length;
    private int health;
    private String type;
    private int id = 0;
    private boolean isCouler;

    public Boat(){
        init();
    }

    public Boat(String type, int id){
        this.type = type;
        switchBoat(type);
        this.health = length;
        this.id = id;
    }
    public Boat(int posX, int posY, boolean isHorizontal, String type, int id){
        this.posX = posX;
        this.posY = posY;
        this.isHorizontal = isHorizontal;
        this.type = type;
        switchBoat(type);
        this.health = length;
        this.id = id;
        this.isCouler = false;
    }

    public void switchBoat(String type){
        if (type.equals("torpilleur") || type.equals("Torpilleur") || type.equals("2")){
            this.length = 2;
        }else{
            if (type.equals("sous-marin") || type.equals("Sous-Marin") || type.equals("3")){
                this.length = 3;
            }else{
                if (type.equals("Contre-Torpilleur") || type.equals("contre-torpilleur") || type.equals("3")){
                    this.length = 3;
                }else{
                    if (type.equals("Croiseur") || type.equals("croiseur") || type.equals("4")){
                        this.length = 4;
                    }else{
                        if (type.equals("Porte-Avion") || type.equals("porte-avion") || type.equals("5")){
                            this.length = 5;
                        }else {
                            System.out.println("ERROR:  Type non EXIXSTENT");
                        }
                    }
                }
            }
        }
    }

    public void init(){
        System.out.println("PosX");
        this.posX = input.nextInt();
        System.out.println("PosY");
        this.posY = input.nextInt();
        System.out.println("orient");
        this.isHorizontal = input.nextBoolean();
        System.out.println("Type ?");
        this.type = input.next();
        switchBoat(type);
    }

    /**
     * Getter & Setter
     * @return
     */
    public int getPosY() {
        return posY;
    }
    public void setPosY(int posY) {
        this.posY = posY;
    }
    public boolean getIsPlacer() {
        return isPlacer;
    }
    public void setPlacer(boolean placer) {
        isPlacer = placer;
    }
    public int getLength() {
        return length;
    }
    public void setLength(int length){
        this.length = length;
    }
    public int getPosX() {
        return posX;
    }
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public void setPosX(int posX) {
        this.posX = posX;
    }
    public boolean getIsHorizontal() {
        return isHorizontal;
    }
    public void setIsHorizontal(boolean isHorizontal) {
        this.isHorizontal = isHorizontal;
    }
    public int getHealth() {
        return health;
    }
    public void setHealth(int health) {
        this.health = health;
    }
    public boolean getIsCouler() {
        return isCouler;
    }
    public void setIsCouler(boolean couler) {
        isCouler = couler;
    }

    public String toString(){
        return "PosX : " + this.posX +
                "\nPosY : " + this.posY +
                "\norientation : " + this.isHorizontal +
                "\nlength : " + this.length +
                "\nhealth : " + this.health +
                "\ntype : " + this.type +
                "\nid : " + this.id;
    }
}

/*
ESSAIE DE REDEFINITION DE LA METHODE EQUALS
       public boolean equalsBoat(Boat boat){
        if(this == boat){
            System.out.println("trouve");
            return true;
        }
        if(this == null){
            System.out.println("pas trouve");
            return false;
        }
        if(this.getClass() != boat.getClass()){
            System.out.println("pas le meme type");
            return false;
        }
        System.out.println("Rien du tout .. ");
        return false;
    }*/