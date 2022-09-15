package cs3500.animator.model;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.stream.Collectors;

/**
 * A class that represents a SimpleAnimatorModel. It implements AnimatorModel with the
 * appropriate implementations for interface methods.
 */
public class SimpleAnimatorModel implements AnimatorModel {
  private int tick;
  protected Map<String, IShape> shapes;
  protected Map<IShape, Color> initColors;
  protected Map<IShape, PriorityQueue<IAnimation>> animationMap;
  private final int width;
  private final int height;
  private int tempo;

  /**
   * The constructor for the SimpleAnimatorModel with built-in canvas width and height.
   */
  public SimpleAnimatorModel() {
    this.shapes = new HashMap<>();
    this.animationMap = new HashMap<>();
    this.initColors = new HashMap<>();
    this.tick = 1;
    this.width = 500;
    this.height = 500;
    this.tempo = 1;
  }

  /**
   * the constructor for an animator model for an input width and height of a canvas.
   *
   * @param width  of the canvas
   * @param height of the canvas.
   */

  public SimpleAnimatorModel(int width, int height) {
    if (width <= 0) {
      throw new IllegalArgumentException("Width of animation screen cannot be negative.");
    }
    if (height <= 0) {
      throw new IllegalArgumentException("Height of animation screen cannot be negative.");
    }
    this.tick = 1;
    this.shapes = new HashMap<>();
    this.animationMap = new HashMap<>();
    this.initColors = new HashMap<>();
    this.width = width;
    this.height = height;
    this.tempo = 1;
  }


  @Override
  public void addShape(Shape shape, String name, float x, float y, float width,
                       float height, Color color, int start, int end) {
    if (width < 0 || height < 0 || shape == null || name.isEmpty()
            || name.isBlank()) {
      throw new IllegalArgumentException("negative or invalid shape arguments");
    }
    switch (shape) {
      case OVAL:
        Oval oval = new Oval(name, x, y, width, height, color, start, end);
        addShape(oval);
        break;
      case RECTANGLE:
        Rectangle rectangle = new Rectangle(name, x, y, width, height, color, start, end);
        addShape(rectangle);
        break;
      default:
        throw new IllegalArgumentException("invalid shape type");
    }
  }

  @Override
  public void addShape(IShape shape) {
    if (shapes.containsKey(shape.getName())) {
      throw new IllegalArgumentException("This shape already exists");
    }
    shapes.put(shape.getName(), shape);
    initColors.put(shape, shape.getColor());
    animationMap.put(shape, new PriorityQueue<>(Comparator.comparingInt(IAnimation::getStartTime)));
  }

  @Override
  public void addMove(String shapeName, int startTime, int endTime,
                      float endX, float endY) {
    Move move = new Move(startTime, endTime, endX, endY);
    gapChecker(shapeName, startTime, endTime, move);
  }

  /**
   * Resets the color of a given shape. It will create a new shape of the same name and initial
   * parameters but with a different color.
   * @param shape the shape to update
   * @param animations the queue of animations to preserve
   */
  private void resetColor(IShape shape, PriorityQueue<IAnimation> animations) {
    switch (shape.getType()) {
      case OVAL:
        IShape oval = new Oval(shape.getName(), shape.getX(), shape.getY(), shape.getWidth(),
                shape.getHeight(), initColors.get(shape), shape.getShapeSpawnTime(),
                shape.getShapeEndTime());
        shapes.replace(shape.getName(), oval);
        animationMap.replace(oval, animations);
        break;
      case RECTANGLE:
        IShape rectangle = new Rectangle(shape.getName(), shape.getX(), shape.getY(),
                shape.getWidth(), shape.getHeight(), initColors.get(shape),
                shape.getShapeSpawnTime(), shape.getShapeEndTime());
        shapes.replace(shape.getName(), rectangle);
        animationMap.replace(rectangle, animations);
        break;
      default:
        throw new IllegalArgumentException("Input shape is not of type that is implemented in the"
                + " model");
    }
  }

