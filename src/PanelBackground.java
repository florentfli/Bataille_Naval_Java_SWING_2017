import javax.swing.*;
import java.awt.*;

public class PanelBackground extends JPanel {
    private int theme;

    public PanelBackground(int theme){
        this.theme = theme;
    }

        public void paint (Graphics g) {
            //Image imageBackground = Toolkit.getDefaultToolkit().getImage("out/production/image/background.jpg");
            switch (theme) {
                case 1: {
                    Image imageBackground = Toolkit.getDefaultToolkit().getImage(getClass().getResource("image/1/bg/bg.gif"));
                    g.drawImage(imageBackground, 0, 0, 1600, 900, this);
                    break;
                }
                case 2: {
                    Image imageBackground = Toolkit.getDefaultToolkit().getImage(getClass().getResource("image/2/bg/bg.gif"));
                    g.drawImage(imageBackground, 0, 0, 1600, 900, this);
                    break;
                }
                case 3: {
                    Image imageBackground = Toolkit.getDefaultToolkit().getImage(getClass().getResource("image/3/bg/bg.gif"));
                    g.drawImage(imageBackground, 0, 0, 1600, 900, this);
                    break;
                }
            }
            setOpaque(false);
            super.paint(g);
            setOpaque(true);
        }

}
