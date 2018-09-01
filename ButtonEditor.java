import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class ButtonEditor extends DefaultCellEditor {
	protected JButton button;
	private boolean isPushed;
	private ButtonListener bListener = new ButtonListener();

	public ButtonEditor(JCheckBox checkBox) {
		super(checkBox);
		button = new JButton();
		button.setOpaque(true);
		button.addActionListener(bListener);
	}

	public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) { 
		bListener.setRow(row);
		bListener.setColumn(column);
		bListener.setTable(table);
		button.setText((value == null) ? "" : "Détail");
		return button;
	}

	class ButtonListener implements ActionListener{        
		private int column, row;
		private JTable table;
		private int nbre = 0;
		private ZModel model = new ZModel();

		public void setColumn(int col){
			this.column = col;
		}

		public void setRow(int row){
			this.row = row;
		}

		public void setTable(JTable table){
			this.table = table;
		}

		public void actionPerformed(ActionEvent event) {
			model.fireTableDataChanged();
			Statement stmt = null;
			try {
				stmt = Connexion.getInstance().createStatement();
				Connexion.getInstance().setAutoCommit(false);
				ResultSet result = null;
				if(table.getValueAt(row, 3) == "Livre"){
					result = stmt.executeQuery("SELECT * FROM LIVRE");
					for(int i = 0; i <= row; i++){
						if(table.getValueAt(i, 3) == "Livre") result.next();
					}
					JOptionPane.showMessageDialog(new JFrame("Détail du document"), "Titre du livre :\n" + result.getString("TITRE") + "\n\nAuteur du livre :\n" + result.getString("AUTEUR") + "\n\nAnnée :\n" + result.getString("ANNEE") + "\n\nCode :\n" + result.getString("CODE") + "\n\nNombre de pages :\n" + result.getString("PAGES") + "\n\nEditeur :\n" + result.getString("EDITEUR"));
				}
				else if(table.getValueAt(row, 3) == "Vidéo"){ 
					result = stmt.executeQuery("SELECT * FROM VIDEO");
					for(int i = 0; i <= row; i++){
						if(table.getValueAt(i, 3) == "Vidéo") result.next();
					}
					JOptionPane.showMessageDialog(new JFrame("Détail du document"), "Titre du film :\n" + result.getString("TITRE") + "\n\nRéalisateur du film :\n" + result.getString("AUTEUR") + "\n\nAnnée :\n" + result.getString("ANNEE") + "\n\nCode :\n" + result.getString("CODE") + "\n\nDurée :\n" + result.getString("DUREE") + "\n\nPays :\n" + result.getString("PAYS"));
				}
				else if(table.getValueAt(row, 3) == "Audio"){
					result = stmt.executeQuery("SELECT * FROM AUDIO");
					for(int i = 0; i <= row; i++){
						if(table.getValueAt(i, 3) == "Audio") result.next();
					}
					JOptionPane.showMessageDialog(new JFrame("Détail du document"), "Titre de l'album :\n" + result.getString("TITRE") + "\n\nArtiste de l'album :\n" + result.getString("AUTEUR") + "\n\nAnnée :\n" + result.getString("ANNEE") + "\n\nCode :\n" + result.getString("CODE") + "\n\nGenre :\n" + result.getString("ANNEE") + "\n\nPays :\n" + result.getString("PAYS"));
				}
				stmt.close();
			} catch ( Exception e ) {
				System.err.println( e.getClass().getName() + ": " + e.getMessage() );
				System.exit(0);
			}
			//table.setValueAt("New Value " + (++nbre), this.row, (this.column -1));
		}
	}     
}
