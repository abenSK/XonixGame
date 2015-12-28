/*
 * Button Panel to be used by the Control Panel on the left side of the Screen.
 */
package a4;

import a4.Commands.CommandAddSB;
import a4.Commands.CommandCarHitBall;
import a4.Commands.CommandCarHitBomb;
import a4.Commands.CommandCarHitTicket;
import a4.Commands.CommandChangeStrategy;
import a4.Commands.CommandOwnSquares;
import a4.Commands.CommandPause;
import a4.Commands.CommandQuit;
import a4.Commands.CommandTick;
import a4.Commands.CommandDelete;
import java.awt.Dimension;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.border.TitledBorder;

/**
 *
 * @author Aben
 */
public class ButtonPanel extends JPanel {

    private GameWorldProxy gwp;
    private JButton commandDeleteButton;
    private GameWorldProxy gameWorldProxy = null;
    private JButton pauseButton;
    private Game instance;

    public ButtonPanel(GameWorldProxy gwp, Game game) {
        this.gameWorldProxy = gwp;
        this.instance = game;
        this.setPreferredSize(new Dimension(200, 700));
        this.setLayout(new GridLayout(11, 1));
        this.setBorder(new TitledBorder("Commands"));
        addButtons();
    }

    public void addButtons() {

        /* Get Commands */
        CommandTick tick = CommandTick.getInstance();
        CommandOwnSquares ownSquares = CommandOwnSquares.getInstance();
        CommandCarHitBall ballCollision = CommandCarHitBall.getInstance();
        CommandCarHitTicket ticketHit = CommandCarHitTicket.getInstance();
        CommandQuit quit = CommandQuit.getInstance();
        CommandAddSB bomb = CommandAddSB.getInstance();
        CommandCarHitBomb bombCollision = CommandCarHitBomb.getInstance();
        CommandChangeStrategy changeStrategy = CommandChangeStrategy.getInstance();
        CommandDelete commandDelete = CommandDelete.getInstance();
        CommandPause pauseCommand = CommandPause.getInstance();

        commandDelete.target(this.gameWorldProxy);
        this.commandDeleteButton = new JButton(commandDelete);
        commandDelete.setEnabled(false);

        pauseCommand.target(this.gameWorldProxy, this.instance);
        this.pauseButton = new JButton(pauseCommand);

        /* Create JButtons */
        JButton addSmartBomb = new JButton(bomb);
        JButton carHitBall = new JButton(ballCollision);
        JButton carOwnsSquare = new JButton(ownSquares);
        JButton carHitTicket = new JButton(ticketHit);
        JButton carHitBomb = new JButton(bombCollision);
        JButton switchStrategy = new JButton(changeStrategy);
        JButton tickButton = new JButton(tick);
        JButton Myquit = new JButton(quit);

        /* Remove key-binding for space key for buttons*/
        addSmartBomb.getInputMap().put(KeyStroke.getKeyStroke("SPACE"), "none");
        carHitBall.getInputMap().put(KeyStroke.getKeyStroke("SPACE"), "none");
        carOwnsSquare.getInputMap().put(KeyStroke.getKeyStroke("SPACE"), "none");
        carHitTicket.getInputMap().put(KeyStroke.getKeyStroke("SPACE"), "none");
        carHitBomb.getInputMap().put(KeyStroke.getKeyStroke("SPACE"), "none");
        switchStrategy.getInputMap().put(KeyStroke.getKeyStroke("SPACE"), "none");
        tickButton.getInputMap().put(KeyStroke.getKeyStroke("SPACE"), "none");
        Myquit.getInputMap().put(KeyStroke.getKeyStroke("SPACE"), "none");
        commandDeleteButton.getInputMap().put(KeyStroke.getKeyStroke("SPACE"), "none");
        pauseButton.getInputMap().put(KeyStroke.getKeyStroke("SPACE"), "none");


        /* Add buttons to the panel*/
        //this.add(addSmartBomb);
        //this.add(carHitBomb);
        //this.add(carHitBall);
        //this.add(carHitTicket);
        //this.add(carOwnsSquare);
        //this.add(tickButton);
        //this.add(switchStrategy);  
        add(this.pauseButton);
        add(this.commandDeleteButton);
        this.add(Myquit);

        setVisible(true); //make panel visible

    }

    public void togglePauseButton() {
        this.commandDeleteButton.setEnabled(!this.gameWorldProxy.getTimerStat());
    }

    public void changeTextOnPausePlayButton(String s) {
        this.pauseButton.setText(s);
    }
}
