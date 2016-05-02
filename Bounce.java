import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;


public class Bounce extends Animation {

    protected double X, Y, Vx, Vy, deltaT, pixelsPerMeter;
    protected int radius, firstTime = 1, pixelX, pixelY;
    protected double gravity;
    protected Map<Ball, List<Ball>> contactList;

    protected List<Ball> balls;

    protected void initAnimator() {
        deltaT = 0.005; // simulation time interval in seconds
        setDelay((int) (1000 * deltaT)); // needed for Animation superclass
        gravity = 80;
        pixelsPerMeter = 50;

        balls = new ArrayList<Ball>();
        //adding balls
        Ball ball1 = new Ball(3,2,2, 50, Color.red, 0.5 );
        Ball ball2 = new Ball(8,2,2, 50, Color.blue,0.5);
        balls.add(ball1);
        balls.add(ball2);


        Ball ball3 = new Ball(10,2,3, 10, Color.cyan,20);
        Ball ball4 = new Ball(12,2,4, 50, Color.black,20);
        //balls.add(ball3);
        //balls.add(ball4);

        contactList = new HashMap<Ball, List<Ball>>();
    }



    protected void paintAnimator(Graphics g) {
        if (firstTime == 1) {
            g.setColor(Color.white);
            g.fillRect(0, 0, d.width, d.height);
            g.setColor(Color.red);
            g.drawLine(0,2*(50-25),d.width,2*(50-25));
            firstTime = 0;
        }
        //g.fillRect(0,0,d.width,d.height); // slower?
        for(int i = 0; i < balls.size(); i++) {
            Ball ball = balls.get(i);
            X = ball.X;
            Y = ball.Y;
            pixelX = (int) (pixelsPerMeter * X);
            pixelY = (int) (pixelsPerMeter * Y);
            radius = ball.radius;
            Vx = ball.Vx;
            Vy = ball.Vy;


            g.setColor(Color.WHITE);
            g.fillOval(pixelX - radius, pixelY - radius, radius * 2, radius * 2);

            //collision
            for (int j = 0; j < balls.size(); j++) {
                Ball ball2 = balls.get(j);
                double centerDistance = Math.sqrt(Math.pow(ball2.X - ball.X, 2) + Math.pow(ball2.Y - ball.Y, 2));

                //finns kontakt?
                if (ball != ball2){
                    if ((centerDistance <= (ball.radius + ball2.radius) / pixelsPerMeter)) {

                        if (!(contactList.containsKey(ball) && contactList.get(ball).contains(ball2)) && !(contactList.containsKey(ball2) && contactList.get(ball2).contains(ball))) {

                            //gör krock

                            //skapar vektor mellan center på bollarna
                            Vector2D centerVector = new Vector2D(ball.X-ball2.X, ball.Y - ball2.Y);
                            centerVector.normalize();

                            Vector2D ball1Vector = new Vector2D(ball.Vx, ball.Vy);
                            //Vector2D ball1Projected = centerVector.project(ball1Vector);

                            Vector2D ball2Vector = new Vector2D(ball2.Vx, ball2.Vy);
                            //Vector2D ball2Projected = centerVector.project(ball2Vector);

                            Vector2D tangent = new Vector2D(-(centerVector.y), centerVector.x);

                            //Vector2D ball1Tangent = tangent.project(ball1Vector);
                            //Vector2D ball2Tangent = tangent.project(ball2Vector);

                            double ball1normal = ball1Vector.dotProduct(centerVector);
                            double ball2normal = ball2Vector.dotProduct(centerVector);
                            double ball1tangent = ball1Vector.dotProduct(tangent);
                            double ball2tangent = ball2Vector.dotProduct(tangent);

                            double ball1VelocityNormal = (ball1normal * (ball.weight - ball2.weight) + 2 * ball2.weight * ball2normal) / (ball.weight + ball2.weight);
                            double ball2VelocityNormal = (ball2normal * (ball2.weight - ball.weight) + 2 * ball.weight * ball1normal) / (ball.weight + ball2.weight);

                            Vector2D ball1NewNormal = centerVector.copy();
                            ball1NewNormal.scalarMult(ball1VelocityNormal);

                            Vector2D ball2NewNormal = centerVector.copy();
                            ball2NewNormal.scalarMult(ball2VelocityNormal);

                            Vector2D ball1TangentVec = centerVector.copy();
                            ball1TangentVec.scalarMult(ball1tangent);

                            Vector2D ball2TangentVec = centerVector.copy();
                            ball2TangentVec.scalarMult(ball2tangent);

                            ball.Vx = ball1NewNormal.x + ball1TangentVec.x;
                            ball.Vy = ball1NewNormal.y + ball1TangentVec.y;

                            ball2.Vx = ball2NewNormal.x + ball2TangentVec.x;
                            ball2.Vy = ball2NewNormal.y + ball2TangentVec.y;

                            //Lägger till krock
                            System.out.println("Krock mellan " + i + "och " + j);
                            if (contactList.containsKey(ball)) {
                                contactList.get(ball).add(ball2);
                            } else {
                                List<Ball> listOfContacts = new ArrayList<Ball>();
                                listOfContacts.add(ball2);
                                contactList.put(ball, listOfContacts);
                            }
                        }
                    } else {
                        //ta bort kollisionen om den finns
                        boolean ballHadCollision = false;
                        if (contactList.containsKey(ball)) {
                            ballHadCollision = contactList.get(ball).remove(ball2);
                        } else if (contactList.containsKey(ball2)) {
                            contactList.get(ball2).remove(ball2);
                        }
                    }
                }
            }//for-loop end

            //edge detection
            if (pixelX < radius || pixelX > d.width - radius) {
                Vx = -Vx;
            }
            if (pixelY > d.height - radius) {
                if(Vy > 0 ){
                    Vy = -Vy;
                }
            }else{
                //gravity
                Vy += gravity * deltaT;
            }
            Y += (Vy) * deltaT;
            X += Vx * deltaT;

            pixelX = (int) (pixelsPerMeter * X);
            pixelY = (int) (pixelsPerMeter * Y);

            g.setColor(ball.color);
            g.fillOval(pixelX - radius, pixelY - radius, radius * 2, radius * 2);


            ball.Vx = Vx;
            ball.Vy = Vy;
            ball.X = X;
            ball.Y = Y;
        }
    }

}



            /*if(!ball.done){
                Vy += gravity * deltaT;
                Y += (Vy) * deltaT;
            }*/

