import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import java.io.*;
import java.util.List;
import java.util.Vector;

interface SelectionListener {
    void updateButtons(Color color, int thickness);
}

public class SketchArea extends JPanel {
    public enum Mode{
        Select,
        Erase,
        Line,
        Circle,
        Rectangle,
        Fill
    }

    //private JPanel canvas;

    private Mode mode;
    private Color color;
    private int thickness;

    private SketchObject newSketchObject;
    private int selectedSketchObject = -1;
    private Point startPoint;
    private Point endPoint;

    int x_distance_start;
    int y_distance_start;
    int x_distance_end;
    int y_distance_end;

    List<SketchObject> sketchObjects;


    String[] CSVcolumn = {"mode","startPointX","startPointY", "endPointX", "endPointY","color","fillColor","thickness"};


    public SelectionListener SelectListener;
    public void addSelectionListener(SelectionListener listener){
        SelectListener = listener;
    }


    MouseListener mouseListener = new MouseAdapter() {
        @Override
        public void mousePressed(MouseEvent e) {
            if(mode == Mode.Select || mode==Mode.Erase || mode==Mode.Fill){
                int index = selectSketchObject(e.getPoint());
                if(index == -1) return;
                if(mode==Mode.Fill){
                    sketchObjects.get(index).fillColor = color;
                }
                else if(mode == Mode.Erase){
                    sketchObjects.remove(index);
                }
                else if(mode == Mode.Select){
                    cancelSelected();
                    startPoint = e.getPoint();
                    SketchObject selectedObj = sketchObjects.get(index);
                    sketchObjects.remove(index);
                    selectedObj.setSelect(true);
                    sketchObjects.add(selectedObj);

                    x_distance_start = selectedObj.startPoint.x - e.getX();
                    y_distance_start = selectedObj.startPoint.y - e.getY();
                    x_distance_end =  selectedObj.endPoint.x - e.getX();
                    y_distance_end =  selectedObj.endPoint.y - e.getY();

                    selectedSketchObject = sketchObjects.size()-1;

                    SelectListener.updateButtons(selectedObj.color, selectedObj.thickness);
                }
                repaint();
            }
            else {
                startPoint = e.getPoint();
                newSketchObject = new SketchObject(startPoint, startPoint, mode, color, thickness);
            }
        }
        @Override
        public void mouseReleased(MouseEvent e){
            if(mode == Mode.Select || mode == Mode.Fill || mode==Mode.Erase)
                return;
            if(startPoint.x!=e.getX() || startPoint.y!=e.getY()) {
                sketchObjects.add(newSketchObject);
                //JOptionPane.showMessageDialog(null,"new Object Added!");
            }

        }
    };
    MouseMotionListener mouseMotionListener = new MouseMotionAdapter() {
        @Override
        public void mouseDragged(MouseEvent e) {
            if(mode == Mode.Select || mode==Mode.Erase||mode==Mode.Fill){

                if(selectedSketchObject != -1){
                    boolean move = sketchObjects.get(selectedSketchObject).mode == Mode.Line;
                    if(!move)
                        move = sketchObjects.get(selectedSketchObject).isAbleSelect(e.getPoint());

                    if(move) {
                        Point startPoint = new Point(e.getX() + x_distance_start, e.getY() + y_distance_start);
                        Point endPoint = new Point(e.getX() + x_distance_end, e.getY() + y_distance_end);
                        sketchObjects.get(selectedSketchObject).startPoint = startPoint;
                        sketchObjects.get(selectedSketchObject).endPoint = endPoint;
                        repaint();
                    }
                }
            }
            else {
                endPoint = e.getPoint();
                newSketchObject.endPoint = endPoint;
                repaint();
            }
        }
    };

