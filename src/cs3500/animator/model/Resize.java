package cs3500.animator.model;

import java.awt.Color;

/**
 * Resizes a shape based on given dimensions and times.
 */
public class Resize extends AbstractAnimation {

  private final float endWidth;
  private final float endHeight;

  /**
   * Constructs an animation for resizing a shape.
   *
   * @param startTime when the shape starts moving.
   * @param endTime   when the shape stops moving.
   * @param endWidth  the ending width of the shape after the shape has been resized.
   * @param endHeight the ending width of the shape after the shape has been resized.
   */
  public Resize(int startTime, int endTime, float endWidth,
               float endHeight) {
    super(startTime, endTime);

    if (endWidth < 0 || endHeight < 0) {
      throw new IllegalArgumentException("negative dimensions invalid");
    }
    this.endWidth = endWidth;
    this.endHeight = endHeight;
    this.animation = Animation.RESIZE;
  }

  /**
   * Constructs an animation for resizing a shape at a tempo.
   *
   * @param startTime when the shape starts moving.
   * @param endTime   when the shape stops moving.
   * @param endWidth  the ending width of the shape after the shape has been resized.
   * @param endHeight the ending width of the shape after the shape has been resized.
   * @tempo tempo     the speed at which an animation resizes.
   */

  public Resize(int startTime, int endTime, float endWidth,
                float endHeight, int tempo) {
    super(startTime, endTime);

    if (endWidth < 0 || endHeight < 0) {
      throw new IllegalArgumentException("negative dimensions invalid");
    }
    if (tempo > 0) {
      this.tempo = tempo;
    } else {
      throw new IllegalArgumentException("Tempo cannot be less than 0");
    }
    this.endWidth = endWidth;
    this.endHeight = endHeight;
    this.animation = Animation.RESIZE;
  }

  @Override
  public Color getNewColor() {
    throw new IllegalStateException("The shape was resized, so there is no new color");
  }

  /**
   * Returns the final end width of the resized shape.
   *
   * @return the end width.
   */
  public float getEndWidth() {
    return this.endWidth;
  }

  /**
   * Returns the final end height of the resized shape.
   *
   * @return the end height.
   */
  public float getEndHeight() {
    return this.endHeight;
  }

  @Override
  public float getEndX() {
    throw new IllegalStateException("The shape was resized, so there is no new x position");
  }

  @Override
  public float getEndY() {
    throw new IllegalStateException("The shape was resized, so there is no new y position");
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