  @Override
  public void changeStateAtTick(int tick) {
    if (tick == 1) {
      for (IShape shape : animationMap.keySet()) {
        PriorityQueue<IAnimation> animations =
                new PriorityQueue<>(animationMap.get(shape));
        resetColor(shape, animations);
      }
    }
    for (IShape shape : animationMap.keySet()) {
      PriorityQueue<IAnimation> currentAnimations =
              new PriorityQueue<>(animationMap.get(shape));
      for (IAnimation animation : animationMap.get(shape)) {
        if (animation.getStartTime() <= tick && animation.getEndTime() >= tick) {
          switch (animation.getAnimation()) {
            case RESIZE:
              resizer(shape.getName(), tick, animation, currentAnimations);
              break;
            case COLORCHANGE:
              colorChanger(shape.getName(), tick, animation, currentAnimations);
              break;
            case MOVE:
              mover(shape.getName(), tick, animation, currentAnimations);
              break;
            default:
              throw new IllegalStateException("The requested animation type is not found");
          }
        }
      }
    }
  }

  @Override
  public void reset() {
    this.tick = 1;
  }

  @Override
  public void addTick() {
    tick++;
  }

  /**
   * Returns the value of a field at a given tick based off of linear
   * interpolation of the duration of an animation
   * using f(t) = a((tb - t)/(tb-ta)) + b((t-ta -t) / (tb-ta)) where t is the tick and
   * a and b are the values of the field at that the given tick.
   *
   * @param start     the starting value of the animation
   * @param end       the ending value of the animation
   * @param animation the animation that requires the intermediate values of its shape
   *                  to be interpolated.
   * @param tick      the current tick.
   * @return the value of the field at that tick.
   */
  private float interpolate(float start, float end, IAnimation animation, int tick) {
    int endTime = animation.getEndTime();
    int startTime = animation.getStartTime();
    return (start * ((float) (endTime - tick) / (endTime
            - startTime)) + (end
            * ((float) (tick - startTime) / (endTime - startTime))));
  }

  /**
   * Replaces a shape in the model when it undergoes a resize animation.
   * Creates a new shape with the corresponding width and height values at the given
   * tick and replaces it in both the shape and animation maps.
   *
   * @param shapeName the name of the shape to be replaced.
   * @param tick      the current tick.
   * @param animation the resize animation to be undergone.
   */
  private void resizer(String shapeName, int tick, IAnimation animation,
                       PriorityQueue<IAnimation> animationQ) {
    float widthAtTick;
    float heightAtTick;
    switch (shapes.get(shapeName).getType()) {
      case RECTANGLE:
        IShape rectangle = new Rectangle((Rectangle) shapes.get(shapeName));
        widthAtTick =
                interpolate(rectangle.getWidth(), animation.getEndWidth(), animation, tick);
        heightAtTick =
                interpolate(rectangle.getHeight(), animation.getEndHeight(), animation, tick);
        IShape brandNewRectangle = new Rectangle(shapeName, rectangle.getX(), rectangle.getY(),
                widthAtTick,
                heightAtTick, rectangle.getColor(),
                rectangle.getShapeSpawnTime(),
                rectangle.getShapeEndTime());
        shapes.replace(shapeName, brandNewRectangle);
        animationMap.replace(brandNewRectangle, animationQ);
        break;
      case OVAL:
        IShape oval = new Oval((Oval) shapes.get(shapeName));
        widthAtTick =
                interpolate(oval.getWidth(), animation.getEndWidth(), animation, tick);
        heightAtTick =
                interpolate(oval.getHeight(), animation.getEndHeight(), animation, tick);
        IShape brandNewOval = new Oval(shapeName, oval.getX(), oval.getY(),
                widthAtTick,
                heightAtTick, oval.getColor(),
                oval.getShapeSpawnTime(),
                oval.getShapeEndTime());
        shapes.replace(shapeName, brandNewOval);
        animationMap.replace(brandNewOval, animationQ);
        break;
      default:
        break;
    }
  }

