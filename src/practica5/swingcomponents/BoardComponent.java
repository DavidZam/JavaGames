package practica5.swingcomponents;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.JPanel;

import es.ucm.fdi.tp.basecode.bgame.model.Board;
import es.ucm.fdi.tp.basecode.bgame.model.Piece;

@SuppressWarnings("serial")

public abstract class BoardComponent extends JPanel {

	private SwingButton[][] swingBoard = null;
	private SwingView swingview;
	
	public BoardComponent(SwingView swingview) {
		this.swingview = swingview;
		this.redraw(this.swingview.getBoard());
	}
	
	public void redraw(Board b) {
		// Diferenciamos los dos casos en los que se inicia el juego por primera vez o ya ha sido ejecutado.
		if(this.swingBoard != null) {
			for(int i = 0; i < b.getRows(); i++) {
				for(int j = 0; j < b.getCols(); j++) {
					swingBoard[i][j].revalidate(); // repaint() o revalidate()??
				}
			}
		} else {
			this.setLayout(new GridLayout(b.getRows(), b.getCols()));
			this.swingBoard = new SwingButton[b.getRows()][b.getCols()];
			for(int i = 0; i < b.getRows(); i++) {
				for(int j = 0; j < b.getCols(); j++) {
					swingBoard[i][j] = new SwingButton(i,j, this.swingview);
					this.add(swingBoard[i][j]);
				}
			}
		}
		this.setVisible(true);
	}
	
	final protected SwingButton[][] getSwingBoard() {
		return this.swingBoard;
	}
	
	protected abstract Color getPieceColor(Piece p);
	protected abstract boolean isPlayerPiece(Piece p);
	protected abstract void mouseClicked(int row, int col, int mouseButton);
	
}
