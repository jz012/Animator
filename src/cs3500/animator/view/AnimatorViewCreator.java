package cs3500.animator.view;


import cs3500.animator.model.AnimatorModel;

/**
 * The creator for the AnimatorView interface. It creates a view according to what the input view
 * type is.
 */
public class AnimatorViewCreator {

  /**
   * The method that creates an object from the AnimatorView interface of the specific class that
   * has been implemented.
   *
   * @param viewType the type of view to create
   * @param model the model to create the view from
   * @return one of the four view types
   * @throws IllegalArgumentException if parameters are null
   * @throws IllegalArgumentException if the viewtype is not implemented in the codebase
   */
  public static AnimatorView create(ViewType viewType, AnimatorModel model) {
    if (viewType != null && model != null) {
      switch (viewType) {
        case SVG:
          return new AnimatorSVGView(model);
        case Text:
          return new AnimatorTextView(model);
        case Visual:
          return new VisualView(model);
        case Interactive:
          return new InteractiveVisualView(model);
        default:
          throw new IllegalArgumentException("viewType is not found");
      }
    } else {
      throw new IllegalArgumentException("null viewtype or model");
    }
  }
}
