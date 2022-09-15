package cs3500.animator.model;

import java.util.ArrayList;
import java.util.List;

/**
 * This interface represents different operations that an animator model must support to return
 * various aspects of its state. This interface does not provide any operations to mutate the
 * state of an animator model.
 */
public interface AnimatorModelState {

  /**
   * Get the number of shapes from a given model.
   *
   * @return the number of shapes.
   */
  int getNumberOfShapes();

  /**
   * Get the specific shape in a model, specified by its name.
   *
   * @param shapeName the name of the shape
   * @return the shape object
   * @throws IllegalStateException if the shape cannot be found in the model
   */
  IShape getShapeAt(String shapeName);

  /**
   * Get the number of animations for a specific shape.
   *
   * @param shapeName the name of the shape
   * @return the number of animations the shape has
   * @throws IllegalStateException if the shape does not exist
   */
  int getNumberOfAnimations(String shapeName);

  /**
   * Get all the animations for a particular shape.
   *
   * @param shapeName the name of the shape
   * @return a list containing all the animations in a particular shape
   */
  List<IAnimation> getAllAnimationsAt(String shapeName);

  /**
   * Get all the animations for a particular shape of a particular animation type.
   *
   * @param shapeName the name of the shape
   * @param animation the type of the animation
   * @return a list that contains only the specific animation type that is sorted by start time
   *         or an empty list
   */
  List<IAnimation> getAllAnimationsAt(String shapeName, Animation animation);

  /**
   * Get a sorted list of shapes from the model.
   *
   * @return the list of shapes sorted by the shape spawn time and an empty list if it is empty
   */
  ArrayList<IShape> getSortedShapes();

  /**
   * Return the width of the model's canvas.
   * @return the width of the canvas.
   */
  int getWidth();

  /**
   * Return the heigh of the model's canvas.
   * @return the height of the canvas.
   */
  int getHeight();

  /**
   * Return the current tick of the model.
   * @return the current tick.
   */
  int getTick();

  /**
   * Return the current tempo of the model.
   * @return the current tempo.
   */
  int getTempo();


  /**
   * Returns the total duration of the animations of the model.
   * @return the total duration of all animations in the model.
   */
  int getFinalTime();

}
