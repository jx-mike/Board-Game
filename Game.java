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
import java.util.ArrayList;

public class Game implements GameWindow.GameInterface {
	 private Ai gooseAi=null;
	 private Board board;
	 private GamePiece selectedPiece=null;//creat select a game piece.
	 private static long  turn=0;//count number of turns.
	 private GameWindow gw;
    /**
     * Constructs a game with no Goose AI. A Game constructed in this manner
     * should allow a human player to move both the goose pieces when it is
     * the geese's turn AND the fox piece when it is the fox's turn.
     * 
     * <p>This constructor should create a GameWindow for displaying this Game
     * and should show the window. It should also start the game by giving
     * the geese the first move.
     * 
     * <p>Initially, no game piece should be selected.
     */
    public Game() {
        // TODO left to the student
    	this.board = new Board();
        gw = new GameWindow();
    	gw.show();//show the game window
    	gw.gameUpdated(this);//update game window
    }

    /**
     * Constructs a game with a Goose AI. A Game constructed in this manner
     * should only allow the human player to move the fox piece when it is the
     * fox's turn. The goose pieces should move according to the specified AI,
     * not a human player. The goose pieces should move immediately after each
     * fox move occurs.
     * 
     * <p>This constructor should create a GameWindow for displaying this Game
     * and should show the window. It should also start the game by giving
     * the geese the first move. <b>Since the geese are controlled by an AI,
     * they should make their first move BEFORE this constructor returns.</b>
     * That way, when the GameWindow is presented to the user, they are shown
     * the board right before the fox's first move, which the user must provide.
     * 
     * <p>Initially, no game piece should be selected.
     * 
     * @param gooseAi The AI for determining goose moves, or null to use no
     *        goose AI.
     */
    public Game(Ai gooseAi) {
        // TODO left to the student
    	this.gooseAi = gooseAi;
    	this.board = new Board();
        gw = new GameWindow();//creat a game window
    	gw.show();
    	gooseAi.makeNextMove(board);
    	turn++;//update turns 
    	gw.gameUpdated(this);//update game window
    }

    /**
     * Gets the Board object containing all the pieces in this Game. This method
     * will be called by the GameWindow when it tries to display your game
     * board on the screen. If this method returns {@code null}, the GameWindow
     * will never show any pieces on the screen.
     */
    public Board getBoard() {
        // TODO left to the student
    	return board;
//        return null;
    }

    /**
     * Gets the currently selected GamePiece. A piece becomes selected
     * when the user clicks on it on the GUI. The user should only be permitted
     * to select pieces whose turn it currently is. Only one piece can be the
     * selected piece at any given time. This method will be called by the
     * GameWindow when it tires to display your game pieces on the screen.
     * 
     * @return The currently selected GamePiece, or {@code null} if no GamePiece
     *         is currently selected.
     */
    public GamePiece getSelectedPiece() {
        // TODO left to the student
        return selectedPiece;
    }

    /**
     * Determines if the fox has lost the game. This method will be called by
     * the GameWindow to determine if it should display the game-end screen.
     * 
     * @return True if and only if the fox has lost the game. False if the game
     *         is still in progress or if the fox has won.
     */
    public boolean hasFoxLost() {
        // TODO left to the student
    	GamePiece fox = board.getFox();
    	if(fox.getLegalMoves().size()==0)
    		return true;
    	return false;
    }

    /**
     * Determines if the geese have lost the game. This method will be called by
     * the GameWindow to determine if it should display the game-end screen.
     * 
     * @return True if and only if the geese have lost the game. False if the
     *         game is still in progress or if the geese have won.
     */
    public boolean haveGeeseLost() {
        // TODO left to the student
    	ArrayList<GamePiece> Gooses = board.getGeese();//getGeese game piece
    		if(Gooses.get(0).getLegalMoves().size()==0
    				&&Gooses.get(1).getLegalMoves().size()==0
    				&&Gooses.get(2).getLegalMoves().size()==0
    				&&Gooses.get(3).getLegalMoves().size()==0)
    			return true;
    		if(board.getFox().getPosition().getRow()==7)
    			return true;
        return false;
    }

    /**
     * Handles the user clicking a square on the game board. This can either
     * change the selected game piece or move the selected game piece,
     * depending on whether the user clicks a square containing a piece or a
     * square where the selected piece could legally move. <b>Your code should
     * not call this method.</b> The GameWindow class will call it for you
     * whenever the user clicks a square on the displayed game board.
     * 
     * <p>If the user clicks a piece controlled by the current player (i.e. if
     * they click a fox while it is the fox's
     * turn, or a goose while it is the geese's turn), that piece should become
     * the selected piece. In particular, if a user clicks on an already
     * selected piece, that piece should remain selected.If the user clicks a
     * square containing a piece whose turn it isn't,
     * or an empty square that isn't a legal move for the selected piece,
     * this method should deselect all pieces by setting the selected piece
     * to null.</p>
     * 
     * @param clicked Position that was clicked.
     */
    public void handleBoardClick(Position clicked) {
        // TODO left to the student
    	if(board.getPieceAt(clicked)==null)
    	{
    		if(board.isInBounds(clicked))//check if clicked was in bounds.
    			if(selectedPiece==null);
    			else
    			{
    				if(selectedPiece.getLegalMoves().contains(clicked))
    				{
        				selectedPiece.moveTo(clicked);
    				}
    				turn++;//update turns
    				selectedPiece = null;//re-initialize the selectedPiece for
    				//next move
    			}
    				
    		else
    			selectedPiece = null;
    	}
    	else
    	{
    		GamePiece clickedPiece = board.getPieceAt(clicked);
    		if(clickedPiece.isFox())
    		{
    			if(isFoxTurn())//check if it if FoxTurn then...
    			{
    				selectedPiece = clickedPiece;
    			}
    			else
    			{
    				selectedPiece = null;
    			}
    		}
    		else
    		{
    			if(isFoxTurn())
    			{
    				selectedPiece = null;
    			}
    			else
    			{
    				selectedPiece = clickedPiece;
    			}
    		}
    	}
    	gw.gameUpdated(this);
    	AiControl();
    }

    /**
     * Determines whether it is the fox's turn. This method will be called
     * by the GameWindow to display the proper turn at the bottom of
     * the window.
     * 
     * @return True if it is currently the fox's turn. False if it is currently
     *         the geese's turn.
     */
    public boolean isFoxTurn() {
        // TODO left to the student
    	if(turn%2==1)
    		return true;
        return false;
    }
    
    private void AiControl()
    {
    	if(this.gooseAi != null && this.isFoxTurn() == false)
    	{
    		gooseAi.makeNextMove(board);
    		turn++;
    		selectedPiece = null;
    		gw.gameUpdated(this);
    	}
    }
}
