import javax.swing.*;
import javax.swing.BorderFactory;
import javax.swing.border.EtchedBorder;
import javax.swing.border.BevelBorder;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener; 
import java.io.*;
import java.lang.*;
import java.util.*;

public class GUI implements ActionListener{
	private JFrame frame;
	private JButton button1 = new JButton("Ajouter ou supprimer un étudiant");
	private JButton button2 = new JButton("Ajouter ou voir les réservation");
	//private JButton button3 = new JButton("Voir les réservations");
	private JButton button4 = new JButton("Ajouter ou supprimer un document");
	//private Livre doc = new Livre();
	//private Livre liv = new Livre("Le rouge et le noir", "Stendhal", "1830", "002", "400", "Levasseur");
	//private Client etudiant = new Client();
	private ZModel model;
	//private ZModel2 model2;
	private ZModel3 model3;

	public GUI(ZModel model, /*ZModel2 model2,*/ ZModel3 model3) {
		frame = new JFrame("Médiathèque ESIEA (administrateur)");
		this.model = model;
		//this.model2 = model2;
		this.model3 = model3;
		JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		JPanel leftPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		JPanel ensemble = new JPanel(new GridLayout(2,1));
		JPanel image = new JPanel(new BorderLayout());
		JLabel icon = new JLabel(new ImageIcon(new ImageIcon("LogoESIEA.jpg").getImage().getScaledInstance(500, 302, Image.SCALE_DEFAULT)));
		frame.setMinimumSize(new Dimension(820,620));
		//frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setLayout(new GridLayout(2, 1));
		JLabel title = new JLabel("Bienvenue à la médiathèque ESIEA !", JLabel.LEADING);
		Font font = new Font("Arial", Font.BOLD, 38);
		title.setFont(font);
		topPanel.add(title);
		//frame.add(topPanel, BorderLayout.NORTH);
		button1.setActionCommand("key1");
		button1.addActionListener(this);
		button2.setActionCommand("key2");
		button2.addActionListener(this);
		//button3.setActionCommand("key3");
		//button3.addActionListener(this);
		button4.setActionCommand("key4");
		button4.addActionListener(this);
		leftPanel.add(button1);
		leftPanel.add(button2);
		//leftPanel.add(button3);
		leftPanel.add(button4);
		//frame.add(leftPanel);
		image.add(icon, BorderLayout.NORTH);
		image.setBorder(BorderFactory.createLineBorder(Color.BLUE, 4));
		ensemble.add(topPanel);
		ensemble.add(leftPanel);
		frame.add(ensemble, BorderLayout.NORTH);
		frame.add(image, BorderLayout.CENTER);
		frame.pack();
		frame.setVisible(true);
		//model2 = new AjoutRes().getModel2();
	}

	public void actionPerformed(ActionEvent e){
		switch(e.getActionCommand()){
			case "key1":
				new AjoutClient(model3).setVisible(true);
				break;
			case "key2":
				//frame.setVisible(false);
				//frame.dispose();
				new AjoutRes(model, model3).setVisible(true);
				model.fireTableDataChanged();
				//model2.fireTableDataChanged();
				break;
			/*case "key3":
				new Res(model2).setVisible(true);
				break;*/
			case "key4":
				//frame.setVisible(false);
				//frame.dispose();
				new AjoutDoc(model).setVisible(true);
				break;
		}
	}
}
