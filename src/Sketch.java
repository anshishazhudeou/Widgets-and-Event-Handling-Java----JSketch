import javax.imageio.ImageIO;
import javax.swing.*;
import java.io.File;
import java.nio.file.Paths;
import java.awt.*;
import java.awt.event.*;


public class Sketch extends JFrame{

    private JSplitPane splitpanelMain;
    private JPanel panelPalette;
    private JPanel panelTool, panelColor,panelLine;

    private JButton buttonSeletion, buttonEarse, buttonLineDraw, buttonCircle, buttonRectangle, buttonFill;
    private JButton buttonColor1, buttonColor2, buttonColor3, buttonColor4, buttonColor5, buttonColor6, buttonChooser;
    private JButton buttonLine1, buttonLine2,  buttonLine3;

    private JMenuBar menubarMenu;
    private JMenuItem menuItemSave, menuItemLoad, menuItemNew;

    Color[] colors = {Color.darkGray, Color.YELLOW, Color.GREEN, Color.BLUE, Color.PINK, Color.ORANGE};

    private  SketchArea sketchArea;



    private void buttonIndicator(JPanel panel, JButton button){
        if(panel != panelColor) {
            for (Component component : panel.getComponents()) {
                JButton b = (JButton) component;
                //b.setBorder(BorderFactory.createEmptyBorder());
                b.setBorder(UIManager.getBorder("Button.border"));
            }
            if(button != null) {
                button.setBackground(Color.white);
                button.setOpaque(true);
                button.setBorder(BorderFactory.createLineBorder(Color.black, 3));
            }
        }

        else if(panel == panelColor){
                for (Component component : panel.getComponents()) {
                    JButton b = (JButton) component;
                    //b.setBorder(BorderFactory.createEmptyBorder());
                    b.setBorder(UIManager.getBorder("Button.border"));
                    b.setBorderPainted(false);
                }
                if(button != null) {
                    button.setBorder(BorderFactory.createLineBorder(Color.black, 3));
                    button.setBorderPainted(true);
                }
        }

    }
    private void panelColorLineEnable(boolean ColorEnable, boolean LineEnable, boolean select){
        buttonIndicator(panelColor, buttonColor1);
        buttonIndicator(panelLine, buttonLine1);
        for(Component c : panelColor.getComponents()) {
            c.setEnabled(ColorEnable);
            if(!ColorEnable)
                buttonIndicator(panelColor, null);
        }
        for(Component c : panelLine.getComponents()) {
            c.setEnabled(LineEnable);
            if(!LineEnable)
                buttonIndicator(panelLine, null);
        }
        if(!select) {
            sketchArea.setColor(colors[0]);
            sketchArea.setLineThickness(1);
        }
    }
    ActionListener actionListener = new ActionListener() {
        public void actionPerformed(ActionEvent e){
            if(e.getSource() == menuItemNew){
                sketchArea.clear();
            }
            else if(e.getSource() == menuItemSave){
                JFileChooser chooser = new JFileChooser();
                chooser.setCurrentDirectory(new File("."));
                int returnVal = chooser.showSaveDialog(menubarMenu);
                String filename=chooser.getSelectedFile().getName() + ".csv";
                if(returnVal == JFileChooser.APPROVE_OPTION){
                    sketchArea.save(filename);
                }
            }
            else if(e.getSource() == menuItemLoad){
                JFileChooser chooser = new JFileChooser();
                chooser.setCurrentDirectory(new File("."));
                int returnVal = chooser.showDialog(menubarMenu , "Load");
                if(returnVal == JFileChooser.APPROVE_OPTION){
                    sketchArea.clear();
                    sketchArea.load(chooser.getSelectedFile().getName());
                }
            }
            else if(e.getSource() == buttonSeletion){
                //JOptionPane.showMessageDialog(null,"select!");
                buttonIndicator(panelTool, buttonSeletion);
                sketchArea.setSelect();
                panelColorLineEnable(false, false, true);

            }
            else if(e.getSource() == buttonEarse){
                //JOptionPane.showMessageDialog(null,"earse!");
                buttonIndicator(panelTool, buttonEarse);
                sketchArea.setErase();
                panelColorLineEnable(false, false, false);

            }
            else if(e.getSource() == buttonLineDraw){
                //JOptionPane.showMessageDialog(null,"line!");
                buttonIndicator(panelTool, buttonLineDraw);
                sketchArea.setLine();
                panelColorLineEnable(true, true, false);

            }
            else if(e.getSource() == buttonCircle){
                //JOptionPane.showMessageDialog(null,"circle!");
                buttonIndicator(panelTool, buttonCircle);
                sketchArea.setCircle();
                panelColorLineEnable(true, true, false);

            }
            else if(e.getSource() == buttonRectangle){
                //JOptionPane.showMessageDialog(null,"rectangle!!");;
                buttonIndicator(panelTool, buttonRectangle);
                sketchArea.setRectangle();
                panelColorLineEnable(true, true, false);

            }
            else if(e.getSource() == buttonFill){
                //JOptionPane.showMessageDialog(null,"fill!!");
                buttonIndicator(panelTool, buttonFill);
                sketchArea.setFill();
                panelColorLineEnable(true, false, false);

            }
            else if(e.getSource() == buttonColor1) {
                //JOptionPane.showMessageDialog(null,"color1!!");
                buttonIndicator(panelColor, buttonColor1);
                sketchArea.setColor(Color.DARK_GRAY);
            }
            else if(e.getSource() == buttonColor2){
                //JOptionPane.showMessageDialog(null,"color2!");
                buttonIndicator(panelColor, buttonColor2);
                sketchArea.setColor(Color.YELLOW);
            }
            else if(e.getSource() == buttonColor3){
                //JOptionPane.showMessageDialog(null,"color3!");
                buttonIndicator(panelColor, buttonColor3);
                sketchArea.setColor(Color.GREEN);
            }
            else if(e.getSource() == buttonColor4) {
                //JOptionPane.showMessageDialog(null,"color4!");
                buttonIndicator(panelColor, buttonColor4);
                sketchArea.setColor(Color.BLUE);
            }
            else if(e.getSource() == buttonColor5) {
                //JOptionPane.showMessageDialog(null,"color5!");
                buttonIndicator(panelColor, buttonColor5);
                sketchArea.setColor(Color.PINK);
            }
            else if(e.getSource() == buttonColor6) {
                //JOptionPane.showMessageDialog(null,"color6!");
                buttonIndicator(panelColor, buttonColor6);
                sketchArea.setColor(Color.ORANGE);
            }
            else if(e.getSource() == buttonChooser){
                Color chooserColor = JColorChooser.showDialog(null,
                        "JColorChooser", Color.BLACK);
                if(chooserColor!=null) {
                    buttonIndicator(panelColor, buttonChooser);
                    sketchArea.setColor(chooserColor);
                }
            }
            else if(e.getSource() == buttonLine1){
                //JOptionPane.showMessageDialog(null,"line1!");
                buttonIndicator(panelLine, buttonLine1);
                sketchArea.setLineThickness(1);
            }
            else if(e.getSource() == buttonLine2) {
                //JOptionPane.showMessageDialog(null,"line2!");
                buttonIndicator(panelLine, buttonLine2);
                sketchArea.setLineThickness(2);
            }
            else if(e.getSource() == buttonLine3){
                //JOptionPane.showMessageDialog(null,"line3!");
                buttonIndicator(panelLine, buttonLine3);
                sketchArea.setLineThickness(3);
            }
        }
    };

