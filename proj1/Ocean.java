/* Ocean.java */

/**
 *  The Ocean class defines an object that models an ocean full of sharks and
 *  fish.  Descriptions of the methods you must implement appear below.  They
 *  include a constructor of the form
 *
 *      public Ocean(int i, int j, int starveTime);
 *
 *  that creates an empty ocean having width i and height j, in which sharks
 *  starve after starveTime timesteps.
 *
 *  See the README file accompanying this project for additional details.
 */

public class Ocean {

  /**
   *  Do not rename these constants.  WARNING:  if you change the numbers, you
   *  will need to recompile Test4.java.  Failure to do so will give you a very
   *  hard-to-find bug.
   */

  public final static int EMPTY = 0;
  public final static int SHARK = 1;
  public final static int FISH = 2;

  /**
   *  Define any variables associated with an Ocean object here.  These
   *  variables MUST be private.
   */
    private int width;
    private int height;
    private int starveTime;
    private int[][] myOcean;

  /**
   *  The following methods are required for Part I.
   */

  /**
   *  Ocean() is a constructor that creates an empty ocean having width i and
   *  height j, in which sharks starve after starveTime timesteps.
   *  @param i is the width of the ocean.
   *  @param j is the height of the ocean.
   *  @param starveTime is the number of timesteps sharks survive without food.
   */

    public Ocean(int i, int j, int starveTime) {
	this.width = i;
	this.height = j;
	this.starveTime = starveTime;
	myOcean  = new int[i][j];
	for (int x = 0; x < width(); x++) {
	    for (int y = 0; y < height(); y++) {
		myOcean[x][y] = -2;  //initialize to EMPTY (-2)
	    }
	}
	System.out.println(cellContents(i-1,j-1));
	System.out.println(myOcean[i-1][j-1]);

    }

  /**
   *  width() returns the width of an Ocean object.
   *  @return the width of the ocean.
   */

  public int width() {
      return width;
  }

  /**
   *  height() returns the height of an Ocean object.
   *  @return the height of the ocean.
   */

  public int height() {
      return height;
  }

  /**
   *  starveTime() returns the number of timesteps sharks survive without food.
   *  @return the number of timesteps sharks survive without food.
   */

  public int starveTime() {
      return starveTime;
  }

  /**
   *  addFish() places a fish in cell (x, y) if the cell is empty.  If the
   *  cell is already occupied, leave the cell as it is.
   *  @param x is the x-coordinate of the cell to place a fish in.
   *  @param y is the y-coordinate of the cell to place a fish in.
   */

  public void addFish(int x, int y) {
      int[] converted = convertXY(x , y); //convert to correct coordinates
      int i = converted[0];
      int j = converted[1]; 
	  myOcean[i][j] = -1; //insert fish value (-1) into myOcean array
      
  }

  /**
   *  addShark() (with two parameters) places a newborn shark in cell (x, y) if
   *  the cell is empty.  A "newborn" shark is equivalent to a shark that has
   *  just eaten.  If the cell is already occupied, leave the cell as it is.
   *  @param x is the x-coordinate of the cell to place a shark in.
   *  @param y is the y-coordinate of the cell to place a shark in.
   */

  public void addShark(int x, int y) {
      int[] converted = convertXY(x , y); //convert to correct coordinates
      int i = converted[0];
      int j = converted[1];
	  myOcean[i][j] = starveTime(); //insert well-fed shark (value of starveTime) into myOcean array
      }


  /**
   *  cellContents() returns EMPTY if cell (x, y) is empty, FISH if it contains
   *  a fish, and SHARK if it contains a shark.
   *  @param x is the x-coordinate of the cell whose contents are queried.
   *  @param y is the y-coordinate of the cell whose contents are queried.
   */

  public int cellContents(int x, int y) {     
      int[] converted = convertXY(x , y); //convert to correct coordinates
      int i = converted[0];
      int j = converted[1];
      int inspect = myOcean[i][j]; //look at value of {i,j} on myOcean array
      if (inspect == -2) { //if value is -2, cell contains EMPTY (final int 0)
	  return EMPTY;
      } else if (inspect == -1) { //if value is -1, cell contains FISH (final int 2)
	  return FISH;
      } else { //if value is 0 or greater, cell contains SHARK (final int 1)
	  return SHARK;
      }
  }

    /**convertXY() uses modular arithmetic to convert wrapped x,y Ocean coordinates to a regular format **/

    private int[] convertXY(int x, int y) {
	int[] holder = new int[2]; //create holder for converted x,y values
	if (x > width - 1) { 
	    holder[0] = x % width();  
	} else if (x < 0) { 
	    while (x < 0) {
		x = x + width();
	    }
	    holder[0] = x; //set 0th index to converted x
	}
	if (y > height - 1) {
	    holder[1] = y % height();
	} else if (y < 0) {
	    while (y < 0) {
		y = y + height();
	    }
	    holder[1] = y; //set 1st index to converted y
	}
	return holder; //return holder{x,y}
    }

