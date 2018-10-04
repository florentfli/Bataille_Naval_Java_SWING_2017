/* private ArrayList<Boat> boatListP1 = new ArrayList();
    private ArrayList<Boat> boatListP2 = new ArrayList();*/

import java.net.UnknownHostException;
import java.util.Scanner;

public class Game {

    static final Scanner input = new Scanner(System.in);

    View2 view;
    private Player player1;
    private Player player2;

    private int nbPlayer = 2;
    private int nbBoat;
    private Boat[] tabBoat;
    private Boat[] tabBoatP1;
    private Boat[] tabBoatP2;

    private int torpilleur = 1;
    private int sousMarin = 1;
    private int contreTorpilleur = 1;
    private int croiseur =1;
    private int porteAvion = 1;

    private boolean isMulti;
    private boolean isClassic;
    private boolean isOnline;
    private boolean isServer;

    private int theme = 1;

    private String ip;

    private String ipAdverse;
    private String ipSelf;
    private String port = "12345";

    public void getSelfIp() {
        try {
            ipSelf = Recevoir.getIPLocale();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    public Game() {
        this.player1 = new Player("Player1");
        getSelfIp();
    }

    public Game(View2 view) {
        this.player1 = new Player();
        //this.player2 = new Player();
        this.view = view;
        //initGameClassic();
    }

    public Game(View2 view, String player1, String player2) {
        this.view = view;
        this.player1 = new Player(player1);
        this.player2 = new Player(player2);
        initGameTest();
    }
/*
    public void initGame() {
        System.out.println("Nb Boat total (for one player)?");
        this.nbBoat = input.nextInt();
        System.out.println("Taille de la map :");
        int sizeMap = input.nextInt();
        player1.initPlayer(sizeMap);
        player2.initPlayer(sizeMap);
        *//*view.testPrintNames.setText("Player 1 : " + player1.getName() +
                " || Player 2 : " + player2.getName());
        view.testPrintNames2.setText("Player 1 : " + player1.getName() +
                " || Player 2 : " + player2.getName());*//*
        initBoatList();

    }*/

    public void initGameClassicReseauHost(Player player) {
        int sizeMap = 10;
        this.getSelfIp();
        this.ipAdverse = Decodage.getIP(Recevoir.recevoir(port));

        this.nbPlayer = 2;

        player.initPlayer(sizeMap);
        this.initBoatListClassic(player);

        Envoyer.envoyer(this.ipAdverse, port, "" + sizeMap + "/" + 1 + "/" + 1 + "/" + 1 + "/" + 1 + "/" + 1);
        /*System.out.println("game:\n" +this+"");
        System.out.println("view:\n"+view + "fin view");*/
    }

    public void initGamePersoReseauHost(Player player, int sizeMap, int nbSousMarin, int nbTorpilleur, int nbContreTorpilleur, int nbCroiseur, int nbPorteAvion) {
        this.getSelfIp();
        this.ipAdverse = Decodage.getIP(Recevoir.recevoir(port));

        this.nbPlayer = 2;

        player.initPlayer(sizeMap);

        initGamePerso(player, sizeMap, nbSousMarin, nbTorpilleur, nbContreTorpilleur, nbCroiseur, nbPorteAvion);
        Envoyer.envoyer(this.ipAdverse, port, "" + sizeMap + "/" + this.torpilleur + "/" + contreTorpilleur + "/" + sousMarin + "/" + croiseur + "/" + porteAvion);
    }

    protected void initPartiReseauClient() {
        System.out.println("en attente de reception..");
        String infoGame = Recevoir.recevoir(this.getPort());
        int sizeMap = Decodage.tailleGrille(infoGame);
        player1.initPlayer(sizeMap);
        this.torpilleur = (Decodage.nombreBateaux(infoGame)[0]);
        this.sousMarin = (Decodage.nombreBateaux(infoGame)[1]);
        this.contreTorpilleur = (Decodage.nombreBateaux(infoGame)[2]);
        this.croiseur = (Decodage.nombreBateaux(infoGame)[3]);
        this.porteAvion = (Decodage.nombreBateaux(infoGame)[4]);
        initGamePerso(this.getPlayer1(), sizeMap, sousMarin, torpilleur, contreTorpilleur, croiseur, porteAvion);
    }

    public void initGameClassic(Player player, int sizeMap) {
        this.nbPlayer = 2;
        player.initPlayer(sizeMap);
        this.initBoatListClassic(player);
    }

    public void initGameClassic(Ordinateur ordi) {
        this.nbPlayer = 2;
        int sizeMap = 10;
        this.player1.initPlayer(sizeMap);
        ordi.initPlayer(sizeMap);
        player1.initPlayer(sizeMap);
        this.initBoatListClassic(ordi);
        this.initBoatListClassic(player1);
    }

    public void initGamePerso(Player player, int sizeMap, int nbSousMarin, int nbTorpilleur, int nbContreTorpilleur, int nbCroiseur, int nbPorteAvion) {
        this.nbPlayer = 2;
        player.initPlayer(sizeMap);
        this.sousMarin = nbSousMarin;
        this.torpilleur = nbTorpilleur;
        this.contreTorpilleur = nbContreTorpilleur;
        this.croiseur = nbCroiseur;
        this.porteAvion = nbPorteAvion;
        this.nbBoat = nbContreTorpilleur + nbCroiseur + nbPorteAvion + nbSousMarin + nbTorpilleur;
        this.addTabBoat(player, nbBoat);
    }


    public void addTabBoat(Player p, int nb) {
        Boat[] tab = new Boat[nb];
        for (int j = 0; j < torpilleur; j++) {
            tab[j] = new Boat("2", j);
        }
        for (int j = torpilleur; j < torpilleur + contreTorpilleur; j++) {
            tab[j] = new Boat("sous-marin", j);
        }
        for (int j = torpilleur + contreTorpilleur; j < torpilleur + contreTorpilleur + sousMarin; j++) {
            tab[j] = new Boat("contre-torpilleur", j);
        }
        for (int j = torpilleur + contreTorpilleur + sousMarin; j < torpilleur + contreTorpilleur + sousMarin + croiseur; j++) {
            tab[j] = new Boat("4", j);
        }
        for (int j = torpilleur + contreTorpilleur + sousMarin + croiseur; j < torpilleur + contreTorpilleur + sousMarin + croiseur + porteAvion; j++) {
            tab[j] = new Boat("5", j);
        }
        p.setTabBoatPlayer(tab);
    }


    public void initBoatListClassic(Player joueur) {
        this.nbBoat = 5;
        joueur.setTabBoatPlayer(new Boat[nbBoat]);

        this.sousMarin++;
        this.torpilleur++;
        this.contreTorpilleur++;
        this.croiseur++;
        this.porteAvion++;

        joueur.getTabBoatPlayer()[0] = new Boat("2", 0);
        joueur.getTabBoatPlayer()[1] = new Boat("sous-marin", 1);
        joueur.getTabBoatPlayer()[2] = new Boat("contre-torpilleur", 2);
        joueur.getTabBoatPlayer()[3] = new Boat("4", 3);
        joueur.getTabBoatPlayer()[4] = new Boat("5", 4);

    }

    /*public void initBoatList() {
        this.tabBoat = new Boat[nbBoat];
        System.out.println("Boats available, please choose " + nbBoat + " boats.");
        for (int i = 0; i < nbBoat; i++) {
            System.out.println("Type of boat n° " + (i + 1) + " : ");
            String type = input.next();
            if (type.equals("torpilleur") || type.equals("Torpilleur") || type.equals("2")) {
                this.torpilleur++;
            } else {
                if (type.equals("sous-marin") || type.equals("Sous-Marin") || type.equals("3")) {
                    this.sousMarin++;
                } else {
                    if (type.equals("Contre-Torpilleur") || type.equals("contre-torpilleur") || type.equals("3")) {
                        this.contreTorpilleur++;
                    } else {
                        if (type.equals("Croiseur") || type.equals("croiseur") || type.equals("4")) {
                            this.croiseur++;
                        } else {
                            if (type.equals("Porte-Avion") || type.equals("porte-avion") || type.equals("5")) {
                                this.porteAvion++;
                            } else {
                                System.out.println("ERROR:  Type non EXIXSTENT");
                            }
                        }
                    }
                }
            }
            this.tabBoat[i] = new Boat(type, i);
        }
        this.tabBoatP1 = tabBoat;
        this.tabBoatP2 = tabBoat;
    }*/

    /**
     * PartieTEST
     */
    final Boat[] tabBoatTest = {
            new Boat("Torpilleur", 1),
            new Boat("Sous-Marin", 2),
            new Boat("Porte-Avion", 5),
    };

    public void initGameTest() {
        this.nbBoat = 3;
        this.tabBoat = tabBoatTest;
        tabBoatP1 = tabBoat;
        tabBoatP2 = tabBoat;
        this.torpilleur++;
        this.sousMarin++;
        this.porteAvion++;
        int sizeMap = 10;
        player1.initPlayer(sizeMap);
        player2.initPlayer(sizeMap);
        player1.setTabBoatPlayer(tabBoatP1);
        player2.setTabBoatPlayer(tabBoatP2);
    }


    public boolean isInMap(Player player, int x, int y) {
        if (x < player.getMapCheck().getWidthX() && x >= 0 && y >= 0 && y < player.getMapCheck().getLengthY()) {
            return true;
        } else {
            return false;
        }
    }

    public boolean isInMapGame(Player player, int x, int y) {
        if (x < player.getMap().getWidthX() && x >= 0 && y >= 0 && y < player.getMap().getLengthY()) {
            return true;
        } else {
            return false;
        }
    }

    public boolean checkIsOcupied(Player player, int x, int y) {
        return player.getMapCheck().getTabMapCase()[x][y].getIsOccupied();
    }

    public void deleteBoat(Player player, int x, int y) {
        for (int i = 0; i < player.getTabBoatPlayer().length; i++) {
            if (player.getMap().getTabMapCase()[x][y].getIdBoat() == player.getTabBoatPlayer()[i].getId()) {
                for (int j = 0; j < player.getTabBoatPlayer()[i].getLength(); j++) {
                    if (player.getTabBoatPlayer()[i].getIsHorizontal()) {
                        player.getMap().getTabMapCase()[x + j][y].setTargettable(true);
                        player.getMap().getTabMapCase()[x + j][y].setOccupied(false);
                        player.getMap().getTabMapCase()[x + j][y].setIdBoat(-1);
                    } else {
                        player.getMap().getTabMapCase()[x][y + j].setTargettable(true);
                        player.getMap().getTabMapCase()[x][y + j].setOccupied(false);
                        player.getMap().getTabMapCase()[x][y + j].setIdBoat(-1);
                    }
                    player.getTabBoatPlayer()[i].setPlacer(false);
                }
            }
        }
    }

    // marche pour tout sauf les bordure de map..
    public boolean checkArea(Player player, Boat boat, int x, int y, boolean isHorizontal) {
        for (int i = 0; i < boat.getLength(); i++) {
            for (int xx = -1; xx <= 1; xx++) {
                for (int yy = -1; yy <= 1; yy++) {
                    if (isHorizontal) {
                        if (!isInMap(player, x + xx + i, y + yy) || checkIsOcupied(player, x + xx + i, y + yy)) {
                            return false;
                        }
                    } else {
                        if (!isInMap(player, x + xx, y + yy + i) || checkIsOcupied(player, x + xx, y + yy + i)) {
                            return false;
                        }
                    }
                }
            }
        }
        return true;
    }

    public boolean isPlacable(Player player, Boat boat, int x, int y, boolean isHorizontal) {
        if (x < 1 || y < 1) {
            return false;
        } else {
            if (isHorizontal && x < player.getMapCheck().getWidthX() - boat.getLength() && y < player.getMapCheck().getLengthY() - 1) {
                return checkArea(player, boat, x, y, true);
            } else {
                if (!isHorizontal && x < player.getMapCheck().getWidthX() - 1 && y < player.getMapCheck().getLengthY() - boat.getLength()) {
                    return checkArea(player, boat, x, y, false);
                } else return false;
            }
        }
    }

    // Verifier & Placer un boat sur une map d'un joueur si c'est possible
    public void placeBoatOnMap(Player player, Boat boat, int x, int y, boolean isHorizontal){
        String s = " ";
        x++;
        y++;
        System.out.println("\nplacement de : " + boat.getType() +" (longeur : "+ boat.getLength()+" en ("+x+" ; "+y+") !");
        if (isPlacable(player,boat,x,y,isHorizontal)){
            switch (x){
                case 1:
                    s = "A";
                    break;
                case 2:
                    s = "B";
                    break;
                case 3:
                    s = "C";
                    break;
                case 4:
                    s = "D";
                    break;
                case 5:
                    s = "E";
                    break;
                case 6:
                    s = "F";
                    break;
                case 7:
                    s = "G";
                    break;
                case 8:
                    s = "H";
                    break;
                case 9:
                    s = "I";
                    break;
                case 10:
                    s = "J";
                    break;
            }
            System.out.println("\nLe " + boat.getType() +" (longeur : "+ boat.getLength() +") du joueur " + player + ", est placer en ("+s+" ; "+y+") !");
            for (int i = 0; i < boat.getLength(); i++){
                if (isHorizontal) {
                    player.getMapCheck().getTabMapCase()[x + i][y].setOccupied(true);
                    player.getMapCheck().getTabMapCase()[x + i][y].setTargettable(true);
                    player.getMapCheck().getTabMapCase()[x + i][y].setIdBoat(boat.getId());
                } else {
                    player.getMapCheck().getTabMapCase()[x][y + i].setOccupied(true);
                    player.getMapCheck().getTabMapCase()[x][y + i].setTargettable(true);
                    player.getMapCheck().getTabMapCase()[x][y + i].setIdBoat(boat.getId());
                }
            }
            boat.setPosX(x);
            boat.setPosY(y);
            boat.setPlacer(true);
            player.convertToTrueMap();
        } else {
            System.out.println("Placement impossible, reassayer !");
        }
    }


    public void placeBoatOnMapOrdinateur(Ordinateur ordinateur, Boat boat, int x, int y, boolean isHorizontal) {
        /*x++;
        y++;*/
        //System.out.println("\nplacement de : " + boat.getType() + " (longeur : " + boat.getLength() + " en (" + x + " ; " + y + ") !");
        if (isPlacable(ordinateur, boat, x, y, isHorizontal)) {
            //System.out.println("\nLe " + boat.getType() + " (longeur : " + boat.getLength() + ") du joueur " + ordinateur.getName() + ", est placer en (" + x + " ; " + y + ") !");
            for (int i = 0; i < boat.getLength(); i++) {
                if (isHorizontal) {
                    ordinateur.getMapCheck().getTabMapCase()[x + i][y].setOccupied(true);
                    ordinateur.getMapCheck().getTabMapCase()[x + i][y].setTargettable(true);
                    ordinateur.getMapCheck().getTabMapCase()[x + i][y].setIdBoat(boat.getId());
                } else {
                    ordinateur.getMapCheck().getTabMapCase()[x][y + i].setOccupied(true);
                    ordinateur.getMapCheck().getTabMapCase()[x][y + i].setTargettable(true);
                    ordinateur.getMapCheck().getTabMapCase()[x][y + i].setIdBoat(boat.getId());
                }
            }
            boat.setPosX(x);
            boat.setPosY(y);
            boat.setPlacer(true);
        } else {
            System.out.println("Placement impossible, reassayer !");
        }
    }

    //type de tir -->   1 = normal
    //                  2 = croix (torpille)
    //message d'envoi de coup==  coordonnéeX/coordonnéeY/typeMissile chaque élément en int
    //boatHit SUR MAP DU PLAYER !!
    public int boatHit(Player player, int x, int y, int type) {
        /*x--;
        y--;*/
        if (isInMapGame(player, x, y)) {
            if (player.getMap().getTabMapCase()[x][y].getIsTargettable()) {
                switch (type) {
                    case 1:
                        if (player.getMap().getTabMapCase()[x][y].getIsOccupied()) {
                            //1 = missille normal : touche une seul case
                            //2 = torpille (tir en croix)
                            // getboat - boat health -1 -istargetable false
                            for (int i = 0; i < player.getTabBoatPlayer().length; i++) {
                                if (player.getTabBoatPlayer()[i].getId() == player.getMap().getTabMapCase()[x][y].getIdBoat()) {

                                    int health = player.getTabBoatPlayer()[i].getHealth();
                                    player.getTabBoatPlayer()[i].setHealth(health - 1);
                                    //player.getMapOponment().getTabMapCase()[x][y].setTargettable(false);

                                    if (player.getTabBoatPlayer()[i].getHealth() <= 0) {
                                        System.out.println("\n  tire sur " + player.getName() + " en (" + (x + 1) + ", " + (y + 1) + ") --> TOUCHE COULE CAP'TAIN ! bato de " + player.getTabBoatPlayer()[i].getLength());
                                        return 2;
                                        //passer markeur rouge sur la vue this.player.view.mapMarkeur
                                    } else {
                                        System.out.println("\n  tire sur " + player.getName() + " en (" + (x + 1) + ", " + (y + 1) + ") --> TOUCHE");
                                        return 1;
                                        //passer markeur rouge sur la vue this.player.view.mapMarkeur
                                    }
                                }
                            }
                        } else {
                            System.out.println("\n  tire sur " + player.getName() + " en (" + (x+1) + ", " + (y+1) + ") --> LOUPE");
                            return 0;
                            //passer markeur blanc sur la vue this.player.view.mapMarkeur
                        }
                        break;
                    /*case 2:
                        for (int i = -1; i <= 1; i++) {
                            for (int j = -1; j <= 1; j++) {
                                if (!(i == -1 && j == -1) || !(i == 1 && j == -1) || !(i == -1 && j == 1) || !(i == 1 && j == 1)) {

                                    //  I  J
                                    // [-1][0]
                                    // [1][0]
                                    // [0][-1]
                                    // [0][1]
                                    // [0][0]
                                    if (isInMap(player, x + i, y + j)) {
                                        if (player.getMap().getTabMapCase()[x][y].getIsOccupied()) {
                                            //1 = missille normal : touche une seul case
                                            //2 = torpille (tir en croix)
                                            // getboat - boat health -1 -istargetable false
                                            for (int z = 0; z < player.getTabBoatPlayer().length; z++) {
                                                if (player.getTabBoatPlayer()[z].getId() == player.getMap().getTabMapCase()[x + i][y + j].getIdBoat()
                                                        && player.getMap().getTabMapCase()[x + i][y + j].getIsTargettable()) {

                                                    player.getTabBoatPlayer()[z].setHealth(player.getTabBoatPlayer()[z].getHealth() - 1);
                                                    player.getMap().getTabMapCase()[x + i][y + j].setTargettable(false);

                                                    System.out.println("case tire : " + (x + i) + " , " + (y + j));

                                                    if (player.getTabBoatPlayer()[z].getHealth() <= 0) {
                                                        System.out.println("\n" + player + " tire sur (" + x + ", " + y + ") --> TOUCHE COULE CAP'TAIN !");
                                                        player.getTabBoatPlayer()[z].setIsCouler(true);
                                                        //passer markeur rouge sur la vue this.player.view.mapMarkeur
                                                    } else {
                                                        System.out.println("\n" + player + " tire sur (" + x + ", " + y + ") --> TOUCHE");
                                                        //passer markeur rouge sur la vue this.player.view.mapMarkeur
                                                    }
                                                }
                                            }
                                        } else {
                                            System.out.println("\n" + player + " tire sur (" + x + ", " + y + ") --> LOUPE");
                                            //passer markeur blanc sur la vue this.player.view.mapMarkeur
                                        }
                                    }
                                }
                            }
                        }
                        break;
                    default:
                        System.out.println("EERROR: MAUVAIS TYPE DE MISSILES");
                        break;*/
                }
            } else {
                System.out.println("\n" + player.getName() + " tire sur (" + (x+1) + ", " + (y+1) + ") --> DEJA TOUCHE PAS BON");
            }
        } else {
            System.out.println("not in map !");
        }
        return 0;
    }

    public int boatHitordi(Player player, int x, int y, int type) {
        /*x--;
        y--;*/
        if (isInMapGame(player, x, y)) {
            if (player.getMap().getTabMapCase()[x][y].getIsTargettable()) {
                switch (type) {
                    case 1:
                        if (player.getMap().getTabMapCase()[x][y].getIsOccupied()) {
                            //1 = missille normal : touche une seul case
                            //2 = torpille (tir en croix)
                            // getboat - boat health -1 -istargetable false
                            for (int i = 0; i < player.getTabBoatPlayer().length; i++) {
                                if (player.getTabBoatPlayer()[i].getId() == player.getMap().getTabMapCase()[x][y].getIdBoat()) {

                                    int health = player.getTabBoatPlayer()[i].getHealth();
                                    player.getTabBoatPlayer()[i].setHealth(health - 1);
                                    player.getMapOponment().getTabMapCase()[x][y].setTargettable(false);

                                    if (player.getTabBoatPlayer()[i].getHealth() <= 0) {
                                        System.out.println("\n  tire sur " + player.getName() + " en (" + (x + 1) + ", " + (y + 1) + ") --> TOUCHE COULE CAP'TAIN ! bato de " + player.getTabBoatPlayer()[i].getLength());
                                        return 2;
                                        //passer markeur rouge sur la vue this.player.view.mapMarkeur
                                    } else {
                                        System.out.println("\n  tire sur " + player.getName() + " en (" + (x + 1) + ", " + (y + 1) + ") --> TOUCHE");
                                        return 1;
                                        //passer markeur rouge sur la vue this.player.view.mapMarkeur
                                    }
                                }
                            }
                        } else {
                            System.out.println("\n  tire sur " + player.getName() + " en (" + (x+1) + ", " + (y+1) + ") --> LOUPE");
                            return 0;
                            //passer markeur blanc sur la vue this.player.view.mapMarkeur
                        }
                        break;
                    /*case 2:
                        for (int i = -1; i <= 1; i++) {
                            for (int j = -1; j <= 1; j++) {
                                if (!(i == -1 && j == -1) || !(i == 1 && j == -1) || !(i == -1 && j == 1) || !(i == 1 && j == 1)) {

                                    //  I  J
                                    // [-1][0]
                                    // [1][0]
                                    // [0][-1]
                                    // [0][1]
                                    // [0][0]
                                    if (isInMap(player, x + i, y + j)) {
                                        if (player.getMap().getTabMapCase()[x][y].getIsOccupied()) {
                                            //1 = missille normal : touche une seul case
                                            //2 = torpille (tir en croix)
                                            // getboat - boat health -1 -istargetable false
                                            for (int z = 0; z < player.getTabBoatPlayer().length; z++) {
                                                if (player.getTabBoatPlayer()[z].getId() == player.getMap().getTabMapCase()[x + i][y + j].getIdBoat()
                                                        && player.getMap().getTabMapCase()[x + i][y + j].getIsTargettable()) {

                                                    player.getTabBoatPlayer()[z].setHealth(player.getTabBoatPlayer()[z].getHealth() - 1);
                                                    player.getMap().getTabMapCase()[x + i][y + j].setTargettable(false);

                                                    System.out.println("case tire : " + (x + i) + " , " + (y + j));

                                                    if (player.getTabBoatPlayer()[z].getHealth() <= 0) {
                                                        System.out.println("\n" + player + " tire sur (" + x + ", " + y + ") --> TOUCHE COULE CAP'TAIN !");
                                                        player.getTabBoatPlayer()[z].setIsCouler(true);
                                                        //passer markeur rouge sur la vue this.player.view.mapMarkeur
                                                    } else {
                                                        System.out.println("\n" + player + " tire sur (" + x + ", " + y + ") --> TOUCHE");
                                                        //passer markeur rouge sur la vue this.player.view.mapMarkeur
                                                    }
                                                }
                                            }
                                        } else {
                                            System.out.println("\n" + player + " tire sur (" + x + ", " + y + ") --> LOUPE");
                                            //passer markeur blanc sur la vue this.player.view.mapMarkeur
                                        }
                                    }
                                }
                            }
                        }
                        break;
                    default:
                        System.out.println("EERROR: MAUVAIS TYPE DE MISSILES");
                        break;*/
                }
            } else {
                System.out.println("\n" + player.getName() + " tire sur (" + (x+1) + ", " + (y+1) + ") --> DEJA TOUCHE PAS BON");
            }
        } else {
            System.out.println("not in map !");
        }
        return 0;
    }


    public boolean estPerdu(Player player) {
        int countDead = 0;
        System.out.println(player.getTabBoatPlayer()[0]);
        for (int i = 0; i < player.getTabBoatPlayer().length; i++) {
            if (player.getTabBoatPlayer()[i].getHealth() <= 0 && player.getTabBoatPlayer()[i].getIsCouler()) {
                countDead++;
            }
        }
        return countDead >= nbBoat;
    }

    /**
     * Getter&Setter
     */
    public Player getPlayer1() {
        return player1;
    }

    public void setPlayer1(Player player1) {
        this.player1 = player1;
    }

    public Player getPlayer2() {
        return player2;
    }

    public void setPlayer2(Player player2) {
        this.player2 = player2;
    }

    public int getNbPlayer() {
        return nbPlayer;
    }

    public void setNbPlayer(int nbPlayer) {
        this.nbPlayer = nbPlayer;
    }

    public int getNbBoat() {
        return nbBoat;
    }

    public void setNbBoat(int nbBoat) {
        this.nbBoat = nbBoat;
    }

    public Boat[] getTabBoat() {
        return tabBoat;
    }

    public void setTabBoat(Boat[] tabBoat) {
        this.tabBoat = tabBoat;
    }

    public int getTorpilleur() {
        return torpilleur;
    }

    public void setTorpilleur(int torpilleur) {
        this.torpilleur = torpilleur;
    }

    public int getSousMarin() {
        return sousMarin;
    }

    public void setSousMarin(int sousMarin) {
        this.sousMarin = sousMarin;
    }

    public int getContreTorpilleur() {
        return contreTorpilleur;
    }

    public void setContreTorpilleur(int contreTorpilleur) {
        this.contreTorpilleur = contreTorpilleur;
    }

    public int getCroiseur() {
        return croiseur;
    }

    public void setCroiseur(int croiseur) {
        this.croiseur = croiseur;
    }

    public int getPorteAvion() {
        return porteAvion;
    }

    public void setPorteAvion(int porteAvion) {
        this.porteAvion = porteAvion;
    }

    public Boat[] getTabBoatTest() {
        return tabBoatTest;
    }

    public View2 getView2() {
        return view;
    }

    public void setView2(View2 view) {
        this.view = view;
    }

    public Boat[] getTabBoatP1() {
        return tabBoatP1;
    }

    public void setTabBoatP1(Boat[] tabBoatP1) {
        this.tabBoatP1 = tabBoatP1;
    }

    public Boat[] getTabBoatP2() {
        return tabBoatP2;
    }

    public void setTabBoatP2(Boat[] tabBoatP2) {
        this.tabBoatP2 = tabBoatP2;
    }

    public static Scanner getInput() {
        return input;
    }

    public View2 getView() {
        return view;
    }

    public void setView(View2 view) {
        this.view = view;
    }

    public String getIpAdverse() {
        return ipAdverse;
    }

    public void setIpAdverse(String ipAdverse) {
        this.ipAdverse = ipAdverse;
    }

    public String getIpSelf() {
        return ipSelf;
    }

    public void setIpSelf(String ipSelf) {
        this.ipSelf = ipSelf;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public boolean isMulti() {
        return isMulti;
    }

    public void setMulti(boolean multi) {
        isMulti = multi;
    }

    public boolean isClassic() {
        return isClassic;
    }

    public void setClassic(boolean classic) {
        isClassic = classic;
    }

    public boolean isOnline() {
        return isOnline;
    }

    public void setOnline(boolean online) {
        isOnline = online;
    }

    public boolean isServer() {
        return isServer;
    }

    public void setServer(boolean server) {
        isServer = server;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public boolean getMulti() { return isMulti; }

    public boolean getClassic() { return isClassic; }

    public boolean getOnline() { return isOnline; }

    public boolean getServer() { return isServer; }

    public int getTheme() { return theme; }

    public void setTheme(int theme) { this.theme = theme; }
}
