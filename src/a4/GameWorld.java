/*
 * GameObjects access and gameObject manipulation take place in the gameWorld
 */
package a4;

import a4.gameObjects.Car;
import a4.gameObjects.FieldSquare;
import a4.gameObjects.MonsterBall;
import a4.gameObjects.SmartBomb;
import a4.gameObjects.Sweepers;
import a4.gameObjects.TimeTicket;
import java.awt.Color;
import java.awt.Point;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
import javax.swing.Timer;

/**
 *
 * @author Aben
 */
public class GameWorld implements IObservable {

    private FieldSquare square[][];
    private FieldSquare squarePass;
    private Point.Float squareLocation; // for square ball location
    private int ownedSquare, groupSquare;
    private Random generator; // used for generating random objects
    private int life;
    private int level, clockStart = 10, ticketStart = 5;
    private double minScore;
    private int clock, ticketTime, decClock, decTicketTime;
    private Car car;
    private MonsterBall ball;
    private TimeTicket ticket;
    private SmartBomb bomb;
    private Sweepers sweeper;
    private GameObjectCollection go;
    // Since GameWorld is observable, it has to register its observers //
    private ArrayList<IObserver> observers = new ArrayList<IObserver>();
    private boolean sound = true;
    private int strat; // number to represent strategy
    private int timerHolder = 0;
    private int stratTimer = 0;
    private Timer timer;
    private Sound carHitBall;
    private Sound carHitBomb;
    private Sound carHitTicket;
    private Sound background;
    private GameWorldProxy myProxy;
    private boolean soundCheck = false;
    private boolean timerOn = true;
    private GameObjectCollection pass;
    private boolean collisionHappened = false;
    private int tmpCount = 0;
    private int deterCount = 0;
    private int deterCount2 = 0;
    private boolean carFieldCol = false;
    private float seedX;
    private float seedY = 0;

    public GameWorld() throws MalformedURLException {
        ownedSquare = groupSquare = 0;
        generator = new Random();
        life = 3; // player has 3 lives
        level = 1;
        minScore = 0;


        clock = 0;
        ticketTime = ticketStart;
        decClock = 2;
        decTicketTime = 1;

        go = new GameObjectCollection(); // Arraylist containg all game objects 

        this.myProxy = new GameWorldProxy(this);

        car = new Car();
        ball = new MonsterBall();
        ticket = new TimeTicket();
        bomb = new SmartBomb();
        squarePass = new FieldSquare();
        sweeper = new Sweepers();
        pass = new GameObjectCollection();

        createFields(); //create the initial fields and car at the beginning of a level

        strat = 1; //1 = default strategy , 2 = follow strategy
        map();

        this.carHitBall = new Sound("car_ball.wav", this.myProxy);
        this.carHitBomb = new Sound("explosion.wav", this.myProxy);
        this.carHitTicket = new Sound("ticket_hit.wav", this.myProxy);
        this.background = new Sound("background.wav", this.myProxy);

    }

    public void createFields() {
        System.out.printf("\nLevel: " + level + "\n");
        setLevel(level);//set the level

        ownedSquare = 0;
        squarePass = new FieldSquare();

        go = new GameObjectCollection();
        Iterator iterator = go.iterator();

        int x = 3, y = 3;

        /*Top row of square is owned by player*/
        for (int i = 0; i < 100; i++) {
            go.add(new FieldSquare());

            GameObject gameObject = (GameObject) iterator.next();

            squarePass = (FieldSquare) gameObject;

            squareLocation = new Point.Float(x, y); // x and y co-ordinates for a square
            squarePass.setLocation(squareLocation);//set location of a square
            ownedSquare++;
            squarePass.setOwned();
            x += 6;
        }

        x = 3;
        y = 597;

        /* Bottom row of square is owned by player*/
        for (int i = 0; i < 100; i++) {

            go.add(new FieldSquare());
            GameObject gameObject = (GameObject) iterator.next();
            squarePass = (FieldSquare) gameObject;

            squareLocation = new Point.Float(x, y); // x and y co-ordinates for a square
            squarePass.setLocation(squareLocation);//set location of a square
            ownedSquare++;
            squarePass.setOwned();
            x += 6;
        }

        x = 3;
        y = 9;
        /* Left column of square is owned by player*/
        for (int i = 0; i < 98; i++) {

            go.add(new FieldSquare());
            GameObject gameObject = (GameObject) iterator.next();
            squarePass = (FieldSquare) gameObject;

            squareLocation = new Point.Float(x, y); // x and y co-ordinates for a square
            squarePass.setLocation(squareLocation);//set location of a square
            ownedSquare++;
            squarePass.setOwned();
            y += 6;
        }

        x = 597;
        y = 9;

        /* Right column of square is owned by player*/
        for (int i = 0; i < 98; i++) {

            go.add(new FieldSquare());
            GameObject gameObject = (GameObject) iterator.next();
            squarePass = (FieldSquare) gameObject;

            squareLocation = new Point.Float(x, y); // x and y co-ordinates for a square
            squarePass.setLocation(squareLocation);//set location of a square
            ownedSquare++;
            squarePass.setOwned();
            y += 6;
        }
        go.add(new Car());

        GameObject gameObject = (GameObject) iterator.next();
        car = (Car) gameObject;
        car.getFields(fieldPass());

    }
    /* Change the direction the car is facing */

