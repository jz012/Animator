package cs3500.animator.controller;

import java.awt.event.ActionEvent;
import java.io.IOException;

import javax.swing.Timer;

import cs3500.animator.model.AnimatorModel;
import cs3500.animator.view.AnimatorViewVisual;

/**
 * De-facto controller to access model information for the view. Will
 * be updated to include actual controller functions including
 * buttons and other things for the panel. Implements an ActionListener to
 * take in the timer and eventually other events.
 */
public class Controller extends AbstractVisualController {

  private final AnimatorViewVisual view;


  /**
   * Constructor for what the controller controls. T
   *
   * @param model the model to pull info from.
   * @param view  the view to be changed.
   */
  public Controller(AnimatorModel model, AnimatorViewVisual view, int speed) {
    super(model, speed);
    if (view == null) {
      throw new IllegalArgumentException("View cannot be null");
    }
    this.view = view;
  }

  /**
   * Animates the visual view. Holds a timer that delays at the model's tempo.
   *
   * @throws IOException with an invalid output or input.
   */
  public void animate() throws IOException {
    view.renderView();
    timer = new Timer(this.speed, this);
    timer.start();
  }


  /**
   * Refreshes the view and increments the tick for the model.
   * As part of the actionListener, whenever an event occurs, this
   * is called.
   *
   * @param e the event that occurs.
   */
  @Override
  public void actionPerformed(ActionEvent e) {
    view.refresh();
    model.addTick();
    model.changeStateAtTick(model.getTick());
  }
}
