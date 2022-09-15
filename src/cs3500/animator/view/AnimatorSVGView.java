package cs3500.animator.view;

import java.io.IOException;

import cs3500.animator.model.Animation;
import cs3500.animator.model.AnimatorModelState;
import cs3500.animator.model.IAnimation;
import cs3500.animator.model.IShape;
import cs3500.animator.model.Shape;

/**
 * The SVG view for the animator. It is a type of abstract text view.
 * It displays the model, including its shapes and animations, to be readable
 * in svg format.
 */
public class AnimatorSVGView extends AbstractTextView {

  private final AnimatorModelState model;
  private final Appendable output;

  /**
   * The constructor for the SVG view.
   * It displays the model, including its shapes and animations, to be readable
   * in SVG format.
   *
   * @param model the model state of a model
   */
  public AnimatorSVGView(AnimatorModelState model) {
    if (model == null) {
      throw new IllegalArgumentException("model can't be null");
    }
    this.model = model;
    this.output = System.out;
    this.type = ViewType.SVG;
  }

  public void renderView() throws IOException {
    output.append(this.toString());
  }

  @Override
  public String toString() {
    return modeltoSVGString(model);
  }


  /**
   * A helper method to convert the model into a string formatted for SVG animations.
   *
   * @param m the model state
   * @return the SVG output in string format
   */
  private String modeltoSVGString(AnimatorModelState m) {
    StringBuilder svg = new StringBuilder();
    svg.append(
            "<svg width=\"" + m.getWidth() + "\" height=\"" + m.getHeight() + "\" " +
                    "version=\"1.1\"\n" +
                    "     xmlns=\"http://www.w3.org/2000/svg\">\n");
    for (IShape shape : m.getSortedShapes()) {
      if (shape.getType() == Shape.RECTANGLE) {
        svg.append(
                "<rect id=\"" + shape.getName() + "\" x=\"" + shape.getX() + "\" y=\"" +
                        shape.getY()
                        + "\" width=\"" + shape.getWidth() + "\" height=\"" + shape.getHeight()
                        + "\" fill=\"rgb(" + shape.getR() + "," + shape.getG() + "," +
                        shape.getB()
                        + ")\" visibility=\"visible\">\n");
      } else if (shape.getType() == Shape.OVAL) {
        svg.append("<ellipse id=\"" + shape.getName() + "\" cx=\"" + shape.getX() + "\" " +
                "cy=\"" + shape.getY()
                + "\" rx=\"" + shape.getWidth() + "\" ry=\"" + shape.getHeight()
                + "\" fill=\"rgb(" + shape.getR() + "," + shape.getG() + "," + shape.getB()
                + ")\" visibility=\"visible\">\n");
      } else {
        throw new IllegalArgumentException("invalid shape type");
      }
      int colorChange = 0;
      int resize = 0;
      int move = 0;
      for (int j = 0; j < m.getAllAnimationsAt(shape.getName()).size(); j++) {
        IAnimation animation = m.getAllAnimationsAt(shape.getName()).get(j);
        switch (animation.getAnimation()) {
          case COLORCHANGE:
            if (colorChange == 0) {
              svg.append(
                      "<animate attributeType=\"xml\" begin=\""
                              + (animation.getStartTime() * 1000) / m.getTempo() + "ms\" " +
                              "dur = \""
                              + (animation.getDuration() * 1000) / m.getTempo() + "ms\" " +
                              "attributeName=\"fill\" from=\"rgb("
                              + shape.getR() + "," + shape.getG() + "," + shape.getB()
                              + ")\" to=\"rgb(" + animation.getEndR() + ","
                              + animation.getEndG() + ","
                              + animation.getEndB()
                              + ")\" fill =\"freeze\"/>\n");
              colorChange++;
            } else if (colorChange < m.getAllAnimationsAt(shape.getName(),
                    Animation.COLORCHANGE).size()) {
              IAnimation prev =
                      m.getAllAnimationsAt(shape.getName(), Animation.COLORCHANGE)
                              .get(colorChange - 1);
              svg.append(
                      "<animate attributeType=\"xml\" begin=\""
                              + (animation.getStartTime() * 1000) / m.getTempo() + "ms\" " +
                              "dur = \""
                              + (animation.getDuration() * 1000) / m.getTempo() + "ms\" " +
                              "attributeName=\"fill\" from=\"rgb("
                              + prev.getEndR() + "," + prev.getEndG() + "," + prev.getEndB()
                              + ")\" to=\"rgb(" + animation.getEndR() + ","
                              + animation.getEndG() + ","
                              + animation.getEndB()
                              + ")\" fill =\"freeze\"/>\n");
              colorChange++;
            }
            break;
          case RESIZE:
            switch (shape.getType()) {
              case OVAL:
                if (resize == 0) {
                  svg.append(
                          "<animate attributeType=\"xml\" begin=\""
                                  + (animation.getStartTime() * 1000) / m.getTempo() +
                                  "ms\" dur = \""
                                  + (animation.getDuration() * 1000) / m.getTempo() +
                                  "ms\" attributeName=\"cx\" from=\""
                                  + shape.getWidth()
                                  + "\" to=\""
                                  + animation.getEndWidth()
                                  + "\" fill =\"freeze\"/>\n");
                  svg.append(
                          "<animate attributeType=\"xml\" begin=\""
                                  + (animation.getStartTime() * 1000) / m.getTempo() +
                                  "ms\" dur = \""
                                  + (animation.getDuration() * 1000) / m.getTempo() +
                                  "ms\" attributeName=\"cy\" from=\""
                                  + shape.getHeight()
                                  + "\" to=\""
                                  + animation.getEndHeight()
                                  + "\" fill =\"freeze\"/>\n");
                  resize++;
                } else if (resize < m.getAllAnimationsAt(shape.getName(),
                        Animation.RESIZE).size()) {
                  IAnimation prev =
                          m.getAllAnimationsAt(shape.getName(), Animation.RESIZE)
                                  .get(resize - 1);
                  svg.append(
                          "<animate attributeType=\"xml\" begin=\""
                                  + (animation.getStartTime() * 1000) / m.getTempo() +
                                  "ms\" dur = \""
                                  + (animation.getDuration() * 1000) / m.getTempo() +
                                  "ms\" attributeName=\"cx\" from=\""
                                  + prev.getEndWidth()
                                  + "\" to=\""
                                  + animation.getEndWidth()
                                  + "\" fill =\"freeze\"/>\n");
                  svg.append(
                          "<animate attributeType=\"xml\" begin=\""
                                  + (animation.getStartTime() * 1000) / m.getTempo() +
                                  "ms\" dur = \""
                                  + (animation.getDuration() * 1000) / m.getTempo() +
                                  "ms\" attributeName=\"cy\" from=\""
                                  + prev.getEndHeight()
                                  + "\" to=\""
                                  + animation.getEndHeight()
                                  + "\" fill =\"freeze\"/>\n");
                  resize++;
                }
                break;
              case RECTANGLE:
                if (resize == 0) {
                  svg.append(
                          "<animate attributeType=\"xml\" begin=\""
                                  + (animation.getStartTime() * 1000) / m.getTempo() +
                                  "ms\" dur = \""
                                  + (animation.getDuration() * 1000) / m.getTempo() +
                                  "ms\" attributeName=\"width\" from=\""
                                  + shape.getWidth()
                                  + "\" to=\""
                                  + animation.getEndWidth()
                                  + "\" fill =\"freeze\"/>\n");
                  svg.append(
                          "<animate attributeType=\"xml\" begin=\""
                                  + (animation.getStartTime() * 1000) / m.getTempo() +
                                  "ms\" dur = \""
                                  + (animation.getDuration() * 1000) / m.getTempo() +
                                  "ms\" attributeName=\"height\" from=\""
                                  + shape.getHeight()
                                  + "\" to=\""
                                  + animation.getEndHeight()
                                  + "\" fill =\"freeze\"/>\n");
                  resize++;
                } else if (resize < m.getAllAnimationsAt(shape.getName(),
                        Animation.RESIZE).size()) {
                  IAnimation prev =
                          m.getAllAnimationsAt(shape.getName(), Animation.RESIZE)
                                  .get(resize - 1);
                  svg.append(
                          "<animate attributeType=\"xml\" begin=\""
                                  + (animation.getStartTime() * 1000) / m.getTempo() + "ms\" " +
                                  "dur = \""
                                  + (animation.getDuration() * 1000) / m.getTempo() + "ms\" " +
                                  "attributeName=\"width\" from=\""
                                  + prev.getEndWidth()
                                  + "\" to=\""
                                  + animation.getEndWidth()
                                  + "\" fill =\"freeze\"/>\n");
                  svg.append(
                          "<animate attributeType=\"xml\" begin=\""
                                  + (animation.getStartTime() * 1000) / m.getTempo() + "ms\" " +
                                  "dur = \""
                                  + (animation.getDuration() * 1000) / m.getTempo() + "ms\" " +
                                  "attributeName=\"height\" from=\""
                                  + prev.getEndHeight()
                                  + "\" to=\""
                                  + animation.getEndHeight()
                                  + "\" fill =\"freeze\"/>\n");
                  resize++;
                }
                break;
              default:
                //none
            }
            break;
          case MOVE:
            if (move == 0) {
              svg.append(
                      "<animate attributeType=\"xml\" begin=\""
                              + (animation.getStartTime() * 1000) / m.getTempo() + "ms\" " +
                              "dur = \""
                              + (animation.getDuration() * 1000) / m.getTempo() + "ms\" " +
                              "attributeName=\"x\" from=\""
                              + shape.getX()
                              + "\" to=\""
                              + animation.getEndX()
                              + "\" fill =\"freeze\"/>\n");
              svg.append(
                      "<animate attributeType=\"xml\" begin=\""
                              + (animation.getStartTime() * 1000) / m.getTempo() + "ms\" " +
                              "dur = \""
                              + (animation.getDuration() * 1000) / m.getTempo() + "ms\" " +
                              "attributeName=\"y\" from=\""
                              + shape.getY()
                              + "\" to=\""
                              + animation.getEndY()
                              + "\" fill =\"freeze\"/>\n");
              move++;
            } else if (move < m.getAllAnimationsAt(shape.getName(),
                    Animation.MOVE).size()) {
              IAnimation prev =
                      m.getAllAnimationsAt(shape.getName(), Animation.MOVE)
                              .get(move - 1);
              svg.append(
                      "<animate attributeType=\"xml\" begin=\""
                              + (animation.getStartTime() * 1000) / m.getTempo() + "ms\" " +
                              "dur = \""
                              + (animation.getDuration() * 1000) / m.getTempo() + "ms\" " +
                              "attributeName=\"x\" from=\""
                              + prev.getEndX()
                              + "\" to=\""
                              + animation.getEndX()
                              + "\" fill =\"freeze\"/>\n");
              svg.append(
                      "<animate attributeType=\"xml\" begin=\""
                              + (animation.getStartTime() * 1000) / m.getTempo() + "ms\" " +
                              "dur = \""
                              + (animation.getDuration() * 1000) / m.getTempo() + "ms\" " +
                              "attributeName=\"y\" from=\""
                              + prev.getEndY()
                              + "\" to=\""
                              + animation.getEndY()
                              + "\" fill =\"freeze\"/>\n");
              move++;
            }
            break;
          default:
            throw new IllegalStateException("Not a valid type");
        }
      }
      if (shape.getType() == Shape.OVAL) {
        svg.append("\n</ellipse>\n\n");
      } else if (shape.getType() == Shape.RECTANGLE) {
        svg.append("\n</rect>\n\n");
      }
    }
    svg.append("\n</svg>\n\n");
    return svg.toString();
  }
}