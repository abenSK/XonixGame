/*
 * Responsible for starting the game, creating the Observable and registering
 * the observers. 
 */
package a4;

import a4.Commands.CommandAbout;
import a4.Commands.CommandAddSB;
import a4.Commands.CommandCarHitBall;
import a4.Commands.CommandCarHitBomb;
import a4.Commands.CommandCarHitTicket;
import a4.Commands.CommandChangeStrategy;
import a4.Commands.CommandAddBall;
import a4.Commands.CommandAddTicket;
import a4.Commands.CommandDecreaseSpeed;
import a4.Commands.CommandDelete;
import a4.Commands.CommandDown;
import a4.Commands.CommandFileNew;
import a4.Commands.CommandFileSave;
import a4.Commands.CommandFileUndo;
import a4.Commands.CommandIncreaseSpeed;
import a4.Commands.CommandLeft;
import a4.Commands.CommandOwnSquare;
import a4.Commands.CommandOwnSquares;
import a4.Commands.CommandQuit;
import a4.Commands.CommandRight;
import a4.Commands.CommandSound;
import a4.Commands.CommandTick;
import a4.Commands.CommandUp;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.net.MalformedURLException;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;
import javax.swing.Timer;
import javax.swing.WindowConstants;

/**
 *
 * @author Aben
 */
public class Game extends JFrame implements MouseListener, MouseMotionListener, MouseWheelListener {

    private GameWorld gw;
    private GameWorldProxy gwp;
    private MapView mv; // for displaying map of current map
    private ScoreView sv; // for displaying current game status
    private ButtonPanel bp;
    int level;
    private Timer timer;
    private Point pressedHere;
    private Point pp;
    private Point mousePrevPoint = new Point(0, 0);

    public Game() throws MalformedURLException {
        gw = new GameWorld(); // create “Observable”
        gwp = new GameWorldProxy(gw); //pass instance of gameworld to gameworldproxy
        mv = new MapView(this.gwp); // create an “Observer” for the map
        sv = new ScoreView(); // create an “Observer” for the game state data
        gw.addObserver(mv); // register the map Observer
        gw.addObserver(sv); // register the score observer
        bp = new ButtonPanel(this.gwp, this); //button panel for the buttons
        this.mv.addMouseListener(this);
        this.mv.addMouseMotionListener(this);
        this.mv.addMouseWheelListener(this);

        CommandTick tick = CommandTick.getInstance();
        tick.setTarget(gwp);
        Game.this.timer = new Timer(20, tick);
        Game.this.timer.start();
        Game.this.gw.setTimer(Game.this.timer);

        play();
    }

    private void play() {
        this.getCommand();
        this.requestFocus();
    }

    public void togglePauseButton() {
        this.bp.togglePauseButton();
        this.mv.requestFocus();
    }

    public void changeTextOnPauseButton(String s) {
        this.bp.changeTextOnPausePlayButton(s);
        this.mv.requestFocus();
    }