    public void carSteer(char direction) {
        car.steerable(direction);
        notifyObservers();
    }

    /* Increase the car's speed by one unit*/
    public void carSpeedInc() {
        car.increaseSpeed();
        notifyObservers();
    }

    /* Decrease the car's speed by one unit */
    public void carSpeedDec() {
        car.decreaseSpeed();
        notifyObservers();
    }

    /* Create a monsterball */
    public void addBall() {
        go.add(new MonsterBall());
        notifyObservers();
    }

    /* Create a ticket */
    public void addTicket() {
        go.add(new TimeTicket());
        notifyObservers();
    }

    /* Create a SmartBomb */
    public void addSmartBomb() {
        go.add(new SmartBomb());
        notifyObservers();
    }

    /* Create a Sweeper at a specific location */
    public void addSweeper(Point.Float newLoc) {
        go.add(new Sweepers(newLoc));
    }

    public void toggleTimer() {
        if (this.timerOn) {
            this.timer.stop();
            //setSound(false);
            this.background.stop();
        }
        if (!this.timerOn) {
            this.timer.start();
            //setSound(true);
            this.background.loop();
        }
        this.timerOn = (!this.timerOn);
    }

    public boolean getTimerStat() {
        return this.timerOn;
    }

    /* Delete selected object(s) */
    public void deleteSelected() {
        Iterator itr = this.go.iterator();
        while (itr.hasNext()) {
            GameObject tmp = (GameObject) itr.next();
            if (((tmp instanceof ISelectable))
                    && (((ISelectable) tmp).isSelected())) {
                itr.remove();
            }
        }
    }

    public void select(int x, int y, boolean ctrl) {
        Iterator itr = this.go.iterator();

        if (!ctrl) {
            while (itr.hasNext()) {
                GameObject obj = (GameObject) itr.next();
                if ((obj instanceof ISelectable)) {
                    ((ISelectable) obj).setSelected(false);
                }
            }
        }
        itr = this.go.iterator();
        while (itr.hasNext()) {
            GameObject obj = (GameObject) itr.next();
            if (((obj instanceof ISelectable))
                    && (((ISelectable) obj).contains(x, y))) {
                ((ISelectable) obj).setSelected(true);
            }
        }
    }


    /* Own a group of squares. Checks for level win and game win */
    public void groupSquareOwn() {
        ownSquareGroup(); //own a group of squares
        System.out.printf("\nOwned " + squareGroup() + " squares"); // display the group of squares

        /* Check for level win status */
        if (winLevel(getLevel()) == true) {
            level++;
            System.out.printf("\nLevel Complete\n");

            if (level == 5) {
                System.out.printf("\n\n!!!!!!Congratulations You Won the Game !!!!!!!");
                System.exit(0);
            }

            /* Create a new level */
            createFields();
        }
        notifyObservers();
    }

    public GameObjectCollection fieldPass() {
        Iterator itr = go.iterator();
        while (itr.hasNext()) {
            GameObject gameObject = (GameObject) itr.next();
            if (gameObject instanceof FieldSquare) {
                pass.add(gameObject);
            }
        }
        return pass;
    }

