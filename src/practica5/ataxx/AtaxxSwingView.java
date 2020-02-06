package practica5.ataxx;

import practica5.swingcomponents.RectBoardSwingView;
import es.ucm.fdi.tp.basecode.bgame.control.Controller;
import es.ucm.fdi.tp.basecode.bgame.control.Player;
import es.ucm.fdi.tp.basecode.bgame.model.GameObserver;
import es.ucm.fdi.tp.basecode.bgame.model.Observable;
import es.ucm.fdi.tp.basecode.bgame.model.Piece;

@SuppressWarnings("serial")
public class AtaxxSwingView extends RectBoardSwingView {

	private AtaxxSwingPlayer player;
	private int filaI;
	private int columnaI;
	private int filaD;
	private int columnaD;
	

	public AtaxxSwingView(Observable<GameObserver> g, Controller c,	Piece localPiece, Player randPlayer, Player aiPlayer) {
		super(g, c, localPiece, randPlayer, aiPlayer);
		//player = new AtaxxSwingPlayer();
	}

	@Override
	protected void handleMouseClick(int row, int col, int mouseButton) {
		// TODO Auto-generated method stub
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
