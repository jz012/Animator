package cs3500.animator.model;

import java.awt.Color;

/**
 * The interface for a shape. It provides ways to access the fields inside a shape object.
 */
public interface IShape {

  /**
   * Get the x coordinate of a shape.
   *
   * @return the float representing an x coordinate
   */
  float getX();

  /**
   * Get the y coordinate of a shape.
   *
   * @return the float representing a y coordinate
   */
  float getY();

  /**
   * Get the width of a shape.
   *
   * @return the float representing the width of a shape
   */
  float getWidth();

  /**
   * Get the height of a shape.
   *
   * @return the float representing the height of a shape
   */
  float getHeight();

  /**
   * Get the color of a shape.
   *
   * @return color representation of the shape
   */
  Color getColor();


  /**
   * Get the shape spawn time.
   *
   * @return the time the shape spawns in
   */
  int getShapeSpawnTime();

  /**
   * Get the shape end time.
   *
   * @return the time the shape disappears
   */
  int getShapeEndTime();

  /**
   * Get the type of the shape object.
   *
   * @return a member of the shape enum
   */
  Shape getType();

  IShape resize(float endWidth, float endHeight);

  /**
   * Get the string representation of a shape.
   *
   * @return a string that represents the shape.
   */
  @Override
  String toString();

  /**
   * Return the string name of the shape.
   *
   * @return the name field of the shape.
   */
  String getName();

  /**
   * Get r value of the color of the shape.
   * @return the r value of the color.
   */
  int getR();

  /**
   * Get g value of the color of the shape.
   * @return the r value of the color.
   */
  int getG();

  /**
   * Get b value of the color of the shape.
   * @return the r value of the color.
   */
  int getB();
}
