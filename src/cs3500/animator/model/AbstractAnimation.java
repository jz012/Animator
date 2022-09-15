package cs3500.animator.model;

import java.awt.Color;

/**
 * An abstract class that holds the things all animations should have. Includes when the
 * animation starts and ends, and what animation it actually is.
 */

abstract class AbstractAnimation implements IAnimation {
  protected final int startTime;
  protected final int endTime;
  protected int tempo;
  protected Animation animation;

  /**
   * The constructor for an abstract animation.
   * @param startTime the start time for the animation
   * @param endTime   the end time of the animation
   */
  AbstractAnimation(int startTime, int endTime) {
    if (startTime < 0 || endTime < 0) {
      throw new IllegalArgumentException("Can't have negative start or end times");
    }
    if (startTime == endTime) {
      throw new IllegalArgumentException("No instantaneous animations");
    }

    this.startTime = startTime;
    this.endTime = endTime;
    this.tempo = 1;
  }

  /**
   * Return the type of animation being performed.
   *
   * @return the animation type.
   */
  @Override
  public Animation getAnimation() {
    return this.animation;
  }

  /**
   * Return the starting time of the animation.
   *
   * @return the animation starting time.
   */
  @Override
  public int getStartTime() {
    return this.startTime;
  }

  /**
   * Return the end time of the animation.
   *
   * @return the animation ending time.
   */
  @Override
  public int getEndTime() {
    return this.endTime;
  }

  /**
   * Return the duration of how long the animation lasts.
   *
   * @return the duration of the animation.
   */
  @Override
  public int getDuration() {
    return (endTime / tempo) - (startTime / tempo);
  }

  @Override
  public String toString() {
    StringBuilder s = new StringBuilder();
    s.append(this.getAnimation()).append(" ");
    return s.toString();
  }

  @Override
  public int getTempo() {
    return this.tempo;
  }

  /**
   * Returns the new color the shape is changed to.
   *
   * @return the new color the shape is changed to.
   */
  public abstract Color getNewColor();

  /**
   * Returns the final end width of the resized shape.
   *
   * @return the end width.
   */
  public abstract float getEndWidth();

  /**
   * Returns the final end height of the resized shape.
   *
   * @return the end height.
   */
  public abstract float getEndHeight();

  /**
   * Return the final x position of the shape.
   *
   * @return the final x position.
   */
  public abstract float getEndX();

  /**
   * Return the end y position of the shape.
   *
   * @return the end y position.
   */
  public abstract float getEndY();

  /**
   * Return the end r value of the color of the shape.
   *
   * @return the end r value of the color.
   */
  public abstract int getEndR();

  /**
   * Return the end g value of the color of the shape.
   *
   * @return the end g value of the color.
   */
  public abstract int getEndG();

  /**
   * Return the end b value of the color of the shape.
   *
   * @return the end b value of the color.
   */
  public abstract int getEndB();

}