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
public class CommandSound extends AbstractAction {
    GameWorldProxy gwProxy;
    private static CommandSound soundCommand = null;

    public CommandSound() {
        super("Sound");
    }

    public void setTarget(GameWorldProxy gwProxy) {
        this.gwProxy = gwProxy;
    }


    public synchronized static CommandSound getInstance() {
        if (soundCommand == null) soundCommand = new CommandSound();
        return soundCommand;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        gwProxy.setSound(!gwProxy.getSound());
    }
}

