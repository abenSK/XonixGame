/*
 * Using this strategy, the Smart Bomb will follow the car. It acquires the
 * same heading as the car. 
 */
package a4;

import a4.gameObjects.Car;
import a4.gameObjects.SmartBomb;

/**
 *
 * @author Aben
 */
public class FollowStrategy implements IStrategy {

    private SmartBomb bomb;
    private Car car;
    private int carPass;

    /* Set strategy for bomb to follow car */
    public FollowStrategy(Car car, SmartBomb bomb) {
        this.car = car;
        this.bomb = bomb;
        if (car.getHeading() == 0 || car.getHeading() == 180) {
            carPass = car.getHeading() + 180;
        } else {
            carPass = car.getHeading();
        }

    }

    @Override
    public void apply() {
        bomb.setHeading(carPass);

    }
}
