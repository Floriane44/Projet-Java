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

public class AjoutClient extends JFrame{
	private JTextField field;
	private ZModel3 model3;
	private JTable tableau;
	private JButton ajout;
	private JButton suppr;
	private JButton search;
	private TableRowSorter<TableModel> sorter;

	public AjoutClient(ZModel3 model3){
		super("Ajouter ou supprimer un étudiant");
		this.model3 = model3;
		this.tableau = new JTable(model3);
		this.sorter  = new TableRowSorter<TableModel>(tableau.getModel());
		this.setMinimumSize(new Dimension(1800,320));
		this.setLayout(new GridLayout(2, 1));
		JPanel titre = new JPanel (new GridLayout(2,1));
		JLabel title = new JLabel("Liste des étudiants :");
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
		tableau.setAutoCreateRowSorter(true);
		tableau.setRowSorter(sorter);
		tableau.getColumn("Afficher détails").setCellRenderer(new ButtonRenderer());
		tableau.getColumn("Afficher détails").setCellEditor(new ButtonEditor3(new JCheckBox()));
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
			final JFrame frame2 = new JFrame("Ajout d'un étudiant");
			frame2.setLocationRelativeTo(tableau);
			final JTextField nomet = new JTextField(30);
			final JTextField prenomet = new JTextField(30);
			final JTextField adresset = new JTextField(30);
			final JPasswordField passwd = new JPasswordField(30);
			final JPasswordField passwd2 = new JPasswordField(30);
			JButton aj = new JButton("Ajouter");
			JPanel panel1 = new JPanel(new BorderLayout());
			JPanel panel2 = new JPanel(new BorderLayout());
			JPanel panel3 = new JPanel(new BorderLayout());
			JPanel panel4 = new JPanel(new BorderLayout());
			JPanel panel5 = new JPanel(new BorderLayout());
			JPanel panelb = new JPanel(new FlowLayout(FlowLayout.CENTER));
			frame2.setLayout(new GridLayout(6, 1));
			panel1.add(nomet);
			panel1.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Nom"));
			panel2.add(prenomet);
			panel2.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Prénom"));
			panel3.add(adresset);
			panel3.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Adresse mail"));
			panel4.add(passwd);
			panel4.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Mot de passe"));
			panel5.add(passwd2);
			panel5.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Confirmer le mot de passe"));
			panelb.add(aj);
			frame2.add(panel1);
			frame2.add(panel2);
			frame2.add(panel3);
			frame2.add(panel4);
			frame2.add(panel5);
			frame2.add(panelb);
			frame2.pack();
			frame2.setVisible(true);
			aj.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e){
					char[] essai = passwd.getPassword();
					char[] essai2 = passwd2.getPassword();
					if(isPasswordCorrect(essai, essai2)){
						model3.addCli(new Client(nomet.getText(), prenomet.getText(), adresset.getText(), passwd.getPassword()));
						frame2.setVisible(false);
						frame2.dispose();
					} else JOptionPane.showMessageDialog(tableau,"Erreur dans la confirmation du mot de passe","Erreur",JOptionPane.ERROR_MESSAGE);
					Arrays.fill(essai, '0');
					Arrays.fill(essai2, '0');
				}
			});
		}

		private boolean isPasswordCorrect(char[] input, char[] correctPassword) {
			boolean isCorrect = true;
			if (input.length != correctPassword.length) {
				isCorrect = false;
			} else isCorrect = Arrays.equals (input, correctPassword);
			Arrays.fill(correctPassword,'0');
			return isCorrect;
		}
	}

	private class RemoveAction extends AbstractAction {
		private RemoveAction() {
			super("Supprimmer");
		}

		public void actionPerformed(ActionEvent e) {
			int[] selection = model3.getSelect(); //tableau.getSelectedRows();
			if(selection.length == 0) JOptionPane.showMessageDialog(tableau, "Sélectionnez les étudiants que vous voulez supprimer.");
			else{
				Client[] modelIndexes = model3.getSelectCli();
 				/*for(int i = 0; i < selection.length; i++){
					modelIndexes[i] = tableau.getRowSorter().convertRowIndexToModel(selection[i]);
				}
				Arrays.sort(modelIndexes);*/
				for(int i = modelIndexes.length - 1; i >= 0; i--){
					model3.removeCli(modelIndexes[i]);
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
