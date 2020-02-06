package practica5.connectn;

import practica5.swingcomponents.RectBoardSwingView;
import es.ucm.fdi.tp.basecode.bgame.control.Controller;
import es.ucm.fdi.tp.basecode.bgame.control.Player;
import es.ucm.fdi.tp.basecode.bgame.model.GameObserver;
import es.ucm.fdi.tp.basecode.bgame.model.Observable;
import es.ucm.fdi.tp.basecode.bgame.model.Piece;

@SuppressWarnings("serial")
public class ConnectNSwingView extends RectBoardSwingView {

	private ConnectNSwingPlayer player;
	
	public ConnectNSwingView(Observable<GameObserver> g, Controller c, Piece localPiece, Player randPlayer, Player aiPlayer) {
		super(g, c, localPiece, randPlayer, aiPlayer);
		player = new ConnectNSwingPlayer();
	}


	@Override
	protected void handleMouseClick(int row, int col, int mouseButton) {
		// no hace nada si el tablero no esta activado
		player.setMove(row,col);
		super.decideMakeManualMove(player);
	}

	@Override
	protected void activateBoard() {
		// Declara el tablero activo, por lo que handleMouseClick acepta movmientos. 
		this.setPlaying(true);
	}
	
	@Override
	protected void deActivateBoard() {
		this.setPlaying(false);
	}

}
