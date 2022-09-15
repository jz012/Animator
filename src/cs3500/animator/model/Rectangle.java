package cs3500.animator.model;

import java.awt.Color;

/**
 * A class that represents a rectangle.
 */
public class Rectangle extends AbstractShape {

  /**
   * The constructor for a rectangle.
   *
   * @param name      the name of the rectangle
   * @param x         the x coordinate
   * @param y         the y coordinate
   * @param width     the width of the rectangle
   * @param height    the height of the rectangle
   * @param color     the color of the rectangle
   * @param spawnTime the appearance time of the rectangle
   * @param endTime   the disappearance time of the rectangle
   */
  public Rectangle(String name, float x, float y, float width, float height, Color color,
                   int spawnTime,
                   int endTime) {
    super(name, x, y, width, height, color, spawnTime, endTime);
    this.shape = Shape.RECTANGLE;
  }

  /**
   * The constructor to create a copy of a rectangle.
   * @param rectangle the oval to be copied from.
   */

  public Rectangle(Rectangle rectangle) {
    super(rectangle.name, rectangle.x, rectangle.y, rectangle.width, rectangle.height,
            rectangle.color, rectangle.spawnTime, rectangle.endTime);
    this.shape = rectangle.shape;
  }

}