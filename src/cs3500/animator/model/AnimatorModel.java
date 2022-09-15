package cs3500.animator.model;

import java.awt.Color;

/**
 * This is the interface of the AnimatorModel. It provides a multitude of functionalities where
 * the model can interact with shapes and animations. The model does not use any concrete
 * classes, only interfaces and their abstractions.
 */
public interface AnimatorModel extends AnimatorModelState {

  /**
   * Adds a shape to the model. The shape that can be added is restricted to various parameter
   * cases, such as the shape type must be a valid type, or the name must be a non-empty string.
   * Shapes are uniquely identified by the name, and if a shape of the same name is added to the
   * model, it will be rejected.
   *
   * @param shape  the type of the shape
   * @param name   the name of the shape
   * @param x      the x coordinate of the shape
   * @param y      the y coordinate of the shape
   * @param width  the width of the shape
   * @param height the height of the shape
   * @param color  the color of the shape
   * @param start  the appearance time of the shape
   * @param end    the disappearance time of the shape
   * @throws IllegalArgumentException if the shape type is invalid or if the shape has invalid
   *                                  arguments
   */
  void addShape(Shape shape, String name, float x, float y, float width, float height,
                Color color,
                int start, int end);

  /**
   * A secondary way of adding a shape to the model. A pre-created shape is passed into this
   * method to add into the model. If the model already contains the shape, it will be rejected.
   *
   * @param shape the shape object to add to the model
   * @throws IllegalArgumentException if the shape already exists
   */
  void addShape(IShape shape);

  /**
   * Adds an animation that is of type Move. It creates a Move object, checks if the input has
   * gaps and if not, it adds the move to a specific shape. A gap is defined a period of time
   * between animations. When an animation of the same type is added, it must have a start time
   * that is lined up with the most recent end time of the move animations already associated
   * with the shape. If there are none, the move animation should start from when the shape was
   * created.
   *
   * @param shapeName     the name of the shape
   * @param startTime     the start time of the shape
   * @param endTime       the end time of the shape
   * @param endX          the ending x coordinate after the move has finished
   * @param endY          the ending y coordinate after the move has finished
   * @throws IllegalStateException if there is a gap between input animations and the current
   *                               animations of the same type
   */
  void addMove(String shapeName, int startTime, int endTime,
               float endX, float endY);

  /**
   * Updates the state of the model at that current tick, including parameters within
   * for models and shapes.
   * @param tick the tick of the model.
   */

  void changeStateAtTick(int tick);

  /**
   * Resets the tick of the model.
   */
  void reset();

  /**
   * Increments the tick of the model.
   */
  void addTick();

  /**
   * Adds an animation that is of type Resize. It creates a Resize object, checks if the input has
   * gaps and if not, it adds the resize to a specific shape. A gap is defined a period of time
   * between animations. When an animation of the same type is added, it must have a start time
   * that is lined up with the most recent end time of the resize animations already associated with
   * the shape. If there are none, the resize animation should start from when the shape was
   * created.
   *
   * @param shapeName     the name of the shape
   * @param startTime     the start time of the animation
   * @param endTime       the end time of the animation
   * @param endWidth      the final width that the shape will be after the resize
   * @param endHeight     the final length that the shape will be after the resize
   * @throws IllegalStateException if there is a gap between input animations and the current
   *                               animations of the same type
   */
  void addResize(String shapeName, int startTime, int endTime,
                 float endWidth, float endHeight);

  /**
   * Adds an animation that is of type ColorChange. It creates a ColorChange object, checks if the
   * input has gaps and if not, it adds the color change  to a specific shape. A gap is defined a
   * period of time between animations. When an animation of the same type is added, it must have a
   * start time that is lined up with the most recent end time of the color change animations
   * already associated with the shape. If there are none, the color change animation should
   * start from when the shape was created.
   *
   * @param shapeName     the name of the shape
   * @param startTime     the start time of the animation
   * @param endTime       the end time of the animation
   * @param newColor      the new color that will be assigned to the shape
   * @throws IllegalStateException if there is a gap between input animations and the current
   *                               animations of the same type
   */
  void addColorChange(String shapeName,
                      int startTime, int endTime, Color newColor);

  /**
   * Removes the animation from the specific shape in the model if it exists. This method only
   * removes the last animation off of a specific shape, because of the constraints that are
   * defined on the way that animations are added.
   *
   * @param shape     the name of the shape
   * @throws IllegalStateException if the shape to remove from doesn't exist
   */
  void removeAnimation(String shape);

  /**
   * Removes a shape from the model if the specific shape exists.
   *
   * @param shape the name of the shape
   * @throws IllegalStateException if the shape is not in the model
   */
  void removeShape(String shape);

  /**
   * Sets the tempo of the model in seconds per tick. Default is 1 tick per second.
   * @param tempo the seconds per tick.
   */
  void setTempo(int tempo);

}
