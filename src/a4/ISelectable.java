/*
 *
 */
package a4;

import java.awt.Graphics;
import java.awt.Graphics2D;

/**
 *
 * @author Aben
 */
public abstract interface ISelectable
{
  public abstract void setSelected(boolean paramBoolean);
  
  public abstract boolean isSelected();
  
  public abstract boolean contains(double param1, double param2);
  
  public abstract void draw(Graphics2D g);
}
