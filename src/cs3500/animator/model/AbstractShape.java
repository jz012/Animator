package cs3500.animator.model;

import java.awt.Color;
import java.util.Arrays;
import java.util.Objects;

/**
 * A class that represents an abstract shape. It creates an extendable shape class in which more
 * shapes can be added. Common fields for all shape types are abstracted here.
 */
public abstract class AbstractShape implements IShape {

  protected final String name;
  protected float x;
  protected float y;
  protected float width;
  protected float height;
  protected Color color;
  protected final int spawnTime;
  protected final int endTime;
  protected Shape shape;

  /**
   * Constructs the abstract shape.
   *
   * @param name      the name of the shape
   * @param x         the x coordinate
   * @param y         the y coordinate
   * @param width     the width
   * @param height    the height
   * @param color     the color
   * @param spawnTime the appearance time
   * @param endTime   the end time
   * @throws IllegalArgumentException if input checking fails
   */
  protected AbstractShape(String name, float x, float y, float width, float height,
                          Color color,
                          int spawnTime, int endTime) throws IllegalArgumentException {
    if (width <= 0 || height <= 0) {
      throw new IllegalArgumentException("No negative/zero dimensions");
    }
    if (endTime <= spawnTime) {
      throw new IllegalArgumentException("Can't have a shape disappear before it appears");
    }
    if (endTime < 0 || spawnTime < 0) {
      throw new IllegalArgumentException("No negative start or end times");
    }
    if (color == null) {
      throw new IllegalArgumentException("Color cannot be null");
    }
    if (name.isEmpty() || name.isBlank()) {
      throw new IllegalArgumentException("String must have a name");
    }

    this.name = name;
    this.x = x;
    this.y = y;
    this.width = width;
    this.height = height;
    this.color = color;
    this.spawnTime = spawnTime;
    this.endTime = endTime;
  }

  @Override
  public float getX() {
    return this.x;
  }

  @Override
  public float getY() {
    return this.y;
  }

  @Override
  public float getWidth() {
    return this.width;
  }

  @Override
  public float getHeight() {
    return this.height;
  }

  @Override
  public Color getColor() {
    return this.color;
  }

  @Override
  public int getShapeSpawnTime() {
    return this.spawnTime;
  }

  @Override
  public int getShapeEndTime() {
    return this.endTime;
  }

  @Override
  public Shape getType() {
    return this.shape;
  }

  @Override
  public String getName() {
    return this.name;
  }

  /**
   * Moves a shape to a new position by mutating the fields and returning the shape.
   *
   * @param endX the final x coordinate
   * @param endY the final y coordinate
   * @return the new mutated shape
   */
  protected IShape moveTo(float endX, float endY) {
    this.x = endX;
    this.y = endY;
    return this;
  }

  /**
   * Changes the color of a shape to a new shape by mutating the field and returning the shape.
   *
   * @param endColor the color to change to
   * @return the new mutated shape
   */
  protected IShape changeColor(Color endColor) {
    this.color = endColor;
    return this;
  }

  @Override
  public IShape resize(float endWidth, float endHeight) {
    this.width = endWidth;
    this.height = endHeight;
    return this;
  }

  @Override
  public String toString() {
    StringBuilder s = new StringBuilder();
    s.append("Shape ").append(this.getName()).append(" ").append(this.getType())
            .append(" ").append("Spawns at:").append(this.getShapeSpawnTime() * 1000).append("ms")
            .append(" ").append("Ends at:").append(this.getShapeEndTime() * 1000).append("ms")
            .append("\n");
    s.append("Created with ").append(" X:").append(this.getX()).append(" Y:")
            .append(this.getY()).append(" Color:")
            .append(Arrays.asList(this.getColor().getRed(), this.getColor().getGreen(),
                    this.getColor().getBlue()))
            .append(". ")
            .append(" Width:").append(this.getWidth()).append(" Height:")
            .append(this.getHeight())
            .append("\n");
    return s.toString();
  }

  @Override
  public int getR() {
    return this.color.getRed();
  }

  @Override
  public int getB() {
    return this.color.getBlue();
  }

  @Override
  public int getG() {
    return this.color.getGreen();
  }

  @Override
  public boolean equals(Object o) {
    return (o instanceof AbstractShape)
            && ((AbstractShape) o).name.equals(name);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name);
  }

}
