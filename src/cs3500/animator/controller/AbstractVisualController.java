package cs3500.animator.controller;

import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.Timer;

import cs3500.animator.model.AnimatorModel;

/**
 * The class that represents an AbstractVisualController. It implements both the controller
 * interface and also listener functionality from java swing. The controller takes in a model and
 * a speed, and then pulls the corresponding view depending on what type of controller it is,
 * interactive or visual.
 */
public abstract class AbstractVisualController implements IController, ActionListener {
  protected final AnimatorModel model;
  protected Timer timer;
  protected int speed;

  /**
   * The constructor for the AbstractVisualController.
   * @param model the model to produce a view for
   * @param speed the speed of the animation
   */
  AbstractVisualController(AnimatorModel model, int speed) {
    if (model == null) {
      throw new IllegalArgumentException("Model cannot be null");
    }
    this.model = model;
    if (speed < 1) {
      throw new IllegalArgumentException("Speed cannot be 0 or negative");
    }
    this.speed = 1000 / speed;
  }

  public abstract void animate() throws IOException;

  /**
   * Stops the timer when all the animations are done.
   */
  public void stop() {
    if (model.getTick() == model.getFinalTime()) {
      this.timer.stop();
    }
  }

  public int getSpeed() {
    return this.speed;
  }
}