    /* Car collides with a ball. Number of lives decreases, check for game over */
    public void ballCollision() {
        Iterator iterator = go.iterator();
        Iterator itr = go.iterator();

        /*boolean ballCheck = false;

         while (iterator.hasNext()) {
         GameObject gameObject = (GameObject) iterator.next();
         if (gameObject instanceof MonsterBall) {
         ballCheck = true;
         }
         }

         if (ballCheck == false) { //check if there is a ball in the level
         System.out.printf("\nNo balls in the level\n");
         } else {*/
        decreaseLife(); //decrease one life
        car.setLocation(null); //reposition car

        /* Change the color of monster balls*/
        while (itr.hasNext()) {
            GameObject gameObject = (GameObject) itr.next();
            if (gameObject instanceof MonsterBall) {
                ball = (MonsterBall) gameObject;
                ball.setColor(null);
            }
        }

        /* If player has no more lives game ends*/
        if (getLife() == 0) {
            System.out.printf("\n\nGame Over\n");
            System.exit(0); //quit
        }

        notifyObservers();
    }

    /* Car collides with a bomb. Number of lives decreases, check for game over */
    public void bombCollision(float passedLoc) {
        Iterator iterator = go.iterator();
        Iterator itr = go.iterator();

        boolean bombCheck = false;

        while (iterator.hasNext()) {
            GameObject gameObject = (GameObject) iterator.next();
            if (gameObject instanceof SmartBomb) {
                bombCheck = true;
            }
        }

        if (bombCheck == false) { //check if there is a bomb in the level
            System.out.printf("\nNo bombs in the level\n");
        } else {
            decreaseLife(); //decrease one life
            car.setLocation(null); //reposition car


            /* Remove smart Bomb from the level after it explodes */
            while (itr.hasNext()) {
                GameObject gameObject = (GameObject) itr.next();
                if (gameObject instanceof SmartBomb) {
                    SmartBomb bomb = (SmartBomb) gameObject;

                    /* Choose to remove the correct smart bomb by comparing the location 
                     of the current smartbomb and passed location of the smartbomb
                     during collision from tick()*/

                    if (passedLoc == bomb.getLocation().x) {
                        itr.remove();
                    }
                }
            }

            /* If player has no more lives game ends*/
            if (getLife() == 0) {
                System.out.printf("\n\nGame Over");
                System.exit(0); //quit
            }
        }
        notifyObservers();
    }

    public void removeGameObject(float passedLoc) {
        Iterator iterator = go.iterator();

        while (iterator.hasNext()) {
            GameObject gameObject = (GameObject) iterator.next();
            if (gameObject instanceof SmartBomb || gameObject instanceof MonsterBall || gameObject instanceof TimeTicket) {
                if (passedLoc == gameObject.getLocation().x) {
                    iterator.remove();
                }
            }
        }
    }

    /* Switch between the two strategies 
     * 1 = default Strategy
     * 2 = Follow Strategy
     */
    public void toggleStrategy() {
        if (strat == 1) {
            strat = 2;
        } else {
            strat = 1;
        }
    }

    /* Own one square and checks for level completion or game win */
    public void squareOwn() {

        increaseOwnedSquares(); // increase owned square

        /* Check for level win status */
        if (winLevel(getLevel()) == true) {
            level++;
            System.out.printf("\nLevel Complete\n");

            if (level == 5) {
                System.out.printf("\n\n!!!!!!Congratulations You Won the Game !!!!!!!");
                System.exit(0); //quit
            }

            /* Create a new level */
            createFields();
        }
        notifyObservers();
    }

    /* Add time to the clock resulting from a car hitting a time ticket */
    public void addTime(float passedLoc) {

        boolean ticketCheck = false;

        Iterator iterator = go.iterator();
        Iterator itr = go.iterator();

        while (iterator.hasNext()) {
            GameObject gameObject = (GameObject) iterator.next();
            if (gameObject instanceof TimeTicket) {
                ticketCheck = true;
            }
        }

        if (ticketCheck == false) { //check if there is a time ticket in the level
            System.out.printf("\nNo Time tickets\n");
        } else {
            addClockTime(getTicketTime()); //Increase clock by amount of ticket time

            /* Remove Time Ticket from the level */
            while (itr.hasNext()) {
                GameObject gameObject = (GameObject) itr.next();
                if (gameObject instanceof TimeTicket) {
                    TimeTicket ticket = (TimeTicket) gameObject;

                    /* Select the correct ticket*/
                    if (passedLoc == ticket.getLocation().x) {
                        itr.remove();
                    }
                }
            }
        }

        notifyObservers();
    }

