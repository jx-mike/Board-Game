import java.util.*;

/**
 * An unbeatable Goose AI. Algorithm taken from "Winning Ways for your
 * Mathematical Plays" (Academic Press, 1982) by Elwyn R. Berlekamp, John H.
 * Conway, and Richard K. Guy, pp. 670-671.
 * 
 * <p><b>STUDENTS SHOULD NOT EDIT THIS FILE.</b></p>
 * <p>Also, if you try to defeat this AI, you <i>will</i> fail.</p>
 * 
 * @author pollen
 */
public class CrazyGoodGooseAi extends Ai {
	private static final int BOARD_SIZE = 8;
	private static final int NUM_GEESE = 4;
	
	/** List of formations which cover all possible board combinations. */
	private static final Formation[] FORMATIONS = {
		new Formation.A(),
		new Formation.B(),
		new Formation.C(),
		new Formation.D(),
		new Formation.E()
	};
	
	/** The current maneuver. */
	private Iterator<Move> maneuver;
	
	@Override public void makeNextMoveImpl(Board board) {
		GamePiece goose;
		
		// The geese always begin by moving their rightmost comrade out of
		// his starting corner.
		goose = board.getPieceAt(new Position(BOARD_SIZE - 1, BOARD_SIZE - 1));
		if (goose != null) { 
			// The corner goose is still in his corner, so move him. He should
			// only have one legal move.
			checkMove(goose,
					new Position(BOARD_SIZE - 2, BOARD_SIZE - 2));
			return; // Done.
		}
		
		// Check if we aren't already in the midst of a maneuver.
		if (maneuver == null || !maneuver.hasNext()) {
			// We need to come up with our next maneuver.
			for (Formation formation : FORMATIONS) {
				maneuver = formation.match(board);
				if (maneuver != null) {
					// Great! We found a valid formation, since it produced a
					// usable maneuver.
					break;
				}
			}
		}
		
		// If we still don't have a usable maneuver, something has gone wrong.
		if (maneuver == null || !maneuver.hasNext()) {
			throw new AssertionError(
					"The unbeatable AI has gotten confused; something must " +
					"be wrong with your code for moving pieces or " +
					"determining legal moves.");
		}
		
		// We are now guaranteed to have a valid maneuver, so use it.
		maneuver.next().make();
	}
	
	/** Pattern for matching geese formations against actual boards. */
	private static class Pattern {
		/**
		 * Position where the fox endangers the geese for this formation,
		 * relative to the referencePosition.
		 */
		private final List<Position> refDangerSpots;
		
		/**
		 * Positions of the geese for this pattern. These positions may be
		 * offset vertically to find a match.
		 */
		private final List<Position> refGeesePositions;
		
		public Pattern(
				Position refPos1,
				Position refPos2,
				Position refPos3,
				Position refPos4,
				Position[] refDangerSpots) {
			this.refDangerSpots = Arrays.asList(refDangerSpots);
			refGeesePositions = new ArrayList<Position>(4);
			refGeesePositions.add(refPos1);
			refGeesePositions.add(refPos2);
			refGeesePositions.add(refPos3);
			refGeesePositions.add(refPos4);
		}
		
		/**
		 * Matches this pattern against a Board.
		 * @param board The Board containing pieces to match against.
		 * @return An array of the geese, in the same order as their corresponding
		 *         referencePositions, or null if the match failed.
		 */
		public Match match(Board board) {
			boolean flipped = false;
			do {
				// Try both possible values for flipped.
				flipped = !flipped;
				
				for (int rowOffset = 0; rowOffset < BOARD_SIZE; rowOffset++) {
					// Construct a list of offset positions.
					List<Position> positions =
							shift(refGeesePositions, rowOffset, flipped);
					List<Position> dangerSpots =
							shift(refDangerSpots, rowOffset, flipped);
					
					// Check if the board's geese match the offset pattern.
					Match possibleMatch = match(
							positions, dangerSpots, flipped, board);
					if (possibleMatch != null) {
						return possibleMatch;
					}
				}
				
				// On the first time through the loop, flipped will be true
				// here, so we will loop once more.
			} while (flipped);
			
			return null; // No match.
		}
		
