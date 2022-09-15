import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import cs3500.animator.controller.IController;
import cs3500.animator.controller.InteractiveVisualViewController;
import cs3500.animator.io.AnimationBuilder;
import cs3500.animator.io.AnimationFileReader;
import cs3500.animator.io.TweenModelBuilder;
import cs3500.animator.model.AnimatorModel;
import cs3500.animator.view.AnimatorView;
import cs3500.animator.view.AnimatorViewCreator;
import cs3500.animator.view.InteractiveVisualView;
import cs3500.animator.view.ViewType;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * The JUnit tests to test listeners between the controller and view.
 */
public class ListenerTest {
  static final String start = "start";
  static final String loop = "loop";
  static final String speedUp = "faster";
  static final String slowDown = "slower";
  static final int speed = 1;
  static final int speedAfterSpeedUp = 998;
  static final int baseSpeed = 1000;
  AnimatorView view;
  AnimationFileReader reader;
  AnimatorModel model;
  TweenModelBuilder<AnimatorModel> tweenModelBuilder;
  IController interactiveController;

  @Before
  public void setUp() throws IOException {
    reader = new AnimationFileReader();
    tweenModelBuilder = new AnimationBuilder();
    model = reader.readFile("toh-3.txt", tweenModelBuilder);
    view = AnimatorViewCreator.create(ViewType.Interactive, model);
    interactiveController =
            new InteractiveVisualViewController(model, (InteractiveVisualView) view, speed);
    interactiveController.animate();
  }

  @Test
  public void testStartListener() throws IOException {
    setUp();
    ((InteractiveVisualView) view).clickButton(start);
    assertTrue(model.getTick() > 1);
  }

  @Test
  public void testLoopListener() throws IOException {
    setUp();
    ((InteractiveVisualView) view).clickButton(loop);
    assertTrue(((InteractiveVisualView) view).getIsLooping());
    ((InteractiveVisualView) view).clickButton(loop);
    assertFalse(((InteractiveVisualView) view).getIsLooping());
  }

  @Test
  public void testSpeedingUpAndSlowingDownListener() throws IOException {
    setUp();
    assertEquals(1, model.getTempo());
    ((InteractiveVisualView) view).clickButton(speedUp);
    assertEquals(speedAfterSpeedUp, interactiveController.getSpeed());
    ((InteractiveVisualView) view).clickButton(slowDown);
    assertEquals(baseSpeed, interactiveController.getSpeed());
  }
}

