/**
 * Created by clutchek on 4/30/16.
 */
public class Vector2D {
    protected double x;
    protected double y;

    public Vector2D(double x, double y){
        this.x = x;
        this.y = y;
    }

    //returns the dot product between two vectors
    public double dotProduct(Vector2D vector2){
        return this.length() * vector2.length() * this.angle(vector2);
    }

    public double length(){
        return Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
    }

    //computes the angle between this and another vector
    public double angle(Vector2D vector2 ){
        double angle = Math.atan2(vector2.y -y, vector2.x - x);
        if(angle < 0){
            angle = angle + Math.PI;
        }
        return angle;
    }
}
