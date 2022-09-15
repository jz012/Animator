package cs3500.animator.premade;


import java.io.IOException;

import cs3500.animator.model.Rectangle;

/**
 * A class to hold a pre-programmed visual animation of a BubbleSort algorithm.
 * Running this program creates by default 12 pseudorandom rectangles of
 * various orders and sorts
 * them by their height using the bubble sort algorithm.
 * This is then created into a string and
 * output into a file.
 */
public class BubbleSortPreProgrammedAnimation extends AbstractPreProgrammedAnimation {

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
    fileName = "BubbleSortAnimation.txt";
    initialMain(args);
    if (fileName.isEmpty() || fileName == null) {
      fileName = "BubbleSortAnimation.txt";
    }
    bubbleSort(rectangles);
    StringBuilder s = new StringBuilder();
    createCanvasRectanglesMoves(s);
    output(s.toString(), fileName);
  }


  /**
   * A bubble sorting algorithm. A simple sorting algorithm that repeatedly
   * swaps adjacent rectangles if they are in the wrong order and sorts
   * them by their height. Simultaneously adds moves in string format as each swap is made.
   *
   * @param arr the rectangle array to be sorted.
   */
  private static void bubbleSort(Rectangle[] arr) {
    int n = arr.length;
    setRectangleX();
    for (int i = 0; i < n - 1; i++) {
      for (int j = 0; j < n - i - 1; j++) {
        if (arr[j].getHeight() > arr[j + 1].getHeight()) {
          Rectangle temp = arr[j];
          arr[j] = arr[j + 1];
          String firstSwap = swapX(arr[j + 1], temp);
          float tempX = rectangleHashMap.get(arr[j + 1]);
          rectangleHashMap.replace(arr[j + 1], rectangleHashMap.get(temp));
          arr[j + 1] = temp;
          String secondSwap = swapX(temp, tempX);
          rectangleHashMap.replace(temp, tempX);
          moves.add(firstSwap);
          moves.add(secondSwap);
          totalSortTime = totalSortTime + 22;
        }
      }
    }
  }
}


