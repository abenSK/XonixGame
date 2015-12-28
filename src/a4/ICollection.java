/*
 *Interface to be used by GameObjectCollection class. 
 */
package a4;

import java.util.Iterator;

/**
 *
 * @author Aben
 */
public interface ICollection {
    
    public Iterator iterator();
    public void add(GameObject obj);

}
