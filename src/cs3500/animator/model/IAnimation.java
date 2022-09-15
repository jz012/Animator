package cs3500.animator.model;

import java.awt.Color;

/**
 * The interface for methods an animation should have. An empty animation is defined as an
 * animation that has the same start and end time. Animations are uniquely identified by their name.
 */
public interface IAnimation {

  /**
   * Return the type of animation being performed.
   *
   * @return the animation type.
   */
  Animation getAnimation();

  /**
   * Return the starting time of the animation.
   *
   * @return the animation starting time.
   */
  int getStartTime();

  /**
   * Return the end time of the animation.
   *
   * @return the animation ending time.
   */
  int getEndTime();

  /**
   * Return the duration of how long the animation lasts.
   *
   * @return the duration of the animation.
   */

  int getDuration();

  /**
   * Describes the animation in verbal terms.
   *
   * @return the description of the animation in verbal terms,
   *          including the time as well as what type of animation it is.
   */

  @Override
  String toString();


  /**
   * Returns the tempo of the animation.
   * @return the tempo of the animation.
   */
  int getTempo();

  /**
  * Returns the new color the shape is changed to.
  *
  * @return the new color the shape is changed to.
  */
  Color getNewColor();

  /**
  * Returns the final end width of the resized shape.
  *
  * @return the end width.
  */
  float getEndWidth();

  /**
  * Returns the final end height of the resized shape.
  *
  * @return the end height.
  */
  float getEndHeight();

  /**
  * Return the final x position of the shape.
  *
  * @return the final x position.
  */
  float getEndX();

  /**
  * Return the end y position of the shape.
  *
  * @return the end y position.
  */
  float getEndY();

  /**
   * Gets the end r value of the animation color.
   * @return the end r value of the animation color.
   */

  int getEndR();

  /**
   * Gets the end g value of the animation color.
   * @return the end g value of the animation color.
   */
  int getEndG();

  /**
   * Gets the end b value of the animation color.
   * @return the end b value of the animation color.
   */

  int getEndB();
}
