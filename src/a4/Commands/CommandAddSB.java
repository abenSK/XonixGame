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
public class CommandAddSB extends AbstractAction{
    private static CommandAddSB bomb = null;
    private GameWorldProxy gameWorldProxy;
    
    public CommandAddSB(){
        super("Add Smart Bomb");
    }
    
    public synchronized static CommandAddSB getInstance(){
        if (bomb == null) bomb = new CommandAddSB();
        return bomb;
    }
    
     public void setTarget(GameWorldProxy gwp) {
        this.gameWorldProxy = gwp;
    }
    
    @Override
    public void actionPerformed(ActionEvent e){
        if (this.gameWorldProxy == null) System.exit(1);
        gameWorldProxy.addSmartBomb();
    }
}