    /* Time tickets in the game resulting in all moveable objects moving 
     Square ownage and object collision checked */
    public void tick() {

        Iterator iterator = go.iterator();
        Iterator itr3 = go.iterator();
        Iterator itr4 = go.iterator();


        /* Which strategy to pick */
        if (strat == 1) {
            bomb.setStrategy(new DefaultStrategy(bomb));
        } else {
            bomb.setStrategy(new FollowStrategy(car, bomb));
        }
        bomb.invokeStrategy();

        /* change strategy to follow after about 10 sec*/
        if (stratTimer == 200) {
            strat = 2;
        }

        /* change strategy to default after another 10 sec*/
        if (stratTimer > 400) {
            strat = 1;
            stratTimer = 0;
            //go.add(new Sweepers());//add sweeper
        }

        while (itr4.hasNext()) {
            GameObject gameObject4 = (GameObject) itr4.next();
            if (gameObject4 instanceof Sweepers) {
                Sweepers sweep = (Sweepers) gameObject4;
                if (sweep.getLocation().x > 1000 || sweep.getLocation().y > 1000) {
                    itr4.remove();
                }
            }
        }

        while (iterator.hasNext()) {
            GameObject gameObject = (GameObject) iterator.next();
            if (gameObject instanceof Car) {
                Car car = (Car) gameObject;
                if (car.carFind() == false) {

                    go.add(new FieldSquare(car.getLocation())); //Add potential field if car is in unowned area 

                }
                car.move(car.getHeading(), car.getSpeed(), .4f); //move the car

            }

            /* when the car returns to safety change all gray (potential) squares to blue*/
            if ((car.carFind() == true) && collisionHappened == false) {

                while (itr3.hasNext()) {
                    GameObject gameObject2 = (GameObject) itr3.next();
                    if (gameObject2 instanceof FieldSquare) {
                        FieldSquare field = (FieldSquare) gameObject2;
                        if (field.getColor() == Color.gray) {
                            field.setColor(Color.blue);

                            if (car.getHeading() == 90) {
                                go.add(new FieldSquare(car.getLocation().x - 12, car.getLocation().y - 15));
                            }
                            if (car.getHeading() == 270) {
                               go.add(new FieldSquare(car.getLocation().x+12, car.getLocation().y-15));
                            }
                            if (car.getHeading() == 180) {
                               go.add(new FieldSquare(car.getLocation().x+12, car.getLocation().y+10));
                            }
                            if (car.getHeading() == 0) {
                               go.add(new FieldSquare(car.getLocation().x+12, car.getLocation().y-8));
                            }
                            
                            tmpCount++;
                            car.getFields(fieldPass()); // update collection of fieldsquares for car

                        }
                    }
                }
            }

            tmpCount /= 6; //number of new owned squares
            ownedSquare += tmpCount; // Increase number of squares 
            tmpCount = 0;

            /* If collision happens remove potientail squares */
            if (collisionHappened == true && (car.carFind() == true)) {
                //System.out.println("happened");
                while (itr3.hasNext()) {
                    GameObject gameObject3 = (GameObject) itr3.next();
                    if (gameObject3 instanceof FieldSquare) {
                        FieldSquare field = (FieldSquare) gameObject3;
                        if (field.getColor() == Color.gray) {
                            field.setColor(Color.white); // hides the square...not good
                            //itr3.remove(); //not working
                        }
                        collisionHappened = false;
                    }
                }

            }



            /* Move the MonsterBall*/
            if (gameObject instanceof MonsterBall) {
                ball = (MonsterBall) gameObject;
                ball.move(ball.getHeading(), ball.getSpeed(), 1f);
            }

            /* Move the SmartBomb based on selected strategy */
            if (gameObject instanceof SmartBomb) {
                bomb = (SmartBomb) gameObject;
                /* Which strategy to pick */
                if (strat == 1) {
                    bomb.setStrategy(new DefaultStrategy(bomb));
                    bomb.invokeStrategy();
                } else {
                    bomb.setStrategy(new FollowStrategy(car, bomb));
                    bomb.invokeStrategy();
                }
                bomb.move(bomb.getHeading(), bomb.getSpeed(), 0.5f);
            }

            /* Move the sweeper wave across the map*/
            if (gameObject instanceof Sweepers) {
                sweeper = (Sweepers) gameObject;
                sweeper.move(sweeper.getHeading(), sweeper.getSpeed(), 0.5f);
            }
        }


        if (timerHolder >= 280) { //equavalent to about 60 secs :/
            decreaseClock(); // decrease game clock
            timerHolder = 0;
        }

        /* If time runs out decrease life */
        if (getClock() == 0) {
            System.out.printf("\n\nTime has ran out!!!");
            decreaseLife(); //decrease one life
            setLevel(getLevel());//get the level and restart time
            car.setLocation(null); //reposition car
        }

        /* If player has no more lives game ends*/
        if (getLife() == 0) {
            System.out.printf("\n\nGame Over");
            System.exit(0); //quit game
        }

        /* Collision Handling of objects */
        Iterator itr = this.go.iterator();
        while (itr.hasNext()) {
            Iterator itr2 = this.go.iterator();
            GameObject tmp = (GameObject) itr.next();
            while (itr2.hasNext()) {
                //if (itr.hasNext()) {
                //    itr.next();
                // }
                GameObject tmp2 = (GameObject) itr2.next();
                if ((tmp.collidesWith(tmp2)) && (tmp != tmp2)) {
                    tmp.handleCollision(tmp2);
                    if (((tmp instanceof Car)) && ((tmp2 instanceof FieldSquare))) { // car collides with MB
                        carFieldCol = true;
                    }
                    if (((tmp instanceof Car)) && ((tmp2 instanceof MonsterBall))) { // car collides with MB
                        if (this.sound) {
                            this.carHitBall.play();
                        }
                        ballCollision();
                        collisionHappened = true;
                    }

                    if (((tmp instanceof Car)) && ((tmp2 instanceof SmartBomb))) { //car collides with SB
                        SmartBomb bomb = (SmartBomb) tmp2;
                        if (this.sound) {
                            this.carHitBomb.play();
                        }
                        bombCollision(bomb.getLocation().x);
                        collisionHappened = true;
                    }

                    if (((tmp instanceof Car)) && ((tmp2 instanceof TimeTicket))) { //car collides with TT
                        TimeTicket ticket = (TimeTicket) tmp2;
                        if (this.sound) {
                            this.carHitTicket.play();
                        }
                        addTime(ticket.getLocation().x);
                    }
                    if (((tmp instanceof FieldSquare)) && ((tmp2 instanceof MonsterBall))) {
                        FieldSquare field = (FieldSquare) tmp;
                        if (field.getColor() == Color.gray && (deterCount == 0)) {
                            ballCollision();
                            collisionHappened = true;
                            deterCount++;
                        }
                    }

                    if (((tmp instanceof FieldSquare)) && ((tmp2 instanceof SmartBomb))) {
                        FieldSquare field = (FieldSquare) tmp;
                        if (field.getColor() == Color.gray && (deterCount2 == 0)) {
                            bombCollision(bomb.getLocation().x);
                            collisionHappened = true;
                            deterCount2++;
                        }
                    }


                    if (((tmp instanceof Sweepers)) && ((tmp2 instanceof Car))) {
                        ballCollision();
                        collisionHappened = true;
                        //System.out.println("happened @" + tmp.getLocation() + " " + tmp2.getLocation());
                    }

                    if (((tmp instanceof Sweepers)) && ((tmp2 instanceof MonsterBall))) {

                        removeGameObject(tmp2.getLocation().x);
                    }

                    if (((tmp instanceof Sweepers)) && ((tmp2 instanceof TimeTicket))) {

                        removeGameObject(tmp2.getLocation().x);
                    }
                    if (((tmp instanceof Sweepers)) && ((tmp2 instanceof SmartBomb))) {

                        removeGameObject(tmp2.getLocation().x);
                    }

                    /*play background sound as long as sound is on and not paused */
                    if (this.sound && soundCheck == false) {
                        this.background.loop();
                        soundCheck = true;
                    }
                    /* Stop playing background sound */
                    if (!this.sound) {
                        this.background.stop();
                        soundCheck = false;
                    }

                }
            }
        }


        timerHolder++;
        stratTimer++;
        deterCount = 0;
        deterCount2 = 0;
        notifyObservers();
    }

