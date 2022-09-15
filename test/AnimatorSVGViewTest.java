import org.junit.Before;
import org.junit.Test;

import java.awt.Color;

import cs3500.animator.model.AnimatorModel;
import cs3500.animator.model.IShape;
import cs3500.animator.model.Oval;
import cs3500.animator.model.Rectangle;
import cs3500.animator.model.SimpleAnimatorModel;
import cs3500.animator.view.AnimatorSVGView;

import static org.junit.Assert.assertEquals;

/**
 * JUnit tests for AnimatorSVGView.
 */
public class AnimatorSVGViewTest {

  AnimatorModel model;
  IShape r;
  IShape o;
  IShape r1;
  IShape o1;
  AnimatorSVGView a1;
  AnimatorModel model2;
  AnimatorSVGView a2;
  AnimatorModel model3;
  AnimatorSVGView a3;
  AnimatorModel model4;
  AnimatorSVGView a4;

  @Before
  public void setUp() {
    model = new SimpleAnimatorModel(500, 500);
    model2 = new SimpleAnimatorModel(500, 500);
    r = new Rectangle("r", 0, 0, 10,
            20, Color.black, 1, 20);
    o = new Oval("o", 5, 5, 5, 5, Color.white, 2, 20);
    r1 = new Rectangle("r", 0, 0, 10,
            20, Color.black, 1, 20);
    o1 = new Oval("o", 5, 5, 5, 5, Color.white, 2, 20);
    model.addShape(r);
    model.addShape(o);
    model.addMove("r", 1, 15, 6, 6);
    model.addMove("r", 15, 20, 6, 6);
    model.addColorChange("r", 1,
            15, Color.red);
    model.addColorChange("r", 15,
            20, Color.blue);
    model.addResize("r", 1, 15,
            6, 6);
    model.addResize("r", 15, 20,
            10, 10);
    model.addMove("o", 2, 15, 6,
            6);
    model.addMove("o", 15, 20, 10, 10);
    model.addColorChange("o", 2,
            15, Color.red);
    model.addColorChange("o", 15,
            20, Color.blue);
    model.addResize("o", 2, 15,
            6, 6);
    model.addResize("o", 15, 20,
            6, 6);
    a1 = new AnimatorSVGView(model);
    a2 = new AnimatorSVGView(model2);
    model2.addShape(r1);
    model2.addShape(o1);
  }

