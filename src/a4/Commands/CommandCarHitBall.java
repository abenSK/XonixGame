/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package a4.Commands;

import a4.GameWorldProxy;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;

/**
 *
 * @author Aben
 */
public class CommandCarHitBall extends AbstractAction{
    private static CommandCarHitBall ballHit = null;
    private GameWorldProxy gameWorldProxy;
    
    public CommandCarHitBall(){
        super("Car Hit Ball");
    }
    
    public synchronized static CommandCarHitBall getInstance(){
        if (ballHit == null) ballHit = new CommandCarHitBall();
        return ballHit;
    }
    
     public void setTarget(GameWorldProxy gwp) {
        this.gameWorldProxy = gwp;
    }
    
    @Override
    public void actionPerformed(ActionEvent e){
        if (this.gameWorldProxy == null) System.exit(1);
        //System.out.println("Car Hit Ball");
        gameWorldProxy.ballCollision();
    }
}
