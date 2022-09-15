import org.junit.Test;

import cs3500.animator.model.AnimatorModel;
import cs3500.animator.model.SimpleAnimatorModel;
import cs3500.animator.view.AnimatorSVGView;
import cs3500.animator.view.AnimatorTextView;
import cs3500.animator.view.AnimatorView;
import cs3500.animator.view.AnimatorViewCreator;
import cs3500.animator.view.InteractiveVisualView;
import cs3500.animator.view.ViewType;
import cs3500.animator.view.VisualView;

import static org.junit.Assert.assertTrue;

/**
 * The JUnit tests for the AnimatorCreator.
 */
public class AnimatorCreatorTest {
  @Test
  public void testCreate() {
    AnimatorModel model = new SimpleAnimatorModel();
    AnimatorView textView = AnimatorViewCreator.create(ViewType.Text, model);
    AnimatorView svg = AnimatorViewCreator.create(ViewType.SVG, model);
    AnimatorView visual = AnimatorViewCreator.create(ViewType.Visual, model);
    AnimatorView interactive = AnimatorViewCreator.create(ViewType.Interactive, model);
    assertTrue(textView instanceof AnimatorTextView);
    assertTrue(svg instanceof AnimatorSVGView);
    assertTrue(visual instanceof VisualView);
    assertTrue(interactive instanceof InteractiveVisualView);
  }
}
