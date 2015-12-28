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
public class CommandUp extends AbstractAction {
    private static CommandUp commandUp = null;
    private GameWorldProxy gameWorldProxy;

    public CommandUp() {
        super("Turn North");
    }

    public synchronized static CommandUp getInstance() {
        if (commandUp == null) commandUp = new CommandUp();
        return commandUp;
    }

    public void setTarget(GameWorldProxy gwp) {
        this.gameWorldProxy = gwp;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //System.out.println("Up Command");
        gameWorldProxy.carSteer('n');
    }
}

