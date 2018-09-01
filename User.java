import javax.swing.*;
import javax.swing.BorderFactory;
import javax.swing.border.EtchedBorder;
import javax.swing.border.BevelBorder;
import javax.swing.JTable;
import javax.swing.JPasswordField;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener; 
import java.io.*;
import java.lang.*;
import java.util.*;
import java.sql.*;

public class User implements ActionListener{
	private JFrame frame;
	private JFrame frame3;
	private JFrame frameA;
	private ZModel model;
	private ZModel2 model2;
	private ZModel3 model3;
	private JComboBox <Client> liste;
	private JPasswordField pass;
	private JPasswordField passu;
	private JButton go = new JButton("Connexion");
	private JButton nouveau = new JButton("Nouvel Utilisateur");
	private JButton admin = new JButton("Administrateur");
	private Client[] possibilities;
	private char[] correctPassword = {'a', 'd', 'm', 'i', 'n'};

	public User(){
		frame = new JFrame("Médiathèque ESIEA : connexion");
		frame.setMinimumSize(new Dimension(520,320));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setLayout(new GridLayout(3, 1));
		JPanel topPanel = new JPanel(new GridLayout(2,1));
		JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		JLabel title = new JLabel("Qui êtes-vous ?\n", JLabel.LEADING);
		Font font = new Font("Arial", Font.BOLD, 28);
		title.setFont(font);
		model = new ZModel();
		model3 = new ZModel3();
		model3.fireTableDataChanged();
		//possibilities = model3.getNameClients();
		possibilities = model3.getClients();
		liste = new JComboBox <>(possibilities);
		topPanel.add(title);
		topPanel.add(liste);
		go.addActionListener(this);
		go.setActionCommand("key1");
		nouveau.addActionListener(this);
		nouveau.setActionCommand("key2");
		admin.addActionListener(this);
		admin.setActionCommand("key3");
		bottomPanel.add(go);
		bottomPanel.add(nouveau);
		bottomPanel.add(admin);
		frame.add(topPanel);
		frame.add(bottomPanel);
		frame.pack();
		frame.setVisible(true);
	}

	public void actionPerformed(ActionEvent e){
		switch(e.getActionCommand()){
			case "key1":
				frameA = new JFrame("Mot de passe");
				passu = new JPasswordField(10);
				frameA.setLocationRelativeTo(go);
				frameA.setMinimumSize(new Dimension(10, 20));
				frameA.setLayout(new GridLayout(2,1));
				JLabel enters = new JLabel("Entrez le mot de passe :");
				Font fontA = new Font("Arial", Font.BOLD, 14);
				enters.setFont(fontA);
				frameA.add(enters);
				frameA.add(passu);
				passu.addActionListener(this);
				passu.setActionCommand("key5");
				frameA.pack();
				frameA.setVisible(true);
				break;
			case "key2":
				final JFrame frame2 = new JFrame("Ajout d'un étudiant");
				frame2.setLocationRelativeTo(frame);
				final JTextField nomet = new JTextField(30);
				final JTextField prenomet = new JTextField(30);
				final JTextField adresset = new JTextField(30);
				final JPasswordField passwd = new JPasswordField(30);
				final JPasswordField passwd2 = new JPasswordField(30);
				JButton aj = new JButton("Connexion");
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
						Client cl = new Client(nomet.getText(), prenomet.getText(), adresset.getText(), passwd.getPassword());
						model2 = new ZModel2(cl);
						char[] essai = passwd.getPassword();
						char[] essai2 = passwd2.getPassword();
						if(isPasswordCorrect(essai, essai2)){
							model3.addCli(cl);
							model3.fireTableDataChanged();
							//possibilities = model3.getNameClients();
							new GUI2(model, model2, model3, cl);
							//frame.setVisible(false);
							frame2.setVisible(false);
						} else JOptionPane.showMessageDialog(frame,"Erreur dans la confirmation du mot de passe","Erreur",JOptionPane.ERROR_MESSAGE);
					}
				});
				break;
			case "key3":
				//new GUI(model, model2, model3);
				//frame.setVisible(false);
				frame3 = new JFrame("Mot de passe");
				pass = new JPasswordField(10);
				frame3.setLocationRelativeTo(admin);
				frame3.setMinimumSize(new Dimension(10, 20));
				frame3.setLayout(new GridLayout(2,1));
				JLabel enter = new JLabel("Entrez le mot de passe :");
				Font font2 = new Font("Arial", Font.BOLD, 14);
				enter.setFont(font2);
				frame3.add(enter);
				frame3.add(pass);
				pass.addActionListener(this);
				pass.setActionCommand("key4");
				frame3.pack();
				frame3.setVisible(true);
				break;
			case "key4":
				char[] input = pass.getPassword();
				if (isPasswordCorrect(input, correctPassword)) {
					//JOptionPane.showMessageDialog(frame,"Success! You typed the right password.");
					new GUI(model, model3);
					//frame.setVisible(false);
					frame3.setVisible(false);
				}else JOptionPane.showMessageDialog(frame,"Mot de passe invalide","Erreur",JOptionPane.ERROR_MESSAGE);
				//Arrays.fill(input, '0');
				pass.selectAll();
				//resetFocus();
				break;
			case "key5":
				char[] inputA = passu.getPassword();
				final Client c = (Client)liste.getSelectedItem();
				model2 = new ZModel2(c);
				char[] correc = c.getpassword();
				//System.out.println(c.getpassword());
				if (isPasswordCorrect(inputA, c.getpassword())) {
					new GUI2(model, model2, model3, c);
					//frame.setVisible(false);
					frameA.setVisible(false);
				}else JOptionPane.showMessageDialog(frame,"Mot de passe invalide","Erreur",JOptionPane.ERROR_MESSAGE);
				passu.selectAll();
				break;
		}
	}

	private static boolean isPasswordCorrect(char[] input, char[] correctPassword) {
		boolean isCorrect = true;
		if (input.length != correctPassword.length) {
			isCorrect = false;
		} else isCorrect = Arrays.equals (input, correctPassword);
		//Arrays.fill(correctPassword,'0');
		return isCorrect;
	}


}