  /**
   * Replaces a shape in the model when it undergoes a move animation.
   * Creates a new shape with the corresponding x and height y at the given
   * tick and replaces it in both the shape and animation maps.
   *
   * @param shapeName the name of the shape to be replaced.
   * @param tick      the current tick.
   * @param animation the move animation to be undergone.
   */
  private void mover(String shapeName, int tick, IAnimation animation,
                     PriorityQueue<IAnimation> animationQ) {
    float xAtTick;
    float yAtTick;
    switch (shapes.get(shapeName).getType()) {
      case RECTANGLE:
        IShape rectangle = new Rectangle((Rectangle) shapes.get(shapeName));
        xAtTick =
                interpolate(rectangle.getX(), animation.getEndX(), animation, tick);
        yAtTick =
                interpolate(rectangle.getY(), animation.getEndY(), animation, tick);
        IShape brandNewRectangle = new Rectangle(shapeName, xAtTick, yAtTick,
                rectangle.getWidth(),
                rectangle.getHeight(), rectangle.getColor(),
                rectangle.getShapeSpawnTime(),
                rectangle.getShapeEndTime());
        shapes.replace(shapeName, brandNewRectangle);
        animationMap.replace(brandNewRectangle, animationQ);
        break;
      case OVAL:
        IShape oval = new Oval((Oval) shapes.get(shapeName));
        xAtTick =
                interpolate(oval.getX(), animation.getEndX(), animation, tick);
        yAtTick =
                interpolate(oval.getY(), animation.getEndY(), animation, tick);
        IShape brandNewOval = new Rectangle(shapeName, xAtTick, yAtTick,
                oval.getWidth(),
                oval.getHeight(), oval.getColor(),
                oval.getShapeSpawnTime(),
                oval.getShapeEndTime());
        shapes.replace(shapeName, brandNewOval);
        animationMap.replace(brandNewOval, animationQ);
        break;
      default:
        break;
    }
  }

  /**
   * Replaces a shape in the model when it undergoes a colorchange animation.
   * Creates a new shape with the corresponding rgb values at the given
   * tick and replaces it in both the shape and animation maps.
   *
   * @param shapeName the name of the shape to be replaced.
   * @param tick      the current tick.
   * @param animation the colorchange animation to be undergone.
   */
  private void colorChanger(String shapeName, int tick, IAnimation animation,
                            PriorityQueue<IAnimation> animationQ) {
    float rAtTick;
    float gAtTick;
    float bAtTick;
    switch (shapes.get(shapeName).getType()) {
      case RECTANGLE:
        IShape rectangle = new Rectangle((Rectangle) shapes.get(shapeName));
        int startR = rectangle.getR();
        int endR = animation.getEndR();
        int startG = rectangle.getG();
        int endG = animation.getEndG();
        int startB = rectangle.getB();
        int endB = animation.getEndB();
        rAtTick =
                interpolate(startR, endR, animation, tick);
        gAtTick =
                interpolate(startG, endG, animation, tick);
        bAtTick =
                interpolate(startB, endB, animation, tick);


        Color colorAtTick =
                new Color((int) rAtTick, (int) gAtTick, (int) bAtTick);
        IShape brandNewRectangle = new Rectangle(shapeName, rectangle.getX(), rectangle.getY(),
                rectangle.getWidth(),
                rectangle.getHeight(), colorAtTick,
                rectangle.getShapeSpawnTime(),
                rectangle.getShapeEndTime());
        shapes.replace(shapeName, brandNewRectangle);
        animationMap.replace(brandNewRectangle, animationQ);
        break;
      case OVAL:
        IShape oval = new Oval((Oval) shapes.get(shapeName));
        rAtTick =
                interpolate(oval.getColor().getRed(), animation.getEndR(), animation, tick);
        gAtTick =
                interpolate(oval.getColor().getBlue(), animation.getEndG(), animation, tick);
        bAtTick =
                interpolate(oval.getColor().getRed(), animation.getEndB(), animation, tick);
        colorAtTick = new Color((int) rAtTick, (int) gAtTick, (int) bAtTick);
        IShape brandNewOval = new Rectangle(shapeName, oval.getX(), oval.getY(),
                oval.getWidth(),
                oval.getHeight(), colorAtTick,
                oval.getShapeSpawnTime(),
                oval.getShapeEndTime());
        shapes.replace(shapeName, brandNewOval);
        animationMap.replace(brandNewOval, animationQ);
        break;
      default:
        break;
    }
  }

