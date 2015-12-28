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
public class CommandFileUndo extends AbstractAction {

    private static CommandFileUndo uFile = null;
    private GameWorldProxy gwp;

    public CommandFileUndo() {
        super("Undo");
    }

    public void setTarget(GameWorldProxy gwp) {
        this.gwp = gwp;
    }

    public synchronized static CommandFileUndo getInstance() {
        if (uFile == null) uFile = new CommandFileUndo();
        return uFile;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println("Undo Action");
    }
}