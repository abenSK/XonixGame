/*
 * Default Strategy that the Smart Bomb is going to use. Smart Bomb will move
 * with a random heading. 
 */
package a4;

import a4.gameObjects.SmartBomb;
import java.util.Random;

/**
 *
 * @author Aben
 */
public class DefaultStrategy implements IStrategy {

    private SmartBomb bomb;
    private Random generator;
    private int bombHeading;

    public DefaultStrategy(SmartBomb bomb) {
        this.bomb = bomb;
        generator = new Random();
        /*random heading for Smart bomb between 0 and 360 degrees*/
        bombHeading = generator.nextInt(180);
    }

    @Override
    public void apply() {
        if (bomb.getHeading() == 0||bomb.getHeading() == 90 || bomb.getHeading() == 180 ||bomb.getHeading() == 270 ) {
            bomb.setHeading(bombHeading); //random heading
        } else {
            bomb.setHeading(bomb.getHeading()); //random heading
        }
    }
}
