package practica5.swingcomponents;

import java.awt.Color;
import es.ucm.fdi.tp.basecode.bgame.control.Controller;
import es.ucm.fdi.tp.basecode.bgame.control.Player;
import es.ucm.fdi.tp.basecode.bgame.model.Board;
import es.ucm.fdi.tp.basecode.bgame.model.GameObserver;
import es.ucm.fdi.tp.basecode.bgame.model.Observable;
import es.ucm.fdi.tp.basecode.bgame.model.Piece;

@SuppressWarnings("serial")
public abstract class RectBoardSwingView extends SwingView {

	private BoardComponent boardComp;
	
	public RectBoardSwingView(Observable<GameObserver> g, Controller c, Piece localPiece, Player randPlayer, Player aiPlayer) { //argumentos...
		super(g, c, localPiece, randPlayer, aiPlayer);
	}

	@Override
	protected void initBoardGui() {
		boardComp = new BoardComponent(this) {
			@Override
			protected void mouseClicked(int row, int col, int mouseButton) {
				// Llama a handleMouseClick para permitir a las subclases manejar el evento
				handleMouseClick(row, col, mouseButton);
			}
			
			@Override
			protected Color getPieceColor(Piece p) {
				Color piececolor;
				if(p.getId().equals("*")) { // Si es un obstaculo de Ataxx: (PROBAR)
					piececolor =  null;
				} else { // Si no es un obstaculo
					piececolor = this.getPieceColor(p);
				}
				return piececolor;
			}

			@Override
			protected boolean isPlayerPiece(Piece p) {
				boolean fichajugador = true;
				if(p.getId().equals("*")) { // Si es un obstaculo de Ataxx: (PROBAR)
					fichajugador = false;
				}
				return fichajugador;
			}
		};
		setBoardArea(boardComp);
	}

	@Override
	protected void redrawBoard() {
		// pide a BoardComp para repintar el tablero
		boardComp.redraw(this.getBoard());
	}
	
	@Override
	protected void activateBoard() {
		// TODO Auto-generated method stub
		for(int i = 0; i < super.getBoard().getRows(); i++) {
			for(int j = 0; j < super.getBoard().getCols(); j++) {
				this.boardComp.getSwingBoard()[i][j].setEnabled(true);
			}
		}
		this.revalidate();
	}

	@Override
	protected void deActivateBoard() {
		// TODO Auto-generated method stub
		for(int i = 0; i < super.getBoard().getRows(); i++) {
			for(int j = 0; j < super.getBoard().getCols(); j++) {
				this.boardComp.getSwingBoard()[i][j].setEnabled(false);
			}
		}
		this.revalidate();
	}
	
	protected abstract void handleMouseClick(int row, int col, int mouseButton);
}
