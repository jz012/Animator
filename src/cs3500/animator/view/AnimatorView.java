package cs3500.animator.view;


import java.io.IOException;

/**
 * Interface that returns the present state of the animation in a view.
 * Can only currently show the animation as a string.
 * The general  for string-view is shown below, starting with the creation of the shape.
 * Shapes are ordered by their creation time.
 *
 * <p>"Shape" ShapeName ShapeType "Spawns at:" SpawnTime "Ends at:" EndTime.
 *
 * <p>(below this would be the info for the position, color, and dimensions for the shape).
 * "Created with X:" X-Value "Y:" Y-Value "Color:" Color "Width:" Width "Height" Height.
 *
 * <p>(below this shape would be listed the animations for that shape by order of spawntime).
 * (The general format follows below).
 * AnimationType AnimationName "Start:" StartTime [Initial Relevant Info]
 * "End:" EndTime [End Relevant Info].
 * (Relevant info differs on the animation type).
 * For Move:
 * Relevant Info: "X:" X-Value "Y:" Y-Value.
 * For ColorChange:
 * Relevant Info: "Color:" RGBValues in an array.
 * For Resize:
 * Relevant Info: "Width:" Width "Height:" Height.
 *
 * <p>Then a new-line and the next shape and its animations.
 * Ends on a new line.
 */

public interface AnimatorView {
  /**
   * Returns the string representation of the model.
   *
   * @return the string representation of the model.
   */
  String toString();

  /**
   * Renders the view of the model according to the view type.
   *
   * @throws IOException if view is not able to be rendered properly
   */
  void renderView() throws IOException;

}
