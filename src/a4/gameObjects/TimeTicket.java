/*
 * 
 */
package a4.gameObjects;

import a4.GameObject;
import a4.ICollider;
import a4.IDrawable;
import a4.ISelectable;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.AffineTransform;
import java.util.Random;

/**
 *
 * @author Aben
 */
public class TimeTicket extends Fixed implements IDrawable, ISelectable {

    private int ticketWidth;
    private int ticketHeight;
    private int ticketTime;
    private Random generator;
    private float x, y; //x & y for co-ordinates
    private Point.Float ticketLocation; // for passing ball location
    private float xCor = 0, yCor = 0;
    private boolean isSelected;

    public TimeTicket() {

        ticketWidth = ticketHeight = 10;
        ticketTime = 5;

        generator = new Random();  // used for generating random objects
        /* random location for x between x = 5 and x = 455*/
        x = (generator.nextFloat() * 450) + 5;
        /* random y location for y between y = 5 and y = 455*/
        y = (generator.nextFloat() * 450) + 5;
        ticketLocation = new Point.Float(x, y); // location with x and y co-ordinates

        super.setColor(Color.blue); //set color of time ticket to blue 
        super.setLocation(ticketLocation); // set locataion of timeTicket
    }

    public int getWidth() {
        return ticketWidth;
    }

    public int getHeight() {
        return ticketHeight;
    }

    public void addTicketTime(int addTime) {
        ticketTime = addTime;
    }

    public int getTicketTime() {
        return ticketTime;
    }

    /* Ticket color set to random color, not defined. Calling this method changes nothing*/
    @Override
    public void setColor(Color carColor) {
    }

    /* Ticket set to random location, unchanged by this method*/
    @Override
    public void setLocation(Point.Float newLoc) {
    }

    @Override
    public void draw(Graphics2D g) {

        AffineTransform save = g.getTransform();
        super.draw(g);

        xCor = ((int) getLocation().x - 6 / 2);
        yCor = ((int) getLocation().y - 6 / 2);

        if (isSelected()) {
            g.setColor(Color.green);
            g.fillRect((int) xCor, (int) yCor, ticketWidth, ticketHeight);
            g.setColor(Color.gray);
            g.drawRect((int) xCor, (int) yCor, ticketWidth, ticketHeight);
        } else {
            g.setColor(Color.green);
            g.fillRect((int) xCor, (int) yCor, ticketWidth, ticketHeight);
        }
         g.setTransform(save);
    }

    @Override
    public void handleCollision(ICollider otherObject) {
        GameObject tmp = (GameObject) otherObject;
        if ((tmp instanceof MonsterBall)) {
            //System.out.println("car hit monsterball");
        }
    }

    @Override
    public int getSize() {
        int max = Math.max(this.ticketWidth, this.ticketHeight);
        max = max / 2 * max / 2 + max / 2 * max / 2;
        max = (int) Math.sqrt(max);

        return max;
    }

    @Override
    public void setSize() {
    }

    @Override
    public void setSelected(boolean s) {
        this.isSelected = s;
    }

    @Override
    public boolean isSelected() {
        return this.isSelected;
    }

    @Override
    public boolean contains(double x, double y) {
        if ((x > getLocation().x - this.ticketWidth) && (y > getLocation().y - this.ticketHeight)
                && (y < getLocation().y + this.ticketHeight) && (x < getLocation().x + this.ticketWidth + 5)) {
           
            return true;
        }
        return false;

    }
}
