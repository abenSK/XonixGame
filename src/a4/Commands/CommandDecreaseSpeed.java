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
public class CommandDecreaseSpeed extends AbstractAction {

    private static CommandDecreaseSpeed commandDecrease = null;
    private GameWorldProxy gameWorldProxy;

    public CommandDecreaseSpeed() {
        super("Decrease Speed");
    }

    public synchronized static CommandDecreaseSpeed getInstance() {
        if (commandDecrease == null) {
            commandDecrease = new CommandDecreaseSpeed();
        }
        return commandDecrease;
    }

    public void setTarget(GameWorldProxy gwp) {
        this.gameWorldProxy = gwp;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println("Speed Decreased");
        gameWorldProxy.carSpeedDec();
    }
}