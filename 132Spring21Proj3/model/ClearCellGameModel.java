package model;

import java.util.Random;

/**
 * This class extends GameModel and implements the logic of the clear cell game,
 * specifically.
 * 
 * @author Dept of Computer Science, UMCP
 */

public class ClearCellGameModel extends GameModel {

	/* Include whatever instance variables you think are useful. */
	
	Random randoms;// this instance variable is used to create randoms.(it is also passed to the constructor)
	
	public int playerScore =0;// i created this instance variable to store the players score
	
	
	/**
	 * Defines a board with empty cells.  It relies on the
	 * super class constructor to define the board.
	 * 
	 * @param rows number of rows in board
	 * @param cols number of columns in board
	 * @param random random number generator to be used during game when
	 * rows are randomly created
	 */
	public ClearCellGameModel(int rows, int cols, Random random) {
		super(rows,cols);//super is used here because clearGameCell implements GameModel
		randoms = random;

	}

	
	/**
	 * The game is over when the last row (the one with index equal
	 * to board.length-1) contains at least one cell that is not empty.
	 */
	public boolean isGameOver() {
		
		for(int col =0; col < getCols(); col++) {
			//goes through the columns at the final row and checks if its not empty
			if((board[board.length-1][col]!=(BoardCell.EMPTY))) {
				
				return true;
			}
		}
		return false;
	}

	
	/**
	 * Returns the player's score.  The player should be awarded one point
	 * for each cell that is cleared.
	 * 
	 * @return player's score
	 */
	public int getScore() {
		
		return playerScore;//returns the players score which gets incremented in the sameColor method
	}


	/**
	 * This method must do nothing in the case where the game is over.
	 * 
	 * As long as the game is not over yet, this method will do 
	 * the following:
	 * 
	 * 1. Shift the existing rows down by one position.
	 * 2. Insert a row of random BoardCell objects at the top
	 * of the board. The row will be filled from left to right with cells 
	 * obtained by calling BoardCell.getNonEmptyRandomBoardCell().  (The Random
	 * number generator passed to the constructor of this class should be
	 * passed as the argument to this method call.)
	 */
	public void nextAnimationStep() {
		//checks if game is over
		if(isGameOver() == true) {
			//it just returns 
			return;
			//else block
		}else {
			/*it iterates, starting from second to the last row. this is done because row is set to row + 1 in set
			 * board cell*/
			for(int row = getRows()-2; row >=0; row --) {
				for(int col = 0; col < getCols(); col ++) {
					
					setBoardCell(row+1, col, this.getBoardCell(row, col));
					
					//if this is the top of the row
					if(row == 0) {
						
						setBoardCell(row, col, BoardCell.getNonEmptyRandomBoardCell(randoms));
					}
				}
			}
		}
	}
	
	
	/*this method checks to see if up, down, left, right,  and the four diagonal directions are the same color with 
	 * the current selected row/col. if so they are also emptied. it takes in two parameters - the colIndex and 
	 * rowIndex of the current selection. Emptying is done by making calls to BoardCell.EMPTY
	 * */
	public void sameColor(int rowIndex, int colIndex) {
		
		int up = rowIndex -1;//created a variable that stores rowIndex-1
		int down = rowIndex +1;//created a variable that stores rowIndex+1
		int left = colIndex -1;//created a variable that stores colIndex-1
		int right = colIndex +1;//created a variable that stores colIndex+1

		if(up >= 0) {
			
			if(board[up][colIndex]== board[rowIndex][colIndex]) {
				
				board[up][colIndex] = BoardCell.EMPTY;
				playerScore++;//player score is incremented
				
			}
			if(left >=0) {
				
				if(board[up][left]== board[rowIndex][colIndex]) {
					
					board[up][left] = BoardCell.EMPTY;
					playerScore++;//player score is incremented
					
				}
			}
			if(right < getCols()) {
				
				if(board[up][right]== board[rowIndex][colIndex]) {
					
					board[up][right] = BoardCell.EMPTY;
					playerScore++;//player score is incremented
					
				}
			}
		}
		if(down < getRows()) {
			
			if(board[down][colIndex]== board[rowIndex][colIndex]) {
				
				board[down][colIndex] = BoardCell.EMPTY;
				playerScore++;//player score is incremented
				
			}
			if(left >=0) {
				
				if(board[down][left]== board[rowIndex][colIndex]) {
					
					board[down][left] = BoardCell.EMPTY;
					playerScore++;//player score is incremented
					
				}
			}
			if(right < getCols()) {
				
				if(board[down][right]== board[rowIndex][colIndex]) {
					
					board[down][right] = BoardCell.EMPTY;
					playerScore++;//player score is incremented
					
				}
			}
		}
		if(left >= 0) {
			
			if(board[rowIndex][left]== board[rowIndex][colIndex]) {
				
				board[rowIndex][left] = BoardCell.EMPTY;
				playerScore++;//player score is incremented
				
			}
		}
		if(right < getCols()) {
			
			if(board[rowIndex][right]== board[rowIndex][colIndex]) {
				
				board[rowIndex][right] = BoardCell.EMPTY;
				playerScore++;//player score is incremented
				
			}
		}
		if(board[rowIndex][colIndex] == board[rowIndex][colIndex]  ) {
			
			board[rowIndex][colIndex] = BoardCell.EMPTY;
			playerScore++;//player score is incremented
			
		}

	}

	
	/*This method goes through the cols at a particular row and checks if the whole row is empty. it takes in one
	 * parameter - rowIndex*/
	public boolean checkEmpty(int rowIndex) {
		
		int cols =0;//tracker for the number of empty columns
		
		for(int col =0; col < getCols(); col++) {
			if(board[rowIndex][col] == (BoardCell.EMPTY) ) {
				
				cols ++;//cols is incremented
			}
		}
		if(cols == getCols()){//this checks if col is the same size as the number of col meaning the whole row is empty
			
			return true;//return true if so
		}
		return false;//and false otherwise
	}

	/**
	 * This method is called when the user clicks a cell on the board.
	 * If the selected cell is not empty, it will be set to BoardCell.EMPTY, 
	 * along with any adjacent cells that are the same color as this one.  
	 * (This includes the cells above, below, to the left, to the right, and 
	 * all in all four diagonal directions.)
	 * 
	 * If any rows on the board become empty as a result of the removal of 
	 * cells then those rows will "collapse", meaning that all non-empty 
	 * rows beneath the collapsing row will shift upward. 
	 * 
	 * @throws IllegalArgumentException with message "Invalid row index" for 
	 * invalid row or "Invalid column index" for invalid column.  We check 
	 * for row validity first.
	 */
	public void processCell(int rowIndex, int colIndex) {

		//ensures that the selected is not empty
		if(board[rowIndex][colIndex]!=(BoardCell.EMPTY)){
			//the same color method is called = read description above
			sameColor(rowIndex, colIndex);
			
			/*i iterated through the rows and checked to see if its empty by calling on the checkEmpty method */
			for(int rowInde = getRows()-2;rowInde > 0; rowInde--) {
				
				if(checkEmpty(rowInde)==true) {//check empty method is called - see description above
					
					/*this is where the collapsing takes place.*/
					for(int row = rowInde; row < getRows()-1; row++) {
						for(int cols = 0; cols < getCols(); cols++) {
							
							//i assign the current values in the row beneath to the one above.
							board[row][cols] = board[row+1][cols];
							//i assign the values below to empty
							board[row+1][cols] = BoardCell.EMPTY;
						}
					}
				}
			}
		}
	}

}