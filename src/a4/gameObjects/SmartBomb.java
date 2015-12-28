/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package a4.gameObjects;

import a4.ISelectable;
import a4.GameObject;
import a4.ICollider;
import a4.IDrawable;
import a4.IStrategy;
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
public class SmartBomb extends Moveable implements IDrawable, ISelectable {

    private int radius;
    private Random generator;
    private float x, y; //x & y for co-ordinates 
    private int r, g, b; //r,g,b for Color
    private Point.Float bombLocation; // for passing bomb location
    private Point.Float moveLocation; // for adjusting bomb location
    private int bombHeading, bombSpeed;
    private IStrategy curStrategy;
    private int width = 40;
    private int height = 40;
    private boolean isSelected;
    
    private AffineTransform myTranslation, myRotation, myScale;
    //int myRadius = 0;
    private Point top, bottomLeft, bottomRight;
    private int degree = 0;
    private int timer = 0;
    private int adjust = 0;

    public SmartBomb() {

        radius = 5;
        x = y = 0;
        r = g = b = bombHeading = bombSpeed = 0;

        generator = new Random();  // used for generating random objects

        /* random location for x between x = 5 and x = 455*/
        x = (generator.nextFloat() * 450) + 5;

        /* random y location for y between y = 5 and y = 455*/
        y = (generator.nextFloat() * 450) + 5;

        /* random red component of color between 0 and 255*/
        r = generator.nextInt(256);

        /* random red component of color between 0 and 255*/
        g = generator.nextInt(256);

        /* random red component of color between 0 and 255*/
        b = generator.nextInt(256);

        /*random heading for monster bomb between 0 and 360 degrees*/
        bombHeading = generator.nextInt(180);

        /*random speed for monster bomb between 0 and 10 units*/
        bombSpeed = generator.nextInt(10);

        bombLocation = new Point.Float(x, y); // location with x and y co-ordinates
        super.setLocation(bombLocation); // set location of SmartBomb

        super.setColor(new Color(r, g, b)); //SmartBomb assigned color
        super.setHeading(bombHeading); //heading for SmartBomb
        super.setSpeed(bombSpeed); //speed for SmartBomb

        myTranslation = new AffineTransform();
        myRotation = new AffineTransform();
        myScale = new AffineTransform();
        
        

    }

    @Override
    public int getHeading() {
        return bombHeading;
    }

    /* Heading is changed according to strategy */
    @Override
    public void setHeading(int heading) {
        bombHeading = heading;
    }

    @Override
    public int getSpeed() {
        return bombSpeed;
    }

    /* Bomb color set to random color, not defined. */
    @Override
    public void setColor(Color carColor) {
        /* random red component of color between 0 and 255*/
        r = generator.nextInt(256);

        /* random red component of color between 0 and 255*/
        g = generator.nextInt(256);

        /* random red component of color between 0 and 255*/
        b = generator.nextInt(256);

        super.setColor(new Color(r, g, b)); //SmartBomb assigned color      
    }

    /* Bomb set to random location, unchanged by this method*/
    @Override
    public void setLocation(Point.Float newLoc) {
    }

    /* Speed is unchaged by method call*/
    @Override
    public void setSpeed(int speed) {
    }

    /* Set Strategy*/
    public void setStrategy(IStrategy s) {
        curStrategy = s;
    }
    /* Apply the selected strategy */

    public void invokeStrategy() {
        curStrategy.apply();
    }

    @Override
    public void move(int heading, int speed, float amount) {
        //System.out.println("H "+heading+" SH "+super.getHeading());
        super.move(heading, speed, amount);

    }

    @Override
    public void draw(Graphics2D g) {

        // save the current graphics transform for later restoration
        AffineTransform saveAT = g.getTransform();
        // append this shape's transforms to the graphics object's transform
        g.transform(myTranslation);
        g.transform(myRotation);
        g.transform(myScale);

        g.rotate(2 * Math.toRadians(degree) / 3, getLocation().x, getLocation().y);
        drawBody(g);
        drawFlame(g);

        if (timer > 1) {
            degree += 25;
            timer = 0;
        }

        if (degree >= 360) {
            degree = 0;
        }
        // restore the old graphics transform (remove this shape's transform)
        g.setTransform(saveAT);
        timer++;
    }

