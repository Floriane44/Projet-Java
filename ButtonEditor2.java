import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ButtonEditor2 extends DefaultCellEditor {
	protected JButton button;
	private boolean isPushed;
	private ButtonListener bListener = new ButtonListener();

	public ButtonEditor2(JCheckBox checkBox) {
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
		private ZModel model2 = new ZModel();

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
			model2.fireTableDataChanged();
			if(table.getValueAt(row, 3) == "Livre") JOptionPane.showMessageDialog(new JFrame("Détail du document"), "Titre du livre :\n" + table.getValueAt(row, 0) + "\n\nAuteur du livre :\n" + table.getValueAt(row, 1) + "\n\nAnnée :\n" + table.getValueAt(row, 2) + "\n\nÀ rendre pour le :\n" + table.getValueAt(row, 4) + "\n\nCode :\n" + model2.getDoc(row).getCode() + "\n\nNombre de pages :\n" + model2.getDoc(row).get1(model2.getDoc(row)) + "\n\nEditeur :\n" + model2.getDoc(row).get2(model2.getDoc(row)));

			else if(table.getValueAt(row, 3) == "Vidéo") JOptionPane.showMessageDialog(new JFrame("Détail du document"), "Titre du film :\n" + table.getValueAt(row, 0) + "\n\nRéalisateur du film :\n" + table.getValueAt(row, 1) + "\n\nAnnée :\n" + table.getValueAt(row, 2) + "\n\nÀ rendre pour le :\n" + table.getValueAt(row, 4) + "\n\nCode :\n" + model2.getDoc(row).getCode() + "\n\nDurée :\n" + model2.getDoc(row).get1(model2.getDoc(row)) + "\n\nPays :\n" + model2.getDoc(row).get2(model2.getDoc(row)));

			else if(table.getValueAt(row, 3) == "Audio") JOptionPane.showMessageDialog(new JFrame("Détail du document"), "Titre de l'album :\n" + table.getValueAt(row, 0) + "\n\nArtiste de l'album :\n" + table.getValueAt(row, 1) + "\n\nAnnée :\n" + table.getValueAt(row, 2) + "\n\nÀ rendre pour le :\n" + table.getValueAt(row, 4) + "\n\nCode :\n" + model2.getDoc(row).getCode() + "\n\nGenre :\n" + model2.getDoc(row).get1(model2.getDoc(row)) + "\n\nPays :\n" + model2.getDoc(row).get2(model2.getDoc(row)));
			//table.setValueAt("New Value " + (++nbre), this.row, (this.column -1));
		}
	}     
}
