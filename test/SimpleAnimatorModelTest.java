
import org.junit.Before;
import org.junit.Test;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


import cs3500.animator.model.AnimatorModel;
import cs3500.animator.model.ColorChange;
import cs3500.animator.model.IShape;
import cs3500.animator.model.Move;
import cs3500.animator.model.Oval;
import cs3500.animator.model.Rectangle;
import cs3500.animator.model.Resize;
import cs3500.animator.model.Shape;
import cs3500.animator.model.SimpleAnimatorModel;

import static org.junit.Assert.assertEquals;

/**
 * JUnit tests for the SimpleAnimatorModel. It tests the functionality of interface methods.
 */
public class SimpleAnimatorModelTest {
  AnimatorModel model;
  IShape r;

  @Before
  public void setUp() {
    model = new SimpleAnimatorModel(500, 500);
    r = new Rectangle("r", 0, 0, 10,
            20, Color.black, 1, 20);
  }

  @Test
  public void testAddAnimationBeforeShapeAppears() {
    setUp();
    model.addShape(Shape.RECTANGLE, "r", 0, 0, 10, 20,
            Color.black, 5, 10);
    try {
      model.addMove("r", 1, 2, 10, 10);
    } catch (IllegalStateException e) {
      assertEquals(e.getMessage(), "Animation starts or ends before shape has appeared");
    }
  }

  @Test
  public void testRemoveShape() {
    setUp();
    model.addShape(r);
    assertEquals(1, model.getNumberOfShapes());
    model.removeShape(r.getName());
    assertEquals(0, model.getNumberOfShapes());
    try {
      model.getNumberOfAnimations("r");
    } catch (IllegalStateException e) {
      assertEquals(e.getMessage(), "Shape does not exist");
    }
  }

  @Test
  public void testAddAnimationAfterShapeIsGone() {
    setUp();
    model.addShape(Shape.RECTANGLE, "r", 0, 0, 10, 20,
            Color.black, 5, 10);
    try {
      model.addMove("r", 11, 13, 10, 10);
    } catch (IllegalStateException e) {
      assertEquals(e.getMessage(), "Animation starts or ends after shape has disappeared");
    }
  }

  @Test
  public void testAddAnimationToShape() {
    setUp();
    model.addShape(r);
    assertEquals(1, model.getNumberOfShapes());
    assertEquals(new ArrayList<IShape>(List.of(new Rectangle("r", 0, 0, 10,
                    20, Color.black, 1, 20))).toString(),
            model.getSortedShapes().toString());
    assertEquals(0, model.getShapeAt("r").getX(), 0.0);
    model.addMove("r", 1, 5, 10, 10);
    assertEquals(1, model.getNumberOfAnimations("r"));
    assertEquals(1, model.getNumberOfShapes());
  }