    KeyListener SelectSketchObject = new KeyAdapter() {
        @Override
        public void keyPressed(KeyEvent e) {
            //JOptionPane.showMessageDialog(null,"ESC Pressed!");
            if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                cancelSelected();
                repaint();
            }
        }
    };


    private void cancelSelected(){
        selectedSketchObject = -1;
        for(SketchObject obj : sketchObjects)
            obj.setSelect(false);
        repaint();
    }



    SketchArea(){
        mode = Mode.Select;
        color = Color.black;
        thickness = 1;

        sketchObjects = new Vector<SketchObject>();

        addMouseListener(mouseListener);
        addMouseMotionListener(mouseMotionListener);
        addKeyListener(SelectSketchObject);
    }

    @Override
    public void paintComponent(Graphics g){
        //super.repaint();
        setBackground(Color.WHITE);
        for(SketchObject objs : sketchObjects)
            objs.draw(g);

        if(newSketchObject != null)
            newSketchObject.draw(g);

    }

    private int selectSketchObject(Point selectPoint){
        for(int i = sketchObjects.size()-1; i >=0; i--){
            if(sketchObjects.get(i).isAbleSelect(selectPoint)) {
                 return i;
            }
        }
        return -1;
    }

    public void clear(){
        sketchObjects.clear();
        selectedSketchObject = -1;
        newSketchObject = null;
        repaint();
    }
    public void setSelect(){
        mode = Mode.Select;
        newSketchObject = null;
        cancelSelected();
        this.requestFocusInWindow();
        this.requestFocus();
    }
    public void setErase(){
        mode=Mode.Erase;
        newSketchObject = null;
        cancelSelected();
    }
    public void setLine(){
        mode=Mode.Line;
        newSketchObject = null;
        cancelSelected();
    }
    public void setCircle(){
        mode=Mode.Circle;
        newSketchObject = null;
        cancelSelected();
    }
    public void setRectangle(){
        mode=Mode.Rectangle;
        newSketchObject = null;
        cancelSelected();
    }
    public void setFill(){
        mode=Mode.Fill;
        newSketchObject = null;
        cancelSelected();
        this.requestFocus();
    }
    public void setColor(Color colour){
        color = colour;
        if(mode == Mode.Select){
            if(selectedSketchObject != -1){
                sketchObjects.get(selectedSketchObject).color = color;
                repaint();
            }
            this.requestFocus();
        }
    }
    public void setLineThickness(int thickness){
        this.thickness = thickness;
        if(mode == Mode.Select){
            if(selectedSketchObject != -1){
                sketchObjects.get(selectedSketchObject).thickness = thickness;
                repaint();
            }
        }
    }

    public void save(String path){
        try {
            File file = new File(path);
            if (!file.exists()) {
                file.createNewFile();
            }
            FileWriter w = new FileWriter(path);
            BufferedWriter writer = new BufferedWriter(w);
            for (SketchObject obj : sketchObjects) {
                writer.write(obj.getCSVstring());
            }
            writer.close();
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    public void load(String path) {
        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            String line = "";

            while (true) {
                line = reader.readLine();
                if(line == null) break;
                String[] CSVInfo = line.split(",");

                Mode mode = null;


                if (CSVInfo[0].equals("Line")) mode = Mode.Line;
                else if (CSVInfo[0].equals("Circle")) mode = Mode.Circle;
                else if (CSVInfo[0].equals("Rectangle")) mode = Mode.Rectangle;


                Point startPoint = new Point(Integer.parseInt(CSVInfo[1]), Integer.parseInt(CSVInfo[2]));
                Point endPoint = new Point(Integer.parseInt(CSVInfo[3]), Integer.parseInt(CSVInfo[4]));


                color = new Color(Integer.parseInt(CSVInfo[5]));

                Color fillColor = null;
                if(!CSVInfo[6].equals("null"))
                    fillColor = new Color(Integer.parseInt(CSVInfo[6]));

                int thickness = Integer.parseInt(CSVInfo[7]);

                SketchObject newObjs = new SketchObject(startPoint, endPoint, mode, color, thickness);
                if(fillColor != null)
                    newObjs.fillColor = fillColor;
                sketchObjects.add(newObjs);
            }
            repaint();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }


  //  String[] CSVcolumn = {"mode","startPointX","startPointY", "endPointX", "endPointY","color","fillColor","thickness"};



}
