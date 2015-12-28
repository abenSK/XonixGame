/*
 *  
 */
package a4.gameObjects;

import a4.GameObject;
import a4.GameObjectCollection;
import a4.GameWorldProxy;
import a4.ICollider;
import a4.ISteerable;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.AffineTransform;
import java.util.Iterator;

/**
 *
 * @author Aben
 */
public class Car extends Moveable implements ISteerable {

    private int carWidth, carHeight;
    private Point.Float location;
    //private Point.Float moveLocation;
    private int xCor, yCor, objX, objY;
    private GameWorldProxy gwp;
    private GameObjectCollection go;
    Point.Float currentLocation = new Point.Float();
    private boolean carFind = false;
    private FieldSquare field;
    private Point.Float squareLocation = new Point.Float();
    private boolean carFindPass = true;
    private int size;
   
    private AffineTransform myRotation;

    public Car() {
        carWidth = 14;
        carHeight = 10;
        super.setColor(Color.red); //unchangeable color for car
        location = new Point.Float(297, 3.0f); // car starts in the center of
        //the square immediately to the left of the middle of the bottom of the field 
        super.setLocation(location); // set location of car
        super.setHeading(90); //default heading of 90 degrees
        super.setSpeed(10);
        field = new FieldSquare();
        size = (int) Math.sqrt(this.carWidth / 2 * this.carWidth / 2 + this.carHeight / 2 * this.carHeight / 2);
        myRotation = new AffineTransform();

    }

    /* Square Fields passsed from gameWorld */
    public void getFields(GameObjectCollection getGameWorldObjects) {
        go = getGameWorldObjects;
    }

    @Override
    public void setColor(Color carColor) {
    }

    /* Car set to default location */
    @Override
    public void setLocation(Point.Float newLoc) {
        location = new Point.Float(297, 3.0f); // car starts in the center of
        super.setLocation(location); // set location of car
    }

    /* Heading is unchanged by method call*/
    @Override
    public void setHeading(int heading) {
    }

    /* Speed is unchaged by method call*/
    @Override
    public void setSpeed(int speed) {
    }

    /* specify the direction the car is facing*/
    @Override
    public void steerable(char direction) {

        if (direction == 'n') {
            super.setHeading(0);
        } else if (direction == 's') {
            super.setHeading(180);
        } else if (direction == 'e') {
            super.setHeading(90);
        } else if (direction == 'w') {
            super.setHeading(270);
        } else {
            System.out.println("Invalid direction");
        }
    }

    public int getWidth() {
        return carWidth;
    }

    public int getHeight() {
        return carHeight;
    }

    /* Increase speed of car by one unit */
    public void increaseSpeed() {
        super.setSpeed(super.getSpeed() + 1);
    }

    /* Decrease speed of car by one unit as long as doesn't reduce speed below zero */
    public void decreaseSpeed() {
        if (super.getSpeed() > 0) {
            super.setSpeed(super.getSpeed() - 1);
        } else {
            super.setSpeed(super.getSpeed());
        }
    }

    @Override
    public void handleCollision(ICollider otherObject) {

        GameObject tmp = (GameObject) otherObject;
        if ((tmp instanceof MonsterBall)) {
            
        }

    }

    @Override
    public void move(int heading, int speed, float amount) {
        currentLocation = getLocation();
        
        Iterator itr = go.iterator();

        if (carFind == false) {
            while (itr.hasNext()) {
                GameObject obj = (GameObject) itr.next();

                if (obj instanceof FieldSquare) {
                    if ((obj.collidesWith(this)) && (obj != this)) {
                        carFind = true;
                    }

                }
            }

        }

        if (carFind == false) {
            carFindPass = false;
            
        }
        if (carFind == true) {
            carFindPass = true;
            carFind = false;
        }
            
        super.move(heading, speed, amount);
                
        /*Constrain the ball within the inner grid for x coordinates*/
        if (getLocation().x < 2) {
            steerable('e');
        }
        if (getLocation().x > 597) {
            steerable('w');
        }
                
        /*Constrain the ball within the inner grid for y coordinates*/
        if (getLocation().y > 597) {
            steerable('s');
        }
        if (getLocation().y < 2) {
            steerable('n');
        }
        //myRotation.rotate(getHeading());
    }

    public boolean carFind() {
        return carFindPass;
    }

    @Override
    public void draw(Graphics2D g) {
        
        AffineTransform save = g.getTransform();
        super.draw(g);
        
        if(getHeading()== 0 || getHeading()==180)
            g.rotate(Math.toRadians(getHeading()+90),getLocation().x+carWidth/2,getLocation().y+carHeight/2);
        else
             g.rotate(Math.toRadians(getHeading()+270),getLocation().x+carWidth/2,getLocation().y+carHeight/2);
        
        g.setColor(Color.red);
        // drawing the square
        g.fillRect((int) getLocation().x, (int) getLocation().y, carWidth,
               carHeight);
        g.setColor(Color.black);
        // the "head"
        g.fillRect((int) getLocation().x +carWidth-1, (int) getLocation().y+1 ,
                4, 7);

        g.setTransform(save);

    }

    @Override
    public int getSize() {
        //int max = Math.max(this.carWidth, this.carHeight);
        int max = Math.max(1, 1);
        max = max / 4 * max / 4 + max / 4 * max / 4;
        max = (int) Math.sqrt(max);

        return max;
    }

    @Override
    public void setSize() {
    }
}
