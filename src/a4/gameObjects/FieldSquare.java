/*
 * Represents a field square in the game. Methods include attributes of a game field. 
 */
package a4.gameObjects;

import a4.GameObject;
import a4.ICollider;
import a4.IDrawable;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.AffineTransform;

/**
 *
 * @author Aben
 */
public class FieldSquare extends Fixed implements IDrawable {

    private int size;
    private float xCor, yCor;
    private int width = 15, height = 15;
    //int xcenter = 0, ycenter = 0;
    private Point.Float location;
    private boolean potentialField = false;
    private boolean ownAll = false;
    private boolean owned = false;

    public FieldSquare() {
        size = 5; //field sized
        owned = false; //Field value default to unowned
        xCor = yCor = 0;
        location = new Point.Float();
        super.setColor(Color.blue);
    }

    public FieldSquare(Point.Float field) {
        location = field;
        super.setLocation(location);
        super.setColor(Color.gray);
        //potentialField = true;

    }
    public FieldSquare(float x, float y) {
        location = new Point.Float(x,y);
        super.setLocation(location);
        super.setColor(Color.blue);
       // potentialField = true;

    }
    /* Own a square*/

    public void owned() {
        owned = true;
    }

    /* Check if square is owned*/
    public boolean checkOwned() {
        return owned;
    }

    @Override
    public void setColor(Color myColor) {
        super.setColor(myColor);
    }

    public int FieldSize() {
        return size;
    }

    @Override
    public void setLocation(Point.Float newLoc) {
        location = newLoc;
    }

    @Override
    public Point.Float getLocation() {
        return location;
    }

    public void setOwnAll(boolean own) {
        this.ownAll = own;
    }

    public void setOwned() {
        //if (potentialField == true)
        owned = true;
        //System.out.println("potential");
    }

    public boolean getOwned() {
        return owned;
    }

    @Override
    public void draw(Graphics2D g) {
        
        AffineTransform save = g.getTransform();
        super.draw(g);

        if (potentialField == false) {
            xCor = ((int) location.x);
            yCor = ((int) location.y);
            g.setColor(super.getColor());
            g.fillRect((int) xCor, (int) yCor, width, height);
           
        }

        if (potentialField == true) {

            xCor = ((int) location.x + 3);
            yCor = ((int) location.y + 3);


            g.setColor(super.getColor());
            g.drawRect((int) xCor, (int) yCor, 5, 5);
            
        }

        g.setTransform(save);
    }

    @Override
    public void handleCollision(ICollider otherObject) {
        GameObject tmp = (GameObject) otherObject;
        if ((tmp instanceof MonsterBall)) {
            
        }
    }

    @Override
    public int getSize() {
        int max = Math.max(this.width, this.height);
        max = max / 2 * max / 2 + max / 2 * max / 2;
        max = (int) Math.sqrt(max);

        return max;
    }

    @Override
    public void setSize() {
    }
}
