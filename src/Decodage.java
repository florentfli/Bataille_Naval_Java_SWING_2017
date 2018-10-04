import java.util.regex.Pattern;

public class Decodage {
    //pour le join
    public static String getIP(String message){
        String[] tableau = message.split(Pattern.quote("/"));//place les elements du message dans un tableau de string
        String IP = tableau[0];
        return IP;
    }
    //bit a 1 pret
    public static int getBitready(String message){
        String[] tableau = message.split(Pattern.quote("/"));//place les elements du message dans un tableau de string
        System.out.println(message);
        int bit = Integer.parseInt(tableau[0]);
        return bit;
    }
    //place les elements du message dans un tableau de string

    //message d'envoi de coup==  coordonnéeX/coordonnéeY/typeMissile chaque élément en int
       //exemple:
       //typeMissile = 0 ou 1
    public static int getX(String message){
        String[] tableau = message.split(Pattern.quote("/"));//place les elements du message dans un tableau de string
        int X = Integer.parseInt(tableau[0]);
        return X;
    }
    public static int getY(String message){
        String[] tableau = message.split(Pattern.quote("/"));
        int Y = Integer.parseInt(tableau[1]);
        return Y;
    }
    public static int getTypeMissile(String message){
        String[] tableau = message.split(Pattern.quote("/"));
        int type = Integer.parseInt(tableau[2]);
        return type;
    }

    //message de resultat ==  résultat/bitFinPartie chaque élément en int
        //exemple:
        //résultat = 0,1 ou 2 pour loupé, touché ou coulé
        //bitFinPartie = 0, 1 ou 2 pour jeuToujoursEnCours, Joueur1gagne et Joueur2Gagne
    public static int getResultatClassic(String message){
        String[] tableau = message.split(Pattern.quote("/"));
        int resultat = Integer.parseInt(tableau[0]);
        return resultat;
    }
   /* public static String[] getResultatCroix(String message){
        String[] tableau = message.split(Pattern.quote("/"));
        return tableau;
    }

    public static void decodeResultCroix(String[] tab, int nbCase){
        int[] result = new int[3];
        switch (nbCase){
            case 0:
                result[0] = getX(tab[0]);
                result[1] = getY(tab[0]);
                result[2] = getResultatClassic(tab[0]);
                break;

        }
    } */
    public static int bitdeFinPartie(String message){
        String[] tableau = message.split(Pattern.quote("/"));
        int fin = Integer.parseInt(tableau[1]);
        return fin;
    }



    //Message de propriétés de la partie ==  taillegrille/nbBateau1/nbBateau2/nbBateau3/nbBateau4 chaque élément en int
    //exemple:
    //taille grille = 10, nbBateau1 = 2..
    public static int tailleGrille(String message){
        String[] tableau = message.split(Pattern.quote("/"));
        int taille = Integer.parseInt(tableau[0]);
        return taille;
    }
    public static int[] nombreBateaux(String message){
        String[] tableau = message.split(Pattern.quote("/"));
        System.out.println(tableau[0]);
        int[] tableauDeBateaux= new int[5];
        tableauDeBateaux[0]=Integer.parseInt(tableau[1]);
        tableauDeBateaux[1]=Integer.parseInt(tableau[2]);
        tableauDeBateaux[2]=Integer.parseInt(tableau[3]);
        tableauDeBateaux[3]=Integer.parseInt(tableau[4]);
        tableauDeBateaux[4]=Integer.parseInt(tableau[5]);
        return tableauDeBateaux;
    }

}
