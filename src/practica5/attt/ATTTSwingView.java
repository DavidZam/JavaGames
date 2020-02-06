package practica5.attt;

import practica5.swingcomponents.RectBoardSwingView;
import es.ucm.fdi.tp.basecode.bgame.control.Controller;
import es.ucm.fdi.tp.basecode.bgame.control.Player;
import es.ucm.fdi.tp.basecode.bgame.model.GameObserver;
import es.ucm.fdi.tp.basecode.bgame.model.Observable;
import es.ucm.fdi.tp.basecode.bgame.model.Piece;

@SuppressWarnings("serial")
public class ATTTSwingView extends RectBoardSwingView {

	private ATTTSwingPlayer player;

	public ATTTSwingView(Observable<GameObserver> g, Controller c, Piece localPiece, Player randPlayer, Player aiPlayer) {
		super(g, c, localPiece, randPlayer, aiPlayer);

	}

	@Override
	protected void handleMouseClick(int row, int col, int mouseButton) {
		player = new ATTTSwingPlayer(row, col);
		super.decideMakeManualMove(player);
	}
	
	@Override
	protected void activateBoard() {
		this.setPlaying(true);
	}
	
	@Override
	protected void deActivateBoard() {
		this.setPlaying(false);
	}
}
