/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package a4.Commands;

import a4.Game;

import a4.GameWorldProxy;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;

/**
 *
 * @author Aben
 */
public class CommandPause extends AbstractAction
{
  private static CommandPause commandPause = null;
  private GameWorldProxy gameWorldProxy;
  private Game game;
  
  public CommandPause()
  {
    super("Pause");
  }
  
  public static synchronized CommandPause getInstance()
  {
    if (commandPause == null) {
      commandPause = new CommandPause();
    }
    return commandPause;
  }
  
  public void target(GameWorldProxy gwp, Game g)
  {
    this.gameWorldProxy = gwp;
    this.game = g;
  }
  
  @Override
  public void actionPerformed(ActionEvent e)
  {
    this.gameWorldProxy.toggleTimer();
    this.game.togglePauseButton();
    if (this.gameWorldProxy.getTimerStat()) {
      this.game.changeTextOnPauseButton("Pause");
    } else {
      this.game.changeTextOnPauseButton("Play");
    }
  }
}

