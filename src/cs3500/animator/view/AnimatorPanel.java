package cs3500.animator.view;

import java.awt.Graphics;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.util.Objects;

import javax.swing.JPanel;

import cs3500.animator.model.AnimatorModelState;
import cs3500.animator.model.IShape;


/**
 * The primary panel class extended from JPanel. This class uses the model and its methods to
 * obtain information from the state and paints corresponding shapes using the JPanel library
 * from Java Swing.
 */
public class AnimatorPanel extends JPanel {
  protected AnimatorModelState model;

  /**
   * The constructor for the AnimatorPanel.
   *
   * @param model the model state of the model
   */
  public AnimatorPanel(AnimatorModelState model) {
    super();
    this.setBackground(Color.WHITE);
    this.model = Objects.requireNonNull(model);
  }

  /**
   * Overrides paintComponent from the Java Swing library to paint the appropriate shapes based
   * on the shapes from the model onto a panel which represents the instantaneous model state.
   *
   * @param g the graphics object to protect
   */
  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D g2 = (Graphics2D) g;
    for (IShape shape : model.getSortedShapes()) {
      switch (shape.getType()) {
        case OVAL:
          Ellipse2D.Float oval = new Ellipse2D.Float(shape.getX(), shape.getY(), shape.getWidth(),
                  shape.getHeight());
          g2.draw(oval);
          g2.setColor(new Color(shape.getR(), shape.getG(), shape.getB()));
          g2.fill(oval);
          break;
        case RECTANGLE:
          Rectangle2D.Float rectangle = new Rectangle2D.Float(shape.getX(),
                  shape.getY(), shape.getWidth(), shape.getHeight());
          g2.draw(rectangle);
          g2.setColor(new Color(shape.getR(), shape.getG(), shape.getB()));
          g2.fill(rectangle);
          break;
        default:
          throw new IllegalArgumentException("The shape type specified has not been implemented");
      }
    }
  }


}