    void getCommand() {

        /* Create the Layout */
        this.setLayout(new BorderLayout());
        this.setTitle("Xonix Game");
        mv.setBackground(Color.white);
        this.setSize(850, 720);
        this.setLocation(1, 1);
        this.add(sv, BorderLayout.NORTH); //Score view on top
        this.add(bp, BorderLayout.WEST);  //Buttons on the left side
        this.add(mv, BorderLayout.CENTER); //Map view in the center


        /* Get Commands to be used */
        //CommandTick tick = CommandTick.getInstance();
        CommandOwnSquares ownSquares = CommandOwnSquares.getInstance();
        CommandCarHitBall ballCollision = CommandCarHitBall.getInstance();
        CommandCarHitTicket ticketHit = CommandCarHitTicket.getInstance();
        CommandSound sound = CommandSound.getInstance();
        CommandLeft turnLeft = CommandLeft.getInstance();
        CommandRight turnRight = CommandRight.getInstance();
        CommandUp goUp = CommandUp.getInstance();
        CommandDown goDown = CommandDown.getInstance();
        CommandIncreaseSpeed speedInc = CommandIncreaseSpeed.getInstance();
        CommandDecreaseSpeed speedDec = CommandDecreaseSpeed.getInstance();
        CommandAbout about = CommandAbout.getInstance();
        CommandAddSB bomb = CommandAddSB.getInstance();
        CommandCarHitBomb bombCollision = CommandCarHitBomb.getInstance();
        CommandChangeStrategy changeStrategy = CommandChangeStrategy.getInstance();
        CommandAddBall ballAdd = CommandAddBall.getInstance();
        CommandAddTicket ticketAdd = CommandAddTicket.getInstance();
        CommandOwnSquare ownSquare = CommandOwnSquare.getInstance();
        CommandFileNew newFile = CommandFileNew.getInstance();
        CommandFileSave saveFile = CommandFileSave.getInstance();
        CommandFileUndo undo = CommandFileUndo.getInstance();
        CommandDelete delete = CommandDelete.getInstance();


        /* Set Targets for Commands */
        //tick.setTarget(gwp);
        ownSquares.setTarget(gwp);
        ballCollision.setTarget(gwp);
        ticketHit.setTarget(gwp);
        sound.setTarget(gwp);
        turnLeft.setTarget(gwp);
        turnRight.setTarget(gwp);
        goUp.setTarget(gwp);
        goDown.setTarget(gwp);
        speedInc.setTarget(gwp);
        speedDec.setTarget(gwp);
        bomb.setTarget(gwp);
        bombCollision.setTarget(gwp);
        changeStrategy.setTarget(gwp);
        ballAdd.setTarget(gwp);
        ticketAdd.setTarget(gwp);
        ownSquare.setTarget(gwp);
        newFile.setTarget(gwp);
        saveFile.setTarget(gwp);
        undo.setTarget(gwp);
        delete.target(gwp);

        /* Menu Items */
        JMenuBar menuBar = new JMenuBar();
        JMenu file = new JMenu("File");
        JMenu commands = new JMenu("Commands");

        JCheckBoxMenuItem soundMenu = new JCheckBoxMenuItem("Sound", true);
        soundMenu.setAction(sound);

        /* Add to menu Items */
        file.add(newFile);
        file.add(saveFile);
        file.add(undo);
        file.add(soundMenu);

        commands.add(ballAdd);
        commands.add(bomb);
        commands.add(ticketAdd);
        commands.add(ownSquares);

        file.add(new JMenuItem(about));
        file.add(new CommandQuit());

        menuBar.add(file);
        menuBar.add(commands);

        this.setJMenuBar(menuBar);

        /* Game Key Binding */
        // Get the "focus is in the window" input map for the center panel
        int mapName = JComponent.WHEN_IN_FOCUSED_WINDOW;
        InputMap imap = mv.getInputMap(mapName);

        // Create keystroke objects to represent the arrow keys
        KeyStroke upKey = KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0);
        KeyStroke downKey = KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0);
        KeyStroke leftKey = KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0);
        KeyStroke rightKey = KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0);
        //KeyStroke deleteSelected = KeyStroke.getKeyStroke(KeyEvent.VK_DELETE,1);

        // Create keystroke objects to represent the 'i' key and 'l' keys
        KeyStroke iKey = KeyStroke.getKeyStroke('i');
        KeyStroke lKey = KeyStroke.getKeyStroke('l');
        KeyStroke deleteSelected = KeyStroke.getKeyStroke("DELETE");

        //Create keystroke object to represent the 'space bar' key
        KeyStroke spaceKey = KeyStroke.getKeyStroke(KeyEvent.VK_SPACE, 0);

        // Put the arrow-Keys,i,l,space keystroke objects into the panel’s "when focus is
        // in the window" input map under the appropriate identifiers
        imap.put(upKey, "up"); //hashtable(k,v)
        imap.put(downKey, "down");
        imap.put(leftKey, "left");
        imap.put(rightKey, "right");
        imap.put(iKey, "increase");
        imap.put(lKey, "decrease");
        imap.put(deleteSelected, "delete");

        //imap.put(KeyStroke.getKeyStroke("SPACE"), "none");
        //imap.put(spaceKey, "strategy");

        // Get the action map for the panel
        ActionMap amap = mv.getActionMap();

        // Put the Arrow-Keys, i,l and space Command objects into the panel's ActionMap
        amap.put("up", goUp); //hashtable(k,v)
        amap.put("down", goDown);
        amap.put("left", turnLeft);
        amap.put("right", turnRight);
        amap.put("increase", speedInc);
        amap.put("decrease", speedDec);
        amap.put("strategy", changeStrategy);
        amap.put("delete", delete);

        //Have the frame request keyboard focus
        this.requestFocus();

        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
    }

    public void requestMapViewFocus() {
        this.mv.requestFocus();
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (!this.gw.getTimerStat()) {
            this.pp = e.getPoint();
            this.mv.select((Point) this.pp, e.isControlDown()); //pass points to MV
            this.mv.sweeperCreate(this.pp, e.isShiftDown());
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        this.pressedHere = e.getPoint();
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        if (e.isAltDown()) {
            int x = (int) this.mousePrevPoint.getX();
            int y = (int) this.mousePrevPoint.getY();
            int newx = e.getX();
            int newy = e.getY();
            if (newx > x) {
                this.mv.pan('d');
            }
            if (newx < x) {
                this.mv.pan('a');
            }
            if (newy > y) {
                this.mv.pan('s');
            }
            if (newy < y) {
                this.mv.pan('w');
            }
            this.mousePrevPoint = e.getPoint();
        }
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        if (e.getWheelRotation() > 0) {
            this.mv.zoomIn();
        } else {
            this.mv.zoomOut();
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    @Override
    public void mouseDragged(MouseEvent me) {
    }
}
