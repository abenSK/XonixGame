/*
 *
 */
package a4.gameObjects;

import a4.GameObject;
import java.awt.Point;

/**
 *
 * @author Aben
 */
public abstract class Moveable extends GameObject {

    private int heading;
    private int speed, theta;
    private float deltaX, deltaY;
    private Point.Float newLocation;
    float x, y;

    public Moveable() {

        heading = 0;
        speed = 0;
    }

    public void setHeading(int newHeading) {
        heading = newHeading;
    }

    public int getHeading() {
        return heading;
    }

    public void setSpeed(int newSpeed) {
        speed = newSpeed;
    }

    public int getSpeed() {
        return speed;
    }

    /* sin and cosine in radians*/
    public void move(int newHeading, int newSpeed, float amount) {
        theta = 90 - newHeading;
        deltaX = (float) Math.cos(Math.toRadians(theta)) * newSpeed * amount;
        deltaY = (float) Math.sin(Math.toRadians(theta)) * newSpeed * amount;

        /* newLocation = oldLocation + deltaValues*/
        x = (deltaX + getLocation().x);
        y = (deltaY + getLocation().y);

        newLocation = new Point.Float(x, y);
        super.setLocation(newLocation);

    }
    
}
