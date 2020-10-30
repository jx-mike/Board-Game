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
 * Represents the state of the game board, including the positions of all the
 * game pieces on the board.
 * 
 * <p>You, the student, are responsible for filling this class in based on
 * the skeleton.</p>
 */
public class Board {

	private GamePiece foxPiece;//creat a fox
	private ArrayList<GamePiece> goosePieces;//creat a list of geese
    /**
     * Creates a new Board with the foxes and geese set to their standard
     * starting positions. The Board should contain one fox and four geese.
     */
    public Board() {
        // TODO left to the student
    	//starting position of fox
    	foxPiece = new GamePiece(this,true,new Position(0,4));
    	goosePieces=new ArrayList<GamePiece>();
    	//set starting position of four geese
    	goosePieces.add(new GamePiece(this,false,new Position(7,1)));
    	goosePieces.add(new GamePiece(this,false,new Position(7,3)));
    	goosePieces.add(new GamePiece(this,false,new Position(7,5)));
    	goosePieces.add(new GamePiece(this,false,new Position(7,7)));
    }

    /** Gets the fox piece on the Board. */
    public GamePiece getFox() {
        // TODO left to the student
    	return foxPiece;
    }

    /** Gets the goose pieces on the Board. */
    public ArrayList<GamePiece> getGeese() {
        // TODO left to the student
    	//add geese to an arraylist
    	return goosePieces;
    }

    /**
     * Gets the game piece at a particular position.
     * @param pos The Position to check.
     * @return The GamePiece at pos, or {@code null} if there is no GamePiece at
     *         pos.
     */
    public GamePiece getPieceAt(Position pos) {
        // TODO left to the student
    	//check if fox position equals "pos" then return fox;
    	//otherwise, check each goose if the position equals "pos", then return
    	//goose
    	if(foxPiece.getPosition().equals(pos))
    		return foxPiece;
    	for(int i=0;i<goosePieces.size();i++)
    	{
    		GamePiece goosePiece = goosePieces.get(i);
    		if(goosePiece.getPosition().equals(pos))
    		{
    			return goosePiece;
    		}
    	}
        return null;
    }

    /**
     * Determines if a position is within the bounds of the game board.
     * @param pos Position to check.
     * @return True if and only if the position is within the board's
     *         boundaries.
     */
    public boolean isInBounds(Position pos) {
        // TODO left to the student
    	//check if the position if in bound of game board.
    	if(pos.getColumn()<=7&&pos.getRow()<=7)
    		return true;
        return false;
    }
}
