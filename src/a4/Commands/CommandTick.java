/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package a4.Commands;

import a4.GameWorldProxy;
import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 *
 * @author Aben
 */
public class CommandTick extends AbstractAction {

    private GameWorldProxy gwProxy;
    
    private static CommandTick _TickCommand = null;

    public CommandTick() {
        super("Tick");
    }
    public static synchronized CommandTick getInstance() {
        if (_TickCommand == null) _TickCommand = new CommandTick();
        return _TickCommand;

    }

    public void setTarget(GameWorldProxy gwp) {
        this.gwProxy = gwp;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        this.gwProxy.tick();
    }
}
