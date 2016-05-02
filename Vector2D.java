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
        //return this.length() * vector2.length() * this.angle(vector2);
        return this.x*vector2.x+this.y*vector2.y;
    }

    public double length(){
        return Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
    }

    //computes the angle between this and another vector
    public double angle(Vector2D vector2 ){
        double angle = Math.atan2(vector2.y -y, vector2.x - x);
        return angle;
    }

    public void scalarMult(double scalar){
        this.x = x*scalar;
        this.y = y*scalar;
    }

    public Vector2D copy(){
        return new Vector2D(x,y);
    }

    public Vector2D project(Vector2D vector2){
        /*
        double scalar = this.dotProduct(vector2)/Math.pow(this.length(),2);
        return new Vector2D(scalar*vector2.x,scalar*vector2.y);
        */
        double divide = this.dotProduct(vector2) / vector2.dotProduct(vector2);
        return new Vector2D(vector2.x * divide, vector2.y * divide);
    }

    public void normalize(){
        double length = this.length();
        this.y = y/length;
        this.x = x/length;
    }
}
