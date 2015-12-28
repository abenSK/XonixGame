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
public class CommandOwnSquares extends AbstractAction{
    
    private static CommandOwnSquares ownSquares = null;
    private GameWorldProxy gameWorldProxy;
    
    public CommandOwnSquares(){
        super("Car Owns Squares");
    }
    
    public synchronized static CommandOwnSquares getInstance(){
        if (ownSquares == null) ownSquares = new CommandOwnSquares();
        return ownSquares;
    }
    
     public void setTarget(GameWorldProxy gwp) {
        this.gameWorldProxy = gwp;
    }
    
    @Override
    public void actionPerformed(ActionEvent e){
        if (this.gameWorldProxy == null) System.exit(1);
        gameWorldProxy.ownSquares();
    }
    
}
