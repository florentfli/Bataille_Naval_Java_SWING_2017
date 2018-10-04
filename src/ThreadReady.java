public class ThreadReady extends Thread{
    Battle b;
    boolean isRec = true;
    int message;

    public ThreadReady(Battle b,Boolean isRec, int message){
        this.b=b;
        this.isRec = isRec;
        this.message = message;
    }

    @Override
    public void run() {
        if (isRec) {
            int i = Decodage.getBitready(Recevoir.recevoir(b.getGame().getPort()));
            b.setHesReady(true);
            if (i ==1 ) {
                b.setOrder(1);
            }else{
                b.setOrder(2);
            }
        }
        else if (message == 1){
            Envoyer.envoyer(b.getGame().getIpAdverse(), b.getGame().getPort(), "1");
        }else if(message==2){
            Envoyer.envoyer(b.getGame().getIpAdverse(), b.getGame().getPort(), "2");
        }
    }
}