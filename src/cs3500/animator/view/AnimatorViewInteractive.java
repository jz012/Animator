package cs3500.animator.view;

import java.awt.event.ActionListener;


/**
 * The interface for AnimatorViewInteractive. This interface controls the interactive view and
 * implements various methods that deal with the view and also allows the controller to access
 * its implementations.
 */
public interface AnimatorViewInteractive extends AnimatorViewVisual {
  /**
   * Gets whether the animation is currently looping.
   * @return true if it is looping and false otherwise
   */
  boolean getIsLooping();

  /**
   * Set all the listeners onto the view.
   * @param listener the ActionListener object to set
   */
  void setListener(ActionListener listener);

  /**
   * A method to automate clicks within the view.
   * @param button the button to click
   */
  void clickButton(String button);

  /**
   * Changes whether the animation is currently looping or not. If it currently is looping, the
   * method will make it stop looping and vice versa.
   */
  void changeLoop();
}
