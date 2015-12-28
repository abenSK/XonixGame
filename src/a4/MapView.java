/*
 * Display the map view. MapView is an observer and upon being notified displays
 * statues of game objects. Accesses the GameWorld via GameWorldProxy. 
 */
package a4;

import a4.gameObjects.Moveable;
import a4.gameObjects.SmartBomb;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.AffineTransform;
import java.awt.geom.NoninvertibleTransformException;
import java.awt.geom.Point2D;
import java.net.MalformedURLException;
import java.util.Iterator;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

/**
 *
 * @author Aben
 */
public class MapView extends JPanel implements IObserver {

    private GameWorldProxy gwp;
    private int height = 700;
    private int width = 700;
    private int topRightY = 700;
    private int topRightX = 700;
    private int bottomLeftX = 0;
    private int bottomLeftY = 0;
    private Point selectionStart = new Point(0, 0);
    private Point selectionEnd = new Point(0, 0);
    private AffineTransform viewTransform = new AffineTransform();
    private AffineTransform worldToND;
    private AffineTransform ndToScreen;
    private AffineTransform theVTM;
    private AffineTransform inverseVTM;
    private double windowRight = 700.0D;
    private double windowTop = 700.0D;
    private double windowLeft = 0.0D;
    private double windowBottom = 0.0D;
    SmartBomb myFireball;

    public MapView(GameWorldProxy gameWorldInstance) throws MalformedURLException {

        this.gwp = gameWorldInstance;
        this.setBorder(new TitledBorder("Map"));
        this.setBorder(new LineBorder(Color.black, 2));
        setPreferredSize(new Dimension(700, 700));

        this.bottomLeftX = 0;
        this.bottomLeftY = 0;
    }

    private void setHW() {
        this.height = getHeight();
        this.width = getWidth();
        this.topRightY = this.height;
        this.topRightX = this.width;
        this.bottomLeftX = (this.width - this.topRightX);
        this.bottomLeftY = (this.height - this.topRightY);
    }

    protected void zoomIn() {
        double h = this.windowTop - this.windowBottom;
        double w = this.windowRight - this.windowLeft;

        if (this.gwp.getTimerStat()) { //only supported when play mode
            this.windowLeft += w * 0.05D;
            this.windowRight -= w * 0.05D;
            this.windowTop -= h * 0.05D;
            this.windowBottom += h * 0.05D;
            repaint();
        }
    }

    protected void zoomOut() {
        double h = this.windowTop - this.windowBottom;
        double w = this.windowRight - this.windowLeft;

        if (this.gwp.getTimerStat()) {
            this.windowLeft -= w * 0.05D;
            this.windowRight += w * 0.05D;
            this.windowTop += h * 0.05D;
            this.windowBottom -= h * 0.05D;
            repaint();
        }
    }

    protected void pan(char x) {
        if (this.gwp.getTimerStat()) {
            switch (x) {
                case 'w'://up
                    this.windowTop += 10.0D;
                    this.windowBottom += 10.0D;
                    break;
                case 's'://down
                    this.windowTop -= 10.0D;
                    this.windowBottom -= 10.0D;
                    break;
                case 'a'://left
                    this.windowLeft -= 10.0D;
                    this.windowRight -= 10.0D;
                    break;
                case 'd'://right
                    this.windowLeft += 10.0D;
                    this.windowRight += 10.0D;
                    break;
            }
        }
    }
    /* Call the map method to update it .*/

    @Override
    public void update(IObservable obs, Object obj) {
        this.gwp = (GameWorldProxy) obs;
        this.repaint();
    }

    private AffineTransform buildScreenTransform(double width, double height) {
        AffineTransform result = new AffineTransform();
        result.setToIdentity();
        result.translate(0.0D, height);
        result.scale(width, -height);
        return result;
    }

    private AffineTransform buildNDTransform(double windowWidth, double windowHeight, double windowLeft, double windowBottom) {
        AffineTransform result = new AffineTransform();
        result.setToIdentity();
        result.scale(1.0D / windowWidth, 1.0D / windowHeight);
        result.translate(-windowLeft, -windowBottom);
        return result;
    }

    public AffineTransform getViewTransform() {
        return (AffineTransform) this.viewTransform.clone();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        setHW();
        Graphics2D g2d = (Graphics2D) g;

        this.worldToND = buildNDTransform(this.windowRight - this.windowLeft, this.windowTop - this.windowBottom, this.windowLeft, this.windowBottom);
        this.ndToScreen = buildScreenTransform(getSize().getWidth(), getSize().getHeight());
        this.theVTM = ((AffineTransform) this.ndToScreen.clone());
        this.theVTM.concatenate(this.worldToND);
        try {
            this.inverseVTM = this.theVTM.createInverse();
        } catch (NoninvertibleTransformException e) {
        }
        g2d.transform(this.theVTM);
        AffineTransform save = g2d.getTransform();

        GameObjectCollection localGameObjectCollection = this.gwp.getGameWorldObjects();
        Iterator itr = localGameObjectCollection.iterator();
        Iterator itr2 =localGameObjectCollection.iterator();

        /* Draw all objects in the collection */
        while (itr.hasNext()) {
            GameObject tmp = (GameObject) itr.next();

            if (!(tmp instanceof Moveable)) {//draw stationary objects first
                tmp.draw(g2d);
            }

            g2d.setTransform(save);

            /* Unselect all selected object when not paused */
            if (this.gwp.getTimerStat()) {
                if (tmp instanceof ISelectable) {
                    ((ISelectable) tmp).setSelected(false);
                }
            }
        }
        while (itr2.hasNext()) {
            GameObject tmp = (GameObject) itr2.next();
            if (tmp instanceof Moveable) {//draw moveable objects last
                tmp.draw(g2d);
            }
             g2d.setTransform(save);
        }
        g2d.setTransform(save);
    }

    public void setSelectionPoints(Point s, Point e) {
        this.selectionStart = s;
        this.selectionEnd = e;
    }

    /* Select an object based on the clicked points (pp) and the object's co-ordinates
     Select multiple object with ctrl pressed */
    public void select(Point pp, boolean ctrl) {
        GameObjectCollection go = this.gwp.getGameWorldObjects();
        Iterator itr = go.iterator();
        if (!ctrl) {
            while (itr.hasNext()) {
                GameObject obj = (GameObject) itr.next();
                if ((obj instanceof ISelectable)) {
                    ((ISelectable) obj).setSelected(false);
                    repaint();
                }
            }
        }
        itr = go.iterator();
        while (itr.hasNext()) {
            GameObject obj = (GameObject) itr.next();
            if ((obj instanceof ISelectable)) {
                Point2D.Float transformedPoints = new Point2D.Float();
                this.inverseVTM.transform(pp, transformedPoints);

                if (((ISelectable) obj).contains(transformedPoints.getX(), transformedPoints.getY())) {
                    //if (((ISelectable) obj).contains((int) obj.getLocation().x, (int) obj.getLocation().y)) {//object's points
                    //if (((ISelectable) obj).contains((int) pp.getX(), (int) pp.getY())) {//clicked points
                    ((ISelectable) obj).setSelected(true);
                } else if (!ctrl) {
                    ((ISelectable) obj).setSelected(false);
                }
                repaint();
            }
        }
    }

    /* Create a sweeper at the location the mouse is clicked while shift key is
     * held and during game paused */
    public void sweeperCreate(Point pp, boolean shiftDown) {

        if (shiftDown) {
            Point2D.Float transformedPoints = new Point2D.Float();
            this.inverseVTM.transform(pp, transformedPoints);

            this.gwp.addSweeper(transformedPoints);

            repaint();

        }
    }
}
