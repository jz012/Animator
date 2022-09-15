package cs3500.animator.view;

import java.awt.Dimension;

/**
 * The interface for the animator view. It extends animator view interface and implements methods
 * required for the visual view.
 */
public interface AnimatorViewVisual extends AnimatorView {

  /**
   * Get the preferred size of the visual view.
   * @param width the width
   * @param height the height
   * @return the dimensions
   */
  Dimension getPreferredSize(int width, int height);

  /**
   * Refreshes the panel to show the current state.
   */
  void refresh();

  /**
   * Shows an error message when something goes wrong.
   * @param error the error to be output
   */
  void showErrorMessage(String error);
}
