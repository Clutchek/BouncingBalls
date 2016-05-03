import java.awt.geom.Ellipse2D;
import java.util.*;
import java.util.LinkedList;
import java.util.List;

public class DummyModel implements IBouncingBallsModel {

	private final double areaWidth;
	private final double areaHeight;

	private double x, y, vx, vy, r, gravity;
    private Map<Ball, List<Ball>> contactList;
    private boolean applyGravity;

    private List<Ball> balls;

	public DummyModel(double width, double height) {
		//arena storlek
		this.areaWidth = width;
		this.areaHeight = height;

        gravity = -9.82;
        applyGravity = true;

        balls = new ArrayList<Ball>();
		Ball ball1 = new Ball(3,8,2.3,1,1,0.5);
		Ball ball2 = new Ball(6,8,2.3,1,1,0.5);
        Ball ball3 = new Ball(9,8,2.3,1,1,0.5);

		balls.add(ball1);
		balls.add(ball2);
        balls.add(ball3);

        contactList = new HashMap<Ball, List<Ball>>();

	}

	@Override
	public void tick(double deltaT) {
		for(Ball ball1 : balls) {
            applyGravity = true;
            //check if our ball is in a collision
            for(Ball ball2 : balls){
                if(ball1 != ball2){
                    //create vector from the centers of the balls
                    Vector2D centerVector = new Vector2D(ball1.X - ball2.X, ball1.Y - ball2.Y);
                    double centerDistance = centerVector.length();
                    if(centerDistance <= (ball1.radius + ball2.radius)){
                        if (!(contactList.containsKey(ball1) && contactList.get(ball1).contains(ball2)) && !(contactList.containsKey(ball2) && contactList.get(ball2).contains(ball1))) {
                            applyGravity = false;

                            centerVector.normalize();

                            Vector2D ball1Vector = new Vector2D(ball1.Vx, ball1.Vy);

                            Vector2D ball2Vector = new Vector2D(ball2.Vx, ball2.Vy);

                            //create tangent to centerVector
                            Vector2D tangent = new Vector2D(-(centerVector.y), centerVector.x);

                            //project our velocity vectors for the balls to the center vector and tangent
                            double ball1normal = ball1Vector.dotProduct(centerVector);
                            double ball2normal = ball2Vector.dotProduct(centerVector);
                            double ball1tangent = ball1Vector.dotProduct(tangent);
                            double ball2tangent = ball2Vector.dotProduct(tangent);

                            //compute the new velocitys after the collision
                            double ball1VelocityNormal = (ball1normal * (ball1.weight - ball2.weight) + 2 * ball2.weight * ball2normal) / (ball1.weight + ball2.weight);
                            double ball2VelocityNormal = (ball2normal * (ball2.weight - ball1.weight) + 2 * ball1.weight * ball1normal) / (ball1.weight + ball2.weight);

                            //multiply our calculated values to our projected vectors
                            Vector2D ball1NewNormal = centerVector.copy();
                            ball1NewNormal.scalarMult(ball1VelocityNormal);

                            Vector2D ball2NewNormal = centerVector.copy();
                            ball2NewNormal.scalarMult(ball2VelocityNormal);

                            Vector2D ball1TangentVec = tangent.copy();
                            ball1TangentVec.scalarMult(ball1tangent);

                            Vector2D ball2TangentVec = tangent.copy();
                            ball2TangentVec.scalarMult(ball2tangent);

                            //add the values from the velocity vectors projected tangent and the new velocities in the centerdirection after the collision
                            ball1.Vx = ball1NewNormal.x + ball1TangentVec.x;
                            ball1.Vy = ball1NewNormal.y + ball1TangentVec.y;

                            ball2.Vx = ball2NewNormal.x + ball2TangentVec.x;
                            ball2.Vy = ball2NewNormal.y + ball2TangentVec.y;

                            //add collision to our list
                            if (contactList.containsKey(ball1)) {
                                contactList.get(ball1).add(ball2);
                            } else {
                                List<Ball> listOfContacts = new ArrayList<Ball>();
                                listOfContacts.add(ball2);
                                contactList.put(ball1, listOfContacts);
                            }
                        }
                    } else {
                        //remove collision if it does no longer exist
                        boolean ballHadCollision = false;
                        if (contactList.containsKey(ball1)) {
                            ballHadCollision = contactList.get(ball1).remove(ball2);
                        } else if (contactList.containsKey(ball2)) {
                            contactList.get(ball2).remove(ball2);
                        }
                    }
                }
            }

            //check edge collision
            if ( ((ball1.X < ball1.radius) && (ball1.Vx < 0))  || ((ball1.X > areaWidth - ball1.radius) && (ball1.Vx > 0))) {
                ball1.Vx *= -1;
                applyGravity = false;
			}
			if (((ball1.Y < ball1.radius) && (ball1.Vy < 0))|| ((ball1.Y > areaHeight - ball1.radius) && (ball1.Vy > 0))) {
				ball1.Vy *= -1;
                applyGravity = false;
			}
            //We do not want to apply gravity if there has been a change in Vx/Vy in the same tick.
            if(applyGravity){
                ball1.Vy += gravity * deltaT;
            }
			ball1.X += ball1.Vx * deltaT;
			ball1.Y += ball1.Vy * deltaT;	
		}
		
	}

	@Override
	public List<Ellipse2D> getBalls() {
		List<Ellipse2D> myBalls = new LinkedList<Ellipse2D>();
		for(Ball ball : balls){
			myBalls.add(ball.getEllipse());
		}
		return myBalls;
	}
}
