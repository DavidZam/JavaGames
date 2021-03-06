package practica5.ttt;

import javax.swing.SwingUtilities;

import practica5.connectn.ConnectNSwingView;
import es.ucm.fdi.tp.basecode.bgame.control.Controller;
import es.ucm.fdi.tp.basecode.bgame.control.Player;
import es.ucm.fdi.tp.basecode.bgame.model.GameObserver;
import es.ucm.fdi.tp.basecode.bgame.model.Observable;
import es.ucm.fdi.tp.basecode.bgame.model.Piece;
import es.ucm.fdi.tp.basecode.ttt.TicTacToeFactory;

@SuppressWarnings("serial")
public class TTTFactoryExt extends TicTacToeFactory {
	
	public TTTFactoryExt() {
		super();
	}
	
	@Override
	public void createSwingView(Observable<GameObserver> game, Controller ctrl, Piece viewPiece, Player randPlayer, Player aiPlayer) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				new ConnectNSwingView(game, ctrl, viewPiece, aiPlayer, aiPlayer);
			}
		});
	}
}
