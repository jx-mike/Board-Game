/**
 * Application entry point for running with a human fox player and an AI goose
 * player. Starts a Game instance, which will show the GameWindow and run
 * the game.
 * <p><b>STUDENTS SHOULD NOT EDIT THIS FILE (or at least they should make sure
 * that their submitted code works with this file as-is, without any edits).
 * </b></p>
 * @author pollen
 */
public class FoxAndGeeseAi {
	/**
	 * Application entry point. Creates a Game object with a human player
	 * controlling the fox and an AI controlling the geese.
	 * If the Game constructor is coded properly, this will start the game
	 * (with the geese getting the first move) and display the GameWindow so
	 * that the user can observe and control the game.
	 * 
	 * @param args Command-line arguments (not used for this program).
	 */
	public static void main(String[] args) {
		// Students: you can try the following different AIs here:
		//    new CrazyGoodGooseAi()   - will beat you every time.
		//    new RandomGooseAi()      - makes random moves.
		//    new ProgressiveGooseAi() - not stupid, but not clever either.
		new Game(new CrazyGoodGooseAi());
	}
}
