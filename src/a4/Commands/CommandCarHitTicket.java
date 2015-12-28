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
public class CommandCarHitTicket extends AbstractAction{
    private static CommandCarHitTicket ticketHit = null;
    private GameWorldProxy gameWorldProxy;
    
    public CommandCarHitTicket(){
        super("Car Hit Ticket");
    }
    
    public synchronized static CommandCarHitTicket getInstance(){
        if (ticketHit == null) ticketHit = new CommandCarHitTicket();
        return ticketHit;
    }
    
     public void setTarget(GameWorldProxy gwp) {
        this.gameWorldProxy = gwp;
    }
    
    @Override
    public void actionPerformed(ActionEvent e){
        if (this.gameWorldProxy == null) System.exit(1);
         //System.out.println("Ticket Hit");
        gameWorldProxy.ticketHit();
    }
}
