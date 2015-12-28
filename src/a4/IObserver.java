/*
 * Interface Observer classes are going to use. 
 */
package a4;

/**
 *
 * @author Aben
 */
public interface IObserver {

    public void update(IObservable obs, Object arg);
}

