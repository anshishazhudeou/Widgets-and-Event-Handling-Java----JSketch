import java.awt.*;
import java.awt.geom.Line2D;

public class SketchObject {
    Point startPoint, endPoint;
    SketchArea.Mode mode;
    Color color;
    Color fillColor;
    int thickness;
    boolean selected;
    SketchObject(Point startPoint, Point endPoint, SketchArea.Mode mode, Color color, int thickness){
        this.startPoint = startPoint;
        this.endPoint = endPoint;
        this.mode = mode;
        this.color = color;
        this.thickness = thickness;
    }

    public void setSelect(boolean selected){
        this.selected = selected;
    }

    public String getCSVstring(){
        StringBuilder sb = new StringBuilder();
        sb.append(mode.toString());
        sb.append(",");
        sb.append(startPoint.x);
        sb.append(",");
        sb.append(startPoint.y);
        sb.append(",");
        sb.append(endPoint.x);
        sb.append(",");
        sb.append(endPoint.y);
        sb.append(",");
        sb.append(color.getRGB());
        sb.append(",");
        if(fillColor == null)
            sb.append("null");
        else
            sb.append(fillColor.getRGB());

        sb.append(",");
        sb.append(thickness);
        sb.append("\n");
        return sb.toString();
        //  String[] CSVcolumn = {"mode","startPointX","startPointY", "endPointX", "endPointY","color","fillColor","thickness"};

    }

    public boolean isAbleSelect(Point selectPoint){
        boolean select = false;
        if(mode == SketchArea.Mode.Rectangle || mode == SketchArea.Mode.Line || mode == SketchArea.Mode.Circle) {
            select = selectPoint.x <= Math.max(startPoint.x, endPoint.x) &&
                    selectPoint.x >= Math.min(startPoint.x, endPoint.x) &&
                    selectPoint.y <= Math.max(startPoint.y, endPoint.y) &&
                    selectPoint.y >= Math.min(startPoint.y, endPoint.y);
        }
        if(mode == SketchArea.Mode.Line){
            Line2D line = new Line2D.Double(startPoint.x, startPoint.y, endPoint.x, endPoint.y);
            select = line.intersects(selectPoint.x+1, selectPoint.y+1, 1,1);
        }
        return select;
    }

    public void draw(Graphics g){
        Graphics2D g2 = (Graphics2D) g;
        g2.setStroke(new BasicStroke(thickness));
        if(selected)
            g2.setStroke(new BasicStroke(thickness, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[]{9}, 0));

        if(mode == SketchArea.Mode.Line){
            g2.setColor(color);
            g2.drawLine(startPoint.x, startPoint.y, endPoint.x, endPoint.y);
        }
        else if(mode == SketchArea.Mode.Circle){
            if(fillColor != null) {
                g2.setColor(fillColor);
                g2.fillOval(Math.min(startPoint.x, endPoint.x), Math.min(startPoint.y, endPoint.y),
                        Math.abs(endPoint.x - startPoint.x), Math.abs(endPoint.y - startPoint.y));
            }
            g2.setColor(color);
            g2.drawOval(Math.min(startPoint.x, endPoint.x), Math.min(startPoint.y, endPoint.y),
                    Math.abs(endPoint.x - startPoint.x), Math.abs(endPoint.y - startPoint.y));
        }
        else if(mode == SketchArea.Mode.Rectangle){
            if(fillColor!=null) {
                g2.setColor(fillColor);
                g2.fillRect(Math.min(startPoint.x, endPoint.x), Math.min(startPoint.y, endPoint.y),
                        Math.abs(endPoint.x - startPoint.x), Math.abs(endPoint.y - startPoint.y));
            }
            g2.setColor(color);
            g2.drawRect(Math.min(startPoint.x, endPoint.x), Math.min(startPoint.y, endPoint.y),
                    Math.abs(endPoint.x - startPoint.x), Math.abs(endPoint.y - startPoint.y));
        }

    }
}
