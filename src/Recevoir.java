import java.io.IOException;
import java.net.*;

class Recevoir {
    private static String texte;

    public static String getIPLocale() throws UnknownHostException {
        return InetAddress.getLocalHost().getHostAddress();
    }

    public static String recevoir(String port) {
        DatagramSocket socketUDP;
        DatagramPacket message;
        byte[] tampon;
        int portLocal;
        byte[] tamponAccuse = "accuse de reception".getBytes();
        int longueurAccuse = tamponAccuse.length;


        try {
            portLocal = Integer.parseInt(port);
            socketUDP = new DatagramSocket(portLocal);

            // on se prepare à recevoir un datagramme
            tampon = new byte[256];
            message = new DatagramPacket(tampon, tampon.length);
            socketUDP.receive(message);
            InetAddress adresseIP = message.getAddress();
            int portDistant = message.getPort();
            texte = new String(tampon);
            texte = texte.substring(0, message.getLength());
            System.out.println("Reception du port " + portDistant + " de la machine distante: ");

            for (int i = 0; i < 5000; i++) {
                message = new DatagramPacket(tamponAccuse, tamponAccuse.length, adresseIP, portDistant);
                socketUDP.send(message);
            }
           Thread.sleep(5000);
            socketUDP.close();

        } catch (IOException exc) {
            System.out.println("Probleme sur la reception ou l'envoi du message");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return texte;
    }
}