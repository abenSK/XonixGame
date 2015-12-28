/* This class is used to hid the way objects are stored.
 * 
 * It is able to iterate, add or remover to/from the collection.
 * Any outside class is unaware of how the game objects will be stored. 
 */
package a4;

import java.util.ArrayList;
import java.util.Iterator;

/**
 *
 * @author Aben
 */

public class GameObjectCollection implements ICollection {
 
    private ArrayList<GameObject> gameObjects;

    public GameObjectCollection() {
        gameObjects = new ArrayList<>();
    }

    @Override
    public Iterator iterator() {
        GameObjectIterator gameItr = new GameObjectIterator(gameObjects);
        return gameItr;

    }

    @Override
    public void add(GameObject o) {
        gameObjects.add(o);
    }

    private class GameObjectIterator implements Iterator<GameObject> {

        private int current = 0, next = 0;

        public GameObjectIterator(ArrayList<GameObject> go) {
            gameObjects = go;
        }


        @Override
        public boolean hasNext() {
            return next < gameObjects.size();
        }

        @Override
        public GameObject next() {
            current = next;
            GameObject currentObj = gameObjects.get(current);
            next += 1;
            return currentObj;

        }


        @Override
        public void remove() {
            gameObjects.remove(current);
        }
        
    }
    
}
