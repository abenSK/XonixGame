/*
 * 
 */
package a4.gameObjects;

import a4.GameObject;
import a4.ICollider;
import a4.IDrawable;
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
public class MonsterBall extends Moveable implements IDrawable {

    private int radius;
    private Random generator;
    private float x, y; //x & y for co-ordinates 
    private int r, g, b; //r,g,b for Color
    private Point.Float ballLocation; // for passing ball location
    private Point.Float moveLocation; // for adjusting ball location
    private int ballHeading, ballSpeed;
    private int width = 10;
    private int height = 10;
    private int size = (int) Math.sqrt(width / 2 * width / 2 + height / 2 * height / 2);

    public MonsterBall() {
        radius = 5;
        x = y = 0;
        r = g = b = ballHeading = ballSpeed = 0;

        generator = new Random();  // used for generating random objects

        /* random location for x between x = 5 and x = 455*/
        x = (generator.nextFloat() * 450) + 5;

        /* random y location for y between y = 5 and y = 455*/
        y = (generator.nextFloat() * 450) + 5;

        /* random red component of color between 0 and 255*/
        r = generator.nextInt(256);

        /* random green component of color between 0 and 255*/
        g = generator.nextInt(256);

        /* random green component of color between 0 and 255*/
        b = generator.nextInt(256);

        /*random heading for monster ball between 0 and 360 degrees*/
        ballHeading = generator.nextInt(180);

        /*random speed for monster ball between 0 and 10 units*/
        ballSpeed = generator.nextInt(10);

        ballLocation = new Point.Float(x, y); // location with x and y co-ordinates
        super.setLocation(ballLocation); // set locataion of monsterball

        super.setColor(new Color(r, g, b)); //monster ball assigned color
        super.setHeading(ballHeading); //heading for monsterball
        super.setSpeed(ballSpeed); //speed for monsterball

    }

    @Override
    public int getHeading() {
        return ballHeading;
    }

    /* Heading is unchanged by method call*/
    @Override
    public void setHeading(int heading) {
        ballHeading = heading;
    }

    @Override
    public int getSpeed() {
        return ballSpeed;
    }

    /* Ball color set to random color, not defined. */
    @Override
    public void setColor(Color carColor) {
        /* random red component of color between 0 and 255*/
        r = generator.nextInt(256);

        /* random green component of color between 0 and 255*/
        g = generator.nextInt(256);

        /* random green component of color between 0 and 255*/
        b = generator.nextInt(256);

        super.setColor(new Color(r, g, b)); //monster ball assigned color
    }

    /* Ball set to random location, unchanged by this method*/
    @Override
    public void setLocation(Point.Float newLoc) {
    }

    /* Speed is unchaged by method call*/
    @Override
    public void setSpeed(int speed) {
    }

    @Override
    public void move(int heading, int speed, float amount) {
        super.move(heading, speed, amount);
    }

    public int getRadius() {
        return radius;
    }

    @Override
    public int getSize() {
        return this.size;
    }

    @Override
    public void setSize() {
    }

    @Override
    public void handleCollision(ICollider otherObject) {
        GameObject tmp = (GameObject) otherObject;
        if ((tmp instanceof Car)) {
            //System.out.println("monsterball hit car");
        }
        if ((tmp) instanceof FieldSquare) {
            FieldSquare field = (FieldSquare) tmp;
            if (field.getColor() != Color.gray) {
                if (getLocation().x < 6) {
                    getLocation().x += 10;
                    setHeading(getHeading() + 90);
                }
                if (getLocation().x > 585) {
                    getLocation().x -= 10;
                    setHeading(getHeading() + 90);
                }

                /*Constrain the ball within the inner grid for y coordinates*/
                if (getLocation().y > 585) {
                    getLocation().y -= 10;
                    setHeading(getHeading() + 90);
                }
                if (getLocation().y < 6) {
                    getLocation().y += 10;
                    setHeading(getHeading() + 90);
                } else {
                    setHeading(getHeading() + 90);
                    //getLocation().x += 5;
                    //getLocation().y += 5;
                }
            }
        }

    }

    @Override
    public void draw(Graphics2D g) {
        AffineTransform save = g.getTransform();
        super.draw(g);
        g.setColor(this.getColor());
        g.fillOval((int) getLocation().x, (int) getLocation().y, 10, 10);
        g.setColor(Color.black);
        g.drawOval((int) getLocation().x, (int) getLocation().y, 10, 10);
        g.setTransform(save);
    }
}
