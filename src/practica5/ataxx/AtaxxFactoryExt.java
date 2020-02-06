package practica5.ataxx;

import javax.swing.SwingUtilities;

import practica4.accesorios.Constantes;
import practica4.ataxx.AtaxxFactory;
import es.ucm.fdi.tp.basecode.bgame.control.Controller;
import es.ucm.fdi.tp.basecode.bgame.control.Player;
import es.ucm.fdi.tp.basecode.bgame.model.GameObserver;
import es.ucm.fdi.tp.basecode.bgame.model.Observable;
import es.ucm.fdi.tp.basecode.bgame.model.Piece;

@SuppressWarnings("serial")
public class AtaxxFactoryExt extends AtaxxFactory {

	public AtaxxFactoryExt() {
		super();
	}
	
	public AtaxxFactoryExt(int dimR, int dimC, int obstaculos) {
		super(dimR, dimC, obstaculos);
	}
	
	public AtaxxFactoryExt(int obstaculos) {
		super(Constantes.dimPorDefecto, Constantes.dimPorDefecto, obstaculos);
	}
	
	@Override
	public void createSwingView(Observable<GameObserver> game, Controller ctrl, Piece viewPiece, Player randPlayer, Player aiPlayer) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				new AtaxxSwingView(game, ctrl, viewPiece, aiPlayer, aiPlayer);
			}
		});
	}
}
