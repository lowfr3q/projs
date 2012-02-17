/* RunLengthEncoding.java */

/**
 *  The RunLengthEncoding class defines an object that run-length encodes an
 *  Ocean object.  Descriptions of the methods you must implement appear below.
 *  They include constructors of the form
 *
 *      public RunLengthEncoding(int i, int j, int starveTime);
 *      public RunLengthEncoding(int i, int j, int starveTime,
 *                               int[] runTypes, int[] runLengths) {
 *      public RunLengthEncoding(Ocean ocean) {
 *
 *  that create a run-length encoding of an Ocean having width i and height j,
 *  in which sharks starve after starveTime timesteps.
 *
 *  The first constructor creates a run-length encoding of an Ocean in which
 *  every cell is empty.  The second constructor creates a run-length encoding
 *  for which the runs are provided as parameters.  The third constructor
 *  converts an Ocean object into a run-length encoding of that object.
 *
 *  See the README file accompanying this project for additional details.
 */

public class RunLengthEncoding {
    private int encodingLength, width, height, size;
    private int starveTime;
    private int runCount = 1;
    private myListNode head = null;


  /**
   *  The following methods are required for Part II.
   */

  /**
   *  RunLengthEncoding() (with three parameters) is a constructor that creates
   *  a run-length encoding of an empty ocean having width i and height j,
   *  in which sharks starve after starveTime timesteps.
   *  @param i is the width of the ocean.
   *  @param j is the height of the ocean.
   *  @param starveTime is the number of timesteps sharks survive without food.
   */

  public RunLengthEncoding(int i, int j, int starveTime) {
      this.encodingLength = 1;
      this.starveTime = starveTime;
      this.width = i;
      this.height = j;
      insertFront(0 , i * j);
  }

  /**
   *  RunLengthEncoding() (with five parameters) is a constructor that creates
   *  a run-length encoding of an ocean having width i and height j, in which
   *  sharks starve after starveTime timesteps.  The runs of the run-length
   *  encoding are taken from two input arrays.  Run i has length runLengths[i]
   *  and species runTypes[i].
   *  @param i is the width of the ocean.
   *  @param j is the height of the ocean.
   *  @param starveTime is the number of timesteps sharks survive without food.
   *  @param runTypes is an array that represents the species represented by
   *         each run.  Each element of runTypes is Ocean.EMPTY, Ocean.FISH,
   *         or Ocean.SHARK.  Any run of sharks is treated as a run of newborn
   *         sharks (which are equivalent to sharks that have just eaten).
   *  @param runLengths is an array that represents the length of each run.
   *         The sum of all elements of the runLengths array should be i * j.
   */

  public RunLengthEncoding(int i, int j, int starveTime,
                           int[] runTypes, int[] runLengths) {
      this.encodingLength = runTypes.length;
      this.starveTime = starveTime;
      this.width = i;
      this.height = j;
      for (int xx = 0; xx < runTypes.length; xx++) {
	  int type = runTypes[xx];
	  int amt = runLengths[xx];
	  insertEnd(type, amt, starveTime);
	
      }
  }

  /**
   *  restartRuns() and nextRun() are two methods that work together to return
   *  all the runs in the run-length encoding, one by one.  Each time
   *  nextRun() is invoked, it returns a different run (represented as a
   *  TypeAndSize object), until every run has been returned.  The first time
   *  nextRun() is invoked, it returns the first run in the encoding, which
   *  contains cell (0, 0).  After every run has been returned, nextRun()
   *  returns null, which lets the calling program know that there are no more
   *  runs in the encoding.
   *
   *  The restartRuns() method resets the enumeration, so that nextRun() will
   *  once again enumerate all the runs as if nextRun() were being invoked for
   *  the first time.
   *
   *  (Note:  Don't worry about what might happen if nextRun() is interleaved
   *  with addFish() or addShark(); it won't happen.)
   */

  /**
   *  restartRuns() resets the enumeration as described above, so that
   *  nextRun() will enumerate all the runs from the beginning.
   */

  public void restartRuns() {
      runCount = 1;
  }
  /**
   *  nextRun() returns the next run in the enumeration, as described above.
   *  If the runs have been exhausted, it returns null.  The return value is
   *  a TypeAndSize object, which is nothing more than a way to return two
   *  integers at once.
   *  @return the next run in the enumeration, represented by a TypeAndSize
   *          object.
   */

  public TypeAndSize nextRun() {
      int tempType, tempSize;
      myListNode current = head;
      if (runCount <= encodingLength) {
	  current = nth(runCount);
	  tempType = current.type;
	  tempSize = current.amt;
	  runCount++;
	  return new TypeAndSize(tempType, tempSize);
      } else {
	  return null;
      }
  }


  /**
   *  nth() returns the item at the specified position.  If position < 1 or
   *  position > this.length(), null is returned.  Otherwise, the item at
   *  position "position" is returned.  The list does not change.
   *  @param position the desired position, from 1 to length(), in the list.
   *  @return the item at the given position in the list.
   **/

public myListNode nth(int position) {
    myListNode currentNode;

    if ((position < 1) || (head == null)) {
      return null;
    } else {
      currentNode = head;
      while (position > 1) {
        currentNode = currentNode.next;
        if (currentNode == null) {
          return null;
        }
        position--;
      }
      return currentNode;
    }
 }


  /**
   *  insertFront() inserts item "obj" at the beginning of this list.
   *  @param obj the item to be inserted.
   **/

    private void insertFront(int type, int amt, int sT) {
	head = new myListNode(type, amt, sT, head);
      size++;
  }

    private void insertFront(int type, int amt) {
	insertFront(type, amt, -1);
    }

  /**
   *  insertEnd() inserts item "obj" at the end of this list.
   *  @param obj the item to be inserted.
   **/

