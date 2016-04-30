/**
 * Created by clutchek on 4/30/16.
 */
public class VectorTest {
    public static void main(String[] args){

        Vector2D vector1 = new Vector2D(-2,-3);
        Vector2D vector2 = new Vector2D(-3,-1);

        System.out.println(Math.toDegrees(vector1.angle(vector2)));
        System.out.println(Math.toDegrees(vector2.angle(vector1)));

    }
}