  @Test
  public void testShapeOutOfBounds() {
    setUp();
    model.addShape(r);
    model.addShape(Shape.OVAL, "o", -100, -100, 10,
            20, Color.red, 1, 10);
    assertEquals(2, model.getNumberOfShapes());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testEmptyAnimation() {
    setUp();
    model.addShape(r);
    // same start and end time
    model.addMove("r", 1, 1, 10, 10);
  }

  @Test
  public void testOverlapMoveInvalid() {
    setUp();
    model.addShape(r);
    model.addMove("r", 1, 5, 10, 10);
    try {
      model.addMove("r", 1, 3, 10, 10);
    } catch (IllegalStateException e) {
      assertEquals(e.getMessage(), "Cannot have time gap in between moves");
    }
  }

  @Test
  public void testRemoveAnimation() {
    setUp();
    model.addShape(r);
    model.addMove("r", 1, 5, 10, 10);
    model.addMove("r", 5, 10, 20, 20);
    assertEquals(2, model.getNumberOfAnimations("r"));
    model.removeAnimation("r");
    assertEquals(1, model.getNumberOfAnimations("r"));
    try {
      model.removeAnimation("t");
    } catch (IllegalStateException e) {
      assertEquals(e.getMessage(), "Can't remove animation for non-existent shape");
    }
    model.removeAnimation("r");
    assertEquals(0, model.getNumberOfAnimations("r"));
    try {
      model.removeAnimation("r");
    } catch (IllegalStateException e) {
      assertEquals(e.getMessage(), "No animations in specified shape");
    }

  }

  @Test
  public void testOverlapResizeInvalid() {
    setUp();
    model.addShape(r);
    model.addResize("r", 1, 5,
            10, 30);
    try {
      model.addMove("r", 1, 3, 10, 10);
    } catch (IllegalStateException e) {
      assertEquals(e.getMessage(), "Cannot have time gap in between moves");
    }
  }

  @Test
  public void testOverlapColorChangeInvalid() {
    setUp();
    model.addShape(r);
    model.addColorChange("r", 1, 5, Color.red);
    try {
      model.addColorChange("r", 1, 3, Color.blue);
    } catch (IllegalStateException e) {
      assertEquals(e.getMessage(), "Cannot have time gap in between moves");
    }
  }


  @Test
  public void testCanAddDifferentAnimationsOverlap() {
    setUp();
    model.addShape(r);
    model.addMove("r", 1, 5, 10, 10);
    try {
      model.addMove("r", 1, 3, 10, 10);
    } catch (IllegalStateException e) {
      assertEquals(e.getMessage(), "Cannot have time gap in between moves");
    }
    model.addResize("r", 1,
            5, 20, 30);
    model.addColorChange("r", 1, 3, Color.red);
    assertEquals(3, model.getNumberOfAnimations("r"));
  }

  @Test
  public void testAnimationsAreSorted() {
    setUp();
    model.addShape(r);
    model.addMove("r", 1, 10, 0, 0);
    model.addMove("r", 10, 12, 10, 10);
    model.addResize("r", 1,
            5, 30, 10);
    model.addResize("r", 5, 6,
            40, 50);
    StringBuilder s = new StringBuilder();
    s.append(model.getAllAnimationsAt("r").get(0)).append(", ")
            .append(model.getAllAnimationsAt("r").get(1)).append(", ")
            .append(model.getAllAnimationsAt("r").get(2)).append(", ")
            .append(model.getAllAnimationsAt("r").get(3));
    String list =
            model.getAllAnimationsAt("r")
                    .stream().map(Object::toString).collect(Collectors.joining(", "));
    assertEquals(s.toString(), list);

  }

  @Test
  public void testInvalidArgumentsAddShape() {
    setUp();
    try {
      model.addShape(Shape.RECTANGLE, "broken", 0, 0, 10,
              -20, Color.black, 1, 20);
    } catch (IllegalArgumentException e) {
      assertEquals(e.getMessage(), "negative or invalid shape arguments");
    }

    try {
      model.addShape(Shape.RECTANGLE, "broken", 0, 0, -10,
              20, Color.black, 1, 20);
    } catch (IllegalArgumentException e) {
      assertEquals(e.getMessage(), "negative or invalid shape arguments");
    }

    try {
      model.addShape(null, "broken", 0, 0, 10,
              20, Color.black, 1, 20);
    } catch (IllegalArgumentException e) {
      assertEquals(e.getMessage(), "negative or invalid shape arguments");
    }

    try {
      model.addShape(Shape.RECTANGLE, "", 0, 0, 10,
              20, Color.black, 1, 20);
    } catch (IllegalArgumentException e) {
      assertEquals(e.getMessage(), "negative or invalid shape arguments");
    }

    try {
      model.addShape(Shape.RECTANGLE, "broken", 0, 0, -10,
              20, Color.black, 1, 20);
    } catch (IllegalArgumentException e) {
      assertEquals(e.getMessage(), "negative or invalid shape arguments");
    }

    try {
      model.addShape(r);
      model.addShape(r);
    } catch (IllegalArgumentException e) {
      assertEquals(e.getMessage(), "This shape already exists");
    }
  }

  @Test
  public void testDuplicateAddShape() {
    setUp();
    try {
      model.addShape(r);
      model.addShape(r);
    } catch (IllegalArgumentException e) {
      assertEquals(e.getMessage(), "This shape already exists");
    }
  }

  @Test
  public void testBadArgumentsMove() {
    setUp();
    try {
      Move move = new Move(1, 5, -1, 1);
    } catch (IllegalArgumentException e) {
      assertEquals(e.getMessage(), "invalid negative arguments");
    }

    setUp();
    try {
      Move move = new Move(1, 5, 1, -1);
    } catch (IllegalArgumentException e) {
      assertEquals(e.getMessage(), "invalid negative arguments");
    }

    try {
      Move move = new Move(1, 5, 1, 1);
    } catch (IllegalArgumentException e) {
      assertEquals(e.getMessage(), "Animation should have a name");
    }
    try {
      Move move = new Move(1, 1, 1, 1);
    } catch (IllegalArgumentException e) {
      assertEquals(e.getMessage(), "No instantaneous animations");
    }
  }

  @Test
  public void testBadArgumentsResize() {
    setUp();
    try {
      Resize resize = new Resize(1, 5, -1, 1);
    } catch (IllegalArgumentException e) {
      assertEquals(e.getMessage(), "negative dimensions invalid");
    }

    try {
      Resize resize = new Resize(1, 5, 1, -1);
    } catch (IllegalArgumentException e) {
      assertEquals(e.getMessage(), "negative dimensions invalid");
    }

    try {
      Resize resize = new Resize(1, 5, 1, 1);
    } catch (IllegalArgumentException e) {
      assertEquals(e.getMessage(), "Animation should have a name");
    }
    try {
      Resize resize = new Resize(1, 1, 1, 1);
    } catch (IllegalArgumentException e) {
      assertEquals(e.getMessage(), "No instantaneous animations");
    }
  }

  @Test
  public void testBadArgumentsColorChange() {
    setUp();
    try {
      ColorChange colorChange = new ColorChange(1, 5, null);
    } catch (IllegalArgumentException e) {
      assertEquals(e.getMessage(), "invalid/null color");
    }

    try {
      ColorChange colorChange = new ColorChange(1, 5, Color.BLACK);
    } catch (IllegalArgumentException e) {
      assertEquals(e.getMessage(), "Animation should have a name");
    }
    try {
      ColorChange colorChange = new ColorChange(1, 1, Color.white);
    } catch (IllegalArgumentException e) {
      assertEquals(e.getMessage(), "No instantaneous animations");
    }
  }

  @Test
  public void testBadRectangle() {
    setUp();
    try {
      Rectangle r = new Rectangle("r", 0, 0, 0,
              5, Color.black, 1, 20);
    } catch (IllegalArgumentException e) {
      assertEquals(e.getMessage(), "No negative/zero dimensions");
    }
    try {
      Rectangle r = new Rectangle("r", 0, 0, 5,
              0, Color.black, 1, 20);
    } catch (IllegalArgumentException e) {
      assertEquals(e.getMessage(), "No negative/zero dimensions");
    }
    try {
      Rectangle r = new Rectangle("r", 0, 0, -5,
              5, Color.black, 1, 20);
    } catch (IllegalArgumentException e) {
      assertEquals(e.getMessage(), "No negative/zero dimensions");
    }
    try {
      Rectangle r = new Rectangle("r", 0, 0, 5,
              -5, Color.black, 1, 20);
    } catch (IllegalArgumentException e) {
      assertEquals(e.getMessage(), "No negative/zero dimensions");
    }

    try {
      Rectangle r = new Rectangle("r", 0, 0, 5,
              5, Color.black, 7, 5);
    } catch (IllegalArgumentException e) {
      assertEquals(e.getMessage(), "Can't have a shape disappear before it appears");
    }
    try {
      Rectangle r = new Rectangle("r", 0, 0, 5,
              5, Color.black, -1, 20);
    } catch (IllegalArgumentException e) {
      assertEquals(e.getMessage(), "No negative start or end times");
    }
    try {
      Rectangle r = new Rectangle("r", 0, 0, 5,
              5, Color.black, -4, -1);
    } catch (IllegalArgumentException e) {
      assertEquals(e.getMessage(), "No negative start or end times");
    }
    try {
      Rectangle r = new Rectangle("r", 0, 0, 5,
              5, null, 1, 20);
    } catch (IllegalArgumentException e) {
      assertEquals(e.getMessage(), "Color cannot be null");
    }
    try {
      Rectangle r = new Rectangle("", 0, 0, 5,
              5, Color.black, 1, 20);
    } catch (IllegalArgumentException e) {
      assertEquals(e.getMessage(), "String must have a name");
    }

  }

  @Test
  public void testBadOval() {
    setUp();
    try {
      Oval oval = new Oval("o", 0, 0, 0,
              5, Color.black, 1, 20);
    } catch (IllegalArgumentException e) {
      assertEquals(e.getMessage(), "No negative/zero dimensions");
    }
    try {
      Oval oval = new Oval("o", 0, 0, 5,
              0, Color.black, 1, 20);
    } catch (IllegalArgumentException e) {
      assertEquals(e.getMessage(), "No negative/zero dimensions");
    }
    try {
      Oval oval = new Oval("o", 0, 0, -5,
              5, Color.black, 1, 20);
    } catch (IllegalArgumentException e) {
      assertEquals(e.getMessage(), "No negative/zero dimensions");
    }
    try {
      Oval oval = new Oval("o", 0, 0, 5,
              -5, Color.black, 1, 20);
    } catch (IllegalArgumentException e) {
      assertEquals(e.getMessage(), "No negative/zero dimensions");
    }

    try {
      Oval oval = new Oval("o", 0, 0, 5,
              5, Color.black, 7, 5);
    } catch (IllegalArgumentException e) {
      assertEquals(e.getMessage(), "Can't have a shape disappear before it appears");
    }
    try {
      Oval oval = new Oval("o", 0, 0, 5,
              5, Color.black, -1, 20);
    } catch (IllegalArgumentException e) {
      assertEquals(e.getMessage(), "No negative start or end times");
    }
    try {
      Oval oval = new Oval("o", 0, 0, 5,
              5, Color.black, -4, -1);
    } catch (IllegalArgumentException e) {
      assertEquals(e.getMessage(), "No negative start or end times");
    }
    try {
      Oval oval = new Oval("o", 0, 0, 5,
              5, null, 1, 20);
    } catch (IllegalArgumentException e) {
      assertEquals(e.getMessage(), "Color cannot be null");
    }
    try {
      Oval oval = new Oval("", 0, 0, 5,
              5, Color.black, 1, 20);
    } catch (IllegalArgumentException e) {
      assertEquals(e.getMessage(), "String must have a name");
    }

  }

  @Test
  public void testTickAt() {
    setUp();
    model.addShape(r);
    model.addResize("r", 1, 5,
            10, 30);
    model.changeStateAtTick(1);
    assertEquals(10, model.getShapeAt("r").getWidth(), 0.0);
    assertEquals(20, model.getShapeAt("r").getHeight(), 0.0);
    model.changeStateAtTick(3);
    assertEquals(10, model.getShapeAt("r").getWidth(), 0.0);
    assertEquals(25.0, model.getShapeAt("r").getHeight(), 0.0);
    model.changeStateAtTick(5);
    assertEquals(10, model.getShapeAt("r").getWidth(), 0.0);
    assertEquals(30, model.getShapeAt("r").getHeight(), 0.0);
  }

  @Test
  public void testTickMultipleAnimations() {
    setUp();
    model.addShape(r);
    model.addResize("r", 1, 5,
            10, 30);
    model.addColorChange("r", 1, 5, Color.green);
    model.changeStateAtTick(4);
    assertEquals(10, model.getShapeAt("r").getWidth(), 0.0);
    assertEquals(27.5, model.getShapeAt("r").getHeight(), 0.0);
    assertEquals(0, model.getShapeAt("r").getColor().getRed(), 0.0);
    assertEquals(0, model.getShapeAt("r").getColor().getBlue(), 0.0);
    assertEquals(191, model.getShapeAt("r").getColor().getGreen(), 0.0);
  }


  @Test
  public void testFinalTime() {
    setUp();
    model.addShape(r);
    model.addShape(Shape.OVAL, "o", 0, 0, 10, 20, Color.WHITE,
            1, 50);
    assertEquals(50, model.getFinalTime());
  }

  @Test
  public void testGaps() {
    setUp();
    Rectangle r1 = new Rectangle("r1", 600, 100, 100, 240, Color.black, 1, 500);
    model.addShape(r1);
    model.addMove("r1", 1, 11, 480, 100);
    model.addMove("r1", 23, 33, 360, 100);
    assertEquals(model.getAllAnimationsAt("r1").toString(), "[MOVE , MOVE , MOVE ]");
  }


}

