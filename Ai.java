import java.util.*;

/**
 * Abstract superclass for all AI players (both fox and goose AIs). Contains
 * a method that may be used by the Game to get the next move from the AI.
 * <p><b>STUDENTS SHOULD NOT EDIT THIS FILE.</b></p>
 * @author pollen
 */
public abstract class Ai {
	/**
	 * Gets the next move from this AI by calling
	 * {@link #makeNextMoveImpl(Board)}. Checks that
	 * {@link #makeNextMoveImpl(Board)} moves exactly one piece, throwing
	 * an Exception if it moves too few or too many pieces. 
	 * 
	 * @param board Board containing the current game pieces.
	 */
	public final void makeNextMove(Board board) {
		BoardSnapshot before = new BoardSnapshot(board);
		makeNextMoveImpl(board);
		BoardSnapshot after = new BoardSnapshot(board);
		before.checkDifferences(after);
	}
	
	/**
	 * Gets the next move from the AI. In this method, the Ai implementer should
	 * call the {@code moveTo} method on exactly one GamePiece (either a fox or
	 * a goose, depending on what side the Ai is meant to play). This method
	 * is "protected", meaning that it is visible only to subclasses of Ai
	 * (such as RandomGooseAi and ProgressiveGooseAi). Other classes that wish
	 * to use an Ai object should iteract with it using the
	 * {@link #makeNextMove(Board)} method.
	 * 
	 * @param board Board containing the current game pieces.
	 */
	protected abstract void makeNextMoveImpl(Board board);
	
	/**
	 * Helper method for checking return values of moveTo calls. Simply throws
	 * an exception if a move that should have worked failed for some reason.
	 * This method is "protected", which means subclasses may use it to assist
	 * their implementation of {@link #makeNextMoveImpl(Board)}.
	 */
	protected static void checkMove(GamePiece piece, Position move) {
		if (!piece.moveTo(move)) {
			throw new AssertionError(String.format(
					"AI tried to make a move to (row %d, col %d), which " +
					"should be legal, but for some reason moveTo returned " +
					"false. Check your moveTo method.",
					move.getRow(), move.getColumn()));
		}
	}
	
	/**
	 * Represents an immutable snapshot of all the pieces on a Board.
	 * <p>Student code should not use this class.</p>
	 */
	private static class BoardSnapshot {
		private final Position foxPosition;
		private final List<Position> geesePositions = new ArrayList<Position>();
		
		public BoardSnapshot(Board board) {
			foxPosition = board.getFox().getPosition();
			for (GamePiece goose : board.getGeese()) {
				geesePositions.add(goose.getPosition());
			}
		}
		
		/**
		 * Checks if "other" differs from this Board by exactly one move.
		 * @throws IllegalStateException if "other" does not differ from this
		 *         Board by exactly one move.
		 */
		public void checkDifferences(BoardSnapshot other) {
			int differences = 0;
			if (!foxPosition.equals(other.foxPosition)) {
				differences++;
			}
			for (Position goosePosition : geesePositions) {
				if (!other.geesePositions.contains(goosePosition)) {
					differences++;
				}
			}
			
			if (differences < 1) {
				throw new AssertionError("AI did not make any moves.");
			} else if (differences > 1) {
				throw new AssertionError("AI made more than one move.");
			}
		}
	}
}
