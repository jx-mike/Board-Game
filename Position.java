///////////////////////////////////////////////////////////////////////////////
//                   ALL STUDENTS COMPLETE THESE SECTIONS
// Title:            Fox and Geese
// Files:            Board.java
//					 Game.java
//					 GamePiece.java
//					 Position.java
// Semester:         CS302 Spring 2013
//
// Author:           Jianxing Chen (jchen245@wisc.edu)
// CS Login:         jianxing
// Lecturer's Name:  Melissa Tress
// Lab Section:      341
//
//                   PAIR PROGRAMMERS COMPLETE THIS SECTION
// Pair Partner:     Zheng Gao
// CS Login:         Zhengg

// Lecturer's Name:  Deb Deppeler
// Lab Section:      328
//
//                   STUDENTS WHO GET HELP FROM ANYONE OTHER THAN THEIR PARTNER
// Credits:          (list anyone who helped you write your program)
//////////////////////////// 80 columns wide //////////////////////////////////
public class Position {
	private int row;
	private int column;

    /**
     * Constructs a new Position at the given row and column.
     * @param row Row for the new Position. Should be in the range [0,7].
     * @param column Column for the new Position. Should be in the range [0,7].
     */
    public Position(int row, int column) {
        // TODO left to the student
    	this.row = row;
    	this.column = column;
    }

    /**
     * Gets this Position's row. Row 0 corresponds to the top row of the
     * board when it is displayed. Row 7 should correspond to the bottom row of
     * the board when it is displayed.
     */
    public int getRow() {
        // TODO left to the student
    	return row;
    }

    /**
     * Gets this Position's column. Column 0 corresponds to the leftmost row of
     * the board when it is displayed. Column 7 should correspond to the
     * rightmost row of the board when it is displayed.
     */
    public int getColumn() {
        // TODO left to the student
    	return column;
    }

    /**
     * Determines if this Position is equal to another object. Two Positions
     * should be equal if they have the same row and column.
     * @param other Object to compare this object with.
     * @return True if and only if {@code other} is a Position with the same
     *         row and column as {@code this} Position.
     */
    @Override
    public boolean equals(Object other) {
        // TODO left to the student
    	Position otherPos = (Position)other;
    	if(row==otherPos.row && column==otherPos.column)
    		return true;// return true if row == row and column == column 
        return false;
    }

    /**
     * Generates a String representation of this Position. This method is not
     * required for the assignment, but it might help you during debugging
     * by allowing you to print out a Position to the console in a nice,
     * readable format.
     */
    @Override
    public String toString() {
        // TODO left to the student
    	return "Row: "+ row + "  Column: "+column;
    }
}
