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
public class CommandIncreaseSpeed extends AbstractAction {

    private static CommandIncreaseSpeed commandIncrease = null;
    private GameWorldProxy gameWorldProxy;

    public CommandIncreaseSpeed() {
        super("Increase Speed");
    }

    public synchronized static CommandIncreaseSpeed getInstance() {
        if (commandIncrease == null) {
            commandIncrease = new CommandIncreaseSpeed();
        }
        return commandIncrease;
    }

    public void setTarget(GameWorldProxy gwp) {
        this.gameWorldProxy = gwp;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println("Speed Increased");
        gameWorldProxy.carSpeedInc();
    }
}