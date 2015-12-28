/*
 * 
 */
package a4.gameObjects;

import a4.ICollider;
import a4.IDrawable;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.util.Random;

/**
 *
 * @author Aben
 */
public class Sweepers extends Moveable implements IDrawable {

    private Random generator;
    private float x, y; //x & y for co-ordinates 
    private Point.Float sweeperLocation; // for pass
    private Point.Float controlGenPoint;
    private int sweeperHeading, sweeperSpeed;
    private Point2D.Double[] controlPoint = new Point2D.Double[4];
    private float controlGen = 0;
    private int width = 0;
    private int height = 0;
    private int size = 0;

    public Sweepers() {
        x = y = 0;
        sweeperHeading = sweeperSpeed = 0;
        generator = new Random();  // used for generating random objects

        /* random location for x between x = 5 and x = 155*/
        x = (generator.nextFloat() * 150) + 5;

        /* random y location for y between y = 5 and y = 155*/
        y = (generator.nextFloat() * 150) + 5;

        controlGen = (generator.nextFloat() * 60) + 10;

        sweeperHeading = generator.nextInt(180);
        sweeperLocation = new Point.Float(x, y); // location with x and y co-ordinates
        sweeperSpeed = generator.nextInt(10);
        //controlGenPoint = new Point.Float();

        super.setLocation(sweeperLocation); // set locataion of sweeper

        super.setHeading(sweeperHeading); //heading for monsterball
        super.setSpeed(sweeperSpeed); //speed for monsterball


        this.controlPoint[0] = new Point2D.Double(sweeperLocation.x + controlGen, sweeperLocation.y);
        this.controlPoint[1] = new Point2D.Double(sweeperLocation.x, sweeperLocation.y + controlGen - 10);
        this.controlPoint[2] = new Point2D.Double(sweeperLocation.x, -1*sweeperLocation.y + controlGen + 10);
        this.controlPoint[3] = new Point2D.Double(sweeperLocation.x + controlGen, sweeperLocation.y + controlGen * 2);

        width = (int) controlGen;
        height = (int) controlGen * 2;
        //size = (int) Math.sqrt(width / 2 * width / 2 + height / 2 * height / 2);
    }

    public Sweepers(Point.Float newLoc) {
        generator = new Random();
        controlGen = (generator.nextFloat() * 60) + 10;

        sweeperLocation = new Point.Float(newLoc.x,newLoc.y-controlGen); // location passed

        sweeperHeading = generator.nextInt(180);
        sweeperSpeed = generator.nextInt(10);
        super.setLocation(sweeperLocation); // set locataion of sweeper
        super.setHeading(sweeperHeading); //heading for monsterball
        super.setSpeed(sweeperSpeed); //speed for monsterball

        this.controlPoint[0] = new Point2D.Double(sweeperLocation.x + controlGen, sweeperLocation.y);
        this.controlPoint[1] = new Point2D.Double(sweeperLocation.x, sweeperLocation.y + controlGen - 10);
        this.controlPoint[2] = new Point2D.Double(sweeperLocation.x, sweeperLocation.y + controlGen + 10);
        this.controlPoint[3] = new Point2D.Double(sweeperLocation.x + controlGen, sweeperLocation.y + controlGen * 2);

        width = (int) controlGen;
        height = (int) controlGen * 2;
    }

    @Override
    public int getHeading() {
        return sweeperHeading;
    }

    /* Heading is unchanged by method call*/
    @Override
    public void setHeading(int heading) {
        sweeperHeading = heading;
    }

    @Override
    public int getSpeed() {
        return sweeperSpeed;
    }

    /* Sweep set to random location, unchanged by this method*/
    @Override
    public void setLocation(Point.Float newLoc) {
    }

    /* Speed is unchaged by method call*/
    @Override
    public void setSpeed(int speed) {
    }

    @Override
    public void move(int heading, int speed, float amount) {
        //System.out.println("h " + heading + " s " + speed + " a " + amount);
        super.move(heading, speed, amount);


        controlPoint[0].x = controlPoint[0].getX() + getLocation().x / 100;
        controlPoint[0].y = controlPoint[0].getY() + getLocation().y / 100;

        controlPoint[1].x = controlPoint[1].getX() + getLocation().x / 100;
        controlPoint[1].y = controlPoint[1].getY() + getLocation().y / 100;

        controlPoint[2].x = controlPoint[2].getX() + getLocation().x / 100;
        controlPoint[2].y = controlPoint[2].getY() + getLocation().y / 100;

        controlPoint[3].x = controlPoint[3].getX() + getLocation().x / 100;
        controlPoint[3].y = controlPoint[3].getY() + getLocation().y / 100;

        controlGenPoint = new Point.Float((int) controlPoint[1].getX(), (int) controlPoint[0].getY());

        super.setLocation(controlGenPoint);

    }

