import java.awt.*;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Scanner;

public class Battle extends Game {

    private int sizeGrid = 10;

    private Game game;
    private View2 w;
    private ControlClick controlClick;
    private ControlButton controlButton;
    private ControlBarMenu controlBarMenu;
    private boolean firstround = true;
    private int bitFinparti = 0;

    private Ordinateur ordinateur;

    private boolean imReady = false;
    private boolean hesReady = false;
    private boolean isLost = false;

    private int order = 0;
    static final Scanner input = new Scanner(System.in);
    private boolean reseauReady;

    private ArrayList<Integer[]> listCoupTouche = new ArrayList<>();

    public Battle(Game game, View2 w) {
        this.game = game;
        this.w = w;
    }

    public Battle() {
        this.game = new Game();
        this.w = new View2(game);
        this.controlClick = new ControlClick(w, game, this);
        this.controlButton = new ControlButton(w, game, this);
        this.controlBarMenu = new ControlBarMenu(w,game);
        w.display();
    }

    public void run() {
        if (game.isMulti()) {
            if (game.isOnline()) {
                if (game.isServer()) {
                    if (!game.isClassic()) {
                        game.initGamePersoReseauHost(game.getPlayer1(), 10, game.getTorpilleur(), game.getContreTorpilleur(), game.getSousMarin(), game.getCroiseur(), game.getPorteAvion());
                    } else {
                        game.initGameClassicReseauHost(game.getPlayer1());
                    }
                } else {
                    game.setIpAdverse(w.getTfClient().getText());
                    try {
                        Envoyer.envoyer(game.getIpAdverse(), game.getPort(), Recevoir.getIPLocale());
                    } catch (UnknownHostException e) {
                        e.printStackTrace();
                    }
                    game.initPartiReseauClient();
                    reseauReady = true;
                }
            } else {
                if (game.isClassic()) {
                    game.setPlayer2(new Player("Joueur 2"));
                    game.initGameClassic(game.getPlayer1(), 10);
                    game.initGameClassic(game.getPlayer2(), 10);
                } else {
                    game.setPlayer2(new Player("Player 2"));
                    game.initGamePerso(game.getPlayer1(), 10, game.getTorpilleur(), game.getContreTorpilleur(), game.getSousMarin(), game.getCroiseur(), game.getPorteAvion());
                    game.initGamePerso(game.getPlayer2(), 10, game.getTorpilleur(), game.getContreTorpilleur(), game.getSousMarin(), game.getCroiseur(), game.getPorteAvion());
                }
            }
        } else {
            if (game.isClassic()) {
                ordinateur = new Ordinateur(game, w, 10);
                game.initGameClassic(ordinateur, 10);
                ordinateur.placeBoatOrdi();
            } else {
                game.initGamePerso(game.getPlayer1(), 10, game.getSousMarin(), game.getTorpilleur(), game.getContreTorpilleur(), game.getCroiseur(), game.getPorteAvion());
                Ordinateur ordinateur = new Ordinateur(game, w, 10);
                game.initGamePerso(ordinateur, 10, game.getSousMarin(), game.getTorpilleur(), game.getContreTorpilleur(), game.getCroiseur(), game.getPorteAvion());
                ordinateur.placeBoatOrdi();
            }
        }
    }

    public int[] askCoords(Player player) {
        // gere map adverse si deja tiré dessu
        int[] coord = {1, 1, 1};
        do {
            if (!isInMapGame(player, coord[0], coord[1]) || !player.getMapOponment().getTabMapCase()[coord[0]][coord[1]].getIsTargettable()) {
                System.out.println("Veuiller saisir des coord valide et pas deja tire");
            }
            System.out.println("saisir coordonnée X :");
            coord[0] = input.nextInt();
            System.out.println("saisir coordonnée Y :");
            coord[1] = input.nextInt();
            System.out.println("saisir type de missile : (1 = classique, 2 = torpille)");
            coord[2] = input.nextInt();
        }
        while (!isInMapGame(player, coord[0], coord[1]) || !player.getMapOponment().getTabMapCase()[coord[0]][coord[1]].getIsTargettable());
        return coord;
    }

