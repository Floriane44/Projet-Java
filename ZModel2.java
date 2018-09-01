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

public class ZModel2 extends AbstractTableModel{
	private ArrayList<Document> data = new ArrayList<Document>();
	private String[] title = {"Titre", "Auteur/Réalisateur", "Année", "Type", "Date limite", "Afficher détails", "Sélection"};

	public ZModel2(Client cl){
		super();
		data = cl.getDocEmpr();
		/*Statement stmt = null;
		try {
			stmt = Connexion.getInstance().createStatement();
			Connexion.getInstance().setAutoCommit(false);
			ResultSet result = stmt.executeQuery("SELECT * FROM LIVRERESV");
			while(result.next()){
				if(cl.getNom().equals(result.getString("POSSESSEUR"))) data.add(new Livre(result.getString("TITRE"), result.getString("AUTEUR"), result.getString("ANNEE"), result.getString("CODE"), result.getString("PAGES"), result.getString("EDITEUR")));
			}
			ResultSet result2 = stmt.executeQuery("SELECT * FROM VIDEORESV");
			while(result2.next()){  
				if(cl.getNom().equals(result2.getString("POSSESSEUR"))) data.add(new Video(result.getString("TITRE"), result.getString("AUTEUR"), result.getString("ANNEE"), result.getString("CODE"), result.getString("DUREE"), result.getString("PAYS")));
			}
			ResultSet result3 = stmt.executeQuery("SELECT * FROM AUDIORESV");
			while(result3.next()){
				if(cl.getNom().equals(result3.getString("POSSESSEUR"))) data.add(new Audio(result.getString("TITRE"), result.getString("AUTEUR"), result.getString("ANNEE"), result.getString("CODE"), result.getString("GENRE"), result.getString("PAYS")));
			}
			result.close();
			result2.close();
			result3.close();
			stmt.close();
		} catch ( Exception e ) {
			System.err.println( e.getClass().getName() + ": " + e.getMessage() );
			System.exit(0);
		}*/
	}

	public int getColumnCount() {
		return title.length;
	}

	public int getRowCount() {
		return data.size();
	}

	public int getRow(Document doc){
		int Row = 0;
		for(int i=0; i<this.getRowCount(); i++){
			if(data.get(i) == doc) Row = i;
		}
		return Row;
	}

	public Object getValueAt(int row, int col) {
		switch(col){
			case 0:
			return data.get(row).getTitre();
			case 1:
			return data.get(row).getAuteur();
			case 2:
			return data.get(row).getAnnee();
			case 3:
			return data.get(row).getType(data.get(row));
			case 4:
			return data.get(row).getDeadline(data.get(row));
			case 5:
			return data.get(row).getDetail();
			case 6:
			return data.get(row).getEmprunte();
			default:
			return null;
		}
	}

	public String getColumnName(int col) {
		return title[col];
	}

	public Class getColumnClass(int col){
		//return this.data[0][col].getClass();
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
			Document doc = data.get(row);
			if(col==6) doc.setEmprunte((Boolean)aValue);
		}
	}

	public int[] getSelect(){
		LinkedList<Integer> select = new LinkedList<Integer>();
		int compt=0;
		for(int i = 0; i<this.getRowCount(); i++){
			if((Boolean)this.getValueAt(i, 6)==true){
				select.add(i);
				compt++;
			}
		}
		int selection[] = new int[select.size()];
		for(int i = 0; i<select.size(); i++){
			selection[i] = select.get(i);
		}
		return selection;
	}

	public Document getDoc(int row){
		return data.get(row);
	}

	public Document[] getSelectDoc(){
		LinkedList<Document> select = new LinkedList<Document>();
		for(int i = 0; i<this.getRowCount(); i++){
			if((Boolean)this.getValueAt(i, 6)==true){
				select.add(data.get(i));
			}
		}
		Document selection[] = new Document[select.size()];
		for(int i = 0; i<select.size(); i++){
			selection[i] = select.get(i);
		}
		return selection;
	}

	public void addDoc (Document doc){
		data.add(doc);
		fireTableRowsInserted(data.size()-1, data.size()-1);
	}

	/*public void addDoc (Document doc, Client cl){
		data.add(doc);
		doc.setPossesseur(cl);
		//System.out.println(doc.getPossesseur());
		fireTableRowsInserted(data.size()-1, data.size()-1);
	}*/

	public void removeDoc (Document doc){
		//data.get(getRow(doc)).set;
		data.remove(doc);
		fireTableRowsDeleted(getRow(doc), getRow(doc));
	}
}
