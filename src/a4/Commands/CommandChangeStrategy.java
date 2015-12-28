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
public class CommandChangeStrategy extends AbstractAction {

    private static CommandChangeStrategy changeStrategy = null;
    private GameWorldProxy gwp;

    public CommandChangeStrategy() {
        super("Change Strategy");
    }

    public synchronized static CommandChangeStrategy getInstance() {
        if (changeStrategy == null) changeStrategy = new CommandChangeStrategy();
        return changeStrategy;
    }

    public void setTarget(GameWorldProxy gwp) {
        this.gwp = gwp;
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        gwp.toggleStrategy();
        System.out.println("Strategy Changed");

    }
}