    public void partieClassicReseauHost(Game game, View2 w) {
        // Initialisation

        game.initGameClassicReseauHost(game.getPlayer1());

        System.out.println("PLACEMENT BATO J1 \n");

        placementJoueurLocal(game.getPlayer1());
        //placementJoueurLocalTest(game.getPlayer1());


        ThreadReady recevoir = new ThreadReady(this, true, 3);
        ThreadReady envoyer = new ThreadReady(this, false, 1);
        ThreadReady envoyer2 = new ThreadReady(this, false, 2);

        System.out.println("Joueur1 pret");
        recevoir.start();
        envoyer.start();

        // Deroulement
        while (!hesReady) {
            System.out.print("");
        }

        System.out.println("joueur2 pret");
        //Envoyer.envoyer(game.getIpAdverse(), game.getPort(), "1");
        if (order == 1) {
            envoyer2.start();
        }

        //Deroulement
        while (this.bitFinparti == 0 && !isLost) {
            deroulementGame();
        }

        // Fin
        if (bitFinparti == 1) {
            System.out.println("partie finie : player local win.");
            //System.out.println("game.estPerdu(game.getPlayer1()) = "+ game.estPerdu(game.getPlayer1()));
        } else if (isLost) {
            System.out.println("Vous avez perdu RIP...");
        }
    }

