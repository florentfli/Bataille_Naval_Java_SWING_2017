import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;

public class View2 extends JFrame {

    private JLabel labelTitre, labelCredits, labelYourIP, labelChargement;
    private JLabel lNbrTorpilleur, lNbrSM, lNbrCT, lNbrCroiseur, lNbrPA;
    private JButton buttonSingleplayer, buttonMultiplayer;
    private JButton bMoinsTorpilleur, bPlusTorpilleur, bMoinsSM, bPlusSM, bMoinsCT, bPlusCT, bMoinsCroiseur,
            bPlusCroiseur, bMoinsPA, bPlusPA, bValider, bReinit;
    private JButton bClassique, bPerso;
    private JButton bLocal, bOnline;
    private JButton bServer, bClient;
    private JLabel lOr;
    private JTextField tfClient;

    private Game game;
    private JMenuBar barMenu;
    private JMenu theme, menu;
    private JMenuItem item1, item2, quit, tClassic, tFarm, tSpace;
    private JLabel labelDirection, labelOk, labelDelete, labelPosition, labelFire;
    private JLabel labelTorpilleur, labelSousMarin, labelContreTorpilleur, labelCroiseur, labelPorteAvion;
    private JLabel A, B, C, D, E, F, G, H, I, J;
    private Border border;
    private JPanel pBigGrid;
    private JLabel[][] gridLabelBigGrid, gridLabelLittleGrid;
    private ImageIcon imageDirectionHorizontal, imageDirectionVertical, imageOkReleased, imageOkClicked,
            imageDeleteReleased, imageDeleteClicked, imageTorpilleur, imageSousMarin, imageContreTorpilleur,
            imageCroiseur, imagePorteAvion, redDot, whiteDot, blackDot, target;
    private int stateApp;



    public View2(Game game) {
        this.game = game;
        setTitle("BattleShip");
        this.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("image/ico.png")));

        initializeCommonVar();
        createMenuOptions();

        // Pour les menus
        initializeMenuPrincipal();
        createMenuPrincipal();

//        setVisible(true); //Bande blanche si c'est en comentaire
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);
        pack();
//        this.setExtendedState(Frame.MAXIMIZED_BOTH);
//        setUndecorated(true);


