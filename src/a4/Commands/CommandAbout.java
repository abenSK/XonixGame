/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package a4.Commands;

import a4.GameWorldProxy;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.JOptionPane;

/**
 *
 * @author Aben
 */
public class CommandAbout extends AbstractAction {
    GameWorldProxy gwProxy;
    private static CommandAbout theAboutCommand = null;

    /**
     * Constructor to pass the name of the command to the parent AbstractAction to be used when
     * creating menu items
     */
    public CommandAbout() {
        super("About");
    }


    public static synchronized CommandAbout getInstance() {
        if (theAboutCommand == null) {
            theAboutCommand = new CommandAbout();
        }
        return theAboutCommand;
    }


    @Override
    public synchronized void actionPerformed(ActionEvent e) {
        JOptionPane.showMessageDialog(null, " Xonix Game \n Author: Aben Kebede \n CSC 133\n Version 1.0", "About", JOptionPane.INFORMATION_MESSAGE);

    }
}
