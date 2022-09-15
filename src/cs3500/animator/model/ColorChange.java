package cs3500.animator.model;

import java.awt.Color;


/**
 * Class the handles the animation for changing the color of a shape.
 */
public class ColorChange extends AbstractAnimation {

  private final Color newColor;

  /**
   * Animates the color change of  shape given a new color and timings.
   *
   * @param startTime the start time in which the color begins to change
   * @param endTime   the end time when the color finishes changing
   * @param newColor  the new color that the shape assumes
   */
  public ColorChange(int startTime, int endTime, Color newColor) {
    super(startTime, endTime);
    if (newColor == null) {
      throw new IllegalArgumentException("invalid/null color");
    }

    this.newColor = newColor;
    this.animation = Animation.COLORCHANGE;

  }

  /**
   * Animates the color change of  shape given a new color and timings to a tempo.
   *
   * @param startTime the start time in which the color begins to change
   * @param endTime   the end time when the color finishes changing
   * @param newColor  the new color that the shape assumes
   * @param tempo the speed at which the animation occurs
   */
  public ColorChange(int startTime, int endTime, Color newColor, int tempo) {
    super(startTime, endTime);
    if (newColor == null) {
      throw new IllegalArgumentException("invalid/null color");
    }
    if (tempo > 0) {
      this.tempo = tempo;
    } else {
      throw new IllegalArgumentException("Tempo cannot be less than 0");
    }
    this.newColor = newColor;
    this.animation = Animation.COLORCHANGE;

  }

  /**
   * Returns the new color the shape is changed to.
   *
   * @return the new color the shape is changed to.
   */
  public Color getNewColor() {
    return this.newColor;
  }

  @Override
  public float getEndWidth() {
    throw new IllegalStateException("The shape underwent a color change, so there is no new end "
            + "width");
  }

  @Override
  public float getEndHeight() {
    throw new IllegalStateException("The shape underwent a color change, so there is no new end "
            + "height");
  }

  @Override
  public float getEndX() {
    throw new IllegalStateException("The shape underwent a color change, so there is no new end "
            + "x coordinate");
  }

  @Override
  public float getEndY() {
    throw new IllegalStateException("The shape underwent a color change, so there is no new end "
            + "y coordinate");
  }

  @Override
  public int getEndR() {
    return newColor.getRed();
  }

  @Override
  public int getEndG() {
    return newColor.getGreen();
  }

  @Override
  public int getEndB() {
    return newColor.getRed();
  }


}