    public void floodFill(float x, float y, Color old, float floodWidth, float floodHeight) {
        // tmp = new Point.Float(0,0);
        go.add(new FieldSquare(x + 50, y + 50));

        /* if ((x < 0) || (x >= floodWidth)) {System.out.println(floodWidth);
         return;
         }
         if ((y < 0) || (y >= floodHeight)) {
         return;
         }
         if (old == Color.white) {
         //setPixel(fill, x, y);
         tmp = new Point.Float(x,y);
         go.add (new FieldSquare(tmp));
         floodFill(x + 1, y, old, floodWidth,floodHeight);
         floodFill(x, y + 1,old,floodWidth,floodHeight);
         floodFill(x - 1, y, old,floodWidth,floodHeight);
         floodFill(x, y - 1, old,floodWidth,floodHeight);
         }*/
    }

    public void setTimer(Timer t) {
        this.timer = t;
    }

    /* Display Map of game objects for current level. To be used by MapView NOT USED*/
    public void map() {
        Iterator iterator = go.iterator();

        while (iterator.hasNext()) {
            GameObject gameObject = (GameObject) iterator.next();

            if (gameObject instanceof Car) {
                car = (Car) gameObject;
                System.out.printf("\nCar loc =(%.2f,%.2f) ", car.getLocation().x, car.getLocation().y);
                System.out.printf("Color = [%d,%d,%d]",
                        car.getColor().getRed(), car.getColor().getGreen(), car.getColor().getBlue());
                System.out.printf(" Speed = %d, heading = %d, height = %d, width = %d\n",
                        car.getSpeed(), car.getHeading(), car.getHeight(), car.getWidth());
            }

            if (gameObject instanceof MonsterBall) {
                ball = (MonsterBall) gameObject; // cast gameObject to ball 
                System.out.printf("Ball loc =(%.2f,%.2f) ", ball.getLocation().x, ball.getLocation().y);
                System.out.printf("Color = [%d,%d,%d]",
                        ball.getColor().getRed(), ball.getColor().getGreen(), ball.getColor().getBlue());
                System.out.printf(" Speed = %d, heading = %d, radius = %d\n",
                        ball.getSpeed(), ball.getHeading(), ball.getRadius());

            }

            if (gameObject instanceof TimeTicket) {
                ticket = (TimeTicket) gameObject;
                System.out.printf("Ticket loc =(%.2f,%.2f) ", ticket.getLocation().x, ticket.getLocation().y);
                System.out.printf("Color = [%d,%d,%d]",
                        ticket.getColor().getRed(), ticket.getColor().getGreen(), ticket.getColor().getBlue());
                System.out.printf(" height = %d, width = %d, time = %d\n",
                        ticket.getHeight(), ticket.getWidth(), ticket.getTicketTime());
            }

            if (gameObject instanceof SmartBomb) {
                bomb = (SmartBomb) gameObject;
                System.out.printf("Bomb loc =(%.2f,%.2f) ", bomb.getLocation().x, bomb.getLocation().y);
                System.out.printf("Color = [%d,%d,%d]",
                        bomb.getColor().getRed(), bomb.getColor().getGreen(), bomb.getColor().getBlue());
                System.out.printf(" Speed = %d, heading = %d\n",
                        bomb.getSpeed(), bomb.getHeading());
            }
        }

    }

