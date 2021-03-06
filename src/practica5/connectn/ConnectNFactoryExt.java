package practica5.connectn;

import javax.swing.SwingUtilities;
import es.ucm.fdi.tp.basecode.bgame.control.Controller;
import es.ucm.fdi.tp.basecode.bgame.control.Player;
import es.ucm.fdi.tp.basecode.bgame.model.GameObserver;
import es.ucm.fdi.tp.basecode.bgame.model.Observable;
import es.ucm.fdi.tp.basecode.bgame.model.Piece;
import es.ucm.fdi.tp.basecode.connectn.ConnectNFactory;

@SuppressWarnings("serial")
public class ConnectNFactoryExt extends ConnectNFactory {

	public ConnectNFactoryExt() {
		super();
	}
	
	public ConnectNFactoryExt(int dim) {
		super(dim);
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