//|| ,, inget tak pixelY < radius  + Vy*gravity*deltaT  !ball.done

                /*if(((Y + (Vy*-1 - Vy*gravity*deltaT) * deltaT) > (d.height-radius)/pixelsPerMeter)){
                    System.out.println("Ball done");
                    ball.done = true;
                    Vy = 0;
                    Y = (d.height-radius)/pixelsPerMeter;
                }else{
                    Vy = -Vy;
                }

                public static int factorial(int n) {
        int fact = 1; // this  will be the result
        for (int i = 1; i <= n; i++) {
            fact *= i;
        }
        return fact;
    }
                */

/*
                            Vector2D temp1 = ball1Projected.copy();
                            temp1.scalarMult((ball.weight-ball2.weight)/(ball.weight + ball2.weight));
                            Vector2D temp2 = ball2Projected.copy();
                            temp2.scalarMult(2*ball2.weight/(ball.weight+ball2.weight));

                            Vector2D ball1After = new Vector2D(temp1.x+temp2.x,temp1.y+temp2.y);

                            temp1 = ball2Projected.copy();
                            temp1.scalarMult((ball2.weight-ball.weight)/(ball.weight + ball2.weight));
                            temp2 = ball1Projected.copy();
                            temp2.scalarMult(2*ball.weight/(ball.weight+ball2.weight));

                            Vector2D ball2After = new Vector2D(temp1.x+temp2.x,temp1.y+temp2.y );
                            */
