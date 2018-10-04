import javax.swing.event.MenuEvent;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class ControlBarMenu implements ActionListener {

    private View2 view;
    private Game game;

    public ControlBarMenu(View2 view, Game game){
        this.view = view;
        this.game = game;
        view.listenerMenuBar(this);
    }

    public void menuDeselected(MenuEvent e) {
    }


    public void menuCanceled(MenuEvent e) {
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource().equals(view.getItem1())) {
            System.out.println("~~~~~~~~~~~~~~~  Accessing Rules ... ~~~~~~~~~~~~~~~ ");
            try {
                Desktop.getDesktop().browse(java.net.URI.create("https://www.regles-de-jeux.com/regle-de-la-bataille-navale/"));
            } catch (IOException er) {
                System.out.println(er.getMessage());
            }
        }
        if(e.getSource()==view.getItem2()){
            System.out.println("ici");
            view.changeView(1);
        }
        if(e.getSource()==view.getQuit()){
            System.exit(0);
        }
        if (e.getSource()==view.gettClassic()) {
            game.setTheme(1);
        }
        if (e.getSource()==view.gettFarm()) {
            game.setTheme(2);
        }
        if (e.getSource()==view.gettSpace()) {
            game.setTheme(3);
        }
    }
}