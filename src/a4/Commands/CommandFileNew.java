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
public class CommandFileNew extends AbstractAction {

    private static CommandFileNew pFile = null;
    private GameWorldProxy gwp;

    public CommandFileNew() {
        super("New");
    }

    public void setTarget(GameWorldProxy gwp) {
        this.gwp = gwp;
    }

    public synchronized static CommandFileNew getInstance() {
        if (pFile == null) {
            pFile = new CommandFileNew();
        }
        return pFile;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println("New File");
    }
}