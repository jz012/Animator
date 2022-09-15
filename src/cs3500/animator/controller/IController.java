package cs3500.animator.controller;

import java.io.IOException;

/**
 * De-facto controller to access model information for the view. Will
 * be updated to include actual controller functions including
 * buttons and other things for the panel.
 */
public interface IController {
  /**
   * A method that tells the controller to stop the timer.
   */
  void stop();

  /**
   * The main execution method for the controller.
   *
   * @throws IOException if view is not able to be rendered properly
   */
  void animate() throws IOException;

  /**
   * Get the speed for the controller that controls the speed of the animation.
   *
   * @return the speed in integer form
   */
  int getSpeed();
}
