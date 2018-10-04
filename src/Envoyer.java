import java.io.BufferedReader;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

class Envoyer {
    public static void envoyer(String adresse, String port, String ligne) {
        int portDestinataire;
        InetAddress adresseIP;
        DatagramSocket socketUDP;
        DatagramPacket message;
        BufferedReader entree;
        int longueur;
        byte[] tampon;

        try {
            socketUDP = new DatagramSocket();
            //System.out.println
              //      ("Port local : " + socketUDP.getLocalPort());
            adresseIP = InetAddress.getByName(adresse);
            portDestinataire = Integer.parseInt(port);


            for (int i = 0; i < 50; i++) {
                // on construit le paquet a envoyer
                tampon = ligne.getBytes();
                longueur = tampon.length;
                message = new DatagramPacket(tampon, longueur, adresseIP, portDestinataire);
                socketUDP.send(message);
            }

            // on attend un accusé de réception
            tampon = new byte[256];
            message = new DatagramPacket(tampon, tampon.length);
            socketUDP.receive(message);
            ligne = new String(tampon);
            ligne = ligne.substring(0, message.getLength());
            System.out.println("Du port " + message.getPort() + " de la machine " +
                    message.getAddress().getHostName() + " : " + ligne);
            socketUDP.close();


        }catch(IOException exc) {
            exc.printStackTrace();
        }
    }
}