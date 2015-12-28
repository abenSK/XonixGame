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
public class CommandOwnSquare extends AbstractAction{
    
    private static CommandOwnSquare ownSquare = null;
    private GameWorldProxy gameWorldProxy;
    
    public CommandOwnSquare(){
        super("Car Owns a Square");
    }
    
    public synchronized static CommandOwnSquare getInstance(){
        if (ownSquare == null) ownSquare = new CommandOwnSquare();
        return ownSquare;
    }
    
     public void setTarget(GameWorldProxy gwp) {
        this.gameWorldProxy = gwp;
    }
    
    @Override
    public void actionPerformed(ActionEvent e){
        if (this.gameWorldProxy == null) System.exit(1);
        gameWorldProxy.ownSquare();
    }
    
}