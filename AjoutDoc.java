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

public class AjoutDoc extends JFrame{
	private JTextField field;
	//private Livre doc = new Livre();
	//private Livre liv = new Livre("Le rouge et le noir", "Stendhal", "1830", "002", "400", "Levasseur");
	private ZModel model;
	private JTable tableau;
	private JButton ajout;
	private JButton suppr;
	private JButton search;
	private TableRowSorter<TableModel> sorter;

	public AjoutDoc(ZModel model){
		super("Ajouter ou supprimer un document");
		this.model = model;
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
		ajout = new JButton(new AddAction());
		suppr = new JButton(new RemoveAction());
		boutons.add(ajout);
		boutons.add(suppr);
		affiche.add(new JScrollPane(tableau), BorderLayout.CENTER);
		affiche.add(boutons, BorderLayout.SOUTH);
		this.getContentPane().add(titre, BorderLayout.NORTH);
		this.getContentPane().add(affiche, BorderLayout.CENTER);
		this.pack();
	}

	private class AddAction extends AbstractAction {
		private AddAction() {
			super("Ajouter");
		}
 
		public void actionPerformed(ActionEvent e){
			//model.addLivre(new Livre("Madame Bovary", "Gustave Flaubert", "1857", "003", 479, "Michel Lévy frères"));
			Object[] possibilities = {"Livre", "Audio", "Vidéo"};
			ImageIcon icon = new ImageIcon("question.png");
			JFrame frame = new JFrame();
			String s = (String)JOptionPane.showInputDialog(frame, "Indiquez le type de document que vous voulez :\n","Ajouter un document", JOptionPane.PLAIN_MESSAGE, icon, possibilities, "Livre");
			if ((s != null) && (s.length() > 0) && (s=="Livre")) {
				final JFrame frame2 = new JFrame("Ajout d'un document");
				frame2.setLocationRelativeTo(tableau);
				frame.setVisible(false);
				frame.dispose();
				final JTextField titredoc = new JTextField(30);
				final JTextField auteur = new JTextField(30);
				final JTextField annee = new JTextField(30);
				final JTextField code = new JTextField(30);
				final JTextField nbPage = new JTextField(30);
				final JTextField editeur = new JTextField(30);
				JButton aj = new JButton("Ajouter");
				JPanel panel1 = new JPanel(new BorderLayout());
				JPanel panel2 = new JPanel(new BorderLayout());
				JPanel panel3 = new JPanel(new BorderLayout());
				JPanel panel5 = new JPanel(new BorderLayout());
				JPanel panel6 = new JPanel(new BorderLayout());
				JPanel panel7 = new JPanel(new BorderLayout());
				JPanel panelb = new JPanel(new FlowLayout(FlowLayout.CENTER));
				frame2.setLayout(new GridLayout(7, 1));
				panel1.add(titredoc);
				panel1.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Titre"));
				panel2.add(auteur);
				panel2.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Auteur"));
				panel3.add(annee);
				panel3.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Annee"));
				panel5.add(code);
				panel5.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Code"));
				panel6.add(nbPage);
				panel6.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Nombre de pages"));
				panel7.add(editeur);
				panel7.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Éditeur"));
				panelb.add(aj);
				frame2.add(panel1);
				frame2.add(panel2);
				frame2.add(panel3);
				frame2.add(panel5);
				frame2.add(panel6);
				frame2.add(panel7);
				frame2.add(panelb);
				frame2.pack();
				frame2.setVisible(true);
				aj.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e){
						model.addDoc(new Livre(titredoc.getText(), auteur.getText(), annee.getText(), code.getText(), nbPage.getText(), editeur.getText()));
						frame2.setVisible(false);
						frame2.dispose();
					}
				});
				return;
			}
			if ((s != null) && (s.length() > 0) && (s=="Vidéo")) {
				final JFrame frame3 = new JFrame("Ajout d'un document");
				frame3.setLocationRelativeTo(tableau);
				frame.setVisible(false);
				frame.dispose();
				final JTextField titrevid = new JTextField(30);
				final JTextField auteurvid = new JTextField(30);
				final JTextField anneevid = new JTextField(30);
				final JTextField codevid = new JTextField(30);
				final JTextField duree = new JTextField(30);
				final JTextField pays = new JTextField(30);
				JButton ajo = new JButton("Ajouter");
				JPanel pane1 = new JPanel(new BorderLayout());
				JPanel pane2 = new JPanel(new BorderLayout());
				JPanel pane3 = new JPanel(new BorderLayout());
				JPanel pane4 = new JPanel(new BorderLayout());
				JPanel pane5 = new JPanel(new BorderLayout());
				JPanel pane6 = new JPanel(new BorderLayout());
				JPanel paneb = new JPanel(new FlowLayout(FlowLayout.CENTER));
				frame3.setLayout(new GridLayout(7, 1));
				pane1.add(titrevid);
				pane1.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Titre"));
				pane2.add(auteurvid);
				pane2.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Réalisateur"));
				pane3.add(anneevid);
				pane3.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Annee"));
				pane4.add(codevid);
				pane4.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Code"));
				pane5.add(duree);
				pane5.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Durée"));
				pane6.add(pays);
				pane6.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Pays"));
				paneb.add(ajo);
				frame3.add(pane1);
				frame3.add(pane2);
				frame3.add(pane3);
				frame3.add(pane4);
				frame3.add(pane5);
				frame3.add(pane6);
				frame3.add(paneb);
				frame3.pack();
				frame3.setVisible(true);
				ajo.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e){
						model.addDoc(new Video(titrevid.getText(), auteurvid.getText(), anneevid.getText(), codevid.getText(), duree.getText(), pays.getText()));
						frame3.setVisible(false);
						frame3.dispose();
					}
				});
				return;
			}
			if ((s != null) && (s.length() > 0) && (s=="Audio")) {
				final JFrame frame4 = new JFrame("Ajout d'un document");
				frame4.setLocationRelativeTo(tableau);
				frame.setVisible(false);
				frame.dispose();
				final JTextField titrea = new JTextField(30);
				final JTextField auteura = new JTextField(30);
				final JTextField anneea = new JTextField(30);
				final JTextField codea = new JTextField(30);
				final JTextField genre = new JTextField(30);
				final JTextField paysa = new JTextField(30);
				JButton ajou = new JButton("Ajouter");
				JPanel pan1 = new JPanel(new BorderLayout());
				JPanel pan2 = new JPanel(new BorderLayout());
				JPanel pan3 = new JPanel(new BorderLayout());
				JPanel pan4 = new JPanel(new BorderLayout());
				JPanel pan5 = new JPanel(new BorderLayout());
				JPanel pan6 = new JPanel(new BorderLayout());
				JPanel panb = new JPanel(new FlowLayout(FlowLayout.CENTER));
				frame4.setLayout(new GridLayout(7, 1));
				pan1.add(titrea);
				pan1.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Titre"));
				pan2.add(auteura);
				pan2.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Artiste"));
				pan3.add(anneea);
				pan3.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Annee"));
				pan4.add(codea);
				pan4.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Code"));
				pan5.add(genre);
				pan5.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Genre"));
				pan6.add(paysa);
				pan6.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Pays"));
				panb.add(ajou);
				frame4.add(pan1);
				frame4.add(pan2);
				frame4.add(pan3);
				frame4.add(pan4);
				frame4.add(pan5);
				frame4.add(pan6);
				frame4.add(panb);
				frame4.pack();
				frame4.setVisible(true);
				ajou.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e){
						model.addDoc(new Audio(titrea.getText(), auteura.getText(), anneea.getText(), codea.getText(), genre.getText(), paysa.getText()));
						frame4.setVisible(false);
						frame4.dispose();
					}
				});
				return;
			}
        	}
	}

	private class RemoveAction extends AbstractAction {
		private RemoveAction() {
			super("Supprimmer");
		}

		public void actionPerformed(ActionEvent e) {
			int[] selection = model.getSelect(); //tableau.getSelectedRows()
			if(selection.length == 0) JOptionPane.showMessageDialog(tableau, "Sélectionnez les documents que vous voulez supprimer.");
			else{
				Document[] modelIndexes = model.getSelectDoc();
	 			/*for(int i = 0; i < selection.length; i++){
					modelIndexes[i] = tableau.getRowSorter().convertRowIndexToModel(selection[i]);
				}
				Arrays.sort(modelIndexes);*/
				for(int i = modelIndexes.length - 1; i >= 0; i--){
					model.removeDoc(modelIndexes[i]);
					//modelIndexes[i].dispose();
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