    SelectionListener SketchAreaActionListener = new SelectionListener() {
        public void updateButtons(Color color, int thickness) {
            panelColorLineEnable(true, true, true);
            for(int i = 0; i < 6; i++){
                if(colors[i].getRGB() == color.getRGB()) {
                    buttonIndicator(panelColor, (JButton)panelColor.getComponents()[i]);
                    break;
                }
            }
            for(int i = 0; i < 3; i++){
                if(i+1 == thickness){
                    buttonIndicator(panelLine, (JButton)panelLine.getComponents()[i]);
                    break;
                }
            }
        }
    };

    Sketch(){
        try {
            //setMinimumSize(new Dimension(400,400));
            setSize(1000, 800);
            setResizable(false);
            setDefaultCloseOperation(EXIT_ON_CLOSE);

            //palette
            panelPalette = new JPanel();
            panelPalette.setLayout(new GridLayout(0, 1, 0, 5));

            //canvas
            sketchArea = new SketchArea();
            sketchArea.addSelectionListener(SketchAreaActionListener);
            sketchArea.setAutoscrolls(true);

            //menu
            menubarMenu = new JMenuBar();
            JMenu MenuFile = new JMenu("File");
            menuItemNew = new JMenuItem("New");
            menuItemNew.addActionListener(actionListener);
            menuItemSave = new JMenuItem("Save");
            menuItemSave.addActionListener(actionListener);
            menuItemLoad = new JMenuItem("Load");
            menuItemLoad.addActionListener(actionListener);
            MenuFile.add(menuItemNew);
            MenuFile.add(menuItemSave);
            MenuFile.add(menuItemLoad);
            menubarMenu.add(MenuFile);
            menubarMenu.add(new JMenu("View"));
            this.setJMenuBar(menubarMenu);

            //button

            buttonSeletion = new JButton(new ImageIcon(ImageIO.read(new File("./src/icons/select.png"))));
            buttonSeletion.addActionListener(actionListener);
            buttonEarse = new JButton(new ImageIcon(ImageIO.read(new File("./src/icons/erase.png"))));
            buttonEarse.addActionListener(actionListener);
            buttonLineDraw = new JButton(new ImageIcon(ImageIO.read(new File("./src/icons/line.png"))));
            buttonLineDraw.addActionListener(actionListener);
            buttonCircle = new JButton(new ImageIcon(ImageIO.read(new File("./src/icons/circle.png"))));
            buttonCircle.addActionListener(actionListener);
            buttonRectangle = new JButton(new ImageIcon(ImageIO.read(new File("./src/icons/rectangle.png"))));
            buttonRectangle.addActionListener(actionListener);
            buttonFill = new JButton(new ImageIcon(ImageIO.read(new File("./src/icons/fill.png"))));
            buttonFill.addActionListener(actionListener);
            buttonColor1 = new JButton();
            buttonColor1.addActionListener(actionListener);
            buttonColor2 = new JButton();
            buttonColor2.addActionListener(actionListener);
            buttonColor3 = new JButton();
            buttonColor3.addActionListener(actionListener);
            buttonColor4 = new JButton();
            buttonColor4.addActionListener(actionListener);
            buttonColor5 = new JButton();
            buttonColor5.addActionListener(actionListener);
            buttonColor6 = new JButton();
            buttonColor6.addActionListener(actionListener);
            buttonChooser = new JButton("Chooser");
            buttonChooser.addActionListener(actionListener);
            buttonColor1.setBackground(colors[0]);
            buttonColor1.setOpaque(true);
            buttonColor1.setBorderPainted(false);
            buttonColor2.setBackground(colors[1]);
            buttonColor2.setOpaque(true);
            buttonColor2.setBorderPainted(false);
            buttonColor3.setBackground(colors[2]);
            buttonColor3.setOpaque(true);
            buttonColor3.setBorderPainted(false);
            buttonColor4.setBackground(colors[3]);
            buttonColor4.setOpaque(true);
            buttonColor4.setBorderPainted(false);
            buttonColor5.setBackground(colors[4]);
            buttonColor5.setOpaque(true);
            buttonColor5.setBorderPainted(false);
            buttonColor6.setBackground(colors[5]);
            buttonColor6.setOpaque(true);
            buttonColor6.setBorderPainted(false);

            buttonLine1 = new JButton(new ImageIcon(ImageIO.read(new File("./src/icons/line_thin.png"))));
            buttonLine1.addActionListener(actionListener);
            buttonLine2 = new JButton(new ImageIcon(ImageIO.read(new File("./src/icons/line_median.png"))));
            buttonLine2.addActionListener(actionListener);
            buttonLine3 = new JButton(new ImageIcon(ImageIO.read(new File("./src/icons/line_thick.png"))));
            buttonLine3.addActionListener(actionListener);

            //Boxes
            panelTool = new JPanel();
            panelTool.setLayout(new GridLayout(0, 2));

            panelTool.add(buttonSeletion);
            panelTool.add(buttonEarse);
            panelTool.add(buttonLineDraw);
            panelTool.add(buttonCircle);
            panelTool.add(buttonRectangle);
            panelTool.add(buttonFill);

            panelColor = new JPanel();
            panelColor.setLayout(new GridLayout(0, 2, 5, 5));
            panelColor.add(buttonColor1);
            panelColor.add(buttonColor2);
            panelColor.add(buttonColor3);
            panelColor.add(buttonColor4);
            panelColor.add(buttonColor5);
            panelColor.add(buttonColor6);
            panelColor.add(buttonChooser);

            panelLine = new JPanel();
            panelLine.setLayout(new GridLayout(3, 1));
            panelLine.add(buttonLine1);
            panelLine.add(buttonLine2);
            panelLine.add(buttonLine3);

            panelPalette.add(panelTool);
            panelPalette.add(panelColor);
            panelPalette.add(panelLine);

            splitpanelMain = new JSplitPane();
            splitpanelMain.setLeftComponent(panelPalette);
            splitpanelMain.setRightComponent(sketchArea);
            splitpanelMain.setDividerLocation(0.15);
            splitpanelMain.setResizeWeight(0.02);
            splitpanelMain.setDividerSize(1);
            splitpanelMain.setEnabled(false);

            add(splitpanelMain);
            buttonLine1.doClick();
            buttonColor1.doClick();
            buttonSeletion.doClick();
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

}
