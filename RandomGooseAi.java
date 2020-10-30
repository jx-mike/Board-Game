import java.util.*;

/**
 * AI goose player that moves his geese randomly.
 * <p><b>STUDENTS SHOULD NOT EDIT THIS FILE.</b></p>
 * @author pollen
 */
public class RandomGooseAi extends Ai {
	@Override public void makeNextMoveImpl(Board board) {
		// Randomly shuffle a list of geese to decide which order to try their
		// moves in. Make sure to safely copy the geese list before shuffling
		// it.
		ArrayList<GamePiece> geese = new ArrayList<GamePiece>(board.getGeese());
		Collections.shuffle(geese);
		for (GamePiece goose : geese) {
			// Randomly shuffle a list of legal moves. Make sure to safely
			// copy the list first.
			List<Position> moves =
					new ArrayList<Position>(goose.getLegalMoves());
			Collections.shuffle(moves);
			
			// Make the first move in the list, if there are any moves in the
			// list.
			for (Position move : moves) {
				checkMove(goose, move);
				return; // We successfully made a move!
			}
		}
		
		// If we get here, there were no legal moves which could be made.
		throw new AssertionError("There are no legal goose moves. You " +
				"shouldn't call makeNextMove on an Ai if there aren't " +
				"any legal moves.");
	}
}
