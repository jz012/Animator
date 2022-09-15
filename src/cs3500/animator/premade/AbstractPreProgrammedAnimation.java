package cs3500.animator.premade;

import java.awt.Color;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import cs3500.animator.model.Rectangle;

/**
 * An abstract class to hold common methods for a pre-programmed
 * animation.
 */
public abstract class AbstractPreProgrammedAnimation {
  protected static final List<String> moves = new ArrayList<>();
  protected static Rectangle[] rectangles = null;
  protected static final HashMap<Rectangle, Float> rectangleHashMap = new HashMap<>();
  protected static int totalSortTime = 1;
  protected static String fileName;


  /**
   * The initial things a mainfunction in our prebuilt animations should do.
   * Takes in designated file name and the number of rectangles to be made in the animation.
   *
   * @param args the arguments to be input
   * @throws IOException for invalid outputs.
   */
  public static void initialMain(String[] args) throws IOException {
    String numberOfRectangles;

    int numRectangles = 12;
    for (int i = 0; i < args.length; i++) {
      String s = args[i];
      switch (s) {
        case "-out":
          fileName = args[i + 1];
          break;
        case "-rectangles":
          numberOfRectangles = args[i + 1];
          numRectangles = Integer.parseInt(numberOfRectangles);
          break;
        default:
          // no default case
      }
    }
    rectangles = shuffledRecs(numRectangles).toArray(new Rectangle[0]);
  }

  /**
   * Creates rectangles along the same y axis with different heights and colors randomly.
   *
   * @param n the number of rectangles to be made.
   * @return a list of rectangles.
   */
  public static List<Rectangle> shuffledRecs(int n) {
    List<Rectangle> rectangles = new ArrayList<>();
    Random rand = new Random(5);
    for (int i = 1; i < n + 1; i++) {
      int x = (rand.nextInt(25)) + 6;
      Rectangle rec = new Rectangle("Rectangle" + i, i * 120, 100,
              100, x * 20, new Color(10 * i, 6 * i, 7 * i),
              1, 30);
      rectangles.add(rec);
    }

    return rectangles;
  }

  /**
   * Sets each rectangle to a certain X-position in an animation.
   */
  public static void setRectangleX() {
    for (Rectangle rec : rectangles) {
      rectangleHashMap.put(rec, rec.getX());
    }
  }

  /**
   * Outputs the string to a file.
   *
   * @param out   the string to be made into a file.
   * @param title the title of the file to be made.
   * @throws IOException if an invalid out is made.
   */
  static void output(String out, String title) throws IOException {
    if (out == null || out.isEmpty() || title == null || title.isEmpty()) {
      throw new IllegalArgumentException("invalid arguments");
    }
    FileOutputStream outputStream = new FileOutputStream(title);
    byte[] strToBytes = out.getBytes();
    outputStream.write(strToBytes);
    outputStream.close();
  }

  /**
   * Creates the string for canvas size, rectangles, and moves to be parsed by the file reader.
   *
   * @param s the string to be appended to.
   */
  public static void createCanvasRectanglesMoves(StringBuilder s) {
    s.append("canvas 500 500 \n");
    for (Rectangle rectangle : rectangles) {
      s.append("rectangle name " + rectangle.getName() + " min-x " + rectangle.getX() +
              " min-y " + rectangle.getY() + " width " + rectangle.getWidth() + " height " +
              rectangle.getHeight() + " color " + (float) rectangle.getR() / 255 + " "
              + (float) rectangle.getG() / 255 + " " + (float) rectangle.getB() / 255 + " " +
              "from 1 to 2500 \n");
    }
    for (String swap : moves) {
      s.append(swap);
    }
  }

  /**
   * Swaps two rectangles x positions in a string format recognizable by the file parser
   * as a move.
   *
   * @param first  the first rectangle to be moved
   * @param second the second rectangle whose position the first will be moved to.
   * @return the string that can be read as a move swap.
   */
  public static String swapX(Rectangle first, Rectangle second) {
    return "move name " + first.getName() + " moveto " + rectangleHashMap.get(first) + " " +
            first.getY() + " " + rectangleHashMap.get(second) + " " + second.getY() + " from "
            + totalSortTime +
            " to " + (totalSortTime + 10) + "\n";
  }

  /**
   * Swaps two rectangles x positions in a string format recognizable by the file parser
   * as a move.
   *
   * @param first  the first rectangle to be swapped.
   * @param second the second rectangles float position to be moved to.
   */

  public static String swapX(Rectangle first, float second) {
    return "move name " + first.getName() + " moveto " + rectangleHashMap.get(first) + " " +
            first.getY() + " " + second + " " + first.getY() + " from " + (totalSortTime + 11) +
            " to " + (totalSortTime + 21) + "\n";
  }
}