  /**
   * Checks if there are gaps between animations. This is important because when adding an
   * animation of the same type, the latest end time must be equal to the start time of the new
   * animation. This method will also fill in spaces between gaps if they exist with still
   * animations, eg. animations that move the same point to the same point or change the same
   * color to the same color.
   *
   * @param shapeName the name of the shape
   * @param startTime the start time of the animation
   * @param endTime   the end time of the animation
   * @param animation the actual animation object
   * @throws IllegalStateException if there is a gap between the input animation and animations
   *                               already present
   */
  private void gapChecker(String shapeName, int startTime,
                          int endTime, IAnimation animation) {
    List<IAnimation> animations =
            new ArrayList<>(getAllAnimationsAt(shapeName, animation.getAnimation()));
    int numberOfMoves = animations.size();
    if (!animations.isEmpty() && (animations.get(numberOfMoves - 1).getEndTime() == startTime
            || (animations.get(numberOfMoves - 1).getEndTime() + 1 == startTime))) {
      addAnimationToShape(shapeName, startTime, endTime, animation);
    } else if (animations.isEmpty()) {
      addAnimationToShape(shapeName, startTime, endTime, animation);
    } else if (animations.get(numberOfMoves - 1).getEndTime() < startTime) {
      switch (animations.get(numberOfMoves - 1).getAnimation()) {
        case MOVE:
          addAnimationToShape(shapeName, startTime, endTime,
                  new Move(animations.get(numberOfMoves - 1).getEndTime(), startTime,
                  animations.get(numberOfMoves - 1).getEndX(),
                  animations.get(numberOfMoves - 1).getEndY(), tempo));
          addAnimationToShape(shapeName, startTime, endTime,
                  new Move(startTime, endTime, animation.getEndX(),
                          animation.getEndY(), tempo));
          break;
        case RESIZE:
          addAnimationToShape(shapeName, startTime, endTime,
                  new Resize(animations.get(numberOfMoves - 1).getEndTime(), startTime,
                          animations.get(numberOfMoves - 1).getEndWidth(),
                          animations.get(numberOfMoves - 1).getEndHeight(), tempo));
          addAnimationToShape(shapeName, startTime, endTime,
                  new Resize(startTime, endTime, animation.getEndWidth(),
                          animation.getEndHeight(), tempo));
          break;
        case COLORCHANGE:
          addAnimationToShape(shapeName, startTime, endTime,
                  new ColorChange(animations.get(numberOfMoves - 1).getEndTime(), startTime,
                          animations.get(numberOfMoves - 1).getNewColor(), tempo));
          addAnimationToShape(shapeName, startTime, endTime,
                  new ColorChange(startTime, endTime,
                          animation.getNewColor(), tempo));
          break;
        default:
          throw new IllegalStateException("Type is not found");
      }

    } else {
      throw new IllegalStateException("Cannot have time gap in between moves");
    }
  }

  /**
   * Adds the animation to the shape. This helper will check for overlap, whether the
   * animation times are within the shape duration, and that the first animation of a given type
   * for a given shape starts at the shape spawn time.
   *
   * @param shapeName the name of the shape
   * @param startTime the spawn time of the shape
   * @param endTime   the end time of the shape
   * @param animation the animation object to add
   * @throws IllegalStateException if the animation overlaps, the first animation does not start
   *                               at the shape spawn time, or if the animation times are not
   *                               within the shape duration
   */
  private void addAnimationToShape(String shapeName, int startTime,
                                   int endTime, IAnimation animation) {
    for (IAnimation animationFromList : animationMap.get(shapes.get(shapeName))) {
      if ((startTime >= animationFromList.getStartTime()
              && endTime <= animationFromList.getEndTime())
              && animationFromList.getAnimation().equals(animation.getAnimation())) {
        throw new IllegalStateException("Can't have overlap in animations");
      }
    }
    if (startTime >= shapes.get(shapeName).getShapeEndTime()
            || endTime > shapes.get(shapeName).getShapeEndTime()) {
      throw new IllegalStateException("Animation starts or ends after shape has disappeared");
    }
    if (startTime < shapes.get(shapeName).getShapeSpawnTime()
            || endTime < shapes.get(shapeName).getShapeSpawnTime()) {
      throw new IllegalStateException("Animation starts or ends before shape has appeared");
    }
    if (getAllAnimationsAt(shapeName, animation.getAnimation()).isEmpty()) {
      if (startTime != shapes.get(shapeName).getShapeSpawnTime()) {
        animationMap.get(shapes.get(shapeName)).add(new Move(startTime, endTime,
                shapes.get(shapeName).getX(),
                shapes.get(shapeName).getY()));
      }
    }
    animationMap.get(shapes.get(shapeName)).add(animation);
  }

