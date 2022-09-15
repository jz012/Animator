package cs3500.animator.view;


import java.awt.Dimension;
import java.awt.BorderLayout;


import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JOptionPane;

import cs3500.animator.model.AnimatorModelState;

/**
 * The VisualView class that implements the AnimatorViewVisual. It extends JFrame and implements
 * the methods necessary to render the JPanels onto the JFrame. These include setting the visible
 * parameter, refreshing the frame, and showing an error message.
 */
public class VisualView extends JFrame implements AnimatorViewVisual {
  /**
   * The constructor for the visual view.
   *
   * @param model the model to be input
   */
  public VisualView(AnimatorModelState model) {
    super();
    this.setTitle("Animator");
    this.setSize(model.getWidth(), model.getHeight());
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    AnimatorPanel animatorPanel = new AnimatorPanel(model);
    animatorPanel.setPreferredSize(getPreferredSize(model.getWidth(), model.getHeight()));
    this.setLayout(new BorderLayout());
    JScrollPane scrollPanel = new JScrollPane(animatorPanel);
    scrollPanel.setPreferredSize(new Dimension(model.getWidth(), model.getHeight()));
    this.add(scrollPanel);
  }

  @Override
  public Dimension getPreferredSize(int width, int height) {
    return new Dimension(width, height);
  }

  @Override
  public void renderView() {
    setVisible(true);
  }

  @Override
  public void refresh() {
    this.repaint();
  }

  @Override
  public void showErrorMessage(String error) {
    JOptionPane.showMessageDialog(this, error, "Error",
            JOptionPane.ERROR_MESSAGE);
  }


}
