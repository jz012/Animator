package cs3500.animator.premade;

import java.io.IOException;


import cs3500.animator.model.Rectangle;

/**
 * A class to hold a pre-programmed visual animation of a SelectionSort algorithm.
 * Running this program creates by default 12 pseudorandom rectangles of
 * various orders and sorts
 * them by their height using the bubble sort algorithm. This is
 * then created into a string and
 * output into a file.
 */
public class SelectionSortPreProgrammedAnimation extends AbstractPreProgrammedAnimation {

  /**
   * The main file to add arguments to the animation. Although the animation can be created
   * without any arguments, you can customize output file name and rectangle number.
   * Runs the sorting method on the algorithm on an array of rectangles and outputs it to a file.
   *
   * @param args the arguments to be added. -out allows for you to change file name, -rectangles
   *             allows you to change rectangle number in the animation.
   * @throws IOException for invalid outputs.
   */

  public static void main(String[] args) throws IOException {
    fileName = "SelectionSortAnimation.txt";
    initialMain(args);
    if (fileName.isEmpty() || fileName == null) {
      fileName = "SelectionSortAnimation.txt";
    }
    selectionSort(rectangles);
    StringBuilder s = new StringBuilder();
    createCanvasRectanglesMoves(s);
    output(s.toString(), fileName);
  }

  /**
   * A selection sorting algorithm. Ordered by their height,
   * this algorithm continuously finds the smallest element and swaps it with the first element.
   * Simultaneously adds moves in string format as each swap is made.
   *
   * @param arr the rectangle array to be sorted.
   */

  private static void selectionSort(Rectangle[] arr) {
    int n = arr.length;
    setRectangleX();
    for (int i = 0; i < n - 1; i++) {
      int minimumIndex = i;
      for (int j = i + 1; j < n; j++) {
        if (arr[j].getHeight() < arr[minimumIndex].getHeight()) {
          minimumIndex = j;
        }
        Rectangle swapTemp = arr[minimumIndex];
        arr[minimumIndex] = arr[i];
        String firstSwap = swapX(arr[i], swapTemp);
        float tempX = rectangleHashMap.get(arr[i]);
        rectangleHashMap.replace(arr[i], rectangleHashMap.get(swapTemp));
        arr[i] = swapTemp;
        String secondSwap = swapX(swapTemp, tempX);
        rectangleHashMap.replace(swapTemp, tempX);
        moves.add(firstSwap);
        moves.add(secondSwap);
        totalSortTime = totalSortTime + 22;
      }
    }
  }
}
