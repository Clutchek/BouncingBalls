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

		balls.add(ball1);
		balls.add(ball2);

        contactList = new HashMap<Ball, List<Ball>>();

	}

	@Override
	public void tick(double deltaT) {
		for(Ball ball1 : balls) {
            applyGravity = true;
            //kolla krock
            for(Ball ball2 : balls){
                if(ball1 != ball2){
                    Vector2D centerVector = new Vector2D(ball1.X - ball2.X, ball1.Y - ball2.Y);
                    double centerDistance = centerVector.length();
                    if(centerDistance <= (ball1.radius + ball2.radius)){
                        if (!(contactList.containsKey(ball1) && contactList.get(ball1).contains(ball2)) && !(contactList.containsKey(ball2) && contactList.get(ball2).contains(ball1))) {
                            System.out.println("Krock");
                            if (contactList.containsKey(ball1)) {
                                contactList.get(ball1).add(ball2);
                            } else {
                                List<Ball> listOfContacts = new ArrayList<Ball>();
                                listOfContacts.add(ball2);
                                contactList.put(ball1, listOfContacts);
                            }
                        }
                    } else {
                        //ta bort kollisionen om den finns
                        boolean ballHadCollision = false;
                        if (contactList.containsKey(ball1)) {
                            ballHadCollision = contactList.get(ball1).remove(ball2);
                        } else if (contactList.containsKey(ball2)) {
                            contactList.get(ball2).remove(ball2);
                        }
                    }
                }
            }



            if ( ((ball1.X < ball1.radius) && (ball1.Vx < 0))  || ((ball1.X > areaWidth - ball1.radius) && (ball1.Vx > 0))) {
                ball1.Vx *= -1;
                applyGravity = false;
			}
			if (((ball1.Y < ball1.radius) && (ball1.Vy < 0))|| ((ball1.Y > areaHeight - ball1.radius) && (ball1.Vy > 0))) {
				ball1.Vy *= -1;
                applyGravity = false;
			}
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
