package cs3500.animator.view;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;

import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JLabel;

import cs3500.animator.model.AnimatorModelState;

/**
 * The class that represents the interactive visual view. It extends the original visual view
 * because the only additional implementation is that there are additional JButtons. It adds a
 * panel with these buttons onto the original view.
 */
public class InteractiveVisualView extends VisualView implements AnimatorViewInteractive {
  private final JButton restart;
  private final JButton start;
  private final JButton pause;
  private final JButton resume;
  private final JButton loop;
  private final JButton speedUp;
  private final JButton speedDown;
  private final JLabel looping;
  private boolean isLooping = false;

  /**
   * The constructor for the interactive visual view.
   *
   * @param model the model to be input
   */
  public InteractiveVisualView(AnimatorModelState model) {
    super(model);
    looping = new JLabel("Currently looping : " + isLooping);

    JPanel labels = new JPanel();
    labels.add(looping);
    this.add(labels, BorderLayout.NORTH);

    JPanel interactive = new JPanel();
    start = new JButton("Start");
    start.setActionCommand("Start Button");
    interactive.add(start);

    restart = new JButton("Restart");
    restart.setActionCommand("Restart Button");
    interactive.add(restart);

    pause = new JButton("Pause");
    pause.setActionCommand("Pause Button");
    interactive.add(pause);

    resume = new JButton("Resume");
    resume.setActionCommand("Resume Button");
    interactive.add(resume);

    loop = new JButton("Loop");
    loop.setActionCommand("Loop Button");
    interactive.add(loop);

    speedUp = new JButton("Faster");
    speedUp.setActionCommand("Fast Button");
    interactive.add(speedUp);

    speedDown = new JButton("Slower");
    speedDown.setActionCommand("Slow Button");
    interactive.add(speedDown);
    interactive.setLayout(new GridLayout());
    this.add(interactive, BorderLayout.SOUTH);
  }

  @Override
  public boolean getIsLooping() {
    return isLooping;
  }

  @Override
  public void setListener(ActionListener listener) {
    start.addActionListener(listener);
    restart.addActionListener(listener);
    pause.addActionListener(listener);
    resume.addActionListener(listener);
    loop.addActionListener(listener);
    speedUp.addActionListener(listener);
    speedDown.addActionListener(listener);
  }

  @Override
  public void clickButton(String button) {
    switch (button) {
      case "start":
        start.doClick();
        break;
      case "restart":
        restart.doClick();
        break;
      case "pause":
        pause.doClick();
        break;
      case "resume":
        resume.doClick();
        break;
      case "loop":
        loop.doClick();
        break;
      case "faster":
        speedUp.doClick();
        break;
      case "slower":
        speedDown.doClick();
        break;
      default:
        throw new IllegalArgumentException("Button doesn't exist");
    }
  }

  @Override
  public void changeLoop() {
    isLooping = !isLooping;
    looping.setText("Currently looping : " + isLooping);
  }
}
