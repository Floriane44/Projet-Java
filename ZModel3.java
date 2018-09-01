import javax.swing.*;
import javax.swing.BorderFactory;
import javax.swing.border.EtchedBorder;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener; 
import java.io.*;
import java.lang.*;
import java.util.*;
import java.sql.*;

public class ZModel3 extends AbstractTableModel{
	private ArrayList<Client> data = new ArrayList<Client>();
	private String[] title = {"Nom", "Prénom", "Adresse mail", "Nombre d'emprunts", "Date d'inscription", "Afficher détails", "Sélection"};
	/*private char[] passwd = {'F','l','o', 'r','i','a','n','e'};
	private char[] passwd2 = {'P','r','é','n','o','m'};
	private Client client = new Client("Peltier", "Floriane", "fpeltier@et.esiea.fr", passwd);
	private Client client2 = new Client("Nom", "Prénom", "adresse@mail", passwd2);*/

	public ZModel3(){
		super();
		/*data.add(client);
		data.add(client2);
		fireTableDataChanged();*/
		Statement stmt = null;
		try {
			stmt = Connexion.getInstance().createStatement();
			Connexion.getInstance().setAutoCommit(false);
			ResultSet result = stmt.executeQuery("SELECT * FROM CLIENT");
			while(result.next()){
				data.add(new Client(result.getString("NOM"), result.getString("PRENOM"), result.getString("ADRESSE"), result.getString("PASS").toCharArray()));
			}
			result.close();
			stmt.close();
		} catch ( Exception e ) {
			System.err.println( e.getClass().getName() + ": " + e.getMessage() );
			System.exit(0);
		}
	}

	public int getColumnCount() {
		return title.length;
	}

	public int getRowCount() {
		return data.size();
	}

	public int getRow(Client c){
		int Row = 0;
		for(int i=0; i<this.getRowCount(); i++){
			if(data.get(i) == c) Row = i;
		}
		return Row;
	}

	public Client[] getClients(){
		Client[] clie = new Client[data.size()];
		for(int i = 0; i < data.size(); i++) clie[i] = data.get(i);
		return clie;
	}

	public Client getClient(int row){
		return data.get(row);
	}

	public Object getValueAt(int row, int col) {
		String sc = " ";
		switch(col){
			case 0:
			return data.get(row).getNom();
			case 1:
			return data.get(row).getPrenom();
			case 2:
			return data.get(row).getAdresse();
			case 3:
			return data.get(row).getNbEmpruntsEncours();
			case 4:
			return data.get(row).getDateInscription();
			case 5:
			return data.get(row).getDetails();
			case 6:
			return data.get(row).getSel();
			/*case 7:
			for(int i = 0; i < data.get(row).getNbEmpruntsEncours(); i++) sc = sc + data.get(row).getDocEmpr().get(i).getTitre();
			return sc;*/
			default:
			return null;
		}
	}

	public String getColumnName(int col) {
		return title[col];
	}

	public Class getColumnClass(int col){
		switch(col){
			case 5:
				return JButton.class;
			case 6:
				return Boolean.class;
			default:
				return Object.class;
		} 
	}

	public boolean isCellEditable(int row, int col){
		boolean a = false;
		if(col==5 || col==6) a = true;
		return a;
	}

	public void setValueAt(Object aValue, int row, int col){
		if(aValue != null){
			Client c = data.get(row);
			if(col==6) c.setSel((Boolean)aValue);
		}
	}

	public int[] getSelect(){
		LinkedList<Integer> select = new LinkedList<Integer>();
		for(int i = 0; i<this.getRowCount(); i++){
			if((boolean)this.getValueAt(i, 6)==true){
				select.add(i);
			}
		}
		int selection[] = new int[select.size()];
		for(int i = 0; i<select.size(); i++){
			selection[i] = select.get(i);
		}
		return selection;
	}

	public Client[] getSelectCli(){
		LinkedList<Client> select = new LinkedList<Client>();
		int compt=0;
		for(int i = 0; i<this.getRowCount(); i++){
			if((boolean)this.getValueAt(i, 6)==true){
				select.add(data.get(i));
				compt++;
			}
		}
		Client selection[] = new Client[select.size()];
		for(int i = 0; i<select.size(); i++){
			selection[i] = select.get(i);
		}
		return selection;
	}

	public void addCli (Client c){
		data.add(c);
		fireTableRowsInserted(data.size()-1, data.size()-1);
		Statement stmt = null;
		try {
			stmt = Connexion.getInstance().createStatement();
			Connexion.getInstance().setAutoCommit(false);
			String add = "INSERT INTO CLIENT (NOM,PRENOM,ADRESSE,PASS) " +
			"VALUES ('"+c.getNom()+"', '"+c.getPrenom()+"', '"+c.getAdresse()+"', '"+c.getpassword().toString()+"'); ";
			stmt.executeUpdate(add);
			Connexion.getInstance().commit();
			stmt.close();
		} catch ( Exception e ) {
			System.err.println( e.getClass().getName() + ": " + e.getMessage() );
			System.exit(0);
		}
	}

	public void removeCli (Client c){
		data.remove(c);
		fireTableRowsDeleted(getRow(c), getRow(c));
		Statement stmt = null;
		try {
			stmt = Connexion.getInstance().createStatement();
			Connexion.getInstance().setAutoCommit(false);
			String remove = "DELETE from CLIENT where NOM='"+c.getNom()+"';";
			stmt.executeUpdate(remove);
			Connexion.getInstance().commit();
			stmt.close();
		} catch ( Exception e ) {
			System.err.println( e.getClass().getName() + ": " + e.getMessage() );
			System.exit(0);
		}
	}
}
