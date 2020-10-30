/**
 * Application entry point for running with no Ai players.  Starts a Game
 * instance, which will show the GameWindow and run the game.
 * <p><b>STUDENTS SHOULD NOT EDIT THIS FILE (or at least they should make sure
 * that their submitted code works with this file as-is, without any edits).
 * </b></p>
 * @author pollen
 */
public class FoxAndGeese {
	/**
	 * Application entry point. Creates a Game object with two human players.
	 * If the Game constructor is coded properly, this will start the game
	 * (with the geese getting the first move) and display the GameWindow so
	 * that the user can observe and control the game.
	 * 
	 * @param args Command-line arguments (not used for this program).
	 */
	public static void main(String[] args) {
		new Game();
	}
}
