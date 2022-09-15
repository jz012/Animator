import org.junit.Before;
import org.junit.Test;

import cs3500.animator.model.AnimatorModel;
import cs3500.animator.model.IShape;
import cs3500.animator.model.Oval;
import cs3500.animator.model.Rectangle;
import cs3500.animator.model.SimpleAnimatorModel;
import cs3500.animator.view.AnimatorTextView;

import java.awt.Color;

import static org.junit.Assert.assertEquals;

/**
 * Tests to ensure that the text view of the animation works properly.
 */

public class AnimatorTextViewTest {

  AnimatorModel model;
  IShape r;
  IShape o;
  IShape r1;
  IShape o1;
  AnimatorTextView a1;
  AnimatorModel model2;
  AnimatorTextView a2;
  AnimatorModel model3;
  AnimatorTextView a3;
  AnimatorModel model4;
  AnimatorTextView a4;

  @Before
  public void setUp() {
    model = new SimpleAnimatorModel();
    model2 = new SimpleAnimatorModel();
    r = new Rectangle("r", 0, 0, 10,
            20, Color.black, 1, 20);
    o = new Oval("o", 5, 5, 5, 5, Color.white, 2, 20);
    r1 = new Rectangle("r", 0, 0, 10,
            20, Color.black, 1, 20);
    o1 = new Oval("o", 5, 5, 5, 5, Color.white, 2, 20);
    model.addShape(r);
    model.addShape(o);
    model.addMove("r", 1, 15, 6, 6);
    model.addMove( "r", 15, 20, 6, 6);
    model.addColorChange( "r", 1,
            15, Color.red);
    model.addColorChange( "r", 15,
            20, Color.blue);
    model.addResize("r", 1, 15,
            6, 6);
    model.addResize("r", 15, 20,
            10, 10);
    model.addMove( "o", 2, 15, 6,
            6);
    model.addMove("o", 15, 20, 10, 10);
    model.addColorChange( "o", 2,
            15, Color.red);
    model.addColorChange( "o", 15,
            20, Color.blue);
    model.addResize( "o", 2, 15,
            6, 6);
    model.addResize("o", 15, 20,
            6, 6);
    a1 = new AnimatorTextView(model);
    a2 = new AnimatorTextView(model2);
    model2.addShape(r1);
    model2.addShape(o1);
  }

  @Test
  public void testGetString() {
    setUp();
    assertEquals("canvas 500 500\n" +
            "Shape r RECTANGLE Spawns at:1000ms Ends at:20000ms\n" +
            "Created with  X:0.0 Y:0.0 Color:[0, 0, 0].  Width:10.0 Height:20.0\n" +
            "\n" +
            "MOVE  Start:1 X:0.0 Y:0.0 Ends:15 X:6.0 Y:6.0\n" +
            "RESIZE  Start:1 Width:10.0 Height:20.0 Ends:15 Width:6.0 Height:6.0\n" +
            "COLORCHANGE  Start:1 Color:[0, 0, 0] Ends: 15 Color:[255, 0, 0]\n" +
            "COLORCHANGE  Start:15 Color:[255, 0, 0] Ends: 20 Color:[0, 0, 255]\n" +
            "MOVE  Start:15 X:6.0 Y:6.0 Ends:20 X:6.0 Y:6.0\n" +
            "RESIZE  Start:15 Width:6.0 Height:6.0 Ends:20 Width:10.0 Height:10.0\n" +
            "\n" +
            "Shape o OVAL Spawns at:2000ms Ends at:20000ms\n" +
            "Created with  X:5.0 Y:5.0 Color:[255, 255, 255].  Width:5.0 Height:5.0\n" +
            "\n" +
            "MOVE  Start:2 X:5.0 Y:5.0 Ends:15 X:6.0 Y:6.0\n" +
            "RESIZE  Start:2 Width:5.0 Height:5.0 Ends:15 Width:6.0 Height:6.0\n" +
            "COLORCHANGE  Start:2 Color:[255, 255, 255] Ends: 15 Color:[255, 0, 0]\n" +
            "COLORCHANGE  Start:15 Color:[255, 0, 0] Ends: 20 Color:[0, 0, 255]\n" +
            "MOVE  Start:15 X:6.0 Y:6.0 Ends:20 X:10.0 Y:10.0\n" +
            "RESIZE  Start:15 Width:6.0 Height:6.0 Ends:20 Width:6.0 Height:6.0"
            +
            "\n", a1.toString());

    assertEquals("canvas 500 500\n" +
            "Shape r RECTANGLE Spawns at:1000ms Ends at:20000ms\n" +
            "Created with  X:0.0 Y:0.0 Color:[0, 0, 0].  Width:10.0 Height:20.0\n" +
            "\n" +
            "Shape o OVAL Spawns at:2000ms Ends at:20000ms\n" +
            "Created with  X:5.0 Y:5.0 Color:[255, 255, 255].  Width:5.0 Height:5.0\n" +
            "\n", a2.toString());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNullModel() {
    model3 = null;
    a3 = new AnimatorTextView(model3);
  }

  @Test
  public void testEmptyModel() {
    model4 = new SimpleAnimatorModel(500,500);
    a4 = new AnimatorTextView(model4);
    assertEquals("canvas 500 500\n", a4.toString());
  }
}