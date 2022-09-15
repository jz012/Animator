package cs3500.animator.io;

import java.awt.Color;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

import cs3500.animator.model.AnimatorModel;
import cs3500.animator.model.ColorChange;
import cs3500.animator.model.IAnimation;
import cs3500.animator.model.IShape;
import cs3500.animator.model.Move;
import cs3500.animator.model.Oval;
import cs3500.animator.model.Rectangle;
import cs3500.animator.model.Resize;
import cs3500.animator.model.SimpleAnimatorModel;

/**
 * The builder class that builds the model from the parsed input from the AnimationFileReader. It
 * implements the TweenModelBuilder with the AnimatorModel interface.
 */
public class AnimationBuilder implements TweenModelBuilder<AnimatorModel> {
  private int width;
  private int height;
  private Map<String, IShape> shapes;
  private Map<IShape, PriorityQueue<IAnimation>> animationMap;
  AnimatorModel model;

  /**
   * The constructor for the AnimationBuilder.
   */
  public AnimationBuilder() {
    shapes = new HashMap<>();
    animationMap = new HashMap<>();
  }

  @Override
  public TweenModelBuilder<AnimatorModel> setBounds(int width, int height) {
    if (width <= 0) {
      throw new IllegalArgumentException("Cannot have width less than 0");
    }
    if (height <= 0) {
      throw new IllegalArgumentException("Cannot have height less than 0");
    }
    this.width = width;
    this.height = height;
    return this;
  }

  @Override
  public TweenModelBuilder<AnimatorModel> addOval(String name, float cx, float cy,
                                                  float xRadius, float yRadius, float red,
                                                  float green, float blue, int startOfLife,
                                                  int endOfLife) {
    if (shapes.containsKey(name)) {
      throw new IllegalArgumentException("Shape already exists");
    }
    IShape oval = new Oval(name, cx, cy, xRadius, yRadius, new Color((int) Math.round(red * 255),
            (int) Math.round(green * 255),
            (int) Math.round(blue * 255)),
            startOfLife, endOfLife);
    shapes.put(name, oval);
    animationMap.put(oval, new PriorityQueue<>(Comparator.comparingInt(IAnimation::getStartTime)));
    return this;
  }

  @Override
  public TweenModelBuilder<AnimatorModel> addRectangle(String name, float lx, float ly,
                                                       float width, float height, float red,
                                                       float green, float blue, int startOfLife,
                                                       int endOfLife) {
    if (shapes.containsKey(name)) {
      throw new IllegalArgumentException("Shape already exists");
    }
    IShape rectangle = new Rectangle(name, lx, ly, width, height,
            new Color((int) Math.round(red * 255), (int) Math.round(green * 255),
                    (int) Math.round(blue * 255)),
            startOfLife, endOfLife);
    shapes.put(name, rectangle);
    animationMap.put(rectangle,
            new PriorityQueue<>(Comparator.comparingInt(IAnimation::getStartTime)));
    return this;
  }

  @Override
  public TweenModelBuilder<AnimatorModel> addMove(String name, float moveFromX, float moveFromY,
                                                  float moveToX, float moveToY, int startTime,
                                                  int endTime) {
    if (!shapes.containsKey(name)) {
      throw new IllegalArgumentException("Shape does not exist");
    }
    IAnimation move = new Move(startTime, endTime, moveToX, moveToY);
    animationMap.get(shapes.get(name)).add(move);
    return this;
  }

  @Override
  public TweenModelBuilder<AnimatorModel> addColorChange(String name, float oldR, float oldG,
                                                         float oldB, float newR, float newG,
                                                         float newB, int startTime, int endTime) {
    if (!shapes.containsKey(name)) {
      throw new IllegalArgumentException("Shape does not exist");
    }
    IAnimation colorchange = new ColorChange(startTime, endTime,
            new Color((int) Math.round(newR * 255),
                    (int) Math.round(newG * 255),
                    (int) Math.round(newB * 255)));
    animationMap.get(shapes.get(name)).add(colorchange);
    return this;
  }

  @Override
  public TweenModelBuilder<AnimatorModel> addScaleToChange(String name, float fromSx,
                                                           float fromSy, float toSx, float toSy,
                                                           int startTime, int endTime) {
    if (!shapes.containsKey(name)) {
      throw new IllegalArgumentException("Shape does not exist");
    }
    IAnimation resize = new Resize(startTime, endTime, toSx, toSy);
    animationMap.get(shapes.get(name)).add(resize);
    return this;
  }

  @Override
  public AnimatorModel build() {
    model = new SimpleAnimatorModel();
    if (width > 0 && height > 0) {
      model = new SimpleAnimatorModel(width, height);
    }
    for (IShape shape : shapes.values()) {
      model.addShape(shape);
    }
    for (IShape shape : animationMap.keySet()) {
      for (IAnimation animation : animationMap.get(shape)) {
        switch (animation.getAnimation()) {
          case RESIZE:
            model.addResize(shape.getName(), animation.getStartTime(), animation.getEndTime(),
                    animation.getEndWidth(), animation.getEndHeight());
            break;
          case COLORCHANGE:
            model.addColorChange(shape.getName(), animation.getStartTime(),
                    animation.getEndTime(),
                    new Color((int) Math.round(animation.getEndR()),
                            (int) Math.round(animation.getEndG()),
                            (int) Math.round(animation.getEndB())));
            break;
          case MOVE:
            model.addMove(shape.getName(), animation.getStartTime(),
                    animation.getEndTime(), animation.getEndX(), animation.getEndY());
            break;
          default:
            throw new IllegalArgumentException("Can't add animation of invalid type");
        }
      }
    }
    return model;
  }
}