    public void partieReseauClient(Game game, View2 w) {
        // Initialisation
        game.setIpAdverse(w.getTfClient().getText());
        try {
            Envoyer.envoyer(game.getIpAdverse(), game.getPort(), Recevoir.getIPLocale());
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

        game.initPartiReseauClient();

        System.out.println("PLACEMENT BATO J2 \n");

        placementJoueurLocal(game.getPlayer1());
        //placementJoueurLocalTest();
        //this.placeBoatOnMap(game.getPlayer1(), game.getPlayer1().getTabBoatPlayer()[0], 1, 1, true);

        imReady = true;
        // partie en réseau :
        // juste a attendre que l'autre joueur est placé ses ships...
        ThreadReady recevoir = new ThreadReady(this, true, 3);
        ThreadReady envoyer = new ThreadReady(this, false, 1);
        ThreadReady envoyer2 = new ThreadReady(this, false, 2);

        System.out.println("Joueur1 pret");
        recevoir.start();
        envoyer.start();
        // Deroulement
        while (!hesReady) {
            System.out.print("");
        }
        System.out.println("joueur2 pret");
        //Envoyer.envoyer(game.getIpAdverse(), game.getPort(), "1");
        if (order == 1) {
            envoyer2.start();
            System.out.println("pause");
            try {
                Thread.sleep(6000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        int[] coordFirst = askCoords(game.getPlayer1());
        game.getPlayer1().getMapOponment().getTabMapCase()[coordFirst[0]][1].setTargettable(false);

        Envoyer.envoyer(game.getIpAdverse(), game.getPort(), "" + coordFirst[0] + "/" + coordFirst[1] + "/" + coordFirst[2]);

        String resultatMsgFirst = Recevoir.recevoir(game.getPort());
        int resultat = Decodage.getResultatClassic(resultatMsgFirst);
        bitFinparti = Decodage.bitdeFinPartie(resultatMsgFirst);
        System.out.println(resultat);

        while (this.bitFinparti == 0 && !isLost) {
            deroulementGame();
        }

        // Fin
        if (bitFinparti == 1) {
            System.out.println("partie finie : player local win.");
        } else if (isLost) {
            System.out.println("Vous avez perdu RIP...");
        }
    }

    public void partiePersoReseauHost(Game game, View2 w, int torpilleur, int contreTorpilleur, int sousMarin, int croiseur, int porteAvion) {
        // Initialisation
        game.initGamePersoReseauHost(game.getPlayer1(), 10, sousMarin, torpilleur, contreTorpilleur, croiseur, porteAvion);

        System.out.println("PLACEMENT BATO J1 \n");

        placementJoueurLocal(game.getPlayer1());
        //placementJoueurLocalTest();
        //this.placeBoatOnMap(game.getPlayer1(), game.getPlayer1().getTabBoatPlayer()[0], 1, 1, true, w);

        ThreadReady recevoir = new ThreadReady(this, true, 3);
        ThreadReady envoyer = new ThreadReady(this, false, 1);
        ThreadReady envoyer2 = new ThreadReady(this, false, 2);

        System.out.println("Joueur1 pret");
        recevoir.start();
        envoyer.start();
        // Deroulement
        while (!hesReady) {
            System.out.print("");
        }

        System.out.println("joueur2 pret");
        //Envoyer.envoyer(game.getIpAdverse(), game.getPort(), "1");
        if (order == 1) {
            envoyer2.start();
        }

        // Deroulement
        while (this.bitFinparti == 0 && !isLost) {
            deroulementGame();
        }

        // Fin
        if (bitFinparti == 1) {
            System.out.println("partie finie : player local win.");
            //System.out.println("game.estPerdu(game.getPlayer1()) = "+ game.estPerdu(game.getPlayer1()));
        } else if (isLost) {
            System.out.println("Vous avez perdu RIP...");
        }
    }

    private void placementJoueurLocalTest(Player p) {
        this.placeBoatOnMap(p, p.getTabBoatPlayer()[0], 1, 1, true);
        this.placeBoatOnMap(p, p.getTabBoatPlayer()[1], 1, 3, true);
        this.placeBoatOnMap(p, p.getTabBoatPlayer()[2], 1, 5, true);
        this.placeBoatOnMap(p, p.getTabBoatPlayer()[3], 1, 7, true);
        while (!p.getTabBoatPlayer()[4].getIsPlacer()) {
            System.out.println("Voulez vous votre bato horizontal ? (true/false)");
            boolean isHorizontal = input.nextBoolean();
            this.placeBoatOnMap(p, p.getTabBoatPlayer()[4], 1, 9, isHorizontal);
        }
    }

    public void partieLocaleClassic(Game game, View2 w) {
        // Initialisation
        game.setPlayer2(new Player());
        game.initGameClassic(game.getPlayer1(), 10);
        game.initGameClassic(game.getPlayer2(), 10);

        System.out.println(game.getPlayer2().getTabBoatPlayer().length);

        System.out.println("PLACEMENT BATO J1 \n");

        placementJoueurLocal(game.getPlayer1());

        System.out.println("PLACEMENT BATO J2 \n");
        placementJoueurLocal(game.getPlayer2());

        // Deroulement
        deroulementLocal();

        if (imLost(game.getPlayer1())) {
            System.out.println("Player " + game.getPlayer2().getName() + " win the game !");
        } else {
            System.out.println("Player " + game.getPlayer1().getName() + " win the game !");
        }
    }

    public void partieLocalePerso(Game game, View2 w, int torpilleur, int contreTorpilleur, int sousMarin, int croiseur, int porteAvion) {
        // Initialisation
        game.setPlayer2(new Player());
        game.initGamePerso(game.getPlayer1(), 10, sousMarin, torpilleur, contreTorpilleur, croiseur, porteAvion);
        game.initGamePerso(game.getPlayer2(), 10, sousMarin, torpilleur, contreTorpilleur, croiseur, porteAvion);

        System.out.println(game.getPlayer2().getTabBoatPlayer().length);

        System.out.println("PLACEMENT BATO J1 \n");

        placementJoueurLocal(game.getPlayer1());


        System.out.println("PLACEMENT BATO J2 \n");
        placementJoueurLocal(game.getPlayer2());

        // Deroulement
        deroulementLocal();

        if (imLost(game.getPlayer1())) {
            System.out.println("Player " + game.getPlayer2().getName() + " win the game !");
        } else {
            System.out.println("Player " + game.getPlayer1().getName() + " win the game !");
        }
    }

    public void partieClassicContreOrdi(Game game, View2 w) {
        game.initGameClassic(game.getPlayer1(), 10);

        Ordinateur ordinateur = new Ordinateur(game, w, 10);
        game.initGameClassic(ordinateur, 10);

        ordinateur.placeBoatOrdi();
        placementJoueurLocalTest(game.getPlayer1());

        deroulementIA(game.getPlayer1(), ordinateur);
    }

    public void partiePersoContreOrdinateur(Game game, View2 w, int torpilleur, int contreTorpilleur, int sousMarin, int croiseur, int porteAvion) {
        game.initGamePerso(game.getPlayer1(), 10, game.getSousMarin(), game.getTorpilleur(), game.getContreTorpilleur(), game.getCroiseur(), game.getPorteAvion());
        //game.initGamePerso(game.getPlayer1(), 10, 0, 1, 0, 0, 0);

        Ordinateur ordinateur = new Ordinateur(game, w, 10);

        game.initGamePerso(ordinateur, 10, sousMarin, torpilleur, contreTorpilleur, croiseur, porteAvion);
        //game.initGamePerso(ordinateur, 10, 0, 1, 0, 0, 0);

        ordinateur.placeBoatOrdi();
        //placementJoueurLocal(game.getPlayer1());

        deroulementIA(game.getPlayer1(), ordinateur);
    }

    private void deroulementGame() {
        //System.out.println("Tour de " + game.getPlayer1().getName());
        String msgRecus = Recevoir.recevoir(game.getPort());

        int x = Decodage.getX(msgRecus);
        int y = Decodage.getY(msgRecus);
        int type = Decodage.getTypeMissile(msgRecus);

        this.hit(game.getPlayer1(), x, y, type, view);

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if (!isLost && bitFinparti == 0) {
            int[] coord = askCoords(game.getPlayer1());
            //game.getPlayer1().getMapOponment().getTabMapCase()[coord[0]][coord[1]].setTargettable(false);

            Envoyer.envoyer(game.getIpAdverse(), game.getPort(), "" + coord[0] + "/" + coord[1] + "/" + coord[2]);
            String resultatMsg = Recevoir.recevoir(game.getPort());
            int resultat = Decodage.getResultatClassic(resultatMsg);
            view.placeMarkerBig(resultat, x, y);
            this.bitFinparti = Decodage.bitdeFinPartie(resultatMsg);
            System.out.println(resultat);
            if (coord[2] == 1) {
                game.getPlayer1().getMapOponment().getTabMapCase()[coord[0]][coord[1]].setTargettable(false);
                game.getPlayer1().getMapOponment().getTabMapCase()[coord[0]][coord[1]].setCaseColor(Color.RED);
            } else {
                try {
                    game.getPlayer1().getMapOponment().getTabMapCase()[coord[0]][coord[1]].setTargettable(false);
                    game.getPlayer1().getMapOponment().getTabMapCase()[coord[0] + 1][coord[1]].setTargettable(false);
                    game.getPlayer1().getMapOponment().getTabMapCase()[coord[0] - 1][coord[1]].setTargettable(false);
                    game.getPlayer1().getMapOponment().getTabMapCase()[coord[0]][coord[1] + 1].setTargettable(false);
                    game.getPlayer1().getMapOponment().getTabMapCase()[coord[0]][coord[1] - 1].setTargettable(false);
                } catch (IndexOutOfBoundsException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void deroulementLocal() {
        while ((!imLost(game.getPlayer1())) && (!imLost(game.getPlayer2()))) {

            System.out.println("Tour de " + game.getPlayer1().getName());
            int[] coord = askCoords(game.getPlayer1());
            game.boatHit(game.getPlayer2(), coord[0], coord[1], 1);
            game.getPlayer1().getMapOponment().getTabMapCase()[coord[0]][coord[1]].setTargettable(false);
            if ((!imLost(game.getPlayer1())) && (!imLost(game.getPlayer2()))) {
                System.out.println("Tour de " + game.getPlayer2().getName());
                int[] coord1 = askCoords(game.getPlayer2());
                game.boatHit(game.getPlayer1(), coord1[0], coord1[1], 1);
                game.getPlayer2().getMapOponment().getTabMapCase()[coord[0]][coord[1]].setTargettable(false);
            }
        }
    }

    private void deroulementIA(Player human, Ordinateur ordinateur) {
        while (!imLost(game.getPlayer1()) && !imLost(ordinateur)) {
            System.out.println("\n-----------------------------\nTour de l'ordi :");
            ordinateur.boatHitIAeasy(game.getPlayer1(), view);

            if ((!imLost(game.getPlayer1()) && !imLost(ordinateur))) {
                System.out.println("\n-----------------------------\nTour de " + game.getPlayer1().getName() + " :");
                int[] coord = askCoords(game.getPlayer1());
                game.boatHit(ordinateur, coord[0], coord[1], coord[2]);
                game.getPlayer1().getMapOponment().getTabMapCase()[coord[0]][coord[1]].setTargettable(false);
            }
        }
        if (imLost(ordinateur)) {
            System.out.println("\nVous avez perdu ! L'ordinateur remporte la partie...\n");
        } else {
            System.out.println("\nVous avez Gagné ! L'ordinateur perd la partie...\n");
        }
    }

    private void placementJoueurLocal(Player player) {
        for (int i = 0; i < player.getTabBoatPlayer().length; i++) {
            while (!player.getTabBoatPlayer()[i].getIsPlacer()) {
                System.out.println("\n" + player.getName() + " : Coord X pour placer votre " + player.getTabBoatPlayer()[i].getType());
                int x = input.nextInt() /*- 1*/;
                System.out.println(player.getName() + " : Coord Y pour placer votre " + player.getTabBoatPlayer()[i].getType());
                int y = input.nextInt() /*- 1*/;
                System.out.println("Voulez vous votre bato horizontal ? (true/false)");
                boolean isHorizontal = input.nextBoolean();
                this.placeBoatOnMap(player, player.getTabBoatPlayer()[i], x, y, isHorizontal);
            }
        }
    }

    //check if hit grille local w/ boats
    public void hit(Player player, int x, int y, int type, View2 view) {
        if (type == 1) {
            int res = game.boatHit(player, x, y, type);
            view.placeMarkerLitle(res, x, y);
            int lost;
            if (imLost(player)) {
                lost = 1;
                isLost = true;
            } else lost = 0;
            Envoyer.envoyer(game.getIpAdverse(), game.getPort(), "" + res + "/" + lost);
        } else {

        }
    }

    public boolean imLost(Player player) {
        int count = 0;
        for (int i = 0; i < player.getTabBoatPlayer().length; i++) {
            if (player.getTabBoatPlayer()[i].getHealth() <= 0 || player.getTabBoatPlayer()[i].getIsCouler()) {
                count++;
            }
        }
        return count >= player.getTabBoatPlayer().length;
    }

    /*public void blackBoatBigIa(int boatId, View2 view) {
        for (int i = 0; i < game.getPlayer1().getTabBoatPlayer().length; i++) {
            if (boatId == game.getPlayer1().getTabBoatPlayer()[i].getId()) {
                for (int j = 0; j < game.getPlayer1().getTabBoatPlayer()[i].getLength(); j++) {
                    System.out.println("posX boat = " + game.getPlayer1().getTabBoatPlayer()[i].getPosX());
                    System.out.println("posY boat = " + game.getPlayer1().getTabBoatPlayer()[i].getPosY());
                    if (!game.getPlayer1().getTabBoatPlayer()[i].getIsHorizontal()) {
                        view.getGridLabelBigGrid()[game.getPlayer1().getTabBoatPlayer()[i].getPosX() +j][game.getPlayer1().getTabBoatPlayer()[i].getPosY()].setIcon(null);
                        view.getGridLabelBigGrid()[game.getPlayer1().getTabBoatPlayer()[i].getPosX() +j][game.getPlayer1().getTabBoatPlayer()[i].getPosY()].setIcon(view.getBlackDot());
                    } else {
                        view.getGridLabelBigGrid()[game.getPlayer1().getTabBoatPlayer()[i].getPosX()][game.getPlayer1().getTabBoatPlayer()[i].getPosY() +j].setIcon(null);
                        view.getGridLabelBigGrid()[game.getPlayer1().getTabBoatPlayer()[i].getPosX()][game.getPlayer1().getTabBoatPlayer()[i].getPosY() +j].setIcon(view.getBlackDot());
                    }
                }
            }
        }
    }*/

    public void blackBoat(View2 view){
        for (int i = 0; i < listCoupTouche.size(); i++){
            view.getGridLabelBigGrid()[listCoupTouche.get(i)[1]][listCoupTouche.get(i)[0]].setIcon(null);
            view.getGridLabelBigGrid()[listCoupTouche.get(i)[1]][listCoupTouche.get(i)[0]].setIcon(view.getBlackDot());
        }
        listCoupTouche.clear();
    }

    public String toString() {
        return "\nStatut" + game;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public View2 getW() {
        return w;
    }

    public void setW(View2 w) {
        this.w = w;
    }

    public int getSizeGrid() {
        return sizeGrid;
    }

    public void setSizeGrid(int sizeGrid) {
        this.sizeGrid = sizeGrid;
    }

    public int getBitFinparti() {
        return bitFinparti;
    }

    public void setBitFinparti(int bitFinparti) {
        this.bitFinparti = bitFinparti;
    }

    public boolean isImReady() {
        return imReady;
    }

    public void setImReady(boolean imReady) {
        this.imReady = imReady;
    }

    public boolean isHesReady() {
        return hesReady;
    }

    public void setHesReady(boolean hesReady) {
        this.hesReady = hesReady;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public ControlButton getControlButton() {
        return controlButton;
    }

    public void setControlButton(ControlButton controlButton) {
        this.controlButton = controlButton;
    }

    public ControlBarMenu getControlBarMenu() {
        return controlBarMenu;
    }

    public void setControlBarMenu(ControlBarMenu controlBarMenu) {
        this.controlBarMenu = controlBarMenu;
    }

    public boolean isLost() {
        return isLost;
    }

    public void setLost(boolean lost) {
        isLost = lost;
    }

    public ControlClick getControlClick() {
        return controlClick;
    }

    public void setControlClick(ControlClick controlClick) {
        this.controlClick = controlClick;
    }

    public Ordinateur getOrdinateur() {
        return ordinateur;
    }

    public void setOrdinateur(Ordinateur ordinateur) {
        this.ordinateur = ordinateur;
    }

    public boolean isReseauReady() {
        return reseauReady;
    }

    public void setReseauReady(boolean reseauReady) {
        this.reseauReady = reseauReady;
    }

    public ArrayList<Integer[]> getListCoupTouche() {
        return listCoupTouche;
    }

    public void setListCoupTouche(ArrayList<Integer[]> listCoupTouche) {
        this.listCoupTouche = listCoupTouche;
    }

    public boolean isFirstround() {
        return firstround;
    }

    public void setFirstround(boolean firstround) {
        this.firstround = firstround;
    }
}
