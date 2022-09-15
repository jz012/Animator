package cs3500.animator.view;

/**
 * The abstract class for text based views. This class enables the implementation of future views
 * and implements the main animator view interface.
 */
abstract class AbstractTextView implements AnimatorView {
  protected ViewType type;

  /**
   * The constructor for an abstract view.
   */
  AbstractTextView() {
    //Views are created through extended classes
  }
}
