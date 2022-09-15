import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import cs3500.animator.controller.Controller;
import cs3500.animator.controller.IController;
import cs3500.animator.controller.InteractiveVisualViewController;
import cs3500.animator.io.AnimationBuilder;
import cs3500.animator.io.AnimationFileReader;
import cs3500.animator.io.TweenModelBuilder;
import cs3500.animator.model.AnimatorModel;
import cs3500.animator.view.AnimatorPanel;
import cs3500.animator.view.AnimatorView;
import cs3500.animator.view.AnimatorViewCreator;
import cs3500.animator.view.AnimatorViewVisual;
import cs3500.animator.view.InteractiveVisualView;
import cs3500.animator.view.ViewType;

/**
 * The main class of the animator to run the main method.
 */
public class Main {
  static AnimatorModel model = null;

  /**
   * The main method for the animator.
   *
   * @param args the input arguments
   * @throws IOException if view is not able to be rendered properly
   */
  public static void main(String[] args) throws IOException {
    String in = null;
    String viewType = null;
    String out = null;
    String parseSpeed;
    AnimatorView view;
    List<AnimatorPanel> modelStates = new ArrayList<>();

    int speed = 1;
    for (int i = 0; i < args.length; i++) {
      String s = args[i];
      switch (s) {
        case "-in":
          in = args[i + 1];
          break;
        case "-view":
          viewType = args[i + 1];
          break;
        case "-out":
          out = args[i + 1];
          break;
        case "-speed":
          parseSpeed = args[i + 1];
          speed = Integer.parseInt(parseSpeed);
          break;
        default:
          // no default case because jar and -jar need to work
      }
    }

    if (in == null) {
      throw new IllegalArgumentException("No input file given");
    }

    AnimationFileReader reader = new AnimationFileReader();
    TweenModelBuilder<AnimatorModel> tweenModelBuilder = new AnimationBuilder();
    try {
      model = reader.readFile(in, tweenModelBuilder);
    } catch (FileNotFoundException e) {
      System.out.println(e.getMessage());
    }

    if (model == null) {
      throw new IllegalArgumentException("Model could not be read");
    }

    if (viewType != null) {
      switch (viewType) {
        case "visual":
          view = AnimatorViewCreator.create(ViewType.Visual, model);
          IController controller = new Controller(model, (AnimatorViewVisual) view, speed);
          controller.animate();
          controller.stop();
          break;
        case "svg":
          model.setTempo(speed);
          view = AnimatorViewCreator.create(ViewType.SVG, model);
          output(out, view);
          break;
        case "text":
          view = AnimatorViewCreator.create(ViewType.Text, model);
          model.setTempo(speed);
          output(out, view);
          break;
        case "interactive":
          view = AnimatorViewCreator.create(ViewType.Interactive, model);
          IController interactiveController =
                  new InteractiveVisualViewController(model, (InteractiveVisualView) view, speed);
          interactiveController.animate();
          interactiveController.stop();
          break;
        default:
          throw new IllegalArgumentException("Incorrect viewtype found");
      }
    }
  }

  /**
   * A method to write to a certain file if it exists.
   * @param out the file path
   * @param view the view to be exported
   * @throws IOException if inputs and outputs are invalid
   */
  private static void output(String out, AnimatorView view) throws IOException {
    if (out == null && view != null) {
      System.out.println(view.toString());
    } else {
      FileOutputStream outputStream = new FileOutputStream(out);
      byte[] strToBytes = view.toString().getBytes();
      outputStream.write(strToBytes);
      outputStream.close();
    }
  }


}