    public Point.Float fieldTest(Point.Float location) {
        location = squareLocation;
        return squareLocation;
    }

    public GameObjectCollection getGameWorldObjects() {
        return this.go;
    }

    /**
     * *********************************************************************
     */
    /* 100 x 100 Grid created with outer edges owned by player NOT USED*/
    public void createGrid() {
        System.out.printf("\nLevel: " + level + "\n");
        setLevel(level);//set the level
        square = new FieldSquare[100][100]; //100 x 100 grid of squares


        int size = new FieldSquare().FieldSize(); // field size 5 x 5
        int tempX, tempY; //temp values for assigning location of squares
        tempX = 0;
        tempY = 0;
        ownedSquare = 0;

        /* Create each square with its location */
        for (int i = 0; i < 100; i++) {
            for (int j = 0; j < 100; j++) {
                square[i][j] = new FieldSquare();

                square[i][j].setColor(Color.black); //squares have black color
                squareLocation = new Point.Float(tempX, tempY); // x and y co-ordinates for a square
                square[i][j].setLocation(squareLocation);//set location of a square
                tempX += size;
            }
            tempY += size;
            tempX = 0;
        }

        /*Bottom row of square is owned by player*/
        for (int i = 0; i < 100; i++) {
            square[i][0].owned();
            square[i][0].setColor(Color.blue); //owned squares are blue
            //fieldTest(squareLocation);
        }

        /*Top row of square is owned by player*/
        for (int i = 0; i < 100; i++) {
            square[i][99].owned();
            square[i][99].setColor(Color.blue); //owned squares are blue
        }

        /*Left column of square is owned by player*/
        for (int i = 0; i < 100; i++) {
            square[0][i].owned();
            square[0][i].setColor(Color.blue); //owned squares are blue
        }

        /*Right column of square is owned by player*/
        for (int i = 0; i < 100; i++) {
            square[99][i].owned();
            square[99][i].setColor(Color.blue); //owned squares are blue
        }

        /* Count the number of owned squares */
        for (int i = 0; i < 100; i++) {
            for (int j = 0; j < 100; j++) {

                if (square[i][j].checkOwned() == true) {

                    ownedSquare++;
                }
                //System.out.println("test: "+square[i][j].getLocation());
            }
        }


    }