    @Override
    public int getSize() {
        //return this.size;
        int max = Math.max(this.width, this.height);
        max = max / 2 * max / 2 + max / 2 * max / 2;
        max = (int) Math.sqrt(max);

        return max;
    }

    @Override
    public void setSize() {
    }

    @Override
    public void handleCollision(ICollider otherObject) {
    }

    @Override
    public void draw(Graphics2D g) {
        AffineTransform save = g.getTransform();
        super.draw(g);
        
        g.transform(getTranslation());
        g.transform(getScale());
        g.transform(getRotation());
        g.setColor(Color.green);
       
        drawBezierCurve(this.controlPoint, 0, g);
        g.setColor(Color.BLACK);
        g.drawLine((int) this.controlPoint[0].getX(), (int) this.controlPoint[0].getY(), (int) this.controlPoint[1].getX(), (int) this.controlPoint[1].getY());
        g.drawLine((int) this.controlPoint[1].getX(), (int) this.controlPoint[1].getY(), (int) this.controlPoint[2].getX(), (int) this.controlPoint[2].getY());
        g.drawLine((int) this.controlPoint[2].getX(), (int) this.controlPoint[2].getY(), (int) this.controlPoint[3].getX(), (int) this.controlPoint[3].getY());
        g.drawLine((int) this.controlPoint[3].getX(), (int) this.controlPoint[3].getY(), (int) this.controlPoint[0].getX(), (int) this.controlPoint[0].getY());
        g.setTransform(save);
    }

    private void drawBezierCurve(Point2D.Double[] controlPointVector, int level, Graphics2D g) {
        Point2D.Double[] LeftSubVector = new Point2D.Double[4];
        Point2D.Double[] RightSubVector = new Point2D.Double[4];
        if ((straightEnough(controlPointVector)) || (level > 15)) {
            g.drawLine((int) controlPointVector[0].getX(), (int) controlPointVector[0].getY(), (int) controlPointVector[3].getX(), (int) controlPointVector[3].getY());
        } else {
            subdivideCurve(controlPointVector, LeftSubVector, RightSubVector);
            drawBezierCurve(LeftSubVector, level + 1, g);
            drawBezierCurve(RightSubVector, level + 1, g);
        }
    }

    private boolean straightEnough(Point2D.Double[] ControlPointVector) {
        Double d1 = Double.valueOf(lengthOf(ControlPointVector[0], ControlPointVector[1]).doubleValue() + lengthOf(ControlPointVector[1], ControlPointVector[2]).doubleValue() + lengthOf(ControlPointVector[2], ControlPointVector[3]).doubleValue());

        Double d2 = lengthOf(ControlPointVector[0], ControlPointVector[3]);
        if (Math.abs(d1.doubleValue() - d2.doubleValue()) < 9.999999747378752E-005D) {
            return true;
        }
        return false;
    }

    private Double lengthOf(Point2D.Double aDouble, Point2D.Double aDouble1) {
        Double answer = null;
        Double X = Double.valueOf(Math.pow(aDouble.getX() - aDouble1.getX(), 2.0D));
        Double Y = Double.valueOf(Math.pow(aDouble.getY() - aDouble1.getY(), 2.0D));
        answer = Double.valueOf(Math.sqrt(X.doubleValue() + Y.doubleValue()));
        return answer;
    }

    private void subdivideCurve(Point2D.Double[] Q, Point2D.Double[] R, Point2D.Double[] S) {
        Point2D.Double T = doMathOnPoints(Q[1], Q[2]);
        R[0] = Q[0];
        R[1] = doMathOnPoints(Q[0], Q[1]);
        R[2] = doMathOnPoints(R[1], T);
        S[3] = Q[3];
        S[2] = doMathOnPoints(Q[3], Q[2]);
        S[1] = doMathOnPoints(S[2], T);
        R[3] = doMathOnPoints(R[2], S[1]);
        S[0] = R[3];
    }

    private Point2D.Double doMathOnPoints(Point2D.Double p1, Point2D.Double p2) {
        return new Point2D.Double(p1.getX() / 2.0D + p2.getX() / 2.0D, p1.getY() / 2.0D + p2.getY() / 2.0D);
    }
}
