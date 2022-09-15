package cs3500.animator.view;


import java.util.Arrays;
import java.io.IOException;

import cs3500.animator.model.Animation;
import cs3500.animator.model.AnimatorModelState;
import cs3500.animator.model.IAnimation;
import cs3500.animator.model.IShape;

/**
 * Class that returns the present state of the animation as a string.
 * The general format is shown below, starting with the creation of the shape.
 * The general  for string-view is shown below, starting with the creation of the shape.
 * Shapes are ordered by their creation time. Times are shown in actual seconds,
 * dependent on the tempo in seconds per tick.
 *
 * <p>"Shape" ShapeName ShapeType "Spawns at:" SpawnTime "Ends at:" EndTime.
 *
 * <p>(below this would be the info for the position, color, and dimensions for the shape).
 * "Created with X:" X-Value "Y:" Y-Value "Color:" Color "Width:" Width "Height" Height.
 *
 * <p>(below this shape would be listed the animations for that shape by order of spawntime).
 * (The general format follows below).
 * AnimationType AnimationName "Start:" StartTime "End:" EndTime [Relevant Info].
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
public class AnimatorTextView extends AbstractTextView {

  private final AnimatorModelState model;
  private final Appendable output;

  /**
   * The constructor that takes in an animator model and returns its string representation.
   *
   * @param model the animator model to be represented as a string.
   */
  public AnimatorTextView(AnimatorModelState model) {
    if (model == null) {
      throw new IllegalArgumentException("model can't be null");
    }
    this.model = model;
    this.output = System.out;
    this.type = ViewType.Text;
  }

  @Override
  public String toString() {
    return getString(model);
  }

  /**
   * The helper method to turn the model state into legible output.
   *
   * @param m the model state
   * @return the animations in order in a legible output string
   */
  private String getString(AnimatorModelState m) {
    StringBuilder s = new StringBuilder();
    int i = 0;
    s.append("canvas " + m.getWidth() + " " + m.getHeight() + "\n");
    for (IShape shape : m.getSortedShapes()) {
      s.append(shape.toString()).append("\n");
      int colorChange = 0;
      int resize = 0;
      int move = 0;
      for (int j = 0; j < m.getAllAnimationsAt(shape.getName()).size(); j++) {
        IAnimation animation = m.getAllAnimationsAt(shape.getName()).get(j);
        s.append(animation.toString());
        switch (animation.getAnimation()) {
          case COLORCHANGE:
            if (colorChange == 0) {
              s.append(" Start:")
                      .append(animation.getStartTime() / m.getTempo()).append(" Color:")
                      .append(Arrays.asList(shape.getColor().getRed(),
                              shape.getColor().getGreen(),
                              shape.getColor().getBlue())).append(" Ends: ")
                      .append(animation.getEndTime() / m.getTempo())
                      .append(" Color:").append(Arrays.asList(animation.getNewColor().getRed(),
                              animation.getNewColor().getGreen(),
                              animation.getNewColor().getBlue()))
                      .append("\n");
              colorChange++;
            } else if (colorChange < m.getAllAnimationsAt(shape.getName(),
                    Animation.COLORCHANGE).size()) {
              IAnimation prev =
                      m.getAllAnimationsAt(shape.getName(), Animation.COLORCHANGE)
                              .get(colorChange - 1);
              s.append(" Start:")
                      .append(animation.getStartTime() / m.getTempo())
                      .append(" Color:").append(Arrays.asList(prev.getNewColor().getRed(),
                              prev.getNewColor().getGreen(),
                              prev.getNewColor().getBlue())).append(" Ends: ")
                      .append(animation.getEndTime() / m.getTempo())
                      .append(" Color:").append(Arrays.asList(animation.getNewColor()
                                      .getRed(), animation.getNewColor().getGreen(),
                              animation.getNewColor().getBlue()))
                      .append("\n");
              colorChange++;
            }

            break;
          case RESIZE:
            if (resize == 0) {
              s.append(" Start:")
                      .append(animation.getStartTime() / m.getTempo()).append(" Width:")
                      .append(shape.getWidth()).append(" Height:")
                      .append(shape.getHeight())
                      .append(" Ends:")
                      .append(animation.getEndTime() / m.getTempo()).append(" Width:")
                      .append(animation.getEndWidth()).append(" Height:")
                      .append(animation.getEndHeight()).append("\n");
              resize++;
            } else if (resize < m.getAllAnimationsAt(shape.getName(),
                    Animation.RESIZE).size()) {
              IAnimation prev =
                      m.getAllAnimationsAt(shape.getName(), Animation.RESIZE).get(resize - 1);
              s.append(" Start:")
                      .append(animation.getStartTime() / m.getTempo()).append(" Width:")
                      .append(prev.getEndWidth()).append(" Height:")
                      .append(prev.getEndHeight())
                      .append(" Ends:")
                      .append(animation.getEndTime() / m.getTempo()).append(" Width:")
                      .append(animation.getEndWidth()).append(" Height:")
                      .append(animation.getEndHeight()).append("\n");
              resize++;
            }
            break;
          case MOVE:
            if (move == 0) {
              s.append(" Start:")
                      .append(animation.getStartTime() / m.getTempo())
                      .append(" X:").append(shape.getX()).append(" Y:").append(shape.getY())
                      .append(" Ends:")
                      .append(animation.getEndTime() / m.getTempo())
                      .append(" X:").append(animation.getEndX()).append(" Y:")
                      .append(animation.getEndY())
                      .append("\n");
              move++;
            } else if (move < m.getAllAnimationsAt(shape.getName(), Animation.MOVE).size()) {
              IAnimation prev =
                      m.getAllAnimationsAt(shape.getName(), Animation.MOVE).get(move - 1);
              s.append(" Start:")
                      .append(animation.getStartTime() / m.getTempo())
                      .append(" X:").append(prev.getEndX()).append(" Y:").append(prev.getEndY())
                      .append(" Ends:")
                      .append(animation.getEndTime() / m.getTempo())
                      .append(" X:").append(animation.getEndX()).append(" Y:")
                      .append(animation.getEndY())
                      .append("\n");
              move++;
            }

            break;
          default:
            throw new IllegalStateException("Not a valid type");
        }
        if (i++ == m.getAllAnimationsAt(shape.getName()).size() - 1) {
          s.append("\n");
        }
      }
    }
    return s.toString();
  }

  @Override
  public void renderView() throws IOException {
    this.output.append(toString());
  }
}


