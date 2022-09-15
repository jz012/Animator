import org.junit.Before;
import org.junit.Test;

import java.awt.Color;

import cs3500.animator.premade.AbstractPreProgrammedAnimation;
import cs3500.animator.premade.BubbleSortPreProgrammedAnimation;
import cs3500.animator.premade.SelectionSortPreProgrammedAnimation;
import cs3500.animator.model.Rectangle;

import static org.junit.Assert.assertEquals;

/**
 * Test the methods used for the pre-programmed animation.
 */
public class PreProgrammedAnimationsTest {
  BubbleSortPreProgrammedAnimation a1;
  SelectionSortPreProgrammedAnimation a2;
  Rectangle r1;
  Rectangle r2;

  @Before
  public void setUp() {
    a1 = new BubbleSortPreProgrammedAnimation();
    a2 = new SelectionSortPreProgrammedAnimation();
    r1 = new Rectangle("r1", 1,2,3,4, Color.PINK,1,7);
    r2 = new Rectangle("r2", 5,2,3,4, Color.PINK,1,7);
  }

  @Test
  public void testShuffledRectangles() {
    setUp();
    assertEquals(AbstractPreProgrammedAnimation.shuffledRecs(2).size(), 2);
    assertEquals(AbstractPreProgrammedAnimation.shuffledRecs(3).size(), 3);

  }

  @Test
  public void testSwapX() {
    setUp();
    assertEquals(AbstractPreProgrammedAnimation.swapX(r1, r2), "" +
            "move name r1 moveto null 2.0 null 2.0 from 1 to 11\n");
    assertEquals(AbstractPreProgrammedAnimation.swapX(r1, (float) 0.05),
            "move name r1 moveto null 2.0 0.05 2.0 from 12 to 22\n");
  }
}