    private void insertEnd(int type, int amt, int sT) {
    if (head == null) {
	head = new myListNode(type, amt, sT);
    } else {
	myListNode insert = head;
	while (insert.next != null) {
	    insert = insert.next;
	}
	insert.next = new myListNode(type , amt, sT);
    }
    size++;
  }

    private void insertEnd(int type, int amt) {
	insertEnd(type, amt, -1);
    }

  /**
   *  toOcean() converts a run-length encoding of an ocean into an Ocean
   *  object.  You will need to implement the three-parameter addShark method
   *  in the Ocean class for this method's use.
   *  @return the Ocean represented by a run-length encoding.
   */

  public Ocean toOcean() {
      System.out.println(starveTime);
      Ocean oceanHolder = new Ocean(width, height, starveTime);
      int t, a;
      int runningTotal = 0, count = 0;
      for (myListNode start = head; start != null; start = start.next) {
	  t = start.type;
	  a = start.amt;
	  runningTotal = runningTotal + a;
	  while (count < runningTotal) {
	      int[] xyCell = convertToXY(count);
	      int i = xyCell[0];;
	      int j = xyCell[1];;
	      switch (t) {
	      case 1:
		 int sT = start.starveTime;
		  oceanHolder.addShark(i , j, sT);
		  break;
	      case 2:
		  oceanHolder.addFish(i , j);
		  break;
	      }
	      count++;
	  }
      }
      return oceanHolder;
  }
	

    private int[] convertToXY(int xx) {
	int i,j;
	i = xx % width;
	j = xx / width;
	int[] converted = {i , j};
	return converted;
    }



  /**
   *  The following method is required for Part III.
   */

  /**
   *  RunLengthEncoding() (with one parameter) is a constructor that creates
   *  a run-length encoding of an input Ocean.  You will need to implement
   *  the sharkFeeding method in the Ocean class for this constructor's use.
   *  @param sea is the ocean to encode.
   */

  public RunLengthEncoding(Ocean sea) {
      int w = sea.width(), l = sea.height();
      width = w;
      height = l;
      int seaLength =  w * l;
      int eCount= 0, sCount = 0, fCount = 0;
      int mark;
      int[] converted;
      for (int counter = 0; counter < seaLength; counter++) {
	  converted = convertToXY(counter);
	  int i = converted[0], j = converted[1];
	  int cellType = sea.cellContents(i,j);
	  switch (cellType) {
	  case 0:
	      sCount = 0;
	      fCount = 0;
	      eCount++;
	      if (sea.cellContents(convertToXY(counter + 1)) != sea.EMPTY) {
		  insertEnd(sea.EMPTY, eCount);
		  eCount = 0;
	      }
	      break;
	  case 1:
	      eCount = 0;
	      fCount = 0;
	      if (sea.sharkFeeding(convertToXY(counter - 1)) == sea.sharkFeeding(i,j)) {
		  sCount++;
	      } else {
		  sCount = 1;
	      }
	      if (sea.sharkFeeding(convertToXY(counter + 1)) != sea.sharkFeeding(i,j)) {
		  insertEnd(sea.SHARK, sCount, sea.sharkFeeding(i,j));
		  sCount = 0;
	      }
	      break;
	  case 2:
	      sCount = 0;
	      eCount = 0;
	      fCount++;
	      if (sea.cellContents(convertToXY(counter + 1)) != sea.FISH) {
		  insertEnd(sea.FISH, fCount);
		  fCount = 0;
	      }
	      break;
	  }
      }
      check();
  }

     
  /**
   *  The following methods are required for Part IV.
   */

  /**
   *  addFish() places a fish in cell (x, y) if the cell is empty.  If the
   *  cell is already occupied, leave the cell as it is.  The final run-length
   *  encoding should be compressed as much as possible; there should not be
   *  two consecutive runs of sharks with the same degree of hunger.
   *  @param x is the x-coordinate of the cell to place a fish in.
   *  @param y is the y-coordinate of the cell to place a fish in.
   */

  public void addFish(int x, int y) {
    // Your solution here, but you should probably leave the following line
    //   at the end.
    check();
  }

  /**
   *  addShark() (with two parameters) places a newborn shark in cell (x, y) if
   *  the cell is empty.  A "newborn" shark is equivalent to a shark that has
   *  just eaten.  If the cell is already occupied, leave the cell as it is.
   *  The final run-length encoding should be compressed as much as possible;
   *  there should not be two consecutive runs of sharks with the same degree
   *  of hunger.
   *  @param x is the x-coordinate of the cell to place a shark in.
   *  @param y is the y-coordinate of the cell to place a shark in.
   */

  public void addShark(int x, int y) {
    // Your solution here, but you should probably leave the following line
    //   at the end.
    check();
  }

  /**
   *  check() walks through the run-length encoding and prints an error message
   *  if two consecutive runs have the same contents, or if the sum of all run
   *  lengths does not equal the number of cells in the ocean.
   */

  public void check() {
      myListNode starter = this.head;
      int count = 0;
      while (starter.next != null) {
	  int t = starter.type, a = starter.amt, sT = starter.starveTime;
	  int nextT = starter.next.type, nextA = starter.next.amt, nextSt = starter.next.starveTime;
	  int[] current = {t,a, sT}, next = {nextT,nextA,nextSt};
	  count = count + a;
	  if (current == next) {
	      System.out.println("Warning, problem found: recurring cells are next to eachother");
	  }
	  starter = starter.next;
      }
      if (count != width * height) {
	  System.out.println("Warning: runEncodingLength != Ocean length");
      }
      
}

    public static void main(String[] args) {
	Integer xx = null;
	System.out.println(xx.intValue());}
}