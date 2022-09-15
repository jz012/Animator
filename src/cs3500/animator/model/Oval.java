package cs3500.animator.model;

import java.awt.Color;

/**
 * A class that represents an oval.
 */
public class Oval extends AbstractShape {

  /**
   * The constructor for an oval.
   *
   * @param name      the name of the oval
   * @param x         the x coordinate
   * @param y         the y coordinate
   * @param width     the width of the oval
   * @param height    the height of the oval
   * @param color     the color of the oval
   * @param spawnTime the appearance time of the oval
   * @param endTime   the disappearance time of the oval
   */
  public Oval(String name, float x, float y, float width, float height, Color color,
              int spawnTime,
              int endTime) {
    super(name, x, y, width, height, color, spawnTime, endTime);
    this.shape = Shape.OVAL;
  }

  /**
   * The contructor to create a copy of an oval.
   * @param oval the oval to be copied from.
   */
  public Oval(Oval oval) {
    super(oval.name, oval.x, oval.y, oval.width, oval.height,
            oval.color, oval.spawnTime, oval.endTime);
    this.shape = oval.shape;
  }

}