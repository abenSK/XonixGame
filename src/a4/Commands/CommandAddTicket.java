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
public class CommandAddTicket extends AbstractAction{
    private static CommandAddTicket createTicket = null;
    private GameWorldProxy gameWorldProxy;
    
    public CommandAddTicket(){
        super("Add Time Ticket");
    }
    
    public synchronized static CommandAddTicket getInstance(){
        if (createTicket == null) createTicket = new CommandAddTicket();
        return createTicket;
    }
    
     public void setTarget(GameWorldProxy gwp) {
        this.gameWorldProxy = gwp;
    }
    
    @Override
    public void actionPerformed(ActionEvent e){
        if (this.gameWorldProxy == null) System.exit(1);
        gameWorldProxy.addTicket();
    }
}