		/**
		 * Shifts (and flips) a list of positions for checking the various
		 * instances of a pattern.
		 * @param positions List positions to be shifted and/or flipped.
		 * @param rowOffset Offset to shift by.
		 * @param flipped Whether to flip columns.
		 * @return List of transformed positions.
		 */
		private static List<Position> shift(
				List<Position> positions,
				int rowOffset,
				boolean flipped) {
			List<Position> newPositions = new ArrayList<Position>();
			for (Position pos : positions) {
				newPositions.add(new Position(
						pos.getRow() + rowOffset,
						// If we are checking for a flipped match,
						// flip all the column indices.
						flipped
								? BOARD_SIZE - 1 - pos.getColumn()
								: pos.getColumn()));
			}
			return newPositions;
		}
		
		private static Match match(
				List<Position> positions,
				List<Position> dangerSpots,
				boolean flipped,
				Board board) {
			GamePiece[] geese = new GamePiece[NUM_GEESE];
			for (GamePiece goose : board.getGeese()) {
				int index = positions.indexOf(goose.getPosition());
				if (index < 0) {
					return null; // Match failed.
				} else {
					// File the goose in the proper slot for this pattern.
					geese[index] = goose;
				}
			}
			
			// Determine if the fox is in one of the danger spots.
			for (Position dangerSpot : dangerSpots) {
				if (board.getFox().getPosition().equals(dangerSpot)) {
					// Danger!
					return new Match(geese, true, flipped);
				}
			}
			
			// No danger.
			return new Match(geese, false, flipped);
		}
		
		/** Represents the result of matching a Pattern against a Board. */
		public static class Match {
			/**
			 * List of geese, in the order corresponding to the pattern's
			 * reference positions.
			 */
			private final GamePiece[] geese;
			
			/** Whether or not the geese are in danger at this point in time. */
			private final boolean danger;
			
			/**
			 * Whether the original pattern had to be flipped to get this
			 * Match.
			 */
			private final boolean flipped;
			
			public Match(GamePiece[] geese, boolean danger, boolean flipped) {
				this.geese = geese;
				this.danger = danger;
				this.flipped = flipped;
			}
			
			public boolean isDangerous() {
				return danger;
			}
			
			/**
			 * Creates a Move from this Match.
			 * @param goose Index of the goose. We use 1-based indexing here
			 *        to match up with the book.
			 * @param colDelta Column delta to move. Should be -1 or 1.
			 * @return The Move object.
			 */
			public Move move(int goose, int colDelta) {
				return new Move(geese[goose - 1],
						// If the match was flipped, flip the move, too.
						flipped ? -colDelta : colDelta);
			}
		}
	}
	
	/** Represents one of the geese's strategic formations. */
	private static abstract class Formation {
		private final Pattern pattern;
		
		protected Formation(Pattern pattern) {
			this.pattern = pattern;
		}
		
		/**
		 * Tries to match up a board with this particular formation. If the
		 * match succeeds, a sequence of moves is returned; these moves should
		 * be executed next. If the match fails, null is returned.
		 */
		public Iterator<Move> match(Board board) {
			Pattern.Match match = pattern.match(board);
			if (match == null) {
				return null;
			} else {
				return Arrays.asList(getManeuver(match)).iterator();
			}
		}
		
		/**
		 * Gets the sequence of moves to make.
		 * @param geese The geese, in the order specified by this formation's
		 *              pattern.
		 * @return Theh sequence of moves to be made.
		 */
		protected abstract Move[] getManeuver(Pattern.Match match);
		
		///////////////////////////////////////////////////////////////////////
		/////// FORMATIONS, AS ENUMERATED BY BERLEKAMP, CONWAY, AND GUY: //////
		///////////////////////////////////////////////////////////////////////
		
