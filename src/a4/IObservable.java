/*
 * Interface that Observable classes are going to use. 
 */
package a4;

/**
 *
 * @author Aben
 */
public interface IObservable {

    public void addObserver(IObserver observer);

    public void notifyObservers();
}
