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
import java.util.*;

/**
 * Represents once game piece (either a fox or a goose).
 * 
 * <p>You, the student, are responsible for filling this class in based on
 * the skeleton.</p>
 */
public class GamePiece {
	private Board board;
	private boolean isFox;
	private Position position;
    /**
     * Constructs a new GamePiece.
     * @param board The Board instance that contains this GamePiece.
     * @param isFox Whether this GamePiece represents a fox.
     * @param position The starting position for this GamePiece.
     */
    public GamePiece(Board board, boolean isFox, Position position) {
        // TODO left to the student
    	this.board = board;
    	this.isFox = isFox;
    	this.position = position;
    }

    /**
     * Determines whether this piece is a fox.
     * @return True if this game piece is a fox. False if this game piece is
     *         a goose.
     */
    public boolean isFox() {
        // TODO left to the student
    	return this.isFox;
    }

    /**
     * Gets the current Position for this GamePiece.
     * @return The piece's current Position.
     */
    public Position getPosition() {
        // TODO left to the student
    	return this.position;
    }

    /**
     * Computes all the legal moves for this piece. In order to be legal, a move
     * must be within the allowed range for the type of GamePiece (fox or
     * goose), it must be within the limits of the board, and it must not be
     * blocked by another GamePiece occupying the destination square.
     * 
     * @return A list of Positions to which this piece may legally move from
     *         its current position.
     */
    public ArrayList<Position> getLegalMoves() {
        // TODO left to the student
    	int row = position.getRow();//creat a row number
    	int column = position.getColumn();//creat a column number
    	ArrayList<Position> legalMoves = new ArrayList<Position>();//creat an
    	//arraylist to store legalMoves
    	if(isFox)
    	{//checked and then update four possible position for fox
    		Position nw = new Position(row-1,column-1);
    		Position ne = new Position(row-1,column+1);
    		Position sw = new Position(row+1,column-1);
    		Position se = new Position(row+1,column+1);
    		//check if they are in bounds and check possible moves
     	   if(board.isInBounds(nw)&&board.getPieceAt(nw)==null)
     	   {
     		   legalMoves.add(nw);
     	   }
     	   if(board.isInBounds(ne)&&board.getPieceAt(ne)==null)
     	   {
     		   legalMoves.add(ne);
     	   }
    	   if(board.isInBounds(sw)&&board.getPieceAt(sw)==null)
    	   {
    		   legalMoves.add(sw);
    	   }
    	   if(board.isInBounds(se)&&board.getPieceAt(se)==null)
    	   {
    		   legalMoves.add(se);
    	   }
    	}
    	else
    	//same for goose
    	{
    	   Position nw = new Position(row-1,column-1);
    	   Position ne = new Position(row-1,column+1);
    	   if(board.isInBounds(nw)&&board.getPieceAt(nw)==null)
    	   {
    		   legalMoves.add(nw);
    	   }
    	   if(board.isInBounds(ne)&&board.getPieceAt(ne)==null)
    	   {
    		   legalMoves.add(ne);
    	   }
    		
    	}
        return legalMoves;
    }

    /**
     * Makes a move with this GamePiece.
     * @param newPosition The new position for the GamePiece to move to. If this
     *        is not a legal position for the piece to move to, then this method
     *        does nothing.
     * @return True if the piece was moved to newPosition. False if the move
     *         was illegal and thus could not be made.
     */
    public boolean moveTo(Position newPosition) {
        // TODO left to the student
    	if(getLegalMoves().contains(newPosition))
    	{
    		position = newPosition;
    		return true; 
    	}
    	else
        return false;
    }
}