		public static class A extends Formation {
			public A() {
				super(new Pattern(
						new Position(0, 1),
						new Position(1, 2),
						new Position(1, 4),
						new Position(1, 6),
						new Position[] { new Position(0, 3) }));
			}
			@Override protected Move[] getManeuver(Pattern.Match match) {
				Move m1 = match.move(4, LEFT);
				Move m2 = match.move(2, RIGHT);
				Move m3 = match.move(4, RIGHT);
				Move m4 = match.move(3, RIGHT);
				return match.isDangerous()
						? new Move[] { m1, m2, m3, m4 }
						: new Move[] { m2 };
			}
		}
		
		public static class B extends Formation {
			public B() {
				super(new Pattern(
						new Position(0, 1),
						new Position(0, 3),
						new Position(1, 4),
						new Position(1, 6),
						new Position[] { new Position(0, 5) }));
			}
			@Override protected Move[] getManeuver(Pattern.Match match) {
				Move m1 = match.move(1, RIGHT);
				Move m2 = match.move(3, RIGHT);
				return match.isDangerous()
						? new Move[] { m1, m2 }
						: new Move[] { m2 };
			}
		}
		
		public static class C extends Formation {
			public C() {
				super(new Pattern(
						new Position(0, 1),
						new Position(0, 3),
						new Position(0, 5),
						new Position(1, 6),
						new Position[] {
							// Use -1 for the row that has danger spots but no
							// geese positions.
							new Position(-1, 4),
							new Position(-1, 6) }));
			}
			@Override protected Move[] getManeuver(Pattern.Match match) {
				Move m1 = match.move(3, RIGHT);
				Move m2 = match.move(4, LEFT);
				Move m3 = match.move(1, RIGHT);
				return match.isDangerous()
						? new Move[] { m3 }
						: new Move[] { m1, m2 };
			}
		}
		
		public static class D extends Formation {
			public D() {
				super(new Pattern(
						new Position(0, 2),
						new Position(1, 3),
						new Position(1, 5),
						new Position(2, 6),
						new Position[] { new Position(-1, 3) }));
			}
			@Override protected Move[] getManeuver(Pattern.Match match) {
				Move m1 = match.move(3, RIGHT);
				Move m2 = match.move(4, LEFT);
				Move m3 = match.move(4, LEFT);
				Move m4 = match.move(1, LEFT);
				Move m5 = match.move(2, LEFT);
				return match.isDangerous()
						// Which goose moves move #3 depends on whether we
						// choose the dangerous maneuver or no. So we must
						// construct a special move here:
						? new Move[] { match.move(3, LEFT) }
						: new Move[] { m1, m2, m3, m4, m5 };
			}
		}
		
		public static class E extends Formation {
			public E() {
				super(new Pattern(
						new Position(0, 2),
						new Position(1, 3),
						new Position(0, 4),
						new Position(2, 6),
						new Position[] { new Position(-2, 2) }));
			}
			@Override protected Move[] getManeuver(Pattern.Match match) {
				Move m1 = match.move(4, LEFT);
				Move m2 = match.move(4, RIGHT);
				Move m3 = match.move(1, LEFT);
				Move m4 = match.move(2, LEFT);
				return match.isDangerous()
						? new Move[] { m3, m4, m1, m2 }
						: new Move[] { m1, m2, m3, m4 };
			}
		}
	}
	
	/** Useful colDelta values. */
	public static final int
		LEFT = -1,
		RIGHT = 1;
	
	/** Represents a single move that can be made. */
	private static class Move {
		/** Which goose in the formation should move? */
		private final GamePiece goose;
		
		/** How many columns should it move? */
		private final int colDelta;
		
		public Move(GamePiece goose, int colDelta) {
			this.goose = goose;
			this.colDelta = colDelta;
		}
		
		/** Makes this move. */
		public void make() {
			Position oldPos = goose.getPosition();
			Position newPos = new Position(
					oldPos.getRow() - 1, // Geese always move upwards.
					oldPos.getColumn() + colDelta);
			checkMove(goose, newPos);
		}
	}
}
