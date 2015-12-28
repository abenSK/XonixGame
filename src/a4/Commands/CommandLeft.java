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
public class CommandLeft extends AbstractAction {
    private static CommandLeft commandLeft = null;
    private GameWorldProxy gameWorldProxy;

    public CommandLeft() {
        super("Turn Left");
    }

    public synchronized static CommandLeft getInstance() {
        if (commandLeft == null) commandLeft = new CommandLeft();
        return commandLeft;
    }

    public void setTarget(GameWorldProxy gwp) {
        this.gameWorldProxy = gwp;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //System.out.println("Left Command");
        gameWorldProxy.carSteer('w');       

    }
}

