import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class ControlClick implements MouseListener {


    private View2 view;
    private Game game;
    private Battle batle;
    private Boat saveBoat;
    int res, xPrev = -1, yPrev = -1;
    private int coordinatesYGrid = -1, coordinatesXGrid = -1, previousCoordinatesYGrid, previousCoordinatesXGrid, idBoat;
    private boolean isABoatSelected, isBoatTemporyPlaced, isMoveable, isABoatToDelete;
    private String position;


    public ControlClick(View2 view, Game game, Battle batle) {
        this.view = view;
        this.game = game;
        this.batle = batle;
    }


    ///////////////////////////////////////////Action au clique de la souris///////////////////////////////////////////
    @Override
    public void mouseClicked(MouseEvent e) {
        if (view.getStateApp() == 6) {
            changeDirectionArrow(e);
            selectBoat(e);
            if (saveBoat != null) {
                placeBoat();
                moveBoat(e);
            }
            loadGame(e);
        } else if (view.getStateApp() == 7) {
            updateCoordinatesAndCursorInGame(e);
            setShotPosition();
            selectSquare(e);
        }
    }


    private void changeDirectionArrow(MouseEvent e) {
        if (e.getSource() == view.getLabelDirection()) {
            if (view.getLabelDirection().getIcon().equals(view.getImageDirectionHorizontal())) {
                if (saveBoat != null) saveBoat.setIsHorizontal(false);
                if (!isABoatSelected || isPlacable() && isNotOccupied()) {
                    view.getLabelDirection().setIcon(view.getImageDirectionVertical());
                    changeBoatDirection(e);
                    view.repaint();
                } else {
                    saveBoat.setIsHorizontal(true);
                    view.dialogBadDirectionBoatPlacement();
                }
            } else {
                if (saveBoat != null) saveBoat.setIsHorizontal(true);
                if (!isABoatSelected || isPlacable() && isNotOccupied()) {
                    view.getLabelDirection().setIcon(view.getImageDirectionHorizontal());
                    changeBoatDirection(e);
                    view.repaint();
                } else {
                    saveBoat.setIsHorizontal(false);
                    view.dialogBadDirectionBoatPlacement();
                }
            }
        }
    }


    private void selectBoat(MouseEvent e) {
        if (getBoat(e) != null) {
            if (!isBoatTemporyPlaced) {
                if (!isABoatSelected) {
                    isABoatSelected = true;
                } else {
                    deleteEntireBoat(coordinatesYGrid, coordinatesXGrid);
//                    //                // Mettre à -1 permet de changer de bateau
                    coordinatesYGrid = -1;
                    coordinatesXGrid = -1;
                }
                saveBoat = getBoat(e);

                if (!saveBoat.getIsPlacer()) {
                    if (view.getLabelDirection().getIcon().equals(view.getImageDirectionHorizontal())) {
                        saveBoat.setIsHorizontal(true);
                    } else if (view.getLabelDirection().getIcon().equals(view.getImageDirectionVertical())) {
                        saveBoat.setIsHorizontal(false);
                    }
                } else {
                    reinitialize();
                }
            }
        }
    }

    /*private boolean isAllboatPlacedType(Boat boat){
        int count=0;
        for (int i = 0; i< game.getPlayer1().getTabBoatPlayer().length; i++){
            System.out.println("type " + game.getPlayer1().getTabBoatPlayer()[i].getType());
            if (boat.getType().equals(game.getPlayer1().getTabBoatPlayer()[i].getType()) ){
                System.out.println("meme type");
                if (game.getPlayer1().getTabBoatPlayer()[i].getIsPlacer()) {
                    count++;
                    System.out.println("is Placer lol");
                }
            }
        }
        System.out.println("count = "+count);
        System.out.println("torpilleur = "+game.getTorpilleur());
        if (boat.getType().equals("torpilleur")){
            return  count == game.getTorpilleur();
        }else if (boat.getType().equals("contre-torpilleur")){
            return  count == game.getContreTorpilleur();
        }else if (boat.getType().equals("sous-marin")){
            return  count == game.getSousMarin();
        }else if (boat.getType().equals("croiseur")){
            return  count == game.getCroiseur();
        }else if (boat.getType().equals("porteAvion")){
            return  count == game.getPorteAvion();
        }
        return false;
    }*/


    private void placeBoat() {
        if (isPlacable() && isNotOccupied() && isABoatSelected) {
            if (!isBoatTemporyPlaced) {
                isBoatTemporyPlaced = true;

                view.getGridLabelBigGrid()[coordinatesYGrid][coordinatesXGrid].setBackground(Color.GREEN);

                saveBoat.setPosY(coordinatesYGrid);
                saveBoat.setPosX(coordinatesXGrid);
            }
        }
    }


    private void moveBoat(MouseEvent e) { //@formatter:off
        if (isMoveable && isBoatTemporyPlaced && coordinatesYGrid == saveBoat.getPosY() &&
                coordinatesXGrid == saveBoat.getPosX() && e.getSource() != view.getLabelDirection() ) { //@formatter:on
            if (e.getSource() != view.getLabelTorpilleur() &&
                    e.getSource() != view.getLabelSousMarin() && e.getSource() != view.getLabelContreTorpilleur() &&
                    e.getSource() != view.getLabelCroiseur() && e.getSource() != view.getLabelPorteAvion()) {
                isBoatTemporyPlaced = false;
                isMoveable = false;
            } else view.dialogSelectionBoat();
        }
    }


    private void loadGame(MouseEvent e) {
        if (e.getSource() == view.getLabelOk() && isAllBoatPlaced()) {
            view.dialogConfirmAllBoatPlacement();
            if (game.isOnline() && game.isServer()) {
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException i) {
                    i.printStackTrace();
                }
                ThreadReady recevoir = new ThreadReady(batle, true, 3);
                ThreadReady envoyer = new ThreadReady(batle, false, 1);
                ThreadReady envoyer2 = new ThreadReady(batle, false, 2);

                System.out.println("Joueur1 pret");
                recevoir.start();
                envoyer.start();

                // Deroulement
                while (!batle.isHesReady()) {
                    System.out.print("");
                }

                System.out.println("joueur2 pret");
                //Envoyer.envoyer(game.getIpAdverse(), game.getPort(), "1");
                if (batle.getOrder() == 1) {
                    envoyer2.start();
                }
            }
            if (game.isOnline() && !game.isServer()) {
                batle.setImReady(true);
                // partie en réseau :
                // juste a attendre que l'autre joueur est placé ses ships...
                ThreadReady recevoir = new ThreadReady(batle, true, 3);
                ThreadReady envoyer = new ThreadReady(batle, false, 1);
                ThreadReady envoyer2 = new ThreadReady(batle, false, 2);

                System.out.println("Joueur1 pret");
                recevoir.start();
                envoyer.start();
                // Deroulement
                while (!batle.isHesReady()) {
                    System.out.print("");
                }
                System.out.println("joueur2 pret");
                //Envoyer.envoyer(game.getIpAdverse(), game.getPort(), "1");
                if (batle.getOrder() == 1) {
                    envoyer2.start();
                    System.out.println("pause");
                    try {
                        Thread.sleep(6000);
                    } catch (InterruptedException i) {
                        i.printStackTrace();
                    }
                }
            }
            if (view.getStateApp() == 7) {
                view.changeView(7);
                view.listenerCreateGame(this);
                view.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                view.drawBoatPlaced(view.getGridLabelLittleGrid());
            }
            if (game.isServer() && game.isOnline()) {
                System.out.println("en attente...");
                String msgRecus = Recevoir.recevoir(game.getPort());
                System.out.println(msgRecus);
                System.out.println("recus!!!");

                int x = Decodage.getX(msgRecus);
                int y = Decodage.getY(msgRecus);
                int type = Decodage.getTypeMissile(msgRecus);

                batle.hit(game.getPlayer1(), x, y, type, view);
            }
        }
    }


    private Boat getBoat(MouseEvent e) {
        if (e.getSource() == view.getLabelTorpilleur() && game.getTorpilleur() > 0) {
            return game.getPlayer1().getTabBoatPlayer()[0];
        } else if (e.getSource() == view.getLabelSousMarin() && game.getSousMarin() > 0) {
            return game.getPlayer1().getTabBoatPlayer()[1];
        } else if (e.getSource() == view.getLabelContreTorpilleur() && game.getContreTorpilleur() > 0) {
            return game.getPlayer1().getTabBoatPlayer()[2];
        } else if (e.getSource() == view.getLabelCroiseur() && game.getCroiseur() > 0) {
            return game.getPlayer1().getTabBoatPlayer()[3];
        } else if (e.getSource() == view.getLabelPorteAvion() && game.getPorteAvion() > 0) {
            return game.getPlayer1().getTabBoatPlayer()[4];
        } else return null;
    }


    private void changeBoatDirection(MouseEvent e) {
        if (saveBoat != null && isPlacable() && isNotOccupied() && e.getSource() == view.getLabelDirection()) { // Change la direction du bateau
            deletePreviousBoatPlacement();
            setNewBoatPlacement(1);
        }
    }

    private void deletePreviousBoatPlacement() {
        // Si la flèche est maintenant vertical il faut supprimer la position précédente du bateau qui était horizontal
        if (!isHorizontal() && isCoordinatesInGridPlayer(0)) {
            int z = saveBoat.getLength();
            while (z > 0) {
                if (coordinatesXGrid + z < 10) {
                    view.getGridLabelBigGrid()[coordinatesYGrid][coordinatesXGrid + z].setIcon(null);
                    view.getGridLabelBigGrid()[coordinatesYGrid][coordinatesXGrid + z].setText("");
                    view.getGridLabelBigGrid()[coordinatesYGrid][coordinatesXGrid + z].setOpaque(false);
                }
                z--;
            }
        } else if (isHorizontal() && isCoordinatesInGridPlayer(1)) { // Si la flèche est maintenant horizontal il
            // faut supprimer la position précédente du bateau qui était vertical
            int z = saveBoat.getLength();
            while (z > 0) {
                if (coordinatesYGrid + z < 10) {
                    view.getGridLabelBigGrid()[coordinatesYGrid + z][coordinatesXGrid].setIcon(null);
                    view.getGridLabelBigGrid()[coordinatesYGrid + z][coordinatesXGrid].setText("");
                    view.getGridLabelBigGrid()[coordinatesYGrid + z][coordinatesXGrid].setOpaque(false);
                }
                z--;
            }
        }
    }


    private boolean isHorizontal() {
        return saveBoat.getIsHorizontal();
    }

    private boolean isCoordinatesInGridPlayer(int i) {//@formatter:off
        if (i == 0) // Flèche horizontal
            return coordinatesYGrid > -1 && coordinatesXGrid > -1 && previousCoordinatesYGrid > -1 &&
                    previousCoordinatesXGrid > -1 && coordinatesYGrid < 10 && previousCoordinatesYGrid < 10 &&
                    coordinatesXGrid + saveBoat.getLength() - 1 < 10 && previousCoordinatesXGrid + saveBoat.getLength() - 1 < 10;
        else // Flèche vertical
            return coordinatesXGrid > -1 && coordinatesYGrid > -1 && previousCoordinatesXGrid > -1 &&
                    previousCoordinatesYGrid > -1 && coordinatesXGrid < 10 && previousCoordinatesXGrid < 10 &&
                    coordinatesYGrid + saveBoat.getLength() - 1 < 10 && previousCoordinatesYGrid + saveBoat.getLength() - 1 < 10;
    }//@formatter:on


    private boolean isAllBoatPlaced() {
        int count = 0;
        for (int i = 0; i < game.getPlayer1().getTabBoatPlayer().length; i++) {
            if (game.getPlayer1().getTabBoatPlayer()[i].getIsPlacer()) {
                count++;
            }
        }
        return count == game.getPlayer1().getTabBoatPlayer().length;
    }

    private void setShotPosition() {
        switch (coordinatesXGrid) {
            case 0:
                position = "A";
                break;
            case 1:
                position = "B";
                break;
            case 2:
                position = "C";
                break;
            case 3:
                position = "D";
                break;
            case 4:
                position = "E";
                break;
            case 5:
                position = "F";
                break;
            case 6:
                position = "G";
                break;
            case 7:
                position = "H";
                break;
            case 8:
                position = "I";
                break;
            case 9:
                position = "J";
                break;
        }
        position += coordinatesYGrid + 1;
    }


    private void selectSquare(MouseEvent e) {

        if (e.getSource() != view.getLabelFire()) {
            if (game.getPlayer1().getMapOponment().getTabMapCase()[coordinatesXGrid][coordinatesYGrid].getIsTargettable()) {
                view.getLabelPosition().setText(position);
                view.placeMarkerBig(3, coordinatesXGrid, coordinatesYGrid);
                if ((xPrev != -1 || yPrev != -1) && (!view.getGridLabelBigGrid()[yPrev][xPrev].getIcon().equals(view.getRedDot())
                        && !view.getGridLabelBigGrid()[yPrev][xPrev].getIcon().equals(view.getBlackDot()) &&
                        !view.getGridLabelBigGrid()[yPrev][xPrev].getIcon().equals(view.getWhiteDot()))) {
                    view.getGridLabelBigGrid()[yPrev][xPrev].setIcon(null);
                }
                xPrev = coordinatesXGrid;
                yPrev = coordinatesYGrid;
            }
        } else if (game.getPlayer1().getMapOponment().getTabMapCase()[coordinatesXGrid][coordinatesYGrid].getIsTargettable()) {
            fire();
        }
    }


    private void fire() {
        if (game.isMulti()) {
            if (game.isOnline()) {
                if (game.isServer()) {
                    if (game.isClassic()) {
                        //Deroulement
                        if (!batle.isLost() && batle.getBitFinparti() == 0) {
                            deroulementGameOnline();
                        }
                        if (batle.isLost()) {
                            Envoyer.envoyer(game.getIpAdverse(),game.getPort(),"1/1/1");
                            view.dialogueLostOnline();
                            view.changeView(1);
                        } else if (batle.getBitFinparti() == 1) {
                            view.dialogWinOnline();
                            view.changeView(1);
                        }
                    } else {

                    }
                } else {
                    if (batle.isFirstround()) {
                        game.getPlayer1().getMapOponment().getTabMapCase()[coordinatesXGrid][coordinatesYGrid].setTargettable(false);
                        System.out.println("envoi");
                        Envoyer.envoyer(game.getIpAdverse(), game.getPort(), "" + coordinatesXGrid + "/" + coordinatesYGrid + "/" + 1);
                        System.out.println("envoyé");

                        String resultatMsgFirst = Recevoir.recevoir(game.getPort());
                        int resultat = Decodage.getResultatClassic(resultatMsgFirst);
                        view.placeMarkerBig(resultat, coordinatesXGrid, coordinatesYGrid);
                        Integer[] tab = new Integer[2];
                        tab[0] = coordinatesXGrid;
                        tab[1] = coordinatesYGrid;
                        if (resultat == 1) {
                            System.out.println("x = " + coordinatesXGrid + "\ny = " + coordinatesYGrid);
                            batle.getListCoupTouche().add(tab);
                        }
                        if (resultat == 2) {
                            batle.getListCoupTouche().add(tab);
                            batle.blackBoat(view);
                        }
                        batle.setBitFinparti(Decodage.bitdeFinPartie(resultatMsgFirst));
                        batle.setFirstround(false);

                        //attente du coup
                        String msgRecus = Recevoir.recevoir(game.getPort());

                        int x = Decodage.getX(msgRecus);
                        int y = Decodage.getY(msgRecus);
                        int type = Decodage.getTypeMissile(msgRecus);

                        batle.hit(game.getPlayer1(), x, y, type, view);

                        try {
                            Thread.sleep(5000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    } else {
                        if (batle.getBitFinparti() == 0 && !batle.isLost()) {
                            deroulementGameOnline();
                        }
                    }
                    // Fin
                    if (batle.isLost() ) {
                        Envoyer.envoyer(game.getIpAdverse(),game.getPort(),"1/1/1");
                        view.dialogueLostOnline();
                        view.changeView(1);
                    }else if( batle.getBitFinparti() == 1){
                        view.dialogWinOnline();
                        view.changeView(1);
                    }
                }
            } else {
                if (game.isClassic()) {

                } else {

                }
            }
        } else {
            if (!batle.imLost(game.getPlayer1()) && !batle.imLost(batle.getOrdinateur())) {
                System.out.println("\n-----------------------------\nTour de l'ordi :");
                batle.getOrdinateur().boatHitIAeasy(game.getPlayer1(), view);

                if ((!batle.imLost(game.getPlayer1()) && !batle.imLost(batle.getOrdinateur()))) {
                    System.out.println("\n-----------------------------\nTour de " + game.getPlayer1().getName() + " :");
                    res = game.boatHit(batle.getOrdinateur(), coordinatesXGrid, coordinatesYGrid, 1);
                    this.view.placeMarkerBig(res, coordinatesXGrid, coordinatesYGrid);
                    Integer[] tab = new Integer[2];
                    tab[0] = coordinatesXGrid;
                    tab[1] = coordinatesYGrid;
                    if (res == 1) {
                        System.out.println("x = " + coordinatesXGrid + "\ny = " + coordinatesYGrid);
                        batle.getListCoupTouche().add(tab);
                    }
                    if (res == 2) {
                        batle.getListCoupTouche().add(tab);
                        batle.blackBoat(view);
                    }
                    game.getPlayer1().getMapOponment().getTabMapCase()[coordinatesXGrid][coordinatesYGrid].setTargettable(false);
                }
            }
            if (batle.imLost(batle.getOrdinateur())) {
                view.dialogWinOrdi();
                view.changeView(1);
            } else if (batle.imLost(game.getPlayer1())) {
                view.dialogueLostOrdi();
                view.changeView(1);
            }
        }
    }

    public void deroulementGameOnline() {
        game.getPlayer1().getMapOponment().getTabMapCase()[coordinatesXGrid][coordinatesYGrid].setTargettable(false);
        Envoyer.envoyer(game.getIpAdverse(), game.getPort(), "" + coordinatesXGrid + "/" + coordinatesYGrid + "/" + 1);
        String resultatMsg = Recevoir.recevoir(game.getPort());

        int resultat = Decodage.getResultatClassic(resultatMsg);
        batle.setBitFinparti(Decodage.bitdeFinPartie(resultatMsg));
        view.placeMarkerBig(resultat, coordinatesXGrid, coordinatesYGrid);

        Integer[] tab = new Integer[2];
        tab[0] = coordinatesXGrid;
        tab[1] = coordinatesYGrid;
        if (resultat == 1) {
            batle.getListCoupTouche().add(tab);
        }
        if (resultat == 2) {
            batle.getListCoupTouche().add(tab);
            batle.blackBoat(view);
        }

        game.getPlayer1().getMapOponment().getTabMapCase()[coordinatesXGrid][coordinatesYGrid].setTargettable(false);

        System.out.println("en attente...");
        String msgRecus = Recevoir.recevoir(game.getPort());

        int x = Decodage.getX(msgRecus);
        int y = Decodage.getY(msgRecus);
        int type = Decodage.getTypeMissile(msgRecus);

        batle.hit(game.getPlayer1(), x, y, type, view);

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /////////////////////////////////////////Fin: action au clique de la souris/////////////////////////////////////////

    ////////////////////////////////////////////Action au survol d'un élément///////////////////////////////////////////
    @Override
    public void mouseEntered(MouseEvent e) {
        if (view.getStateApp() == 6) {
            changeCursor(e);
            if (isABoatSelected && !isBoatTemporyPlaced && saveBoat != null) {
                updateCoordinatesAndCursorInBoatPlacement(e);
                deletePreviousTemporaryBoatPlacement();
                drawDesiredPlacementBoat();
            }
        } else if (view.getStateApp() == 7) {
            changeCursor(e);
            if (coordinatesYGrid != -1 && coordinatesXGrid != -1)
                view.getGridLabelBigGrid()[coordinatesYGrid][coordinatesXGrid].setCursor(new Cursor(Cursor.HAND_CURSOR));
        }
    }


    private void changeCursor(MouseEvent e) {
        if (view.getStateApp() == 6) {
            // @formatter:off
            if (e.getSource() == view.getLabelDirection() || e.getSource() == view.getLabelOk() || getBoat(e) != null
                    && !getBoat(e).getIsPlacer()) {
                view.setCursor(new Cursor(Cursor.HAND_CURSOR));
            }
            if (view.getLabelDelete().getIcon() != null) view.getLabelDelete().setCursor(new Cursor(Cursor.HAND_CURSOR));

        } else if (view.getStateApp() == 7) {
            if (e.getSource() == view.getLabelFire()) { // @formatter:on
                view.setCursor(new Cursor(Cursor.HAND_CURSOR));
            }
        }
    }


    private void drawDesiredPlacementBoat() {
        view.drawBoatPlaced(view.getGridLabelBigGrid());
        if (isPlacable() && isNotOccupied()) setNewBoatPlacement(1); // Placement temporaire
        else setNewBoatPlacement(0); // Placement temporaire
    }


    private void updateCoordinatesAndCursorInBoatPlacement(MouseEvent e) {
        int y = (e.getComponent().getY() / 59) - 1;
        int x = (e.getComponent().getX() / 50) - 1;
        if (isCursorInGridPlayer(y, x)) {
            previousCoordinatesYGrid = coordinatesYGrid;
            previousCoordinatesXGrid = coordinatesXGrid;
            // Vérifie qu'on ait sélectionné une case de la grille
            coordinatesYGrid = (e.getComponent().getY() / 59) - 1; // On enlève 1 car l'indice du tableau commence à 0
            // et non à 1
            coordinatesXGrid = (e.getComponent().getX() / 50) - 1; // On enlève 1 car l'indice du tableau commence à 0
            // et non à 1
        }
        if (coordinatesYGrid != -1 && coordinatesXGrid != -1)
            view.getGridLabelBigGrid()[coordinatesYGrid][coordinatesXGrid].setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    private void updateCoordinatesAndCursorInGame(MouseEvent e) {
        int y = (e.getComponent().getY() / 59) - 1;
        int x = (e.getComponent().getX() / 59) - 1;
        if (isCursorInGridPlayer(y, x)) {
            previousCoordinatesYGrid = coordinatesYGrid;
            previousCoordinatesXGrid = coordinatesXGrid;
            // Vérifie qu'on ait sélectionné une case de la grille
            coordinatesYGrid = (e.getComponent().getY() / 59) - 1; // On enlève 1 car l'indice du tableau commence à 0
            // et non à 1
            coordinatesXGrid = (e.getComponent().getX() / 59) - 1; // On enlève 1 car l'indice du tableau commence à 0
            // et non à 1
        }
        if (coordinatesYGrid != -1 && coordinatesXGrid != -1)
            view.getGridLabelBigGrid()[coordinatesYGrid][coordinatesXGrid].setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    private boolean isCursorInGridPlayer(int y, int x) {
        return (y >= 0 && x >= 0 && y < 10 && x < 10);
    }

    private void deletePreviousTemporaryBoatPlacement() {
        if (isCursorInGridPlayer(previousCoordinatesYGrid, previousCoordinatesXGrid)) {
            if (isHorizontal()) { //La flèche est horizontal
                if (previousCoordinatesYGrid != coordinatesYGrid) { // Si les coordonnées Y sont différentes
                    deleteEntireBoat(previousCoordinatesYGrid, previousCoordinatesXGrid);
                } else if (previousCoordinatesXGrid + saveBoat.getLength() - 1 < 10 && coordinatesXGrid == previousCoordinatesXGrid - 1) { // Si on déplace la bateau vers la gauche
                    deleteABoatSquare(saveBoat.getLength() - 1);
                } else if (coordinatesXGrid == previousCoordinatesXGrid + 1) { // Si on se déplace vers la droite
                    deleteABoatSquare(0);
                } else {
                    deleteEntireBoat(previousCoordinatesYGrid, previousCoordinatesXGrid);
                }
            } else if (!isHorizontal()) { //est vertical
                if (previousCoordinatesXGrid != coordinatesXGrid) { // Si les coordonnées X sont différentes
                    deleteEntireBoat(previousCoordinatesYGrid, previousCoordinatesXGrid);
                } else if (previousCoordinatesYGrid + saveBoat.getLength() - 1 < 10 && coordinatesYGrid == previousCoordinatesYGrid - 1) { // Dépacement vers le haut
                    deleteABoatSquare(saveBoat.getLength() - 1);
                } else if (previousCoordinatesYGrid == coordinatesYGrid - 1) { //Deplacement vers le bas
                    deleteABoatSquare(0);
                } else deleteEntireBoat(previousCoordinatesYGrid, previousCoordinatesXGrid);
            }
        }
    }

    public void deleteEntireBoat(int y, int x) {
        int i = 0;
        if (isHorizontal()) {
            while (i < saveBoat.getLength()) {
                if (isCursorInGridPlayer(y, x + i)) {
                    view.getGridLabelBigGrid()[y][x + i].setIcon(null);
                    view.getGridLabelBigGrid()[y][x + i].setText("");
                    view.getGridLabelBigGrid()[y][x + i].setOpaque(false);
                    view.getGridLabelBigGrid()[y][x + i].
                            setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                }
                i++;
            }
        } else {
            while (i < saveBoat.getLength()) {
                if (y + i < 10) {
                    view.getGridLabelBigGrid()[y + i][x].setIcon(null);
                    view.getGridLabelBigGrid()[y + i][x].setText("");
                    view.getGridLabelBigGrid()[y + i][x].setOpaque(false);
                    view.getGridLabelBigGrid()[y + i][x].
                            setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                }
                i++;
            }
        }
    }

    public void deleteABoatSquare(int previousSquare) {
        if (isHorizontal()) {
            view.getGridLabelBigGrid()[previousCoordinatesYGrid][previousCoordinatesXGrid + previousSquare].
                    setIcon(null);
            view.getGridLabelBigGrid()[previousCoordinatesYGrid][previousCoordinatesXGrid + previousSquare].
                    setText("");
            view.getGridLabelBigGrid()[previousCoordinatesYGrid][previousCoordinatesXGrid + previousSquare].
                    setOpaque(false);
            view.getGridLabelBigGrid()[previousCoordinatesYGrid][previousCoordinatesXGrid + previousSquare].
                    setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        } else {
            view.getGridLabelBigGrid()[previousCoordinatesYGrid + previousSquare][previousCoordinatesXGrid].
                    setIcon(null);
            view.getGridLabelBigGrid()[previousCoordinatesYGrid + previousSquare][previousCoordinatesXGrid].
                    setText("");
            view.getGridLabelBigGrid()[previousCoordinatesYGrid + previousSquare][previousCoordinatesXGrid].
                    setOpaque(false);
            view.getGridLabelBigGrid()[previousCoordinatesYGrid + previousSquare][previousCoordinatesXGrid].
                    setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        }
    }


    private boolean isPlacable() {
        return game.isPlacable(game.getPlayer1(), saveBoat, coordinatesXGrid + 1, coordinatesYGrid + 1, isHorizontal());
    }


    private boolean isNotOccupied() {
        return !game.checkIsOcupied(game.getPlayer1(), coordinatesXGrid + 1, coordinatesYGrid + 1);
    }


    private void setNewBoatPlacement(int desiredPlacement) {
        if (isCursorInGridPlayer(coordinatesYGrid, coordinatesXGrid)) {
            if (isHorizontal()) { // Flèche horizontal
                int z = 0;
                if (desiredPlacement == 1) {
                    while (z < saveBoat.getLength()) {
                        String i = Integer.toString(z+1);
                        ImageIcon img = new ImageIcon(new ImageIcon(getClass().getResource("./image/"+game.getTheme()+"/"+saveBoat.getType()+"/horizontal/"+i+".png")).
                                getImage().getScaledInstance(59,59,Image.SCALE_DEFAULT));
                        view.getGridLabelBigGrid()[coordinatesYGrid][coordinatesXGrid + z].setIcon(img);
                        view.getGridLabelBigGrid()[coordinatesYGrid][coordinatesXGrid + z].setText(" ");
                        view.getGridLabelBigGrid()[coordinatesYGrid][coordinatesXGrid + z].setOpaque(false);
                        z++;
                    }
                } else {
                    while (z < saveBoat.getLength()) {
                        if (coordinatesXGrid + z < 10) {
                            view.getGridLabelBigGrid()[coordinatesYGrid][coordinatesXGrid + z].setText(" ");
                            view.getGridLabelBigGrid()[coordinatesYGrid][coordinatesXGrid + z].setBackground(Color.RED);
                            view.getGridLabelBigGrid()[coordinatesYGrid][coordinatesXGrid + z].setOpaque(false);
                        }
                        z++;
                    }
                }
            } else if (!isHorizontal()) { // Flèche vertical
                int z = 0;
                if (desiredPlacement == 1) {
                    while (z < saveBoat.getLength()) {
                        String i = Integer.toString(z+1);
                        ImageIcon img = new ImageIcon(new ImageIcon(getClass().getResource("./image/"+game.getTheme()+"/"+saveBoat.getType()+"/vertical/"+i+".png")).
                                getImage().getScaledInstance(55,59,Image.SCALE_DEFAULT));
                        view.getGridLabelBigGrid()[coordinatesYGrid + z][coordinatesXGrid].setIcon(img);
                        view.getGridLabelBigGrid()[coordinatesYGrid + z][coordinatesXGrid].setText(" ");
                        view.getGridLabelBigGrid()[coordinatesYGrid + z][coordinatesXGrid].setOpaque(false);
                        z++;
                    }
                } else {
                    while (z < saveBoat.getLength()) {
                        if (coordinatesYGrid + z < 10) {
                            view.getGridLabelBigGrid()[coordinatesYGrid + z][coordinatesXGrid].setText(" ");
                            view.getGridLabelBigGrid()[coordinatesYGrid + z][coordinatesXGrid].setBackground(Color.RED);
                            view.getGridLabelBigGrid()[coordinatesYGrid + z][coordinatesXGrid].setOpaque(false);
                        }
                        z++;
                    }
                }
            }
        }
    }
    /////////////////////////////////////////Fin: action au survol d'un élément/////////////////////////////////////////


    /////////////////////////////////////Action dès qu'un élément n'est plus survolé////////////////////////////////////
    @Override
    public void mouseExited(MouseEvent e) {
        view.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
    }
    ///////////////////////////////////Fin: action dès qu'un élément n'est plus survolé/////////////////////////////////


    ///////////////////////////////////Action quand le bouton de la souris est relachée/////////////////////////////////
    @Override
    public void mouseReleased(MouseEvent e) {
        if (view.getStateApp() == 6) {
            drawImageOkReleased(e);
            drawImageDeleteReleased(e);
        } else if (view.getStateApp() == 7) {
            view.getLabelFire().setOpaque(false);
            view.getLabelFire().setBackground(null);
        }
    }


    private void drawImageOkReleased(MouseEvent e) {
        if (e.getSource() == view.getLabelOk()) {
            view.getLabelOk().setIcon(view.getImageOkReleased());
            view.repaint();
        }
    }


    private void drawImageDeleteReleased(MouseEvent e) {
        if (e.getSource() == view.getLabelDelete()) {
            view.getLabelDelete().setIcon(view.getImageDeleteReleased());
            view.repaint();
        }
    }
    ///////////////////////////////Fin: action quand le bouton de la souris est relachée////////////////////////////////


    ///////////////////////////////////Action quand le bouton de la souris est pressé/////////////////////////////////
    @Override
    public void mousePressed(MouseEvent e) {
        if (view.getStateApp() == 6) {
            authorizeToMoveBoat(e);
            validateBoatPosition(e);

            drawImageOkClicked(e);
            drawImageDeleteClicked(e);

            deleteBoat(e);
        }
        else if (view.getStateApp() == 7) {
            setBackgroundLabelFire(e);
        }
    }


    private void authorizeToMoveBoat(MouseEvent e) {
        if (isBoatTemporyPlaced && saveBoat != null) {
            updateCoordinatesAndCursorInBoatPlacement(e);
            isMoveable = true;
        }
    }


    private void validateBoatPosition(MouseEvent e) {
        if (e.getSource() == view.getLabelOk()) {
            if (isBoatTemporyPlaced) {
                game.placeBoatOnMap(game.getPlayer1(), saveBoat, saveBoat.getPosX(), saveBoat.getPosY(), saveBoat.getIsHorizontal());
                if (view.getLabelDelete().getIcon() == null) drawImageDeleteReleased();
                reinitialize();
            }
        }
    }

    private void drawImageDeleteReleased() {
        view.getLabelDelete().setIcon(view.getImageDeleteReleased());
        view.repaint();
        view.getLabelDelete().addMouseListener(this);
    }

    private void reinitialize() {
        isBoatTemporyPlaced = false;
        isABoatSelected = false;
        isMoveable = false;
        isABoatToDelete = false;
        coordinatesYGrid = -1;
        coordinatesXGrid = -1;
        saveBoat = null;
    }


    private void drawImageOkClicked(MouseEvent e) {
        if (e.getSource() == view.getLabelOk()) {
            view.getLabelOk().setIcon(view.getImageOkClicked());
            view.repaint();
        }
    }


    private void drawImageDeleteClicked(MouseEvent e) {
        if (e.getSource() == view.getLabelDelete()) {
            view.getLabelDelete().setIcon(view.getImageDeleteClicked());
            view.repaint();
        }
    }


    private void deleteBoat(MouseEvent e) {
        if (!isABoatToDelete && saveBoat == null) {
            updateCoordinatesAndCursorInBoatPlacement(e);
            setBoatTodelete();
        }
        if (isBoatDeleteable()) {
            deletePlacementBoat(e);
        }
    }

    private boolean isBoatDeleteable() { //formatter:off
        return isCursorInGridPlayer(coordinatesYGrid, coordinatesXGrid) && isABoatPlaced() && isABoatToDelete &&
                view.getGridLabelBigGrid()[coordinatesYGrid][coordinatesXGrid].getCursor().getType() == Cursor.HAND_CURSOR;
    } //formatter:on

    private boolean isABoatPlaced() {
        /*return game.getPlayer1().getTabBoatPlayer()[0].getIsPlacer() || game.getPlayer1().getTabBoatPlayer()[1].
                getIsPlacer() || game.getPlayer1().getTabBoatPlayer()[2].getIsPlacer() && game.getPlayer1().
                getTabBoatPlayer()[3].getIsPlacer() || game.getPlayer1().getTabBoatPlayer()[4].getIsPlacer();*/
        int count = 0;
        for (int i = 0; i < game.getPlayer1().getTabBoatPlayer().length; i++) {
            if (game.getPlayer1().getTabBoatPlayer()[i].getIsPlacer()) {
                count++;
            }
        }
        return count > 0;
    }

    private void setBoatTodelete() {
        if (isCursorInGridPlayer(coordinatesYGrid, coordinatesXGrid) && !isNotOccupied()) {
            view.getGridLabelBigGrid()[coordinatesYGrid][coordinatesXGrid].setBackground(Color.DARK_GRAY);
            idBoat = game.getPlayer1().getMap().getTabMapCase()[coordinatesXGrid][coordinatesYGrid].getIdBoat();
            saveBoat = game.getPlayer1().getTabBoatPlayer()[idBoat];
            isABoatToDelete = true;
        }
    }

    private void deletePlacementBoat(MouseEvent e) {
        if (e.getSource() == view.getLabelDelete()) {
            coordinatesYGrid = saveBoat.getPosY()-1;
            coordinatesXGrid = saveBoat.getPosX()-1;
            game.deleteBoat(game.getPlayer1(), coordinatesXGrid, coordinatesYGrid);
            deleteEntireBoat(coordinatesYGrid, coordinatesXGrid);
            reinitialize();
        }
    }


    private void setBackgroundLabelFire(MouseEvent e) {
        if (e.getSource() == view.getLabelFire()) {
            view.getLabelFire().setOpaque(true);
            view.getLabelFire().setBackground(Color.GRAY);
        }
    }
    ///////////////////////////////Fin: action quand le bouton de la souris est pressé////////////////////////////////

}

