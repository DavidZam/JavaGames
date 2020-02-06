package practica5.swingcomponents;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

import practica5.swingcomponents.SwingView.PlayerMode;
import es.ucm.fdi.tp.basecode.bgame.Utils;
import es.ucm.fdi.tp.basecode.bgame.control.Controller;
import es.ucm.fdi.tp.basecode.bgame.control.Player;
import es.ucm.fdi.tp.basecode.bgame.model.Piece;

@SuppressWarnings("serial")
public class RightPanel extends JPanel {
	
	private SwingView swingview; // Instancia de la clase SwingView
	private Controller ctrl;
	private String[] columnNames = {"Player", "Mode", "#Pieces"}; // Atributos en clases internas
	private JTextArea statusmsg;
	private JButton randomButton = new JButton("Random");
	private JButton intelligentButton = new JButton("Intelligent");
	
	public RightPanel(Controller ctrl, SwingView swingview) {
		
		this.ctrl = ctrl;
		this.swingview = swingview;
		
		randomButton.setEnabled(false);
		intelligentButton.setEnabled(false);
		
		// Creamos un panel con un BoxLayout que organiza los componentes de arriba a abajo 
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		// Creamos un JPanel con sus respectivos componentes que van a estar en el panel derecho
		
		// StatusMessages Panel
		JPanel statusmessagescomponent = new JPanel();
		statusmessagescomponent.setBorder(BorderFactory.createTitledBorder("Status Messages"));
		statusmsg = new JTextArea(11,14);
		statusmsg.setEditable(false);
		JScrollPane scrollPane = new JScrollPane(statusmsg);
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		statusmessagescomponent.add(scrollPane);
		this.add(statusmessagescomponent);
		
		// PlayerInformation Panel
		JPanel playerinformationcomponent = new JPanel(new BorderLayout());
		playerinformationcomponent.setBorder(BorderFactory.createTitledBorder("Player Information"));
		
		class PlayerInformationTableModel extends DefaultTableModel {
			//private String[] columnNames = {"Player", "Mode", "#Pieces"};
			// Como se usan los atributos de clases internas?
			
			@Override
			public String getColumnName(int col) {
				return columnNames[col];
			}
			@Override
			public int getColumnCount() {
				return columnNames.length;
			}
			@Override
			public int getRowCount() {
				return swingview.getPieces() == null ? 0 : swingview.getPieces().size();
			}
			@Override
			public Object getValueAt(int indiceFila, int indiceCol) {
				if(swingview.getPieces() == null) {
					return null;
				}
				Piece p = swingview.getPieces().get(indiceFila);
				if(indiceCol == 0) {
					return p;
				} else if(indiceCol == 1) {
					return swingview.getPlayerTypes().get(p);
				} else {
					return swingview.getBoard().getPieceCount(p);
				}
			
			}
			public void refresh() {
				this.fireTableDataChanged();
			}
		}
		PlayerInformationTableModel tableModel = new PlayerInformationTableModel();
		
		JTable tableplayers = new JTable(tableModel) {
			@Override
			public Component prepareRenderer(TableCellRenderer renderer, int row, int col) {
				Component comp = super.prepareRenderer(renderer, row, col);
				comp.setBackground(swingview.getPieceColor().get((swingview.getPieces().get(row))));
				return comp;
			}
		};
		JScrollPane scrlP = new JScrollPane(tableplayers);
		playerinformationcomponent.setPreferredSize(new Dimension(100, 100));
		playerinformationcomponent.add(scrlP);
		this.add(playerinformationcomponent);

		// PieceColors Panel
		JPanel piececolorscomponent = new JPanel();
		piececolorscomponent.setBorder(BorderFactory.createTitledBorder("Piece Colors"));
		
		JComboBox<Piece> lstListaJugadores = new JComboBox<Piece>();
		lstListaJugadores.removeAllItems();
		for(Piece p : this.swingview.getPieces()) {
			if(swingview.getPieceColor().get(p) == null) {
				swingview.setPieceColor(p, Utils.randomColor());
			}
			lstListaJugadores.addItem(p);
		}
		lstListaJugadores.setAlignmentX(LEFT_ALIGNMENT);
		lstListaJugadores.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				lstListaJugadores.getSelectedItem();
			}
		});	
		
		JButton choosecolorButton = new JButton("Choose Color");
		choosecolorButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Piece p = (Piece) lstListaJugadores.getSelectedItem(); // Intentar evitar el cast
				ColorChooser co = new ColorChooser(new JFrame(), "Select Color Piece", swingview.getPieceColor().get(p));
				if(co.getColor() != null) { // Si se le asigna un color:
					swingview.setPieceColor(p, co.getColor());
					repaint();
				}
			}
		});
		choosecolorButton.setAlignmentX(RIGHT_ALIGNMENT);
		piececolorscomponent.add(lstListaJugadores);
		piececolorscomponent.add(choosecolorButton);
		this.add(piececolorscomponent);
		
		
		// PlayerModes Panel
		JPanel playermodescomponent = new JPanel();
		playermodescomponent.setBorder(BorderFactory.createTitledBorder("Player Mode"));
		
		// Para cada una de las piezas tenemos que ver de qué tipo es el jugador de esa pieza:
		JComboBox<Piece> lstListaJugadores2 = new JComboBox<Piece>();
		for(Piece p : this.swingview.getPieces()) {
			if(swingview.getPlayerTypes().get(p) == null) {
				swingview.getPlayerTypes().put(p, PlayerMode.MANUAL);
				lstListaJugadores2.addItem(p);
			} else {
				if(swingview.getPlayerTypes().get(swingview.getLocalPiece()) == null) {
					swingview.setPlayerTypes(swingview.getLocalPiece(), PlayerMode.MANUAL);
					lstListaJugadores2.addItem(swingview.getLocalPiece());
				}
			}	
		}
		lstListaJugadores2.setAlignmentX(LEFT_ALIGNMENT);
		lstListaJugadores2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				lstListaJugadores2.getSelectedItem();
			}
		});
		
		JComboBox<PlayerMode> lstListaplayertypes = new JComboBox<PlayerMode>();
		lstListaplayertypes.addItem(PlayerMode.MANUAL);
		lstListaplayertypes.addItem(PlayerMode.RANDOM);
		lstListaplayertypes.addItem(PlayerMode.AI);
		lstListaplayertypes.setAlignmentX(CENTER_ALIGNMENT);
		
		JButton setButton = new JButton("Set");
		setButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// Intentar evitar el cast de alguna manera
				Piece p = (Piece) lstListaJugadores2.getSelectedItem();
				PlayerMode pm = (PlayerMode) lstListaplayertypes.getSelectedItem();
				swingview.setPlayerTypes(p, pm);
				tableModel.refresh(); // Refrescar la JTable
				for(Piece pi: swingview.getPieces()) {
					if(swingview.getPlayerTypes().get(pi) == PlayerMode.RANDOM) {
						randomButton.setEnabled(true);
					}
					if(swingview.getPlayerTypes().get(pi) == PlayerMode.AI) {
						intelligentButton.setEnabled(true);
					}
				}	
			}
		});
		setButton.setAlignmentX(RIGHT_ALIGNMENT);
		
		playermodescomponent.add(lstListaJugadores2);
		playermodescomponent.add(lstListaplayertypes);
		playermodescomponent.add(setButton);
		
		this.add(playermodescomponent);
		
		// AutomaticMoves Panel
		JPanel automaticmovescomponent = new JPanel();
		automaticmovescomponent.setBorder(BorderFactory.createTitledBorder("Automatic Moves"));
		randomButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				swingview.decideMakeAutomaticMove(); // Por qué no hace un movimiento?
			}
		});
		randomButton.setAlignmentX(LEFT_ALIGNMENT);
		automaticmovescomponent.add(randomButton);
		intelligentButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				swingview.decideMakeAutomaticMove();
			}
		});
		intelligentButton.setAlignmentX(RIGHT_ALIGNMENT);
		automaticmovescomponent.add(intelligentButton);
		this.add(automaticmovescomponent);	
		
		// QuitAndRestart Panel
		JPanel quitandrestartcomponent = new JPanel();
		JButton quitButton = new JButton("Quit");
		quitButton.setAlignmentX(LEFT_ALIGNMENT);
		quitButton.addActionListener(new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			int answer;
			answer = JOptionPane.showOptionDialog(new JFrame(), "¿Are you sure you want to quit?", "Quit", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);
			if(answer == 0) {
				ctrl.stop();
				swingview.setVisible(false);
				System.exit(0);
			}
		}
		});
		JButton restartButton = new JButton("Restart"); // No funciona, aparece Restart en los botones ¿?
		restartButton.setAlignmentX(RIGHT_ALIGNMENT);
		restartButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ctrl.restart();
				statusmsg.setText("");
				for(int i = 0; i < swingview.getBoard().getRows(); i++) {
					for(int j = 0; j < swingview.getBoard().getCols(); j++) {
						// Como accedo desde aqui al tablero de swingboard[][] de la clase BoardComponent?
						// Para hacer revalidate() de todos los botones cuando se reinicie
					}
				}
			}
		});
		quitandrestartcomponent.add(quitButton);
		quitandrestartcomponent.add(restartButton);
		this.add(quitandrestartcomponent);
		
		this.setVisible(true);
	}
	
	// Tener un metodo protected  addMsg() con el texto
	protected void addMsgToStatusArea(String msg) {
		statusmsg.append(" " + msg + "\n");
	}	
}