    /* Number of owned square */
    public int ownedSquare() {
        return ownedSquare;
    }

    /* Increase amount of owned square by 1*/
    public void increaseOwnedSquares() {
        ownedSquare++;
    }

    /* Own a group of squares */
    public void ownSquareGroup() {
        groupSquare = generator.nextInt(ownedSquare);
        ownedSquare += groupSquare;
    }

    /* Return the amount of group squares player takes */
    public int squareGroup() {
        return groupSquare;
    }

    /* Return amount of ticket time*/
    public int getTicketTime() {
        return ticketTime;
    }

    /* Return number of lives left*/
    public int getLife() {
        return life;
    }

    /* Decrease number of lives by 1*/
    public void decreaseLife() {
        life--;
    }

    /* Return amount of time left in the level*/
    public int getClock() {
        return clock;
    }

    /* Add to game clock resulting from car hitting time ticket */
    public void addClockTime(int amount) {
        clock += amount;
    }

    /* Decrease Game clock by 1*/
    public void decreaseClock() {
        clock--;
    }

    /* Return current Score */
    public double getCurrentScore() {
        return ownedSquare;
    }

    /* Min Score for a level */
    public double getMinScore() {
        return minScore;
    }

    /* Get current level */
    public int getLevel() {
        return level;
    }

    /* Increase a level */
    public void increaseLevel() {
        level++;
        notifyObservers();
    }

    /* Set min score, clock, ticket time and life for each level */
    public void setLevel(int level) {
        //life = 3; //beginning of each level player has 3 lives
        //System.out.println("enter");
        switch (level) {
            case 1:
                minScore = 5000;
                clock = clockStart;
                ticketTime = ticketStart;
                break;
            case 2:
                minScore = 5000 + (5000 * 0.1);
                clock = (clockStart - decClock);
                ticketTime = (ticketStart - decTicketTime);
                break;
            case 3:
                minScore = 5000 + (5000 * 0.2);
                clock = (clockStart - decClock - 2);
                ticketTime = (ticketStart - decTicketTime - 1);
                break;
            case 4:
                minScore = 5000 + (5000 * 0.3);
                clock = (clockStart - decClock - 4);
                ticketTime = (ticketStart - decTicketTime - 2);
                break;
            case 5:
                minScore = 5000 + (5000 * 0.4);
                clock = (clockStart - decClock - 6);
                ticketTime = (ticketStart - decTicketTime - 3);
                break;

        }

    }

    /* Player wins a level if has not lost all lives and meets the required score */
    public boolean winLevel(int level) {
        //setLevel(level); //check minScore for current level

        if ((life > 0) && (ownedSquare >= minScore)) {
            return true;
        } else {
            return false;
        }
    }

    /* Return the state of the sound: on/off */
    public boolean getSound() {
        return this.sound;
    }

    public void setSound(boolean sound) {
        this.sound = sound;
        notifyObservers();
    }

    /* Implementing IObservable. Adding an observer */
    @Override
    public void addObserver(IObserver observer) {
        observers.add(observer);
    }

    /* Implementing IObservable. Notifiy all observers */
    @Override
    public void notifyObservers() {
        GameWorldProxy tmpProxy = new GameWorldProxy(this);
        Object randomObject = new Object();
        for (int i = 0; i < observers.size(); i++) {
            observers.get(i).update(tmpProxy, randomObject);
        }

    }
}
