import javax.swing.*;
import javax.swing.BorderFactory;
import javax.swing.border.EtchedBorder;
import javax.swing.JTable;
import javax.swing.table.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.JOptionPane;
import javax.swing.ImageIcon;
import javax.swing.text.MaskFormatter;
import javax.swing.JFormattedTextField;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener; 
import java.io.*;
import java.lang.*;
import java.util.*;
import java.util.regex.PatternSyntaxException;
import java.text.NumberFormat;

public class Res extends JFrame{
	private JTextField field;
	private ZModel model = new ZModel();
	private ZModel2 model2;
	private JTable tableau;
	private JButton supp;
	private JButton search;
	private TableRowSorter<TableModel> sorter;
	private Client cl;
	private Client personne = new Client("Personne", " ");

	public Res(ZModel2 model2, Client cl){
		super("Panier");
		this.cl = cl;
		this.model2 = model2;
		tableau = new JTable(model2);
		sorter = new TableRowSorter<TableModel>(tableau.getModel());
		//System.out.println(model2.getDoc(0).getPossesseur());
		this.setMinimumSize(new Dimension(1750,320));
		//this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLayout(new GridLayout(2, 1));
		JPanel titre = new JPanel (new GridLayout(2,1));
		JLabel title = new JLabel("Liste des documents :");
		title.setFont(new Font("Arial", Font.BOLD, 25));
		titre.add(title);
		JPanel saisi = new JPanel (new FlowLayout(FlowLayout.LEFT));
		field = new JTextField(30);
		search = new JButton(new FilterAction());
		saisi.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEmptyBorder(), "Rechercher"));
		saisi.add(field);
		saisi.add(search);
		titre.add(saisi);
		JPanel affiche = new JPanel (new BorderLayout());
		tableau.setRowHeight(30);
		/*TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(tableau.getModel());   
		tableau.setRowSorter(sorter);
		sorter.setSortsOnUpdates(true);*/
		tableau.setAutoCreateRowSorter(true);
		tableau.setRowSorter(sorter);
		//tableau.setDefaultRenderer(JButton.class, new TableComponent());
		tableau.getColumn("Afficher détails").setCellRenderer(new ButtonRenderer());
		tableau.getColumn("Afficher détails").setCellEditor(new ButtonEditor2(new JCheckBox()));
		JPanel boutons = new JPanel();
		supp = new JButton(new RemoveAction());
		boutons.add(supp);
		affiche.add(new JScrollPane(tableau), BorderLayout.CENTER);
		affiche.add(boutons, BorderLayout.SOUTH);
		this.getContentPane().add(titre, BorderLayout.NORTH);
		this.getContentPane().add(affiche, BorderLayout.CENTER);
		this.pack();
	}

	private class RemoveAction extends AbstractAction {
		private RemoveAction() {
			super("Annuler");
		}

		public void actionPerformed(ActionEvent e) {
			int[] selection = model2.getSelect(); //tableau.getSelectedRows()
			if(selection.length == 0) JOptionPane.showMessageDialog(tableau, "Sélectionnez les documents que vous voulez supprimer.");
			else{
				Document[] modelIndexes = model2.getSelectDoc();
	 			/*for(int i = 0; i < selection.length; i++){
					modelIndexes[i] = tableau.getRowSorter().convertRowIndexToModel(selection[i]);
				}
				Arrays.sort(modelIndexes);*/
				for(int i = modelIndexes.length - 1; i >= 0; i--){
					modelIndexes[i].setEmpruntable(true);
					modelIndexes[i].setPossesseur(personne);
					cl.minusNbEmpruntsEncours();
					cl.removeDocEmpr(modelIndexes[i]);
					model2.removeDoc(modelIndexes[i]);
					//model.addDoc(modelIndexes[i]);
					model.fireTableDataChanged();
				}
			}
		}
	}

	private class FilterAction extends AbstractAction {
		private FilterAction() {
			super("GO");
		}

		public void actionPerformed(ActionEvent e) {
			String regex = field.getText();/*JOptionPane.showInputDialog(search, "Rechercher un document (titre, auteur etc.) : ");*/
			sorter.setRowFilter(RowFilter.regexFilter(regex));
		}
	}
}
