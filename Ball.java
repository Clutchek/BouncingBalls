import java.awt.*;

/**
 * Created by clutchek on 4/28/16.
 */
public class Ball {
    protected double X,Y,Vx,Vy;
    protected int radius, weight;
    protected Color color;

    public Ball(double X, double Y, double Vx, int radius, Color color, int weight){
        this.X = X; // in meters
        this.Y = Y; // Y reference direction downwards!
        this.Vx = Vx; // in m/s
        Vy = -1.3;
        this.radius = radius;
        this.color = color;
        this.weight = weight;
    }


}