//        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
//        this.setLocation(dim.width / 3 - this.getSize().width / 3, dim.height / 7 - this.getSize().height / 7);
        this.setSize(1100, 750);
        this.setLocationRelativeTo(null);
    }


    // INITIALIZE

    private void initializeCommonVar() {
        gridLabelBigGrid = new JLabel[10][10];
        gridLabelLittleGrid = new JLabel[10][10];


        border = BorderFactory.createLineBorder(Color.WHITE);

        //Créer les JLabel avec les différentes lettres pour la position du tir
        // Lettre A
        A = new JLabel("A", JLabel.CENTER);
        A.setForeground(Color.WHITE);
        A.setFont(new Font(A.getFont().getName(), Font.PLAIN, 30));
        A.setBackground(new Color(0,0,0, 200));
        A.setOpaque(true);
        // Lettre B
        B = new JLabel("B", JLabel.CENTER);
        B.setForeground(Color.WHITE);
        B.setFont(new Font(B.getFont().getName(), Font.PLAIN, 30));
        B.setBackground(new Color(0,0,0, 200));
        B.setOpaque(true);
        // Lettre C
        C = new JLabel("C", JLabel.CENTER);
        C.setForeground(Color.WHITE);
        C.setFont(new Font(C.getFont().getName(), Font.PLAIN, 30));
        C.setBackground(new Color(0,0,0, 200));
        C.setOpaque(true);
        // Lettre D
        D = new JLabel("D", JLabel.CENTER);
        D.setForeground(Color.WHITE);
        D.setFont(new Font(D.getFont().getName(), Font.PLAIN, 30));
        D.setBackground(new Color(0,0,0, 200));
        D.setOpaque(true);
        // Lettre E
        E = new JLabel("E", JLabel.CENTER);
        E.setForeground(Color.WHITE);
        E.setFont(new Font(E.getFont().getName(), Font.PLAIN, 30));
        E.setBackground(new Color(0,0,0, 200));
        E.setOpaque(true);
        // Lettre F
        F = new JLabel("F", JLabel.CENTER);
        F.setForeground(Color.WHITE);
        F.setFont(new Font(F.getFont().getName(), Font.PLAIN, 30));
        F.setBackground(new Color(0,0,0, 200));
        F.setOpaque(true);
        // Lettre G
        G = new JLabel("G", JLabel.CENTER);
        G.setForeground(Color.WHITE);
        G.setFont(new Font(G.getFont().getName(), Font.PLAIN, 30));
        G.setBackground(new Color(0,0,0, 200));
        G.setOpaque(true);
        // Lettre H
        H = new JLabel("H", JLabel.CENTER);
        H.setForeground(Color.WHITE);
        H.setFont(new Font(H.getFont().getName(), Font.PLAIN, 30));
        H.setBackground(new Color(0,0,0, 200));
        H.setOpaque(true);
        // Lettre I
        I = new JLabel("I", JLabel.CENTER);
        I.setForeground(Color.WHITE);
        I.setFont(new Font(I.getFont().getName(), Font.PLAIN, 30));
        I.setBackground(new Color(0,0,0, 200));
        I.setOpaque(true);
        // Lettre J
        J = new JLabel("J", JLabel.CENTER);
        J.setForeground(Color.WHITE);
        J.setFont(new Font(J.getFont().getName(), Font.PLAIN, 30));
        J.setBackground(new Color(0,0,0, 200));
        J.setOpaque(true);

        redDot = new ImageIcon(new ImageIcon(getClass().getResource("image/redDot.png")).
                getImage().getScaledInstance(20, 20, Image.SCALE_AREA_AVERAGING));
        whiteDot = new ImageIcon(new ImageIcon(getClass().getResource("image/whiteDot.png")).
                getImage().getScaledInstance(20, 20, Image.SCALE_AREA_AVERAGING));
        target = new ImageIcon(new ImageIcon(getClass().getResource("image/target.png")).
                getImage().getScaledInstance(40, 40, Image.SCALE_AREA_AVERAGING));
        blackDot = new ImageIcon(new ImageIcon(getClass().getResource("image/blackDot.png")).
                getImage().getScaledInstance(20, 20, Image.SCALE_AREA_AVERAGING));
    }
    private void initializeMenuPrincipal() {
        // Boutons de redirection, solo et multi
        buttonSingleplayer = new JButton("SINGLEPLAYER (IA)");
        buttonMultiplayer = new JButton("MULTIPLAYER (2P)");
        // Label de credits
        labelTitre = new JLabel("BATTLESHIP");
        labelCredits = new JLabel("Credits:" + " Theo.R; Florent.F; Erwan.R; Hugo.B; Baptiste.V");
    }
    private void initializeChoixMulti() {
        bLocal = new JButton("LOCAL GAME");
        bLocal.setBackground(new Color(255, 255, 255));
        bOnline = new JButton("ONLINE GAME");
        bOnline.setBackground(new Color(255, 255, 255));
        labelTitre = new JLabel("MULTIPLAYER");
    }
    private void initializeChoixPartie() {
        bClassique = new JButton("CLASSIC GAME");
        bClassique.setBackground(new Color(255, 255, 255));
        bPerso = new JButton("CUSTOM GAME");
        bPerso.setBackground(new Color(255, 255, 255));
        labelTitre = new JLabel("GAME TYPE");
    }
    private void initializeChoixOnline() {
        bServer = new JButton("CREATE GAME");
        bServer.setBackground(new Color(255, 255, 255));
        bClient = new JButton("JOIN GAME");
        bClient.setBackground(new Color(255, 255, 255));
        labelTitre = new JLabel("ONLINE MULTIPLAYER");
        lOr = new JLabel("OR");
        lOr.setForeground(Color.WHITE);
        lOr.setFont(new Font("Arial", Font.BOLD, 25));
        tfClient = new JTextField("");
        labelYourIP = new JLabel();
        labelYourIP.setForeground(new Color(255, 255, 255));
    }
    private void initializeGamemode() {
        bMoinsTorpilleur = new JButton("-");
        bMoinsTorpilleur.setBackground(new Color(255, 255, 255));
        lNbrTorpilleur = new JLabel(String.valueOf(this.game.getTorpilleur()));
        lNbrTorpilleur.setForeground(Color.WHITE);
        lNbrTorpilleur.setFont(new Font("Arial", Font.BOLD,30));
        bPlusTorpilleur = new JButton("+");
        bPlusTorpilleur.setBackground(new Color(255, 255, 255));
        bMoinsSM = new JButton("-");
        bMoinsSM.setBackground(new Color(255, 255, 255));
        lNbrSM = new JLabel(String.valueOf(this.game.getSousMarin()));
        lNbrSM.setForeground(Color.WHITE);
        lNbrSM.setFont(new Font("Arial", Font.BOLD, 30));
        bPlusSM = new JButton("+");
        bPlusSM.setBackground(new Color(255, 255, 255));
        bMoinsCT = new JButton("-");
        bMoinsCT.setBackground(new Color(255, 255, 255));
        lNbrCT = new JLabel(String.valueOf(this.game.getContreTorpilleur()));
        lNbrCT.setForeground(Color.WHITE);
        lNbrCT.setFont(new Font("Arial", Font.BOLD, 30));
        bPlusCT = new JButton("+");
        bPlusCT.setBackground(new Color(255, 255, 255));
        bMoinsCroiseur = new JButton("-");
        bMoinsCroiseur.setBackground(new Color(255, 255, 255));
        lNbrCroiseur = new JLabel(String.valueOf(this.game.getCroiseur()));
        lNbrCroiseur.setForeground(Color.WHITE);
        lNbrCroiseur.setFont(new Font("Arial", Font.BOLD, 30));
        bPlusCroiseur = new JButton("+");
        bPlusCroiseur.setBackground(new Color(255, 255, 255));
        bMoinsPA = new JButton("-");
        bMoinsPA.setBackground(new Color(255, 255, 255));
        lNbrPA = new JLabel(String.valueOf(this.game.getPorteAvion()));
        lNbrPA.setForeground(Color.WHITE);
        lNbrPA.setFont(new Font("Arial", Font.BOLD, 30));
        bPlusPA = new JButton("+");
        bPlusPA.setBackground(new Color(255, 255, 255));
        bValider = new JButton("RUN");
        bValider.setBackground(new Color(255, 255, 255));
        bReinit = new JButton("Default");
        bReinit.setBackground(new Color(255, 255, 255));
    }
    public void initializeBoatPlacement() {
        // Instanciation des images
        imageDirectionHorizontal = new ImageIcon(new ImageIcon(getClass().getResource(
                "image/turn_horizontal.png")).getImage().getScaledInstance(
                100, 100, Image.SCALE_DEFAULT)
        );
        imageDirectionVertical = new ImageIcon(new ImageIcon(getClass().getResource(
                "image/turn_vertical.png")).getImage().getScaledInstance(
                100, 100, Image.SCALE_DEFAULT)
        );
        imageOkReleased = new ImageIcon(new ImageIcon(getClass().getResource("image/ok_released.png")).
                getImage().getScaledInstance(120, 120, Image.SCALE_DEFAULT));
        imageOkClicked = new ImageIcon(new ImageIcon(getClass().getResource("image/ok_clicked.png")).
                getImage().getScaledInstance(120, 120, Image.SCALE_DEFAULT));
        imageDeleteReleased = new ImageIcon(new ImageIcon(getClass().getResource("image/delete_released.png")).
                getImage().getScaledInstance(120, 120, Image.SCALE_DEFAULT));
        imageDeleteClicked = new ImageIcon(new ImageIcon(getClass().getResource("image/delete_clicked.png")).
                getImage().getScaledInstance(120, 120, Image.SCALE_DEFAULT));
        switch(game.getTheme()) {
            case 1: {
                imageTorpilleur = new ImageIcon(new ImageIcon(getClass().getResource("image/1/2/torpilleur.png")).
                        getImage().getScaledInstance(140,50,Image.SCALE_DEFAULT));
                imageSousMarin = new ImageIcon(new ImageIcon(getClass().getResource("image/1/sous-marin/sousMarin.png")).
                        getImage().getScaledInstance(210,70,Image.SCALE_DEFAULT));
                imageContreTorpilleur = new ImageIcon(new ImageIcon(getClass().getResource("image/1/contre-torpilleur/contreTorpilleur.png")).
                        getImage().getScaledInstance(210,50,Image.SCALE_DEFAULT));
                imageCroiseur = new ImageIcon(new ImageIcon(getClass().getResource("image/1/4/croiseur.png")).
                        getImage().getScaledInstance(280,50,Image.SCALE_DEFAULT));
                imagePorteAvion = new ImageIcon(new ImageIcon(getClass().getResource("image/1/5/porteAvion.png")).
                        getImage().getScaledInstance(320,70,Image.SCALE_DEFAULT));
                break;
            }
            case 2: {
                imageTorpilleur = new ImageIcon(new ImageIcon(getClass().getResource("image/2/2/torpilleur.png")).
                        getImage().getScaledInstance(120,50,Image.SCALE_DEFAULT));
                imageSousMarin = new ImageIcon(new ImageIcon(getClass().getResource("image/2/sous-marin/sousMarin.png")).
                        getImage().getScaledInstance(120,50,Image.SCALE_DEFAULT));
                imageContreTorpilleur = new ImageIcon(new ImageIcon(getClass().getResource("image/2/contre-torpilleur/contreTorpilleur.png")).
                        getImage().getScaledInstance(120,100,Image.SCALE_DEFAULT));
                imageCroiseur = new ImageIcon(new ImageIcon(getClass().getResource("image/2/4/croiseur.png")).
                        getImage().getScaledInstance(120,50,Image.SCALE_DEFAULT));
                imagePorteAvion = new ImageIcon(new ImageIcon(getClass().getResource("image/2/5/porteAvion.png")).
                        getImage().getScaledInstance(120,50,Image.SCALE_DEFAULT));
                break;
            }
            case 3: {
                imageTorpilleur = new ImageIcon(new ImageIcon(getClass().getResource("image/3/2/torpilleur.png")).
                        getImage().getScaledInstance(140,50,Image.SCALE_DEFAULT));
                imageSousMarin = new ImageIcon(new ImageIcon(getClass().getResource("image/3/sous-marin/sousMarin.png")).
                        getImage().getScaledInstance(210,70,Image.SCALE_DEFAULT));
                imageContreTorpilleur = new ImageIcon(new ImageIcon(getClass().getResource("image/3/contre-torpilleur/contreTorpilleur.png")).
                        getImage().getScaledInstance(210,50,Image.SCALE_DEFAULT));
                imageCroiseur = new ImageIcon(new ImageIcon(getClass().getResource("image/3/4/croiseur.png")).
                        getImage().getScaledInstance(280,50,Image.SCALE_DEFAULT));
                imagePorteAvion = new ImageIcon(new ImageIcon(getClass().getResource("image/3/5/porteAvion.png")).
                        getImage().getScaledInstance(320,70,Image.SCALE_DEFAULT));
                break;
            }
        }

        // Instanciation des labels des bateau
        // Torpedo
        labelTorpilleur = new JLabel(imageTorpilleur, JLabel.CENTER);
        labelTorpilleur.setForeground(Color.WHITE);
        labelTorpilleur.setFont(new Font(labelTorpilleur.getFont().getName(), Font.PLAIN, 20));
        labelTorpilleur.setBorder(border);
        labelTorpilleur.setBackground(new Color(0, 0, 0, 150));
        labelTorpilleur.setOpaque(true);
        // Submarine
        labelSousMarin = new JLabel(imageSousMarin, JLabel.CENTER);
        labelSousMarin.setForeground(Color.WHITE);
        labelSousMarin.setFont(new Font(labelSousMarin.getFont().getName(), Font.PLAIN, 20));
        labelSousMarin.setBorder(border);
        labelSousMarin.setBackground(new Color(0, 0, 0, 150));
        labelSousMarin.setOpaque(true);
        // Destroyer
        labelContreTorpilleur = new JLabel(imageContreTorpilleur, JLabel.CENTER);
        labelContreTorpilleur.setFont(new Font(labelContreTorpilleur.getFont().getName(), Font.PLAIN, 20));
        labelContreTorpilleur.setForeground(Color.WHITE);
        labelContreTorpilleur.setBorder(border);
        labelContreTorpilleur.setBackground(new Color(0, 0, 0, 150));
        labelContreTorpilleur.setOpaque(true);
        // Cruiser
        labelCroiseur = new JLabel(imageCroiseur, JLabel.CENTER);
        labelCroiseur.setFont(new Font(labelCroiseur.getFont().getName(), Font.PLAIN, 20));
        labelCroiseur.setForeground(Color.WHITE);
        labelCroiseur.setBorder(border);
        labelCroiseur.setBackground(new Color(0, 0, 0, 150));
        labelCroiseur.setOpaque(true);
        // Aircraft Carrier
        labelPorteAvion = new JLabel(imagePorteAvion, JLabel.CENTER);
        labelPorteAvion.setFont(new Font(labelPorteAvion.getFont().getName(), Font.PLAIN, 20));
        labelPorteAvion.setForeground(Color.WHITE);
        labelPorteAvion.setBorder(border);
        labelPorteAvion.setBackground(new Color(0, 0, 0, 150));
        labelPorteAvion.setOpaque(true);


        labelDirection = new JLabel(imageDirectionHorizontal);
        labelOk = new JLabel(imageOkReleased);
        labelDelete = new JLabel();

        stateApp = 6;
    }

    public void initializeGame() {
        // Label de la position sélectionnée
        labelPosition = new JLabel("A1", JLabel.CENTER);
        labelPosition.setForeground(Color.WHITE);
        labelPosition.setFont(new Font(labelPosition.getFont().getName(), Font.PLAIN, 50));
        // Label qui lance l'attaque
        labelFire = new JLabel("FIRE", JLabel.CENTER);
        labelFire.setForeground(Color.WHITE);
        labelFire.setFont(new Font(labelPosition.getFont().getName(), Font.PLAIN, 40));

        stateApp = 7;
    }



    // CREATE
    private void createMenuOptions() {
        menu = new JMenu("Options");
        theme = new JMenu("Change Theme");

        item1 = new JMenuItem("Rules");
        item2 = new JMenuItem("Main Menu");
        quit = new JMenuItem("Quit");

        tClassic = new JMenuItem("Classic");
        tFarm = new JMenuItem("Farm");
        tSpace = new JMenuItem("Space");

        barMenu = new JMenuBar();
        menu.add(item1);
        menu.add(item2);
        menu.add(quit);

        theme.add(tClassic);
        theme.add(tFarm);
        theme.add(tSpace);

        barMenu.add(menu);
        barMenu.add(theme);
        setJMenuBar(barMenu);


        setJMenuBar(barMenu);
    }
    private void createMenuPrincipal() {
        // creation panels
        PanelBackground root = new PanelBackground(game.getTheme());
        JPanel pMain = new JPanel();
        pMain.setLayout(new BoxLayout(pMain, BoxLayout.Y_AXIS));
        JPanel pGranMain = new JPanel();
        JPanel pTitre = new JPanel();
        JPanel pSingleplayer = new JPanel(new BorderLayout());
        JPanel pMultiplayer = new JPanel(new BorderLayout());
        JPanel pCredits = new JPanel();
        // ajout des elements
        // label titre
        pMain.add(Box.createVerticalStrut(30));
        labelTitre.setOpaque(false);
        labelTitre.setForeground(new Color(0, 0, 0, 200));
        labelTitre.setFont(new Font("Arial", Font.BOLD, 100));
        labelTitre.setHorizontalAlignment(SwingConstants.CENTER);
        labelTitre.setText("BATTLESHIP");
        pTitre.setOpaque(false);
        pTitre.add(labelTitre);
        pMain.add(pTitre);
        // 1P
        pMain.add(Box.createVerticalStrut(70));
        buttonSingleplayer.setPreferredSize(new Dimension(500, 100));
        buttonSingleplayer.setBackground(Color.BLACK);
        buttonSingleplayer.setForeground(new Color(130, 135, 207, 255));
        buttonSingleplayer.setFont(new Font("Arial", Font.PLAIN, 25));
        pSingleplayer.add(buttonSingleplayer, BorderLayout.CENTER);
        pSingleplayer.setBorder(BorderFactory.createLineBorder(Color.black));
        pMain.add(pSingleplayer);
        // 2P
        pMain.add(Box.createVerticalStrut(10));
        buttonMultiplayer.setPreferredSize(new Dimension(500, 100));
        buttonMultiplayer.setBackground(Color.BLACK);
        buttonMultiplayer.setForeground(new Color(130, 135, 207, 255));
        buttonMultiplayer.setFont(new Font("Arial", Font.PLAIN, 25));
        pMultiplayer.add(buttonMultiplayer, BorderLayout.CENTER);
        pMultiplayer.setBorder(BorderFactory.createLineBorder(Color.black));
        pMain.add(pMultiplayer);
        // Credits
        pMain.add(Box.createVerticalStrut(30));
        labelCredits.setPreferredSize(new Dimension(500, 100));
        labelCredits.setForeground(new Color(130, 135, 207, 255));
        labelCredits.setFont(new Font("Arial", Font.BOLD, 18));
        labelCredits.setHorizontalAlignment(SwingConstants.CENTER);
        pCredits.add(labelCredits, BorderLayout.CENTER);
        pCredits.setBorder(BorderFactory.createLineBorder(Color.black));
        pCredits.setBackground(new Color(0, 0, 0, 150));
        pMain.add(pCredits);
        // main
        pMain.setOpaque(false);
        pGranMain.setOpaque(false);
        pGranMain.add(pMain);
        pGranMain.setPreferredSize(new Dimension(1100,700));
        root.add(pGranMain);
        setContentPane(root);
    }

    private void createChoixMulti() {
        // creation panels
        PanelBackground root = new PanelBackground(game.getTheme());
        JPanel pMain = new JPanel();
        pMain.setLayout(new BoxLayout(pMain, BoxLayout.Y_AXIS));
        JPanel pGranMain = new JPanel();
        JPanel pTitre = new JPanel();
        JPanel pLocal = new JPanel(new BorderLayout());
        JPanel pOnline = new JPanel(new BorderLayout());
        // ajout des elements
        // label titre
        pMain.add(Box.createVerticalStrut(30));
        labelTitre.setOpaque(false);
        labelTitre.setForeground(Color.WHITE);
        labelTitre.setFont(new Font("Arial", Font.BOLD, 50));
        labelTitre.setHorizontalAlignment(SwingConstants.CENTER);
        pTitre.setOpaque(false);
        pTitre.add(labelTitre);
        pMain.add(pTitre);
        // 1P
        pMain.add(Box.createVerticalStrut(70));
        bLocal.setPreferredSize(new Dimension(500, 100));
        bLocal.setForeground(new Color(130, 135, 207, 255));
        bLocal.setBackground(new Color(0, 0, 0));
        bLocal.setFont(new Font("Arial", Font.PLAIN, 25));
        pLocal.add(bLocal, BorderLayout.CENTER);
        pLocal.setBorder(BorderFactory.createLineBorder(Color.black));
        pMain.add(pLocal);
        // Perso
        pMain.add(Box.createVerticalStrut(10));
        bOnline.setPreferredSize(new Dimension(500, 100));
        bOnline.setForeground(new Color(130, 135, 207, 255));
        bOnline.setBackground(new Color(0, 0, 0));
        bOnline.setFont(new Font("Arial", Font.PLAIN, 25));
        pOnline.add(bOnline, BorderLayout.CENTER);
        pOnline.setBorder(BorderFactory.createLineBorder(Color.black));
        pMain.add(pOnline);
        // main
        pMain.setOpaque(false);
        pGranMain.setOpaque(false);
        pGranMain.add(pMain);
        root.add(pGranMain);
        setContentPane(root);
    }

    private void createChoixPartie() {
        // creation panels
        PanelBackground root = new PanelBackground(game.getTheme());
        JPanel pMain = new JPanel();
        pMain.setLayout(new BoxLayout(pMain, BoxLayout.Y_AXIS));
        JPanel pGranMain = new JPanel();
        JPanel pTitre = new JPanel();
        JPanel pClassic = new JPanel(new BorderLayout());
        JPanel pPerso = new JPanel(new BorderLayout());
        // ajout des elements
        // label titre
        pMain.add(Box.createVerticalStrut(30));
        labelTitre.setOpaque(false);
        labelTitre.setForeground(Color.WHITE);
        labelTitre.setFont(new Font("Arial", Font.BOLD, 50));
        labelTitre.setHorizontalAlignment(SwingConstants.CENTER);
        pTitre.setOpaque(false);
        pTitre.add(labelTitre);
        pMain.add(pTitre);
        // 1P
        pMain.add(Box.createVerticalStrut(70));
        bClassique.setPreferredSize(new Dimension(500, 100));
        bClassique.setForeground(new Color(130, 135, 207, 255));
        bClassique.setBackground(new Color(0, 0, 0));
        bClassique.setFont(new Font("Arial", Font.PLAIN, 25));
        ///////
        pClassic.add(bClassique, BorderLayout.CENTER);
        pClassic.setBorder(BorderFactory.createLineBorder(Color.black));
        pMain.add(pClassic);
        // Perso
        pMain.add(Box.createVerticalStrut(10));
        bPerso.setPreferredSize(new Dimension(500, 100));
        bPerso.setForeground(new Color(130, 135, 207, 255));
        bPerso.setBackground(new Color(0, 0, 0));
        bPerso.setFont(new Font("Arial", Font.PLAIN, 25));
        pPerso.add(bPerso, BorderLayout.CENTER);
        pPerso.setBorder(BorderFactory.createLineBorder(Color.black));
        pMain.add(pPerso);
        // main
        pMain.setOpaque(false);
        pGranMain.setOpaque(false);
        pGranMain.add(pMain);
        root.add(pGranMain);
        setContentPane(root);
    }

    private void createChoixOnline() {
        // creation panels
        PanelBackground root = new PanelBackground(game.getTheme());
        JPanel pMain = new JPanel();
        pMain.setLayout(new BoxLayout(pMain, BoxLayout.Y_AXIS));
        JPanel pGranMain = new JPanel();
        JPanel pTitre = new JPanel();
        JPanel pServer = new JPanel(new BorderLayout());
        JPanel pClient = new JPanel(new BorderLayout());
        JPanel pConnect = new JPanel(new BorderLayout());
        JPanel pYourIP = new JPanel(new BorderLayout());
        // ajout des elements
        // label titre
        pMain.add(Box.createVerticalStrut(30));
        labelTitre.setOpaque(false);
        labelTitre.setForeground(Color.WHITE);
        labelTitre.setFont(new Font("Arial", Font.BOLD, 40));
        labelTitre.setHorizontalAlignment(SwingConstants.CENTER);
        pTitre.setOpaque(false);
        pTitre.add(labelTitre);
        pMain.add(pTitre);
        // 1P
        pMain.add(Box.createVerticalStrut(70));
        bServer.setPreferredSize(new Dimension(500, 100));
        bServer.setForeground(new Color(130, 135, 207, 255));
        bServer.setBackground(new Color(0, 0, 0));
        bServer.setFont(new Font("Arial", Font.PLAIN, 25));
        pServer.add(bServer, BorderLayout.CENTER);
        pServer.setBorder(BorderFactory.createLineBorder(Color.black));
        pMain.add(pServer);
        // OR
        pMain.add(Box.createVerticalStrut(10));
        pMain.add(lOr);
        // Perso
        pMain.add(Box.createVerticalStrut(10));
        bClient.setPreferredSize(new Dimension(500, 100));
        bClient.setForeground(new Color(130, 135, 207, 255));
        bClient.setBackground(new Color(0, 0, 0));
        bClient.setFont(new Font("Arial", Font.PLAIN, 25));
        pClient.add(bClient, BorderLayout.CENTER);
        pClient.setBorder(BorderFactory.createLineBorder(Color.black));
        // Connect
        tfClient.setPreferredSize(new Dimension(500, 50));
        tfClient.setForeground(new Color(130, 135, 207, 255));
        tfClient.setBackground(new Color(0, 0, 0));
        tfClient.setFont(new Font("Arial", Font.PLAIN, 25));
        pConnect.add(tfClient, BorderLayout.NORTH);
        pConnect.add(pClient,BorderLayout.SOUTH);
        pConnect.setBorder(BorderFactory.createLineBorder(Color.black));
        pMain.add(pConnect);

        // Your IP
        pMain.add(Box.createVerticalStrut(20));
        labelYourIP.setText("Your IP: " + game.getIpSelf());
        labelYourIP.setForeground(Color.WHITE);
        labelYourIP.setFont(new Font("Arial", Font.BOLD, 40));
        labelYourIP.setHorizontalAlignment(SwingConstants.CENTER);
        pYourIP.setOpaque(false);
        pYourIP.add(labelYourIP);
        pMain.add(pYourIP);

        // main
        pMain.setOpaque(false);
        pGranMain.setOpaque(false);
        pGranMain.add(pMain);
        root.add(pGranMain);
        setContentPane(root);
    }
    private void createGamemode() {
        PanelBackground root = new PanelBackground(game.getTheme());
        JPanel pTorpilleur = new JPanel(new FlowLayout(FlowLayout.LEFT, 35, 35));
        pTorpilleur.add(bMoinsTorpilleur);
        pTorpilleur.add(lNbrTorpilleur);
        pTorpilleur.add(bPlusTorpilleur);
        JPanel pSM = new JPanel(new FlowLayout(FlowLayout.LEFT, 35, 35));
        pSM.add(bMoinsSM);
        pSM.add(lNbrSM);
        pSM.add(bPlusSM);
        JPanel pCT = new JPanel(new FlowLayout(FlowLayout.LEFT, 35, 35));
        pCT.add(bMoinsCT);
        pCT.add(lNbrCT);
        pCT.add(bPlusCT);
        JPanel pCroiseur = new JPanel(new FlowLayout(FlowLayout.LEFT, 35, 35));
        pCroiseur.add(bMoinsCroiseur);
        pCroiseur.add(lNbrCroiseur);
        pCroiseur.add(bPlusCroiseur);
        JPanel pPA = new JPanel(new FlowLayout(FlowLayout.LEFT, 35, 35));
        pPA.add(bMoinsPA);
        pPA.add(lNbrPA);
        pPA.add(bPlusPA);
        JPanel pValider = new JPanel();
        pValider.add(bValider);
        JPanel pReinit = new JPanel();
        pReinit.add(bReinit);

        JPanel main = new JPanel();
        main.setLayout(new GridLayout(6, 2));
        main.setPreferredSize(new Dimension(550, 500));
        main.setBackground(new Color(0, 0, 0, 150));

        JPanel plTorpilleur = new JPanel(new BorderLayout());
        JLabel lTorpedo = new JLabel("   Torpedos");
        lTorpedo.setForeground(Color.WHITE);
        lTorpedo.setFont(new Font("Arial", Font.BOLD,30));
        plTorpilleur.add(lTorpedo,BorderLayout.WEST);
        plTorpilleur.setOpaque(false);
        main.add(plTorpilleur);
        pTorpilleur.setOpaque(false);
        main.add(pTorpilleur);

        JPanel plSM = new JPanel(new BorderLayout());
        JLabel lSubmarines = new JLabel("   Submarines");
        lSubmarines.setForeground(Color.WHITE);
        lSubmarines.setFont(new Font("Arial", Font.BOLD,30));
        plSM.add(lSubmarines,BorderLayout.WEST);
        plSM.setOpaque(false);
        main.add(plSM);
        pSM.setOpaque(false);
        main.add(pSM);

        JPanel plCT = new JPanel(new BorderLayout());
        JLabel lDestroyers = new JLabel("   Destroyers");
        lDestroyers.setForeground(Color.WHITE);
        lDestroyers.setFont(new Font("Arial", Font.BOLD,30));
        plCT.add(lDestroyers,BorderLayout.WEST);
        plCT.setOpaque(false);
        main.add(plCT);
        pCT.setOpaque(false);
        main.add(pCT);

        JPanel plCroiseur = new JPanel(new BorderLayout());
        JLabel lCruisers = new JLabel("   Cruisers");
        lCruisers.setForeground(Color.WHITE);
        lCruisers.setFont(new Font("Arial", Font.BOLD,30));
        plCroiseur.add(lCruisers,BorderLayout.WEST);
        plCroiseur.setOpaque(false);
        main.add(plCroiseur);
        pCroiseur.setOpaque(false);
        main.add(pCroiseur);

        JPanel plPA = new JPanel(new BorderLayout());
        JLabel lAC = new JLabel("   Aircraft Carriers");
        lAC.setForeground(Color.WHITE);
        lAC.setFont(new Font("Arial", Font.BOLD,30));
        plPA.add(lAC,BorderLayout.WEST);
        plPA.setOpaque(false);
        main.add(plPA);
        pPA.setOpaque(false);
        main.add(pPA);

        pValider.setOpaque(false);
        main.add(pValider);
        pReinit.setOpaque(false);
        main.add(pReinit);
        main.setOpaque(false);
        root.add(main);
        setContentPane(root);
    }

    public void createBoatPlacement() {
        PanelBackground root = new PanelBackground(game.getTheme()); // Pannel racine avec un Layout par défaut et l'image de fond
        JPanel pMain = new JPanel(new BorderLayout(40, 0)); // Pannel principal contenant le pannel de jeu et
        // le pannel d'informations
        JPanel pBoat = new JPanel(); // Pannel image des bateaux à placer sur la grille
        pBigGrid = new JPanel(); // Pannel du joueur contenant les cases où le joueur veut lancer le missile
        JPanel pPlacement = new JPanel(); // Pannel de l'ajustement de la position et de la validation


        // Rendre les JPannel transparent pour voir l'image de fond
        pMain.setOpaque(false);
        pBigGrid.setOpaque(false);
        pBoat.setOpaque(false);
        pPlacement.setOpaque(false);


        // Définir les layouts des JPannel
        pBoat.setLayout(new GridLayout(5, 1)); // Afficher une grille avec les letrres, chiffres et JLabel
        pBoat.setPreferredSize(new Dimension(350, 650));
        pBigGrid.setLayout(new GridLayout(11, 11)); // Afficher une grille avec les letrres, chiffres et JLabel
        pBigGrid.setPreferredSize(new Dimension(550, 650));
        pPlacement.setLayout(new GridLayout(3, 1)); // Afficher une grille avec les letrres, chiffres et JLabel
        pPlacement.setPreferredSize(new Dimension(120, 650));


        // Ajouter les emplacements des différents bateaux au JPannel pBoat
        pBoat.add(labelTorpilleur);
        pBoat.add(labelSousMarin);
        pBoat.add(labelContreTorpilleur);
        pBoat.add(labelCroiseur);
        pBoat.add(labelPorteAvion);

        // Création de la grille pour placer les bateaux
        createBigGrid(pBigGrid, gridLabelBigGrid);

        pPlacement.add(labelDirection);
        pPlacement.add(labelOk);
        pPlacement.add(labelDelete);

        pMain.add(pBoat, BorderLayout.WEST);
        pMain.add(pBigGrid, BorderLayout.CENTER);
        pMain.add(pPlacement, BorderLayout.EAST);
        root.setPreferredSize(new Dimension(1100,700));

        root.add(pMain);
        setContentPane(root);
    }

    public void createGame() {
        PanelBackground root = new PanelBackground(game.getTheme()); // Pannel racine avec un Layout par défaut et l'image de fond
        JPanel pMain = new JPanel(new BorderLayout(40, 0)); // Pannel principal contenant le pannel de jeu et le pannel d'informations
        JPanel pBigGrid = new JPanel(); // Pannel du joueur contenant les cases où le joueur veut lancer le missile
        JPanel pInformations = new JPanel(new BorderLayout(10, 50)); // Pannel contenant la grille adversaire,
        // le bouton labelFire, la case
        // sélectionnée et les options
        JPanel pPlayer = new JPanel(); // Pannel contenant la grille de l'adversaire mise à jour à chaque missile lancé
        JPanel pMenu = new JPanel(); // Pannel contenant le bouton labelFire, la case sélectionnée et les options


        // Rendre les JPannel transparent pour voir l'image de fond
        pMain.setOpaque(false);
        pBigGrid.setOpaque(false);
        pInformations.setOpaque(false);
        pPlayer.setOpaque(false);
        pMenu.setOpaque(false);


        //Définir les layouts des JPannel
        pBigGrid.setLayout(new GridLayout(game.getPlayer1().getMap().getWidthX() + 1, game.getPlayer1().getMap().getLengthY() + 1)); // Afficher une grille avec les letrres, chiffres et JLabel
        pBigGrid.setPreferredSize(new Dimension(650, 650));
        pPlayer.setLayout(new GridLayout(10, 10)); // Afficher les différentes cases de l'adversaire
        pPlayer.setPreferredSize(new Dimension(500, 500));
        pMenu.setLayout(new GridLayout(1, 2, 10, 10)); // Afficher pOption à coté de pMenu


        labelPosition.setBorder(border);
        pMenu.add(labelPosition);

        labelFire.setBorder(border);
        pMenu.add(labelFire);


        createBigGrid(pBigGrid, gridLabelBigGrid);
        createLittleGrid(pPlayer, gridLabelLittleGrid);


        pInformations.add(pPlayer, BorderLayout.NORTH);
        pInformations.add(pMenu, BorderLayout.CENTER);

        pMain.add(pBigGrid, BorderLayout.WEST);
        pMain.add(pInformations, BorderLayout.EAST);

        root.add(pMain);
        setContentPane(root);

    }

    private void createBigGrid(JPanel pBigGrid, JLabel[][] gridLabelBigGrid) {
        for (int i = 0; i < 11; i++) {
            for (int j = 0; j < 11; j++) {
                if (i == 0) {
                    switch (j) {
                        // @formatter:off
                        case 1:
                            pBigGrid.add(A);
                            break;
                        case 2:
                            pBigGrid.add(B);
                            break;
                        case 3:
                            pBigGrid.add(C);
                            break;
                        case 4:
                            pBigGrid.add(D);
                            break;
                        case 5:
                            pBigGrid.add(E);
                            break;
                        case 6:
                            pBigGrid.add(F);
                            break;
                        case 7:
                            pBigGrid.add(G);
                            break;
                        case 8:
                            pBigGrid.add(H);
                            break;
                        case 9:
                            pBigGrid.add(I);
                            break;
                        case 10:
                            pBigGrid.add(J);
                            break;
                        default:
                            pBigGrid.add(new JLabel(" "));
                            break;
                        // @formatter:on
                    }
                } else {
                    if (j == 0) {
                        JLabel label = new JLabel(Integer.toString(i), JLabel.CENTER);
                        label.setForeground(Color.WHITE);
                        label.setFont(new Font(label.getFont().getName(), Font.PLAIN, 30));
                        label.setBackground(new Color(0,0,0, 200));
                        label.setOpaque(true);
                        pBigGrid.add(label);
                    } else {
                        gridLabelBigGrid[i - 1][j - 1] = new JLabel();
                        gridLabelBigGrid[i - 1][j - 1].setBorder(border);
                        gridLabelBigGrid[i - 1][j - 1].setForeground(Color.WHITE);
                        gridLabelBigGrid[i - 1][j - 1].setHorizontalAlignment(SwingConstants.CENTER);
                        pBigGrid.add(gridLabelBigGrid[i - 1][j - 1]);
                    }
                }
            }
        }
    }
    private void createLittleGrid(JPanel pPlayer, JLabel[][] gridLabelBigGrid) {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                gridLabelBigGrid[i][j] = new JLabel();
                gridLabelBigGrid[i][j].setBorder(border);
                gridLabelBigGrid[i][j].setForeground(Color.WHITE);
                gridLabelBigGrid[i][j].setHorizontalAlignment(SwingConstants.CENTER);
                pPlayer.add(gridLabelBigGrid[i][j]);
            }
        }
    }



    // DIALOG

    public void dialogBadDirectionBoatPlacement() {
        JOptionPane optionPane = new JOptionPane();
        optionPane.showMessageDialog(
                null,
                "Impossible de changer la direction du bateau. Vérifiez que vous placez votre bateau dans la " +
                        "grille et qu'il n'empiète pas sur un autre bateau",
                "Placement impossible",
                JOptionPane.ERROR_MESSAGE
        );
    }
    public void dialogConfirmAllBoatPlacement() {
        JOptionPane optionPane = new JOptionPane();
        int i = optionPane.showConfirmDialog(
                null,
                "Confirmez-vous le placement des bateaux ?",
                "Validation du placement des bateaux",
                JOptionPane.YES_NO_OPTION
        );
        if (i == 0) stateApp = 7;
    }
    public void dialogSelectionBoat() {
        JOptionPane optionPane = new JOptionPane();
        optionPane.showMessageDialog(
                null,
                "Veuillez confirmer le placement de votre bateau avant d'en choisir un autre",
                "Impossible de sélectionner ce bateau",
                JOptionPane.INFORMATION_MESSAGE
        );
    }
    public void dialogueLostOrdi() {
        JOptionPane optionPane = new JOptionPane();
        optionPane.showMessageDialog(
        null,
        "Vous avez perdu la partie contre l'ordinateur...",
        "You lost !",
        JOptionPane.INFORMATION_MESSAGE
        );
    }
    public void dialogWinOrdi() {
        JOptionPane optionPane = new JOptionPane();
        optionPane.showMessageDialog(
        null,
        "Vous avez Gagné la partie contre l'ordinateur !!",
        "You win !",
        JOptionPane.INFORMATION_MESSAGE
        );
    }

    public void dialogueLostOnline() {
        JOptionPane optionPane = new JOptionPane();
        optionPane.showMessageDialog(
                null,
                "Vous avez perdu la partie contre l'adversaire en ligne...",
                "You lost !",
                JOptionPane.INFORMATION_MESSAGE
        );
    }
    public void dialogWinOnline() {
        JOptionPane optionPane = new JOptionPane();
        optionPane.showMessageDialog(
                null,
                "Vous avez Gagné la partie contre l'adversair en ligne ! !!",
                "You win !",
                JOptionPane.INFORMATION_MESSAGE
        );
    }



    // LISTENER

    public void listenerMenuBar(ActionListener m) {
        item1.addActionListener(m);
        item2.addActionListener(m);
        quit.addActionListener(m);
        tClassic.addActionListener(m);
        tFarm.addActionListener(m);
        tSpace.addActionListener(m);
    }
    public void listenerMenu(ActionListener a) {
        buttonSingleplayer.addActionListener(a);
        buttonMultiplayer.addActionListener(a);
    }
    public void listenerChoixMulti(ActionListener a) {
        bLocal.addActionListener(a);
        bOnline.addActionListener(a);
    }
    public void listenerChoixPartie(ActionListener a) {
        bClassique.addActionListener(a);
        bPerso.addActionListener(a);
    }
    public void listenerChoixOnline(ActionListener a) {
        bServer.addActionListener(a);
        bClient.addActionListener(a);
    }
    public void listenerGamemode(ActionListener a) {
        bMoinsTorpilleur.addActionListener(a);
        bPlusTorpilleur.addActionListener(a);
        bMoinsSM.addActionListener(a);
        bPlusSM.addActionListener(a);
        bMoinsCT.addActionListener(a);
        bPlusCT.addActionListener(a);
        bMoinsCroiseur.addActionListener(a);
        bPlusCroiseur.addActionListener(a);
        bMoinsPA.addActionListener(a);
        bPlusPA.addActionListener(a);
        bValider.addActionListener(a);
        bReinit.addActionListener(a);
    }
    public void listenerBoatPlacement(MouseListener mouseListener) {
        labelTorpilleur.addMouseListener(mouseListener);
        labelSousMarin.addMouseListener(mouseListener);
        labelContreTorpilleur.addMouseListener(mouseListener);
        labelCroiseur.addMouseListener(mouseListener);
        labelPorteAvion.addMouseListener(mouseListener);
        labelDirection.addMouseListener(mouseListener);
        labelOk.addMouseListener(mouseListener);
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                gridLabelBigGrid[i][j].addMouseListener(mouseListener);
            }
        }
    }
    public void listenerCreateGame(MouseListener mouseListener) {
        labelFire.addMouseListener(mouseListener);
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                gridLabelBigGrid[i][j].addMouseListener(mouseListener);
            }
        }
    }



    // OTHERS


    // OTHERS

    public void placeMarkerBig(int type,int x,int y) {
        switch (type){
            case 0:
                this.gridLabelBigGrid[y][x].setIcon(whiteDot);
                break;
            case 1:
                this.gridLabelBigGrid[y][x].setIcon(redDot);
                break;
            case 2:
                this.gridLabelBigGrid[y][x].setIcon(redDot);
                break;
            case 3:
                this.gridLabelBigGrid[y][x].setIcon(target);
                break;
        }
        this.getContentPane().repaint();
    }

    public void placeMarkerLitle(int type,int x,int y) {
        switch (type){
            case 0:
                this.gridLabelLittleGrid[y][x].setIcon(whiteDot);
                break;
            case 1:
                this.gridLabelLittleGrid[y][x].setOpaque(true);
                this.gridLabelLittleGrid[y][x].setBackground(Color.red);
                break;
            case 2:
                this.gridLabelLittleGrid[y][x].setOpaque(true);
                this.gridLabelLittleGrid[y][x].setBackground(Color.RED);
                break;
        }
        this.getContentPane().repaint();
    }


    public void changeView(int vue) {
        switch (vue) {
            case 1: {
                getContentPane().removeAll();
                getContentPane().repaint();
                createMenuPrincipal();
                setVisible(true);
                break;
            }
            case 2: {
                getContentPane().removeAll();
                getContentPane().repaint();
                initializeChoixMulti();
                createChoixMulti();
                setVisible(true);
                break;
            }
            case 3: {
                getContentPane().removeAll();
                getContentPane().repaint();
                initializeChoixPartie();
                createChoixPartie();
                setVisible(true);
                break;
            }
            case 4: {
                getContentPane().removeAll();
                initializeChoixOnline();
                createChoixOnline();
                getContentPane().repaint();
                setVisible(true);
                break;
            }
            case 5: {
                getContentPane().removeAll();
                initializeGamemode();
                createGamemode();
                getContentPane().repaint();
                setVisible(true);
                break;
            }
            case 6: {
                getContentPane().removeAll();
                getContentPane().repaint();
                initializeBoatPlacement();
                createBoatPlacement();
                pack();
                break;
            }
            case 7: {
                getContentPane().removeAll();
                getContentPane().repaint();
                initializeGame();
                createGame();
                pack();
                break;
            }
        }
    }


    public void drawBoatPlaced(JLabel[][] gridLabel) {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if (game.getPlayer1().getMap().getTabMapCase()[i][j].getIsOccupied() && isFaceMur(i,j) && !(game.getPlayer1().getMap().getTabMapCase()[i][j].isVerif())) {
                    int id = game.getPlayer1().getMap().getTabMapCase()[i][j].getIdBoat();
                    if (isFaceMur(i+1,j) && game.getPlayer1().getMap().getTabMapCase()[i+1][j].getIsOccupied()) {
                        String type = "0";
                        int size = 0;
                        switch (id) {
                            case 0: type="2"; size=2; break;
                            case 1: type="sous-marin"; size=3; break;
                            case 2: type="contre-torpilleur"; size=3; break;
                            case 3: type="4"; size=4; break;
                            case 4: type="5"; size=5; break;
                        }
                        for (int k = 1; k <= size; k++) {
                            ImageIcon img = new ImageIcon(new ImageIcon(getClass().getResource("./image/"+game.getTheme()+"/"+type+"/horizontal/"+k+".png")).
                                    getImage().getScaledInstance(59,59,Image.SCALE_DEFAULT));
                            gridLabel[j][i+(k-1)].setIcon(img);
                            game.getPlayer1().getMap().getTabMapCase()[i+(k-1)][j].setVerif(true);
                            gridLabel[j][i+(k-1)].setText(" ");
                            gridLabel[j][i+(k-1)].setOpaque(false);
                        }
                    }
                    if (isFaceMur(i,j+1) && game.getPlayer1().getMap().getTabMapCase()[i][j+1].getIsOccupied()) {
                        String type = "0";
                        int size = 0;
                        switch (id) {
                            case 0: type="2"; size=2; break;
                            case 1: type="sous-marin"; size=3; break;
                            case 2: type="contre-torpilleur"; size=3; break;
                            case 3: type="4"; size=4; break;
                            case 4: type="5"; size=5; break;
                        }
                        for (int k = 1; k <= size; k++) {
                            ImageIcon img = new ImageIcon(new ImageIcon(getClass().getResource("./image/"+game.getTheme()+"/"+type+"/vertical/"+k+".png")).
                                    getImage().getScaledInstance(59,59,Image.SCALE_DEFAULT));
                            gridLabel[j+(k-1)][i].setIcon(img);
                            game.getPlayer1().getMap().getTabMapCase()[i][j+(k-1)].setVerif(true);
                            gridLabel[j+(k-1)][i].setText(" ");
                            gridLabel[j+(k-1)][i].setOpaque(false);
                        }
                    }
                }
            }
        }
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                game.getPlayer1().getMap().getTabMapCase()[i][j].setVerif(false);
            }
        }
    }


    public void display() {
        this.setVisible(true);
    }

    private boolean isFaceMur(int y, int x) {
        return (y >= 0 && x >= 0 && y < 10 && x < 10);
    }


    // TO STRING / GETTERS, SETTERS

    @Override
    public String toString() {
        String s = "\n\n";

        for (int i = 0; i < game.getPlayer1().getMap().getLengthY(); i++) {
            for (int j = 0; j < game.getPlayer1().getMap().getWidthX(); j++) {
                s += this.gridLabelBigGrid[i][j].getText();
            }
            s += "\n";
        }

        return s;
    }


    public JLabel getlNbrTorpilleur() {
        return lNbrTorpilleur;
    }

    public JLabel getlNbrSM() {
        return lNbrSM;
    }

    public JLabel getlNbrCT() {
        return lNbrCT;
    }

    public JLabel getlNbrCroiseur() {
        return lNbrCroiseur;
    }

    public JLabel getlNbrPA() {
        return lNbrPA;
    }

    public JButton getButtonSingleplayer() {
        return buttonSingleplayer;
    }

    public JButton getButtonMultiplayer() {
        return buttonMultiplayer;
    }

    public JButton getbMoinsTorpilleur() {
        return bMoinsTorpilleur;
    }

    public JButton getbPlusTorpilleur() {
        return bPlusTorpilleur;
    }

    public JButton getbMoinsSM() {
        return bMoinsSM;
    }

    public JButton getbPlusSM() {
        return bPlusSM;
    }

    public JButton getbMoinsCT() {
        return bMoinsCT;
    }

    public JButton getbPlusCT() {
        return bPlusCT;
    }

    public JButton getbMoinsCroiseur() {
        return bMoinsCroiseur;
    }

    public JButton getbPlusCroiseur() {
        return bPlusCroiseur;
    }

    public JButton getbMoinsPA() {
        return bMoinsPA;
    }

    public JButton getbPlusPA() {
        return bPlusPA;
    }

    public JButton getbValider() {
        return bValider;
    }

    public JButton getbReinit() {
        return bReinit;
    }

    public JButton getbClassique() {
        return bClassique;
    }

    public JButton getbPerso() {
        return bPerso;
    }

    public JButton getbLocal() {
        return bLocal;
    }

    public JButton getbOnline() {
        return bOnline;
    }

    public JButton getbServer() {
        return bServer;
    }

    public JButton getbClient() {
        return bClient;
    }

    public JTextField getTfClient() {
        return tfClient;
    }

    public JMenuItem getItem1() {
        return item1;
    }

    public JMenuItem getItem2() {
        return item2;
    }

    public JMenuItem getQuit() {
        return quit;
    }

    public JLabel getLabelDirection() {
        return labelDirection;
    }

    public JLabel getLabelOk() {
        return labelOk;
    }

    public JLabel getLabelDelete() {
        return labelDelete;
    }

    public JLabel getLabelPosition() {
        return labelPosition;
    }

    public JLabel getLabelFire() {
        return labelFire;
    }

    public JLabel getLabelTorpilleur() {
        return labelTorpilleur;
    }

    public JLabel getLabelSousMarin() {
        return labelSousMarin;
    }

    public JLabel getLabelContreTorpilleur() {
        return labelContreTorpilleur;
    }

    public JLabel getLabelCroiseur() {
        return labelCroiseur;
    }

    public JLabel getLabelPorteAvion() {
        return labelPorteAvion;
    }

    public JLabel[][] getGridLabelBigGrid() {
        return gridLabelBigGrid;
    }

    public JLabel[][] getGridLabelLittleGrid() {
        return gridLabelLittleGrid;
    }

    public ImageIcon getImageDirectionHorizontal() {
        return imageDirectionHorizontal;
    }

    public ImageIcon getImageDirectionVertical() {
        return imageDirectionVertical;
    }

    public ImageIcon getImageOkReleased() {
        return imageOkReleased;
    }

    public ImageIcon getImageOkClicked() {
        return imageOkClicked;
    }

    public ImageIcon getImageDeleteReleased() {
        return imageDeleteReleased;
    }

    public ImageIcon getImageDeleteClicked() {
        return imageDeleteClicked;
    }

    public int getStateApp() {
        return stateApp;
    }

    public ImageIcon getRedDot() {
        return redDot;
    }

    public void setRedDot(ImageIcon redDot) {
        this.redDot = redDot;
    }

    public ImageIcon getWhiteDot() {
        return whiteDot;
    }

    public void setWhiteDot(ImageIcon whiteDot) {
        this.whiteDot = whiteDot;
    }

    public ImageIcon getTarget() {
        return target;
    }

    public void setTarget(ImageIcon target) {
        this.target = target;
    }

    public ImageIcon getBlackDot() {
        return blackDot;
    }

    public void setBlackDot(ImageIcon blackDot) {
        this.blackDot = blackDot;
    }

    public JMenuItem gettClassic() {
        return tClassic;
    }

    public JMenuItem gettFarm() {
        return tFarm;
    }

    public JMenuItem gettSpace() {
        return tSpace;
    }

    public JLabel getLabelYourIP() { return labelYourIP; }
}
