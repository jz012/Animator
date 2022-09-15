package cs3500.animator.controller;

import java.awt.event.ActionEvent;
import java.io.IOException;

import javax.swing.Timer;

import cs3500.animator.model.AnimatorModel;
import cs3500.animator.view.AnimatorViewInteractive;


/**
 * The class that represents the interactive view controller. It extends the abstract controller
 * test and implements the associating functions required to make the listeners handle different
 * events.
 */
public class InteractiveVisualViewController extends AbstractVisualController {
  private final AnimatorViewInteractive interactiveView;
  private boolean running = false;
  private boolean pressed = false;
  private boolean loop = false;

  /**
   * The constructor for the interactive view controller.
   *
   * @param model the model to input
   * @param view the view to create
   * @param speed the base speed of the view
   */
  public InteractiveVisualViewController(AnimatorModel model, AnimatorViewInteractive view,
                                         int speed) {
    super(model, speed);
    if (view == null) {
      throw new IllegalArgumentException("View cannot be null");
    }
    this.interactiveView = view;

  }

  /**
   * Animates the visual view. Holds a timer that delays at the model's tempo.
   *
   * @throws IOException with an invalid output or input.
   */
  public void animate() throws IOException {
    interactiveView.renderView();
    timer = new Timer(this.speed, this);
    timer.start();
    interactiveView.setListener(this);
  }

  /**
   * Refreshes the view and increments the tick for the model.
   * As part of the actionListener, whenever an event occurs, this
   * is called. This method also runs through different cases of action commands fed through it
   * through listeners from the view.
   *
   * @param e the event that occurs.
   */
  @Override
  public void actionPerformed(ActionEvent e) {
    if (e.getActionCommand() != null) {
      switch (e.getActionCommand()) {
        case "Start Button":
          if (!pressed) {
            running = true;
            pressed = true;
          }
          break;
        case "Restart Button":
          model.reset();
          model.changeStateAtTick(model.getTick());
          timer.restart();
          break;
        case "Pause Button":
          running = false;
          break;
        case "Resume Button":
          if (pressed) {
            running = true;
          }
          break;
        case "Loop Button":
          loop = !loop;
          interactiveView.changeLoop();
          break;
        case "Fast Button":
          this.speed = this.speed - 2;
          if (this.speed <= 0) {
            this.speed = 1;
          }
          this.timer.setDelay(this.speed);
          break;
        case "Slow Button":
          this.speed = this.speed + 2;
          this.timer.setDelay(this.speed);
          break;
        default:
          break;

      }
    }
    if (running) {
      interactiveView.refresh();
      model.addTick();
      model.changeStateAtTick(model.getTick());
      if (loop && model.getTick() == model.getFinalTime()) {
        model.reset();
        model.changeStateAtTick(model.getTick());
        timer.restart();
      }
    }
  }

}
