import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class ButtonEditor3 extends DefaultCellEditor {
	protected JButton button;
	private boolean isPushed;
	private ButtonListener bListener = new ButtonListener();

	public ButtonEditor3(JCheckBox checkBox) {
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
		private ZModel3 model = new ZModel3();

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
			Statement stmt = null;
			try {
				stmt = Connexion.getInstance().createStatement();
				Connexion.getInstance().setAutoCommit(false);
				ResultSet result = stmt.executeQuery("SELECT * FROM CLIENT");
				for(int i = 0; i <= row; i++) result.next();
				JOptionPane.showMessageDialog(new JFrame("Détail de l'étudiant"), "Nom de l'étudiant :\n" + result.getString("NOM") + "\n\nPrénom de l'étudiant :\n" + result.getString("PRENOM") + "\n\nAdresse e-mail :\n" + result.getString("ADRESSE") + "\n\nNombre d'emprunts en cours :\n" + table.getValueAt(row, 3) + "\n\nDate d'inscription :\n" + table.getValueAt(row, 4));
				result.close();
				stmt.close();
			} catch ( Exception e ) {
				System.err.println( e.getClass().getName() + ": " + e.getMessage() );
				System.exit(0);
			}
		}
	}
}
