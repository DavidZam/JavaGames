package practica5.attt;

import javax.swing.SwingUtilities;

import es.ucm.fdi.tp.basecode.attt.AdvancedTTTFactory;
import es.ucm.fdi.tp.basecode.bgame.control.Controller;
import es.ucm.fdi.tp.basecode.bgame.control.Player;
import es.ucm.fdi.tp.basecode.bgame.model.GameObserver;
import es.ucm.fdi.tp.basecode.bgame.model.Observable;
import es.ucm.fdi.tp.basecode.bgame.model.Piece;

@SuppressWarnings("serial")
public class ATTTFactoryExt extends AdvancedTTTFactory {
	
	public ATTTFactoryExt() {
		super();
	}

	@Override
	public void createSwingView(Observable<GameObserver> game, Controller ctrl, Piece viewPiece, Player randPlayer, Player aiPlayer) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				new ATTTSwingView(game, ctrl, viewPiece, aiPlayer, aiPlayer);
			}
		});
	}
	
}