    @Override
    public void handleCollision(ICollider otherObject) {
        GameObject tmp = (GameObject) otherObject;
        if ((tmp instanceof FieldSquare)) {/*Constrain the ball within the inner grid for x coordinates*/
            if (getLocation().x < 5) {
                getLocation().x += 15;
                setHeading(getHeading() + 90);
            }
            if (getLocation().x > 580) {
                getLocation().x -= 15;
                setHeading(getHeading() + 90);
            }

            /*Constrain the ball within the inner grid for y coordinates*/
            if (getLocation().y > 580) {
                getLocation().y -= 15;
                setHeading(getHeading() + 90);
            }
            if (getLocation().y < 5) {
                getLocation().y += 15;
                setHeading(getHeading() + 90);
            } else {
                setHeading(getHeading() + 90);
            }
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

    @Override
    public void setSelected(boolean s) {
        this.isSelected = s;
        //System.out.println("changed");
    }

    @Override
    public boolean isSelected() {
        return this.isSelected;
    }

    @Override
    public boolean contains(double x, double y) {
        if ((x > getLocation().x - this.width) && (y > getLocation().y - this.height)
                && (y < getLocation().y + this.height) && (x < getLocation().x + this.width + 5)) {
            
            return true;
        }
        return false;

    }

    public void drawBody(Graphics2D g2d) {//body of the flame
       
        AffineTransform saveAT = g2d.getTransform();
        g2d.transform(myTranslation);//This time translation is applied LAST

        g2d.transform(myRotation);
        g2d.transform(myScale);
        Point boxCorner = new Point((int) getLocation().x, (int) getLocation().y);

        if (isSelected()) {
            g2d.setColor(Color.black);
           

            g2d.fillOval(boxCorner.x, boxCorner.y, 15, 15);
            g2d.setColor(Color.black);
            g2d.drawOval(boxCorner.x, boxCorner.y, 15, 15);
        } else {
            g2d.setColor(Color.yellow);
            g2d.fillOval(boxCorner.x, boxCorner.y, 15, 15);
        }
        g2d.setTransform(saveAT);
    }

    public void drawFlame(Graphics2D g2d) {
        // save the current graphics transform for later restoration
        AffineTransform saveAT = g2d.getTransform();

        top = new Point((int) getLocation().x, (int) getLocation().y + 10);
        bottomLeft = new Point((int) getLocation().x - 5, (int) getLocation().y - 10);
        bottomRight = new Point((int) getLocation().x + 5, (int) getLocation().y - 10);

        if (adjust > 10) {
            adjust = 0;
        }

        /*Top Flame*/
        g2d.translate(8, 30 + adjust);

        
        if (isSelected) {
            g2d.setColor(Color.black);
        } else {
            g2d.setColor(Color.red);
        }
        g2d.drawLine(top.x, top.y, bottomLeft.x, bottomLeft.y);
        g2d.drawLine(bottomLeft.x, bottomLeft.y, bottomRight.x, bottomRight.y);
        g2d.drawLine(bottomRight.x, bottomRight.y, top.x, top.y);
        // restore the old graphics transform (remove this shape's transform)
        g2d.setTransform(saveAT);

        /*Left Flame*/
        g2d.translate(-15 - adjust, 4);
        g2d.rotate(Math.toRadians(90), getLocation().x, getLocation().y);
         if (isSelected) {
            g2d.setColor(Color.black);
        } else {
            g2d.setColor(Color.red);
        }
        g2d.drawLine(top.x, top.y, bottomLeft.x, bottomLeft.y);
        g2d.drawLine(bottomLeft.x, bottomLeft.y, bottomRight.x, bottomRight.y);
        g2d.drawLine(bottomRight.x, bottomRight.y, top.x, top.y);
        // restore the old graphics transform (remove this shape's transform)
        g2d.setTransform(saveAT);

        /*Bottom Flame*/
        g2d.translate(7, -18 - adjust);
        g2d.rotate(Math.toRadians(180), getLocation().x, getLocation().y);
        if (isSelected) {
            g2d.setColor(Color.black);
        } else {
            g2d.setColor(Color.red);
        }
        g2d.drawLine(top.x, top.y, bottomLeft.x, bottomLeft.y);
        g2d.drawLine(bottomLeft.x, bottomLeft.y, bottomRight.x, bottomRight.y);
        g2d.drawLine(bottomRight.x, bottomRight.y, top.x, top.y);
        // restore the old graphics transform (remove this shape's transform)
        g2d.setTransform(saveAT);

        /*Right Flame*/
        g2d.translate(28 + adjust, 5);
        g2d.rotate(Math.toRadians(270), getLocation().x, getLocation().y);
         if (isSelected) {
            g2d.setColor(Color.black);
        } else {
            g2d.setColor(Color.red);
        }
        g2d.drawLine(top.x, top.y, bottomLeft.x, bottomLeft.y);
        g2d.drawLine(bottomLeft.x, bottomLeft.y, bottomRight.x, bottomRight.y);
        g2d.drawLine(bottomRight.x, bottomRight.y, top.x, top.y);
        // restore the old graphics transform (remove this shape's transform)
        g2d.setTransform(saveAT);

        adjust += 5;
    }
}
