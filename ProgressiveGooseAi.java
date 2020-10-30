import java.util.*;

/**
 * A goose AI that moves all his pieces forward together, never breaking
 * ranks.
 * <p><b>STUDENTS SHOULD NOT EDIT THIS FILE.</b></p>
 * @author pollen
 */
public class ProgressiveGooseAi extends Ai {
	@Override public void makeNextMoveImpl(Board board) {
		// Make a copy of the list of geese before sorting it.
		ArrayList<GamePiece> geese =
				new ArrayList<GamePiece>(board.getGeese());
		
		// Sort the geese by row (furthest back geese first), then by column
		// (leftmost geese first).
		Collections.sort(geese, new Comparator<GamePiece>() {
			@Override public int compare(GamePiece g1, GamePiece g2) {
				// Convenient return codes.
				final int G1_FIRST = -1, G2_FIRST = 1;
				
				Position p1 = g1.getPosition(), p2 = g2.getPosition();
				if (p1.getRow() > p2.getRow()) {
					return G1_FIRST; // G1 is further back.
				} else if (p2.getRow() > p1.getRow()) {
					return G2_FIRST; // G2 is further back.
				} else {
					if (p1.getColumn() < p2.getColumn()) {
						return G1_FIRST; // G1 is further right.
					} else if (p2.getColumn() < p1.getColumn()) {
						return G2_FIRST; // G2 is further right.
					} else {
						return 0;
					}
				}
			}});
		
		// Try to find a goose with a legal move in the preferred direction,
		for (GamePiece goose : geese) {
			if (tryMove(goose, true)) {
				return;
			}
		}
		
		// Try to find a goose with any legal move at all.
		for (GamePiece goose : geese) {
			if (tryMove(goose, false)) {
				return;
			}
		}
		
		// If we get here, there were no legal moves which could be made.
		throw new AssertionError("There are no legal goose moves. You " +
				"shouldn't call makeNextMove on an Ai if there aren't " +
				"any legal moves.");
	}
	
	/**
	 * Tries to move a goose.
	 * 
	 * @param preferredDirection Whether or not to require that any move be
	 *        made in the preferred direction for that row.
	 * @return True iff a move was made.
	 */
	private boolean tryMove(GamePiece goose, boolean preferredDirection) {
		// When in an odd row, prefer to move right. When in an even row,
		// prefer to move left.
		boolean moveLeft = goose.getPosition().getRow() % 2 == 0;
		int currentCol = goose.getPosition().getColumn();
		for (Position move : goose.getLegalMoves()) {
			if (!preferredDirection || (moveLeft
					? (move.getColumn() > currentCol)
					: (move.getColumn() < currentCol))) {
				checkMove(goose, move);
				return true;
			}
		}
		
		return false;
	}
}
