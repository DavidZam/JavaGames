package practica5.swingcomponents;

import java.awt.BorderLayout;
import java.awt.Color;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import es.ucm.fdi.tp.basecode.bgame.control.Controller;
import es.ucm.fdi.tp.basecode.bgame.control.Player;
import es.ucm.fdi.tp.basecode.bgame.model.Board;
import es.ucm.fdi.tp.basecode.bgame.model.Game.State;
import es.ucm.fdi.tp.basecode.bgame.model.GameObserver;
import es.ucm.fdi.tp.basecode.bgame.model.Observable;
import es.ucm.fdi.tp.basecode.bgame.model.Piece;

@SuppressWarnings("serial")
public abstract class SwingView extends JFrame implements GameObserver {

	private Controller ctrl;
	private Piece localPiece; // DONDE se inicaliza esta variable????
	private Piece turn;
	private Board board;
	private JPanel boardPanel;
	private RightPanel rightPanel;
	private boolean playing;
	private Player randomPlayer;
	private Player AIplayer;
	private List<Piece> pieces;
	private Map<Piece, Color> pieceColors;
	private Map<Piece, PlayerMode> playerTypes;
	
	enum PlayerMode {
		MANUAL("Manual"), RANDOM("Random"), AI("Intelligent");
		private String name;
		
		PlayerMode(String name) {
			this.name = name;
		}
		
		public String toString() {
			return name;
		}
	}
	
	final protected Piece getTurn() {
		return turn;
	}
	
	final protected Board getBoard() {
		return board;
	}
	
	final protected void setBoardArea(JPanel c) {
		this.boardPanel.add(c);
	}
	
	final protected List<Piece> getPieces() {
		return pieces;
	}
	
	final protected Piece getLocalPiece() {
		return localPiece;
	}
	
	final protected Map<Piece, PlayerMode> getPlayerTypes() {
		return playerTypes;
	}
	
	final protected PlayerMode setPlayerTypes(Piece p, PlayerMode pm) {
		return playerTypes.put(p, pm);
	}
	
	final protected Map<Piece, Color> getPieceColor() {
		return pieceColors;
	}
	
	final protected Color setPieceColor(Piece p, Color c) {
		return pieceColors.put(p, c);
	}
	
	final protected Player getRandomPlayer() {
		return randomPlayer;
	}
	
	final protected Player getAIPlayer() {
		return AIplayer;
	}
	
	final protected boolean setPlaying(Boolean b) {
		return playing = b;
	}

	public SwingView(Observable<GameObserver> g, Controller c, Piece localPiece, Player randPlayer, Player aiPlayer) {
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.ctrl = c;
		this.localPiece = localPiece;
		this.pieceColors = new HashMap<Piece, Color>();
		this.playerTypes = new HashMap<Piece, PlayerMode>();
		this.randomPlayer = randPlayer;
		this.AIplayer = aiPlayer;
		deActivateBoard();
		g.addObserver(this);
		initGUI();
	}
	
	private void initGUI() {
		JPanel mainPanel = new JPanel(new BorderLayout());
		this.setContentPane(mainPanel);
		
		this.boardPanel = new JPanel(new BorderLayout());
		mainPanel.add(BorderLayout.CENTER, this.boardPanel);
		initBoardGui();
		
		this.rightPanel = new RightPanel(ctrl, this);
		mainPanel.add(BorderLayout.LINE_END, this.rightPanel);
		
		// Información de la ventana
		this.pack();
		this.setSize(700, 600);
		this.setLocation(600, 200);
		this.setVisible(true);
	}
	
	protected void decideMakeManualMove(Player manualPlayer) {
		if(this.playing && this.playerTypes.get(this.turn) == PlayerMode.MANUAL) {
			SwingUtilities.invokeLater(new Runnable(){
				@Override
				public void run() {
					ctrl.makeMove(manualPlayer);
				}
			});
		}
	}
	
	protected void decideMakeAutomaticMove() {
		if(this.playerTypes.get(this.turn) == PlayerMode.AI) {
			decideMakeAutomaticMove(this.AIplayer);
		} else {
			decideMakeAutomaticMove(this.randomPlayer);
		}
	}
	
	protected void decideMakeAutomaticMove(Player player) {
		if(this.playing) {
			SwingUtilities.invokeLater(new Runnable(){
				@Override
				public void run() {
					ctrl.makeMove(player);
				}
			});
		}
	}
	
	protected abstract void initBoardGui();
	protected abstract void activateBoard();
	protected abstract void deActivateBoard();
	protected abstract void redrawBoard();
	
	@Override
	public void onGameStart(Board board, String gameDesc, List<Piece> pieces, Piece turn) {
		// Si inicializo estos atributos en el handle no funciona, por qué??
		this.turn = turn;
		this.board = board;
		this.pieces = pieces;
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				handleOnGameStart(board, gameDesc, pieces, turn);
			}
		});
	}

	@Override
	public void onGameOver(Board board, State state, Piece winner) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				handleOnGameOver(board, state, winner);
			}
		});
	}

	@Override
	public void onMoveStart(Board board, Piece turn) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				handleOnMoveStart(board, turn);
			}
		});
	}

	@Override
	public void onMoveEnd(Board board, Piece turn, boolean success) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				handleOnMoveEnd(board, turn, success);
			}
		});
	}

	@Override
	public void onChangeTurn(Board board, Piece turn) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				handleOnChangeTurn(board, turn);
			}
		});
	}

	@Override
	public void onError(String msg) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				handleOnError(msg);
			}
		});
	}
	
	private void handleOnGameStart(Board board, String gameDesc, List<Piece> pieces, Piece turn) {
		String[] gameDescr = gameDesc.split(" ");
		String playerintitle;
		if(this.localPiece == null) {
			playerintitle = "";
		} else {
			playerintitle = "(" + this.localPiece + ")";
		}
		this.setTitle("Board Games: " + gameDescr[0] + " " + playerintitle);
		activateBoard();
		handleOnChangeTurn(board, turn);
		redrawBoard();
	}
	
	private void handleOnGameOver(Board board, State state, Piece winner) {
		this.rightPanel.addMsgToStatusArea("Game Over: " + state);
		String stategame = "";
		if(state == State.Draw) stategame = "Draw";
		if(state == State.Won) {
			if(winner == this.localPiece) {
				stategame = "You won the game";
			} else {
				stategame = winner + "won the game";
			}
		}
		this.rightPanel.addMsgToStatusArea(stategame);
	}

	private void handleOnMoveStart(Board board, Piece turn) {
		if(this.turn == turn) {
			this.playing = false;
		}
	}

	private void handleOnMoveEnd(Board board, Piece turn, boolean success) {
		if(this.turn == turn) {
			this.playing = false;
		}
		if(!success) {
			handleOnChangeTurn(board, turn);
		}
	}

	private void handleOnChangeTurn(Board board, Piece turn) {
		this.turn = turn;
		String msg = "";
		if(this.turn.equals(this.localPiece)) {
			msg = "Turn for you " + turn;
		} else {
			msg = "Turn for  " + turn;
		}
		this.rightPanel.addMsgToStatusArea(msg);
		if(this.playerTypes.get(turn) != PlayerMode.MANUAL) {
			this.decideMakeAutomaticMove();
		}
	}

	private void handleOnError(String msg) {
		if(!this.playing) {
			JOptionPane.showMessageDialog(new JFrame(), msg, "Error", JOptionPane.ERROR_MESSAGE);
		}
	}
}
