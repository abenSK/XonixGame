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
public class CommandFileSave extends AbstractAction {

    private static CommandFileSave sFile = null;
    private GameWorldProxy gwp;

    public CommandFileSave() {
        super("Save");
    }

    public void setTarget(GameWorldProxy gwp) {
        this.gwp = gwp;
    }

    public synchronized static CommandFileSave getInstance() {
        if (sFile == null) sFile = new CommandFileSave();
        return sFile;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println("Save File");
    }
}