    /**neighbors() takes a x,y cell coordinate and returns an int[2] containing the amount of neighboring
       fish and sharks respectively **/

    private int[] neighbors(int x, int y) {
	int fishCount = 0;
	int sharkCount = 0;
	int emptyCount = 0;
        int top = cellContents(x, y - 1);
        int topLeft = cellContents(x - 1, y - 1);
	int left = cellContents(x - 1, y);
	int bottomLeft = cellContents(x - 1, y + 1);
        int bottom = cellContents(x, y + 1);
	int bottomRight = cellContents(x + 1, y + 1);
        int right = cellContents(x + 1, y);
        int topRight = cellContents(x + 1, y - 1);
	int[] perimeter = {top, topLeft, left, bottomLeft, bottom, bottomRight, right, topRight};
	for (int i = 0; i < 8; i++) {
	    if (perimeter[i] == EMPTY) {
		emptyCount++;
	    } else if (perimeter[i] == SHARK) {
		sharkCount++;
	    } else if (perimeter[i] == FISH) {
		fishCount++;
	    }
	}
	int[] emptyFishShark = {emptyCount, fishCount, sharkCount}; //create array containing amount of neighboring empty, fish, and shark cells
	return emptyFishShark; 

    }
      
  /**
   *  timeStep() performs a simulation timestep as described in README.
   *  @return an ocean representing the elapse of one timestep.
   */

  public Ocean timeStep() {
      Ocean afterStep = new Ocean(width, height, starveTime);  //create a new blank ocean to hold the new cell values after timeStep()
      for (int i = 0; i < width; i++) {
	  for (int j = 0; j < height; j++) {
	      int cellName = this.cellContents(i,j);  
	      int cellValue = this.myOcean[i][j];
	      int[] neighbors = this.neighbors(i,j);
	      if (cellName == EMPTY) {
		  if (neighbors[1] >= 2 && neighbors[2] < 2) {
		      afterStep.addFish(i,j);
		  } else if (neighbors[1] >= 2 && neighbors[2] >= 2) {
		      afterStep.addShark(i,j);
		  } else {
		      afterStep.myOcean[i][j] = -2;
		  }
	      } else if (cellName == SHARK) {               //if item in the Ocean cell is a Shark, then check neighbors
		  if (neighbors[1] > 0) {                   //if Shark has 1 or more fish neighbors
		      afterStep.addShark(i,j);              //then feed Shark (set value to starveTime)
		  } else if (this.myOcean[i][j] == 0) {     //if Shark has no fish neighbors and is starved
		      afterStep.myOcean[i][j] = -2;         //then kill Shark (set to empty)
		  } else {                                  //else Shark gets hungrier
		      afterStep.myOcean[i][j] = cellValue--;
		  }
	      } else if (cellName == FISH) {
		  if (neighbors[2] == 1) {
		      afterStep.myOcean[i][j] = -2;
		  } else if (neighbors[2] >= 2) {
		      afterStep.addShark(i,j);
		  }
	      }
	  }
      }
      return afterStep;
  }

  /**
   *  The following method is required for Part II.
   */

  /**
   *  addShark() (with three parameters) places a shark in cell (x, y) if the
   *  cell is empty.  The shark's hunger is represented by the third parameter.
   *  If the cell is already occupied, leave the cell as it is.  You will need
   *  this method to help convert run-length encodings to Oceans.
   *  @param x is the x-coordinate of the cell to place a shark in.
   *  @param y is the y-coordinate of the cell to place a shark in.
   *  @param feeding is an integer that indicates the shark's hunger.  You may
   *         encode it any way you want; for instance, "feeding" may be the
   *         last timestep the shark was fed, or the amount of time that has
   *         passed since the shark was last fed, or the amount of time left
   *         before the shark will starve.  It's up to you, but be consistent.
   */

  public void addShark(int x, int y, int feeding) {
    // Your solution here.
  }

  /**
   *  The following method is required for Part III.
   */

  /**
   *  sharkFeeding() returns an integer that indicates the hunger of the shark
   *  in cell (x, y), using the same "feeding" representation as the parameter
   *  to addShark() described above.  If cell (x, y) does not contain a shark,
   *  then its return value is undefined--that is, anything you want.
   *  Normally, this method should not be called if cell (x, y) does not
   *  contain a shark.  You will need this method to help convert Oceans to
   *  run-length encodings.
   *  @param x is the x-coordinate of the cell whose contents are queried.
   *  @param y is the y-coordinate of the cell whose contents are queried.
   */

  public int sharkFeeding(int x, int y) {
    // Replace the following line with your solution.
    return 0;
  }

}
