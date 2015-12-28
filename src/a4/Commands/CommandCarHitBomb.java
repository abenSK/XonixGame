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
public class CommandCarHitBomb extends AbstractAction{
    
    private static CommandCarHitBomb bombHit = null;
    private GameWorldProxy gameWorldProxy;
    
    public CommandCarHitBomb(){
        super("Car Hit Bomb");
    }
    
    public synchronized static CommandCarHitBomb getInstance(){
        if (bombHit == null) bombHit = new CommandCarHitBomb();
        return bombHit;
    }
    
     public void setTarget(GameWorldProxy gwp) {
        this.gameWorldProxy = gwp;
    }
    
    @Override
    public void actionPerformed(ActionEvent e){
        if (this.gameWorldProxy == null) System.exit(1);
         //System.out.println("Car Hit Bomb");
        gameWorldProxy.bombCollision();
    }
}

