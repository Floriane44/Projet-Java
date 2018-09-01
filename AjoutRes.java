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
import java.sql.*;

public class AjoutRes extends JFrame{
	private JTextField field;
	private ZModel model;
	private ZModel2 model2;
	private ZModel3 model3;
	private JTable tableau;
	//private JTable tableau2 = new JTable(model2);
	private JButton res;
	private JButton search;
	private JButton panier;
	private TableRowSorter<TableModel> sorter;
	private Client cl;

	public AjoutRes(ZModel model, ZModel2 model2, Client cl){
		super("Ajouter des réservation");
		this.cl = cl;
		this.model = model;
		this.model2 = model2;
		this.tableau = new JTable(model);
		this.sorter  = new TableRowSorter<TableModel>(tableau.getModel());
		this.setMinimumSize(new Dimension(1800,320));
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
		tableau.getColumn("Afficher détails").setCellEditor(new ButtonEditor(new JCheckBox()));
		JPanel boutons = new JPanel();
		res = new JButton(new AddAction());
		//panier = new JButton(new Panier());
		boutons.add(res);
		//boutons.add(panier);
		affiche.add(new JScrollPane(tableau), BorderLayout.CENTER);
		affiche.add(boutons, BorderLayout.SOUTH);
		this.getContentPane().add(titre, BorderLayout.NORTH);
		this.getContentPane().add(affiche, BorderLayout.CENTER);
		this.pack();
	}

	public AjoutRes(ZModel model, ZModel3 model3){
		super("Voir les réservations d'un étudiant");
		this.model = model;
		//this.model2 = model2;
		this.model3 = model3;
		this.tableau = new JTable(model);
		this.sorter  = new TableRowSorter<TableModel>(tableau.getModel());
		this.setMinimumSize(new Dimension(1800,820));
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
		tableau.getColumn("Afficher détails").setCellEditor(new ButtonEditor(new JCheckBox()));
		JPanel boutons = new JPanel();
		res = new JButton(new AddActionAdm());
		panier = new JButton(new PanierAdm());
		boutons.add(res);
		boutons.add(panier);
		affiche.add(new JScrollPane(tableau), BorderLayout.CENTER);
		affiche.add(boutons, BorderLayout.SOUTH);
		this.getContentPane().add(titre, BorderLayout.NORTH);
		this.getContentPane().add(affiche, BorderLayout.CENTER);
		this.pack();
	}

	private class AddAction extends AbstractAction {
		private AddAction() {
			super("Réserver");
		}
		public void actionPerformed(ActionEvent e){
			String sc = "\n";
			int compt=0;
			int[] selection = model.getSelect(); //tableau.getSelectedRows()
			if(selection.length == 0) JOptionPane.showMessageDialog(tableau, "Sélectionnez les documents que vous voulez réserver.");
			else{Document[] modelIndexes = model.getSelectDoc();
 			/*for(int i = 0; i < selection.length; i++){
				modelIndexes[i] = tableau.getRowSorter().convertRowIndexToModel(selection[i]);
			}
			Arrays.sort(modelIndexes);*/
			for(int i = modelIndexes.length - 1; i >= 0; i--){
				if(modelIndexes[i].getEmpruntable() == false){
					JOptionPane.showMessageDialog(tableau,"Ce document est déjà réservé.","Erreur",JOptionPane.ERROR_MESSAGE);
					compt=1;
					break;
				}
				if(compt==0){
					modelIndexes[i].setEmpruntable(false);
					modelIndexes[i].setPossesseur(cl);
					Statement stmt = null;
					//model.setPos(modelIndexes[i], cl);
					cl.addNbEmpruntsEncours();
					cl.addDocEmpr(modelIndexes[i]);
					model.fireTableDataChanged();
					//model2.addDoc(modelIndexes[i], cl);
					model2.fireTableDataChanged();
					//model.removeDoc(modelIndexes[i]);
					sc = sc + modelIndexes[i].getTitre() + "\n";
				}
			}
			if(compt==0){
				JOptionPane.showMessageDialog(tableau, " Vous avez réservé :\n" + sc); //new Res(model2, cl).setVisible(true);
			}
			}
		}

	}

	private class AddActionAdm extends AbstractAction {
		private AddActionAdm() {
			super("Réserver");
		}
		public void actionPerformed(ActionEvent e){
			Client[] possibilities = model3.getClients();
			ImageIcon icon = new ImageIcon("question.png");
			JFrame frame = new JFrame();
			Client cl = (Client)JOptionPane.showInputDialog(tableau, "Indiquez l'étudiant à qui vous voulez effectuer une réservation :\n","Choisir l'étudiant", JOptionPane.PLAIN_MESSAGE, icon, possibilities, possibilities[0]);
			model2 = new ZModel2(cl);
			String sc = "\n";
			int compt=0;
			int[] selection = model.getSelect(); //tableau.getSelectedRows()
			if(selection.length == 0) JOptionPane.showMessageDialog(tableau, "Sélectionnez les documents que vous voulez réserver.");
			else{Document[] modelIndexes = model.getSelectDoc();
 			/*for(int i = 0; i < selection.length; i++){
				modelIndexes[i] = tableau.getRowSorter().convertRowIndexToModel(selection[i]);
			}
			Arrays.sort(modelIndexes);*/
			for(int i = modelIndexes.length - 1; i >= 0; i--){
				if(modelIndexes[i].getEmpruntable() == false){
					JOptionPane.showMessageDialog(tableau,"Ce document est déjà réservé.","Erreur",JOptionPane.ERROR_MESSAGE);
					compt=1;
					break;
				}
				if(compt==0){
					modelIndexes[i].setEmpruntable(false);
					modelIndexes[i].setPossesseur(cl);
					cl.addNbEmpruntsEncours();
					cl.addDocEmpr(modelIndexes[i]);
					Statement stmt = null;
					model.fireTableDataChanged();
					//model2.addDoc(modelIndexes[i], cl);
					model2.fireTableDataChanged();
					sc = sc + modelIndexes[i].getTitre() + "\n";
				}
			}
			if(compt==0){
				JOptionPane.showMessageDialog(tableau, " Vous avez réservé :\n" + sc); //new Res(model2, cl).setVisible(true);
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

	/*private class Panier extends AbstractAction{
		private Panier(){
			super("Voir le panier");
		}

		public void actionPerformed(ActionEvent e) {
			new Res(model2, cl).setVisible(true);
		}
	}*/

	private class PanierAdm extends AbstractAction{
		private PanierAdm(){
			super("Voir le panier d'un étudiant");
		}

		public void actionPerformed(ActionEvent e) {
			Client[] possibilities = model3.getClients();
			ImageIcon icon = new ImageIcon("question.png");
			JFrame frame = new JFrame();
			Client clit = (Client)JOptionPane.showInputDialog(tableau, "Indiquez l'étudiant dont vous voulez voir les réservations :\n","Choisir l'étudiant", JOptionPane.PLAIN_MESSAGE, icon, possibilities, possibilities[0]);
			model2 = new ZModel2(clit);
			new Res(model2, clit).setVisible(true);
		}
	}
}
