/*
 * Contains methods the game world objects will use, including both moveable
 * and fixed objects. All game world objects will inherit from it. 
 */
package a4;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;

/**
 *
 * @author Aben
 */
public abstract class GameObject implements IDrawable, ICollider {

    private float x, y; //x and y co-ordinates for game object
    private Color myColor; //color of game object
    private Point.Float location; //location specified by x and y co-ordinates
    private AffineTransform translation = new AffineTransform();
    private AffineTransform rotation = new AffineTransform();
    private AffineTransform scale = new AffineTransform();

    public GameObject() {
        x = 0;
        y = 0;
        location = new Point.Float(x, y);
    }

    public Color getColor() {
        return myColor;
    }

    public void setColor(Color newColor) {
        myColor = newColor;
    }

    public Point.Float getLocation() {
        return location;
        //return this.translation.getTranslateX();
    }

    public void setLocation(Point.Float newLoc) {
        location = newLoc;
    }

    @Override
    public void draw(Graphics2D g) {
        g.transform(this.translation);
        g.transform(this.scale);
        g.transform(this.rotation);
    }

    @Override
    public boolean collidesWith(ICollider otherObject) {
        
        boolean result = false;
        int thisCenterX = (int) getLocation().x;
        int thisCenterY = (int) getLocation().y;

        GameObject obj = (GameObject) otherObject;
        int otherCenterX = (int) obj.getLocation().x;
        int otherCenterY = (int) obj.getLocation().y;

        int dx = thisCenterX - otherCenterX;
        int dy = thisCenterY - otherCenterY;
        int distBetweenCentersSqr = dx * dx + dy * dy;

        int thisRadius = getSize();
        int otherRadius = obj.getSize();
        int radiiSqr = (int) Math.pow(thisRadius + otherRadius, 2.0D);
        if (distBetweenCentersSqr <= radiiSqr) {
            result = true;
        }

        return result;

    }

    public AffineTransform getTranslation() {
        return this.translation;
    }

    public AffineTransform getRotation() {
        return this.rotation;
    }

    public AffineTransform getScale() {
        return this.scale;
    }

    public void setPoint(Point2D p) {
        location.x = (int)p.getX();
        location.y = (int)p.getY();
        setLocation(location);
    }

    public void rotate(float degrees) {
        this.rotation.rotate(Math.toRadians(-degrees));
    }

    public void scale(double x, double y) {
        this.scale.scale(x, y);
    }

    public void translate(double dx, double dy) {
        this.translation.translate(dx, dy);
    }

    public void resetRotation() {
        this.rotation.setToIdentity();
    }
}
