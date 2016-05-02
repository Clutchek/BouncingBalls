import java.awt.*;

/**
 * Created by clutchek on 4/28/16.
 */
public class Ball {
    protected double X,Y,Vx,Vy,weight;
    protected int radius;
    protected Color color;

    public Ball(double X, double Y, double Vx, int radius, Color color, double weight){
        this.X = X; // in meters
        this.Y = Y; // Y reference direction downwards!
        this.Vx = Vx; // in m/s
        Vy = -1.3;
        this.radius = radius;
        this.color = color;
        this.weight = weight;
    }


}
