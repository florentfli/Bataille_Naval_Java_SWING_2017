/*
import javax.swing.*;
import java.awt.*;
import java.util.Arrays;

public class View extends JFrame {

    ControlBarMenu controlBarMenu;
    JPanel pMap;
    JMenuBar barMenu;
    JMenu menu;
    JMenuItem item1;
    JMenuItem item2;
    JTextField testPrintNames;
    JLabel testPrintNames2;
    JLabel[][] label;

    public View(){
        setTitle("BattleShip");
        setIconImage(new ImageIcon("out/production/image/icon.jpg").getImage());*/
/* ?? *//*

        initialize();
        createWidgetVer1();
        createMenu();
        pack();
        setSize(1600,900);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLocation(100,0);
        for (int i = 0; i < 10; i++){
            for (int j = 0; j < 10; j++){
                System.out.print(label[i][j].getText());
            }
            System.out.println("");
        }
    }

    private void initialize(){
        item1 = new JMenuItem("Rules");
        controlBarMenu = new ControlBarMenu(this);
        item1.addActionListener(controlBarMenu);
        item2 = new JMenuItem("New game");
        item2.addActionListener(controlBarMenu);
        testPrintNames = new JTextField();
        testPrintNames2 = new JLabel();
        pMap = new JPanel(new GridLayout(10,10,20,20));
        label = new JLabel[10][10];
        int t = 0 ;
        for (int i = 0; i < 10; i++){
            for (int j = 0; j < 10; j++){
                label[j][i] = new JLabel(" 0 ");
                pMap.add(label[j][i], i, j);
                t++;
            }
        }
    }

    @Override
    public String toString() {
        String s="\n\n";

        for (int i = 0; i<10; i++){
            for (int j=0;j<10;j++){
                s+=this.label[i][j].getText();
            }
            s+="\n";
        }

        return s;
    }

    public void createWidgetVer1(){


        PanelBackground backGround = new PanelBackground();
       */
/* Grid grid = new Grid(500,500,10,10);
        JPanel mainPan = new JPanel();

        mainPan.setSize(800,800);
        mainPan.setBackground(Color.ORANGE);
        mainPan.add(grid);


        backGround.add(mainPan);*//*


        //pMap = new JPanel(new GridLayout(10,10,20,20));

        */
/*for (int i = 0; i < 10; i++){
            for (int j = 0; j < 10; j++){
                label = new JLabel("0");
                pMap.add(label,i,j);
            }
        }*//*


        backGround.add(pMap);
        */
/*backGround.add(testPrintNames2);
        backGround.add(testPrintNames);*//*

        setContentPane(backGround);
    }

    public void createMenu(){
        barMenu = new JMenuBar();
        menu = new JMenu("Options");
        menu.add(item1);
        menu.add(item2);
        barMenu.add(menu);
        setJMenuBar(barMenu);
    }

    public void updateGrid(int x, int y){
        this.label[x][9-y].setText("X");
    }

    public void drawGrid(Player p){
        for (int i = 0; i < p.getMap().getWidthX(); i++){
            for (int j = 0; j < p.getMap().getLengthY(); j++){
                if (p.getMap().getTabMapCase()[i][j].getIsOccupied()){

                   */
/* System.out.println(p.getMap());
                    System.out.println(p.getMapCheck());*//*


                    //System.out.println(view.getLabel()[i][j].getText() );

                    //System.out.println(view.getLabel()[i][j].getText());
                    this.updateGrid(i,j);
                }
            }
        }
    }

    public JLabel[][] getLabel() {
        return label;
    }

    public void setLabel(JLabel[][] label) {
        this.label = label;
    }

    public JMenuBar getBarMenu() {
        return barMenu;
    }
    public void setBarMenu(JMenuBar barMenu) {
        this.barMenu = barMenu;
    }
    public JMenu getMenu() {
        return menu;
    }
    public void setMenu(JMenu menu) {
        this.menu = menu;
    }
    public JMenuItem getItem1() {
        return item1;
    }
    public void setItem1(JMenuItem item1) {
        this.item1 = item1;
    }
    public JMenuItem getItem2() {
        return item2;
    }
    public void setItem2(JMenuItem item2) {
        this.item2 = item2;
    }
    public JTextField getTestPrintNames() {
        return testPrintNames;
    }
    public void setTestPrintNames(JTextField testPrintNames) {
        this.testPrintNames = testPrintNames;
    }
    public JLabel getTestPrintNames2() {
        return testPrintNames2;
    }
    public void setTestPrintNames2(JLabel testPrintNames2) {
        this.testPrintNames2 = testPrintNames2;
    }
}
*/
