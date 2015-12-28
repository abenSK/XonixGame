
package a4;

/**
 *
 * @author Aben
 */
public abstract interface ICollider {

    public abstract boolean collidesWith(ICollider paramICollider);

    public abstract void handleCollision(ICollider paramICollider);

    public abstract int getSize();

    public abstract void setSize();
}