  @Test
  public void testModelToSVGString() {
    setUp();
    model.setTempo(1);
    assertEquals("<svg width=\"500\" height=\"500\" version=\"1.1\"\n" +
            "     xmlns=\"http://www.w3.org/2000/svg\">\n" +
            "<rect id=\"r\" x=\"0.0\" y=\"0.0\" width=\"10.0\" " +
            "height=\"20.0\" fill=\"rgb(0,0,0)\" visibility=\"visible\">\n" +
            "<animate attributeType=\"xml\" begin=\"1000ms\" " +
            "dur = \"14000ms\" attributeName=\"x\" from=\"0.0\" to=\"6.0\" fill =\"freeze\"/>\n" +
            "<animate attributeType=\"xml\" begin=\"1000ms\" " +
            "dur = \"14000ms\" attributeName=\"y\" " +
            "from=\"0.0\" to=\"6.0\" fill =\"freeze\"/>\n" +
            "<animate attributeType=\"xml\" begin=\"1000ms\" " +
            "dur = \"14000ms\" attributeName=\"width\" " +
            "from=\"10.0\" to=\"6.0\" fill =\"freeze\"/>\n" +
            "<animate attributeType=\"xml\" begin=\"1000ms\" " +
            "dur = \"14000ms\" attributeName=\"height\" " +
            "from=\"20.0\" to=\"6.0\" fill =\"freeze\"/>\n" +
            "<animate attributeType=\"xml\" begin=\"1000ms\" " +
            "dur = \"14000ms\" attributeName=\"fill\" " +
            "from=\"rgb(0,0,0)\" to=\"rgb(255,0,255)\" fill =\"freeze\"/>\n" +
            "<animate attributeType=\"xml\" begin=\"15000ms\" " +
            "dur = \"5000ms\" attributeName=\"fill\" " +
            "from=\"rgb(255,0,255)\" to=\"rgb(0,0,0)\" fill =\"freeze\"/>\n" +
            "<animate attributeType=\"xml\" begin=\"15000ms\" " +
            "dur = \"5000ms\" attributeName=\"x\" from=\"6.0\" to=\"6.0\" fill =\"freeze\"/>\n" +
            "<animate attributeType=\"xml\" begin=\"15000ms\" " +
            "dur = \"5000ms\" attributeName=\"y\" from=\"6.0\" " +
            "to=\"6.0\" fill =\"freeze\"/>\n" +
            "<animate attributeType=\"xml\" begin=\"15000ms\" " +
            "dur = \"5000ms\" attributeName=\"width\" " +
            "from=\"6.0\" to=\"10.0\" fill =\"freeze\"/>\n" +
            "<animate attributeType=\"xml\" begin=\"15000ms\" " +
            "dur = \"5000ms\" " +
            "attributeName=\"height\" from=\"6.0\" to=\"10.0\" fill =\"freeze\"/>\n" +
            "\n" +
            "</rect>\n" +
            "\n" +
            "<ellipse id=\"o\" cx=\"5.0\" cy=\"5.0\" rx=\"5.0\" ry=\"5.0\" " +
            "fill=\"rgb(255,255,255)\" visibility=\"visible\">\n" +
            "<animate attributeType=\"xml\" begin=\"2000ms\" " +
            "dur = \"13000ms\" attributeName=\"x\" from=\"5.0\" to=\"6.0\" fill =\"freeze\"/>\n" +
            "<animate attributeType=\"xml\" begin=\"2000ms\" " +
            "dur = \"13000ms\" attributeName=\"y\" from=\"5.0\" to=\"6.0\" fill =\"freeze\"/>\n" +
            "<animate attributeType=\"xml\" begin=\"2000ms\" " +
            "dur = \"13000ms\" attributeName=\"cx\" from=\"5.0\" to=\"6.0\" fill =\"freeze\"/>\n" +
            "<animate attributeType=\"xml\" begin=\"2000ms\" " +
            "dur = \"13000ms\" attributeName=\"cy\" from=\"5.0\" to=\"6.0\" fill =\"freeze\"/>\n" +
            "<animate attributeType=\"xml\" begin=\"2000ms\" " +
            "dur = \"13000ms\" attributeName=\"fill\" " +
            "from=\"rgb(255,255,255)\" to=\"rgb(255,0,255)\" fill =\"freeze\"/>\n" +
            "<animate attributeType=\"xml\" begin=\"15000ms\" " +
            "dur = \"5000ms\" attributeName=\"fill\" " +
            "from=\"rgb(255,0,255)\" to=\"rgb(0,0,0)\" fill =\"freeze\"/>\n" +
            "<animate attributeType=\"xml\" begin=\"15000ms\" " +
            "dur = \"5000ms\" attributeName=\"x\" from=\"6.0\" to=\"10.0\" fill =\"freeze\"/>\n" +
            "<animate attributeType=\"xml\" begin=\"15000ms\" " +
            "dur = \"5000ms\" attributeName=\"y\" from=\"6.0\" to=\"10.0\" fill =\"freeze\"/>\n" +
            "<animate attributeType=\"xml\" begin=\"15000ms\" " +
            "dur = \"5000ms\" attributeName=\"cx\" from=\"6.0\" to=\"6.0\" fill =\"freeze\"/>\n" +
            "<animate attributeType=\"xml\" begin=\"15000ms\" " +
            "dur = \"5000ms\" attributeName=\"cy\" from=\"6.0\" to=\"6.0\" fill =\"freeze\"/>\n" +
            "\n" +
            "</ellipse>\n" +
            "\n" +
            "\n" +
            "</svg>\n" +
            "\n", a1.toString());

  }

}