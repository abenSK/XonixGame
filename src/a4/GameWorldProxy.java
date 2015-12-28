/*
 * GameWorldProxy gives access to GameWorld methods yet restricts unwanted access
 */
package a4;

import java.awt.Point;

/**
 *
 * @author Aben
 */
public class GameWorldProxy implements IObservable {

    GameWorld gw; // only game has knowldge of the real game world object

    public GameWorldProxy(GameWorld gw) {
        this.gw = gw;
    }

    public int getLevel() {
        return gw.getLevel();
    }

    public int getLife() {
        return gw.getLife();
    }

    public int getClock() {
        return gw.getClock();
    }

    public double getCurrentScore() {
        return gw.getCurrentScore();
    }

    public double getMinScore() {
        return gw.getMinScore();
    }

    public void tick() {
        gw.tick();
    }

    public void ownSquares() {
        gw.groupSquareOwn();
    }

    public void ballCollision() {
        gw.ballCollision();
    }

    public void bombCollision() {
        gw.bombCollision(gw.getLevel());
    }

    public void ticketHit() {
        gw.addTime(gw.getLevel());
    }

    public boolean getSound() {
        return gw.getSound();
    }

    public void setSound(boolean sound) {
        gw.setSound(!gw.getSound()); //switch between on/off states
    }

    public void carSteer(char direction) {
        gw.carSteer(direction);
    }

    public void carSpeedInc() {
        gw.carSpeedInc();
    }

    public void carSpeedDec() {
        gw.carSpeedDec();
    }

    public void addSmartBomb() {
        gw.addSmartBomb();
    }

    public void toggleStrategy() {
        gw.toggleStrategy();
    }

    public void addBall() {
        gw.addBall();
    }

    public void addTicket() {
        gw.addTicket();
    }

    public void ownSquare() {
        gw.squareOwn();
    }

    public void map() {
        gw.map();
    }
    
    public boolean getTimerStat() {
        return gw.getTimerStat();
    }

    public GameObjectCollection getGameWorldObjects() {
        return this.gw.getGameWorldObjects();
    }
    
    
    public void deleteSelected(){
        gw.deleteSelected();
    }
    
    public void toggleTimer(){
        gw.toggleTimer();
    }
    
    public void addSweeper(Point.Float newLoc){
        gw.addSweeper(newLoc);
    }

    @Override
    public void addObserver(IObserver observer) {
    }

    @Override
    public void notifyObservers() {
    }
}
