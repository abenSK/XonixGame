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
public class CommandAddBall extends AbstractAction{
    private static CommandAddBall createBall = null;
    private GameWorldProxy gameWorldProxy;
    
    public CommandAddBall(){
        super("Add Monster Ball");
    }
    
    public synchronized static CommandAddBall getInstance(){
        if (createBall == null) createBall = new CommandAddBall();
        return createBall;
    }
    
     public void setTarget(GameWorldProxy gwp) {
        this.gameWorldProxy = gwp;
    }
    
    @Override
    public void actionPerformed(ActionEvent e){
        if (this.gameWorldProxy == null) System.exit(1);
        gameWorldProxy.addBall();
    }
}