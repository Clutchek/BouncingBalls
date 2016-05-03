import java.awt.*;
import java.awt.geom.Ellipse2D;

/**
 * Created by clutchek on 4/28/16.
 */
public class Ball {
    protected double X,Y,Vx,Vy,weight;
    protected double radius;
    protected Color color;

    public Ball(double X, double Y, double Vx, double Vy, double radius, double weight){
        this.X = X; // in meters
        this.Y = Y; // Y reference direction downwards!
        this.Vx = Vx; // in m/s
        this.Vy = Vy;
        this.radius = radius;
        this.weight = weight;
    }

    public Ellipse2D.Double getEllipse(){
        return new Ellipse2D.Double(X - radius, Y - radius, 2 * radius, 2 * radius);
    }
        


}
