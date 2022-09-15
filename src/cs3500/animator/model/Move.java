package cs3500.animator.model;

import java.awt.Color;

/**
 * An animation that moves the shape across the screen.
 */
public class Move extends AbstractAnimation {

  private final float endX;
  private final float endY;

  /**
   * Constructs an animation for moving a shape.
   *
   * @param startTime when the shape starts moving.
   * @param endTime   when the shape stops moving.
   * @param endX      where the x-coordinate of the center of the shape stops.
   * @param endY      where the y-coordinate of the center of the shape stops.
   */
  public Move(int startTime, int endTime, float endX, float endY) {
    super(startTime, endTime);
    if (startTime == endTime) {
      throw new IllegalArgumentException("can't have equivalent start and end time");
    }

    this.endX = endX;
    this.endY = endY;
    this.animation = Animation.MOVE;
  }

  /**
   * Constructs an animation for moving a shape at a specific tempo.
   *
   * @param startTime when the shape starts moving.
   * @param endTime   when the shape stops moving.
   * @param endX      where the x-coordinate of the center of the shape stops.
   * @param endY      where the y-coordinate of the center of the shape stops.
   * @param tempo     the speed at which the shape should move.
   */

  public Move(int startTime, int endTime, float endX, float endY,
              int tempo) {
    super(startTime, endTime);
    if (endX < 0 || endY < 0) {
      throw new IllegalArgumentException("invalid negative arguments");
    }
    if (startTime == endTime) {
      throw new IllegalArgumentException("can't have equivalent start and end time");
    }
    if (tempo > 0) {
      this.tempo = tempo;
    } else {
      throw new IllegalArgumentException("Tempo cannot be less than 0");
    }
    this.endX = endX;
    this.endY = endY;
    this.animation = Animation.MOVE;
  }

  @Override
  public Color getNewColor() {
    throw new IllegalStateException("The shape was moved, so there is no new color");
  }

  @Override
  public float getEndWidth() {
    throw new IllegalStateException("The shape was moved, so there is no new end width");
  }

  @Override
  public float getEndHeight() {
    throw new IllegalStateException("The shape was moved, so there is no new end height");
  }

  /**
   * Return the final x position of the shape.
   *
   * @return the final x position.
   */
  public float getEndX() {
    return this.endX;
  }

  /**
   * Return the end y position of the shape.
   *
   * @return the end y position.
   */
  public float getEndY() {
    return this.endY;
  }

  @Override
  public int getEndR() {
    throw new IllegalArgumentException("The shape was moved so the color does not change");
  }

  @Override
  public int getEndG() {
    throw new IllegalArgumentException("The shape was moved so the color does not change");
  }

  @Override
  public int getEndB() {
    throw new IllegalArgumentException("The shape was moved so the color does not change");
  }
}
