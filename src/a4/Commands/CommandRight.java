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
public class CommandRight extends AbstractAction {

    private static CommandRight commandRight = null;
    private GameWorldProxy gameWorldProxy;

    public CommandRight() {
        super("Turn North");
    }

    public synchronized static CommandRight getInstance() {
        if (commandRight == null) {
            commandRight = new CommandRight();
        }
        return commandRight;
    }

    public void setTarget(GameWorldProxy gwp) {
        this.gameWorldProxy = gwp;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //System.out.println("Right Command");
        gameWorldProxy.carSteer('e');
    }
}