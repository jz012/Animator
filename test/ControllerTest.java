import org.junit.Test;

import java.io.IOException;

import cs3500.animator.controller.Controller;
import cs3500.animator.controller.IController;
import cs3500.animator.model.AnimatorModel;
import cs3500.animator.model.SimpleAnimatorModel;
import cs3500.animator.view.AnimatorViewVisual;
import cs3500.animator.view.VisualView;

import static org.junit.Assert.assertTrue;

/**
 * The JUnit tests for testing different controller inputs.
 */
public class ControllerTest {
  @Test
  public void testController() {
    AnimatorModel model = new SimpleAnimatorModel();
    AnimatorViewVisual visualView = new VisualView(model);
    IController controller = new Controller(model, visualView, 1);
    try {
      controller.animate();
    } catch (IOException e) {
      assertTrue(e.getMessage().length() > 0);
    }
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNullView() {
    AnimatorModel model = new SimpleAnimatorModel();
    IController controller = new Controller(model, null, 1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNegativeSpeed() {
    AnimatorModel model = new SimpleAnimatorModel();
    AnimatorViewVisual visualView = new VisualView(model);
    IController controller = new Controller(model, visualView, -1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testZeroSpeed() {
    AnimatorModel model = new SimpleAnimatorModel();
    AnimatorViewVisual visualView = new VisualView(model);
    IController controller = new Controller(model, visualView, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNullModel() {
    AnimatorModel model = new SimpleAnimatorModel();
    AnimatorViewVisual visualView = new VisualView(model);
    IController controller = new Controller(null, visualView, 1);
  }
}