  @Override
  public void addResize(String shapeName, int startTime, int endTime,
                        float endWidth, float endHeight) {
    Resize resize = new Resize(startTime, endTime, endWidth, endHeight);
    gapChecker(shapeName, startTime, endTime, resize);
  }

  @Override
  public void addColorChange(String shapeName,
                             int startTime, int endTime, Color newColor) {
    ColorChange colorChange = new ColorChange(startTime, endTime, newColor);
    gapChecker(shapeName, startTime, endTime, colorChange);
  }

  @Override
  public void removeAnimation(String shape) {
    if (animationMap.containsKey(shapes.get(shape))) {
      if (animationMap.get(shapes.get(shape)).size() > 0) {
        animationMap.get(shapes.get(shape)).poll();
      } else {
        throw new IllegalStateException("No animations in specified shape");
      }

    } else {
      throw new IllegalStateException("Can't remove animation for non-existent shape");
    }

  }

  @Override
  public void removeShape(String shape) {
    if (!shapes.containsKey(shape)) {
      throw new IllegalStateException("Shape is not in the model");
    }
    shapes.remove(shape);
    animationMap.remove(shapes.get(shape));
  }

  @Override
  public void setTempo(int tempo) {
    this.tempo = tempo;
  }

  @Override
  public int getNumberOfShapes() {
    return shapes.size();
  }

  @Override
  public IShape getShapeAt(String shapeName) {
    if (shapes.containsKey(shapeName)) {
      return shapes.get(shapeName);
    } else {
      throw new IllegalStateException("Cannot find desired shape");
    }
  }

  @Override
  public int getNumberOfAnimations(String shapeName) {
    if (shapes.containsKey(shapeName)) {
      return animationMap.get(shapes.get(shapeName)).size();
    } else {
      throw new IllegalStateException("Shape does not exist");
    }
  }

  @Override
  public List<IAnimation> getAllAnimationsAt(String shapeName) {
    List<IAnimation> animations = new ArrayList<>();
    PriorityQueue<IAnimation> pq = new PriorityQueue<>(animationMap.get(shapes.get(shapeName)));
    while (pq.size() > 0) {
      animations.add(pq.poll());
    }
    return animations;
  }

  @Override
  public List<IAnimation> getAllAnimationsAt(String shapeName, Animation animationType) {
    if (shapeName == null || !(shapes.containsKey(shapeName))) {
      throw new IllegalArgumentException("null or non-existent shape");
    }
    List<IAnimation> animations = new ArrayList<>();
    PriorityQueue<IAnimation> pq = new PriorityQueue<>(animationMap.get(shapes.get(shapeName)));
    while (pq.size() > 0) {
      animations.add(pq.poll());
    }
    return animations.stream().filter(animation -> animation.getAnimation() == animationType)
            .collect(Collectors.toList());
  }

  @Override
  public ArrayList<IShape> getSortedShapes() {
    ArrayList<IShape> list = new ArrayList<>(this.shapes.values());
    list.sort(Comparator.comparingInt(IShape::getShapeSpawnTime));
    return list;
  }

  @Override
  public int getWidth() {
    return this.width;
  }

  @Override
  public int getHeight() {
    return this.height;
  }

  @Override
  public int getTick() {
    return this.tick;
  }

  @Override
  public int getTempo() {
    return this.tempo;
  }

  @Override
  public int getFinalTime() {
    return shapes.values().stream().max(Comparator.comparing(IShape::getShapeEndTime))
            .get().getShapeEndTime();
  }

}
