import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ControlButton implements ActionListener {

    private View2 view;
    private Game game;
    private Battle battle;

    public ControlButton() {}

    public ControlButton(View2 view, Game game, Battle b) {
        this.view = view;
        this.game = game;
        this.battle = b;
        view.listenerMenu(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource()==view.getButtonSingleplayer()) {
            game.setMulti(false);
            game.setOnline(false);
            view.changeView(3);
            view.listenerChoixPartie(this);
        }

        if (e.getSource()==view.getbClassique()) {
            game.initGameClassic(game.getPlayer1(),10);
            game.setClassic(true);
            view.changeView(6);
            view.listenerBoatPlacement(battle.getControlClick());
            battle.run();
        }

        if (e.getSource()==view.getbLocal()) {
            game.setOnline(false);
            view.changeView(3);
            view.listenerChoixPartie(this);
        }

        if (e.getSource()==view.getButtonMultiplayer()) {
            game.setMulti(true);
            view.changeView(2);
            view.listenerChoixMulti(this);
        }

        if (e.getSource()==view.getbOnline()) {
            game.setOnline(true);
            view.changeView(4);
            view.listenerChoixOnline(this);
        }

        if (e.getSource()==view.getbPerso()) {
            game.setClassic(false);
            view.changeView(5);
            view.listenerGamemode(this);
        }

        if (e.getSource()==view.getbServer()) {
            game.setServer(true);
            view.changeView(3);
            view.listenerChoixPartie(this);
        }

        if (e.getSource()==view.getbClient()) {
            game.setServer(false);
            game.setOnline(true);
            game.setIp(view.getTfClient().getText());
            battle.run();
            view.changeView(6);
            view.listenerBoatPlacement(battle.getControlClick());
        }

        if (e.getSource()==view.getbValider()) {
            view.changeView(6);
            view.listenerBoatPlacement(battle.getControlClick());
            battle.run();
        }

        if (e.getSource()==view.getbReinit()) {
            game.setTorpilleur(1);
            view.getlNbrTorpilleur().setText(String.valueOf(game.getTorpilleur()));
            view.getbMoinsTorpilleur().setEnabled(true);
            view.getbPlusTorpilleur().setEnabled(true);
            game.setSousMarin(1);
            view.getlNbrSM().setText(String.valueOf(game.getSousMarin()));
            view.getbPlusSM().setEnabled(true);
            view.getbMoinsSM().setEnabled(true);
            game.setContreTorpilleur(1);
            view.getlNbrCT().setText(String.valueOf(game.getContreTorpilleur()));
            view.getbPlusCT().setEnabled(true);
            view.getbMoinsCT().setEnabled(true);
            game.setCroiseur(1);
            view.getlNbrCroiseur().setText(String.valueOf(game.getCroiseur()));
            view.getbPlusCroiseur().setEnabled(true);
            view.getbMoinsCroiseur().setEnabled(true);
            game.setPorteAvion(1);
            view.getlNbrPA().setText(String.valueOf(game.getPorteAvion()));
            view.getbPlusPA().setEnabled(true);
            view.getbMoinsPA().setEnabled(true);
        }

        if (e.getSource()==view.getbMoinsTorpilleur()) {
            game.setTorpilleur(game.getTorpilleur()-1);
            view.getlNbrTorpilleur().setText(String.valueOf(game.getTorpilleur()));
            if (game.getTorpilleur()==4) view.getbPlusTorpilleur().setEnabled(true);
            if (game.getTorpilleur()==0) view.getbMoinsTorpilleur().setEnabled(false);
        }
        if (e.getSource()==view.getbPlusTorpilleur()) {
            game.setTorpilleur(game.getTorpilleur()+1);
            view.getlNbrTorpilleur().setText(String.valueOf(game.getTorpilleur()));
            if (game.getTorpilleur()==1) view.getbMoinsTorpilleur().setEnabled(true);
            if (game.getTorpilleur()==5) view.getbPlusTorpilleur().setEnabled(false);
        }
        if (e.getSource()==view.getbMoinsSM()) {
            game.setSousMarin(game.getSousMarin()-1);
            view.getlNbrSM().setText(String.valueOf(game.getSousMarin()));
            if (game.getSousMarin()==4) view.getbPlusSM().setEnabled(true);
            if (game.getSousMarin()==0) view.getbMoinsSM().setEnabled(false);
        }
        if (e.getSource()==view.getbPlusSM()) {
            game.setSousMarin(game.getSousMarin()+1);
            view.getlNbrSM().setText(String.valueOf(game.getSousMarin()));
            if (game.getSousMarin()==1) view.getbMoinsSM().setEnabled(true);
            if (game.getSousMarin()==5) view.getbPlusSM().setEnabled(false);
        }
        if (e.getSource()==view.getbMoinsCT()) {
            game.setContreTorpilleur(game.getContreTorpilleur()-1);
            view.getlNbrCT().setText(String.valueOf(game.getContreTorpilleur()));
            if (game.getContreTorpilleur()==4) view.getbPlusCT().setEnabled(true);
            if (game.getContreTorpilleur()==0) view.getbMoinsCT().setEnabled(false);
        }
        if (e.getSource()==view.getbPlusCT()) {
            game.setContreTorpilleur(game.getContreTorpilleur()+1);
            view.getlNbrCT().setText(String.valueOf(game.getContreTorpilleur()));
            if (game.getContreTorpilleur()==1) view.getbMoinsCT().setEnabled(true);
            if (game.getContreTorpilleur()==5) view.getbPlusCT().setEnabled(false);
        }
        if (e.getSource()==view.getbMoinsCroiseur()) {
            game.setCroiseur(game.getCroiseur()-1);
            view.getlNbrCroiseur().setText(String.valueOf(game.getCroiseur()));
            if (game.getCroiseur()==4) view.getbPlusCroiseur().setEnabled(true);
            if (game.getCroiseur()==0) view.getbMoinsCroiseur().setEnabled(false);
        }
        if (e.getSource()==view.getbPlusCroiseur()) {
            game.setCroiseur(game.getCroiseur()+1);
            view.getlNbrCroiseur().setText(String.valueOf(game.getCroiseur()));
            if (game.getCroiseur()==1) view.getbMoinsCroiseur().setEnabled(true);
            if (game.getCroiseur()==5) view.getbPlusCroiseur().setEnabled(false);
        }
        if (e.getSource()==view.getbMoinsPA()) {
            game.setPorteAvion(game.getPorteAvion()-1);
            view.getlNbrPA().setText(String.valueOf(game.getPorteAvion()));
            if (game.getPorteAvion()==4) view.getbPlusPA().setEnabled(true);
            if (game.getPorteAvion()==0) view.getbMoinsPA().setEnabled(false);
        }
        if (e.getSource()==view.getbPlusPA()) {
            game.setPorteAvion(game.getPorteAvion()+1);
            view.getlNbrPA().setText(String.valueOf(game.getPorteAvion()));
            if (game.getPorteAvion()==1) view.getbMoinsPA().setEnabled(true);
            if (game.getPorteAvion()==5) view.getbPlusPA().setEnabled(false);
        }
    }


}
