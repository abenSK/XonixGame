/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package a4.Commands;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.JOptionPane;

/**
 *
 * @author Aben
 */
public class CommandQuit extends AbstractAction {
    private static CommandQuit quitAction = null;

    public CommandQuit() {
        super("Quit");
    }

    public synchronized static CommandQuit getInstance() {
        if (quitAction == null) quitAction = new CommandQuit();
        return quitAction;
    }


    @Override
    public void actionPerformed(ActionEvent e) {
      
        int answer = JOptionPane.showConfirmDialog(null, "Are you sure?", "Confirm Exit", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

        if (answer == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
        return;
    }
}
