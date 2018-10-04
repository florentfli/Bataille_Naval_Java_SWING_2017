import java.util.ArrayList;
import java.util.Random;

import static java.lang.Math.ceil;
import static java.lang.Math.floorDiv;

public class Ordinateur extends Player {

    private Game game;
    private View2 w;

    private boolean haveClue = false;
    private int xPrev, yPrev, x, y, posXinitial = 11, posYinitial = 11;
    private boolean[] dirChecked = {false, false, false, false}; //dir : nord = 1, est = 2, sud = 3, ouest = 4;
    private int dir;
    private ArrayList<Integer> listdirChecked = new ArrayList<>();

    private boolean[][] mapPrediIsAlreadyHit = new boolean[10][10];
    private boolean isCouler = false;

    public Ordinateur(Game game, View2 w, int sizeMap) {
        super("ordinateur");
        this.game = game;
        this.w = w;
        this.initPlayer(sizeMap);
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                mapPrediIsAlreadyHit[i][j] = false;
            }
        }
    }

    public void placeBoatOrdi() {
        //placement boat
        int posX, posY;
        boolean orientation;

        for (int z = 0; z < this.getTabBoatPlayer().length; z++) {
            do {
                posX = (int) ceil(Math.random() * this.getMap().getLengthY()) - 1;
                posY = (int) ceil(Math.random() * this.getMap().getLengthY()) - 1;
                orientation = getRandomBoolean();
            }
            while (!game.isPlacable(this, this.getTabBoatPlayer()[z], posX, posY, orientation));
            if ((!game.isPlacable(this, this.getTabBoatPlayer()[z], posX, posY, orientation))) {
                System.out.println("posX = " + posX);
                System.out.println("posY = " + posY);
                System.out.println("isHorizontal = " + orientation);
                System.out.println(game.isPlacable(this, this.getTabBoatPlayer()[z], posX, posY, orientation));
                System.out.println((this.getMap().getTabMapCase()[posX][posY].getIsOccupied()));
            }
            this.game.placeBoatOnMapOrdinateur(this, this.getTabBoatPlayer()[z], posX, posY, orientation);
        }
        this.convertToTrueMap();
        System.out.println("\nPlacement random ordi finit !");
    }

    private boolean getRandomBoolean() {
        Random random = new Random();
        return random.nextBoolean();
    }

    public void boatHitIAeasy(Player human, View2 v) {
        if (!haveClue) {
            do {
                x = (int) ceil(Math.random() * this.getMap().getLengthY()) - 1;
                y = (int) ceil(Math.random() * this.getMap().getLengthY()) - 1;
            }
            while (mapPrediIsAlreadyHit[x][y] || !this.getMapOponment().getTabMapCase()[x][y].getIsTargettable() || !game.isInMapGame(human, x, y));

            if (human.getMap().getTabMapCase()[x][y].getIsOccupied()) {
                xPrev = x;
                yPrev = y;
                haveClue = true;
            }
            hitIA(human, x, y, v);
        } else {
            if (posXinitial == 11 && posYinitial == 11) {
                posXinitial = x;
                posYinitial = y;
            }
            if (!dirChecked[0] && !dirChecked[1] && !dirChecked[2] && !dirChecked[3]) {
                do {
                    dir = (int) ceil(Math.random() * 4 - 1);
                    switch (dir) {
                        case 0:
                            x = posXinitial;
                            y = posYinitial - 1;
                            break;
                        case 1:
                            x = posXinitial + 1;
                            y = posYinitial;
                            break;
                        case 2:
                            x = posXinitial;
                            y = posYinitial + 1;
                            break;
                        case 3:
                            x = posXinitial - 1;
                            y = posYinitial;
                            break;
                    }
                } while (listdirChecked.contains(dir) || !game.isInMapGame(human, x, y) || mapPrediIsAlreadyHit[x][y]);

                listdirChecked.add(dir);

                if (human.getMap().getTabMapCase()[x][y].getIsOccupied() && human.getMap().getTabMapCase()[x][y].getIsTargettable() && !mapPrediIsAlreadyHit[x][y]) {
                    dirChecked[dir] = true;
                }
                xPrev = x;
                yPrev = y;

                hitIA(human, x, y, v);
            } else {
                if (dirChecked[0]) {
                    if (game.isInMapGame(human, x, y - 1) && this.getMapOponment().getTabMapCase()[x][y - 1].isTargettable()) {
                        y--;
                         hitIA(human, x, y, v);
                        if (!human.getMap().getTabMapCase()[x][y].getIsOccupied()) {
                            dirChecked[0] = false;
                            dirChecked[2] = true;
                            x = posXinitial;
                            y = posYinitial;
                        }
                    } else {
                        System.out.println("// Hors map OU prevision impossible ! //");
                        listdirChecked.add(3 - dir);
                        y = posYinitial + 1;
                        x = posXinitial;
                        dirChecked[0] = false;
                        dirChecked[2] = true;
                        hitIA(human, x, y, v);
                    }
                } else if (dirChecked[1]) {
                    if (game.isInMapGame(human, x + 1, y) && this.getMapOponment().getTabMapCase()[x + 1][y].isTargettable() && !mapPrediIsAlreadyHit[x+1][y]) {
                        x++;
                        listdirChecked.add(dir);
                        hitIA(human, x, y, v);
                        if (!human.getMap().getTabMapCase()[x][y].getIsOccupied()) {
                            dirChecked[1] = false;
                            dirChecked[3] = true;
                            x = posXinitial;
                            y = posYinitial;
                        }
                    } else {
                        System.out.println("// Hors map OU prevision impossible ! //");
                        listdirChecked.add(3 - dir);
                        x = posXinitial - 1;
                        y = posYinitial;
                        dirChecked[1] = false;
                        dirChecked[3] = true;
                        hitIA(human, x, y, v);
                    }
                } else if (dirChecked[2]) {
                    if (game.isInMapGame(human, x, y + 1) && this.getMapOponment().getTabMapCase()[x][y + 1].isTargettable()) {
                        y++;
                        listdirChecked.add(dir);
                        hitIA(human, x, y, v);
                        if (!human.getMap().getTabMapCase()[x][y].getIsOccupied()) {
                            dirChecked[2] = false;
                            dirChecked[0] = true;
                            x = posXinitial;
                            y = posYinitial;
                        }
                    } else {
                        System.out.println("// Hors map OU prevision impossible ! //");
                        listdirChecked.add(3 - dir);
                        y = posYinitial - 1;
                        x = posXinitial;
                        dirChecked[2] = false;
                        dirChecked[0] = true;
                        hitIA(human, x, y, v);
                    }
                } else if (dirChecked[3]) {
                    if (game.isInMapGame(human, x - 1, y) && this.getMapOponment().getTabMapCase()[x - 1][y].isTargettable()) {
                        x--;
                        listdirChecked.add(dir);
                        hitIA(human, x, y, v);
                        if (!human.getMap().getTabMapCase()[x][y].getIsOccupied()) {
                            dirChecked[3] = false;
                            dirChecked[1] = true;
                            x = posXinitial;
                            y = posYinitial;
                        }
                    } else {
                        System.out.println("// Hors map OU prevision impossible ! //");
                        listdirChecked.add(3 - dir);
                        x = posXinitial + 1;
                        y = posYinitial;
                        dirChecked[3] = false;
                        dirChecked[1] = true;
                        hitIA(human, x, y, v);
                    }
                }
                xPrev = x;
                yPrev = y;
            }
        }
        if (isCouler) {
            previsionJeu(human, xPrev, yPrev);
            isCouler = false;
            haveClue = false;
            posYinitial = 11;
            posXinitial = 11;
            listdirChecked.clear();
            System.out.println("\ntarget locked.\n");
            dirChecked[0] = false;
            dirChecked[1] = false;
            dirChecked[2] = false;
            dirChecked[3] = false;

        }
        //printPrevi();
    }

    private int hitIA(Player human, int x, int y, View2 view) {
        this.mapPrediIsAlreadyHit[x][y] = true;
        this.getMapOponment().getTabMapCase()[x][y].setTargettable(false);
        int res = game.boatHitordi(human, x, y, 1);
        if (res==2) isCouler = true;
        //if (checkBoatIsCouler(human, x, y)) isCouler = true;
        //int res = game.boatHit(human, x, y, 1);
        view.placeMarkerLitle(res, x, y);
        return res;
    }

    private void printPrevi() {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if (mapPrediIsAlreadyHit[j][i]) {
                    System.out.print("1 ");
                } else {
                    System.out.print("0 ");
                }
            }
            System.out.println("");
        }
    }

    private void previsionJeu(Player human, int x, int y) {
        for (int i = 0; i < this.getTabBoatPlayer().length; i++) {
            if (human.getTabBoatPlayer()[i].getId() == human.getMap().getTabMapCase()[x][y].getIdBoat()) {
                for (int j = 0; j < human.getTabBoatPlayer()[i].getLength(); j++) {
                    for (int k = -1; k <= 1; k++) {
                        for (int l = -1; l <= 1; l++) {
                            if (human.getTabBoatPlayer()[i].getIsHorizontal()) {
                                if (game.isInMapGame(human, j - (x + k), y + l)) {
                                    mapPrediIsAlreadyHit[j - (x + k)][y + l] = true;
                                }
                            } else {
                                if (game.isInMapGame(human, x + k, j - (y + l))) {
                                    mapPrediIsAlreadyHit[x + k][j - (y + l)] = true;
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private int[] randomCoord(Player human){
        do {
            x = (int) ceil(Math.random() * this.getMap().getLengthY()) - 1;
            y = (int) ceil(Math.random() * this.getMap().getLengthY()) - 1;
        }
        while (mapPrediIsAlreadyHit[x][y] || !this.getMapOponment().getTabMapCase()[x][y].getIsTargettable() || !game.isInMapGame(human, x, y));
        int[] tab = new int[2];
        tab[0]=x;
        tab[1]=y;
        return tab;
    }

    private boolean checkBoatIsCouler(Player human, int x, int y) {
        for (int i = 0; i < human.getTabBoatPlayer().length; i++) {
            if (human.getTabBoatPlayer()[i].getId() == human.getMap().getTabMapCase()[x][y].getIdBoat()) {
                if (human.getTabBoatPlayer()[i].getIsCouler() || human.getTabBoatPlayer()[i].getHealth() <= 0) {
                    return true;
                }
            }
        }
        return false;
    }

    /*private boolean checkBoatIscouler(Player human, int x, int y) {
        boolean isX = false;
        for (int z = 0; z < human.getTabBoatPlayer().length; z++) {
            for (int j = 0; j < human.getTabBoatPlayer()[z].getLength();j++){
                if (human.getTabBoatPlayer()[z].getPosX()+j == x){
                    if (human.getTabBoatPlayer()[z].getIsCouler() || human.getTabBoatPlayer()[z].getHealth() <= 0){
                       isX = true;
                    }
                }
            }
            for (int j = 0; j < human.getTabBoatPlayer()[z].getLength();j++){
                if (human.getTabBoatPlayer()[z].getPosY()+j == y){
                    if (human.getTabBoatPlayer()[z].getIsCouler() || human.getTabBoatPlayer()[z].getHealth() <= 0){
                        if (isX) return true;
                    }
                }
            }
        }
        return false;
    }*/
}
