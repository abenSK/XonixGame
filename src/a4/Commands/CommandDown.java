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
public class CommandDown extends AbstractAction {
    private static CommandDown commandDown = null;
    private GameWorldProxy gameWorldProxy;

    public CommandDown() {
        super("Turn North");
    }

    public synchronized static CommandDown getInstance() {
        if (commandDown == null) commandDown = new CommandDown();
        return commandDown;
    }

    public void setTarget(GameWorldProxy gwp) {
        this.gameWorldProxy = gwp;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //System.out.println("Down Command");
        gameWorldProxy.carSteer('s');    
    }
}