/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package a4.Commands;

import a4.GameWorld;
import a4.GameWorldProxy;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;

/**
 *
 * @author Aben
 */
public class CommandDelete extends AbstractAction {

    private static CommandDelete commandRe = null;
    private GameWorldProxy gameWorldProxy;

    public CommandDelete() {
        super("Delete");
    }

    public static synchronized CommandDelete getInstance() {
        if (commandRe == null) {
            commandRe = new CommandDelete();
        }
        return commandRe;
    }

    public void target(GameWorldProxy gwp) {
        this.gameWorldProxy = gwp;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        this.gameWorldProxy.deleteSelected();
    }
}
