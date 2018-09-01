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

public class GUI2 implements ActionListener{
	private JFrame frame;
	private JButton button2 = new JButton("Réserver un document");
	private JButton button4 = new JButton("Voir mes documents réservés");
	private ZModel model;
	private ZModel2 model2;
	private ZModel3 model3;
	private Client cl;

	public GUI2(ZModel model, ZModel2 model2, ZModel3 model3, Client cl) {
		frame = new JFrame("Médiathèque ESIEA (user)");
		this.model = model;
		this.model2 = model2;
		this.model3 = model3;
		this.cl = cl;
		JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		JPanel leftPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		JPanel ensemble = new JPanel(new GridLayout(2,1));
		JPanel image = new JPanel(new BorderLayout());
		JLabel icon = new JLabel(new ImageIcon(new ImageIcon("LogoESIEA.jpg").getImage().getScaledInstance(500, 302, Image.SCALE_DEFAULT)));
		frame.setMinimumSize(new Dimension(820,620));
		//frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setLayout(new GridLayout(2, 1));
		JLabel title = new JLabel("Bienvenue à la médiathèque ESIEA " + cl.getPrenom() + " !", JLabel.LEADING);
		Font font = new Font("Arial", Font.BOLD, 38);
		title.setFont(font);
		topPanel.add(title);
		button2.setActionCommand("key2");
		button2.addActionListener(this);
		button4.setActionCommand("key4");
		button4.addActionListener(this);
		leftPanel.add(button2);
		leftPanel.add(button4);
		image.add(icon, BorderLayout.NORTH);
		image.setBorder(BorderFactory.createLineBorder(Color.BLUE, 4));
		ensemble.add(topPanel);
		ensemble.add(leftPanel);
		frame.add(ensemble, BorderLayout.NORTH);
		frame.add(image, BorderLayout.CENTER);
		frame.pack();
		frame.setVisible(true);
	}

	public void actionPerformed(ActionEvent e){
		switch(e.getActionCommand()){
			case "key2":
				new AjoutRes(model, model2, cl).setVisible(true);
				model.fireTableDataChanged();
				model2.fireTableDataChanged();
				break;
			case "key4":
				new Res(model2, cl).setVisible(true);
				break;
		}
	}
}
