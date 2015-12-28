/*
 * 
 */
package a4;

import java.awt.Color;
import java.awt.Dimension;
import javax.swing.Box;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

/**
 *
 * @author Aben
 */
public class ScoreView extends JPanel implements IObserver {

    private JLabel level = new JLabel("1");
    private JLabel clock = new JLabel("10");
    private JLabel lives = new JLabel("3");
    private JLabel score = new JLabel ("396");
    private JLabel minScore = new JLabel ("5000");
    private JLabel soundLabel = new JLabel("ON");
    //private GameWorldProxy gwp;

    public ScoreView() {
        //gwp = new GameWorldProxy();   //Instance of the Game World Proxy 
        
        
        this.setPreferredSize(new Dimension(600, 40)); //dimesions for score view
        this.setBorder (new LineBorder(Color.blue,2));
        
        /* Score View Status */
        this.add(new JLabel("Current Level: "));
        this.add(level);
        this.add(Box.createHorizontalStrut(15));
        
        this.add(new JLabel ("Remaining Time: "));
        this.add(clock);
        this.add(Box.createHorizontalStrut(15));
        
        this.add(new JLabel ("Lives Left:"));
        this.add(lives);
        this.add(Box.createHorizontalStrut(15));
        
        this.add(new JLabel("Current Score: "));
        this.add(score);
        this.add(Box.createHorizontalStrut(15));
        
        this.add(new JLabel("Required Score: "));
        this.add(minScore);
        this.add(Box.createHorizontalStrut(15));
        
        this.add(new JLabel("Sound: "));
        this.add(soundLabel);
        
    }

    /* Upon being notified, updates the score view */
    @Override
    public void update(IObservable obs, Object obj) {
        GameWorldProxy gwp = (GameWorldProxy) obs;
        
        Integer myLevel = gwp.getLevel();
        Integer myClock = gwp.getClock();
        Integer myLives = gwp.getLife();
        double myScore = gwp.getCurrentScore();
        double myMinScore = gwp.getMinScore();
        Boolean sound = gwp.getSound();
        
        this.level.setText(myLevel.toString());
        this.clock.setText(myClock.toString());
        this.lives.setText(myLives.toString());
        this.score.setText(Double.toString(myScore));
        this.minScore.setText(Double.toString(myMinScore));
        
        /* Change the value of sound between on/off setSound*/
        this.lives.setText(myLives.toString());
        if (sound == true) {
            soundLabel.setText("ON");
        } else {
            soundLabel.setText("OFF");
        }
    }

    
}
