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

public class ZModel extends AbstractTableModel{
	private ArrayList<Document> data = new ArrayList<Document>();
	private String[] title = {"Titre", "Auteur/Réalisateur", "Année", "Type", "Empruntable", "Possesseur", "Afficher détails", "Sélection"};

	public ZModel(){
		super();
		Statement stmt = null;
		try {
			stmt = Connexion.getInstance().createStatement();
			Connexion.getInstance().setAutoCommit(false);
			ResultSet result = stmt.executeQuery("SELECT * FROM LIVRE");
			while(result.next()){         
				data.add(new Livre(result.getString("TITRE"), result.getString("AUTEUR"), result.getString("ANNEE"), result.getString("CODE"), result.getString("PAGES"), result.getString("EDITEUR")));
			}
			ResultSet result2 = stmt.executeQuery("SELECT * FROM VIDEO");
			while(result2.next()){  
				data.add(new Video(result.getString("TITRE"), result.getString("AUTEUR"), result.getString("ANNEE"), result.getString("CODE"), result.getString("DUREE"), result.getString("PAYS")));
			}
			ResultSet result3 = stmt.executeQuery("SELECT * FROM AUDIO");
			while(result3.next()){
				data.add(new Audio(result.getString("TITRE"), result.getString("AUTEUR"), result.getString("ANNEE"), result.getString("CODE"), result.getString("GENRE"), result.getString("PAYS")));
			}
			result.close();
			result2.close();
			result3.close();
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

	public int getRow(Document doc){
		int Row = 0;
		for(int i=0; i<this.getRowCount(); i++){
			if(data.get(i) == doc) Row = i;
		}
		return Row;
	}

	public Document getDoc(int row){
		return data.get(row);
	}

	public Object getValueAt(int row, int col) {
		//return this.data[row][col];
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
			if(data.get(row).getEmpruntable() == false) return "Non";
			if(data.get(row).getEmpruntable() == true) return "Oui";
			else return "Not Found";
			case 5:
			return data.get(row).getPossesseur();
			case 6:
			return data.get(row).getDetail();
			case 7:
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
			case 6:
				return JButton.class;
			case 7:
				return Boolean.class;
			default:
				return Object.class;
		} 
	}

	public boolean isCellEditable(int row, int col){
		boolean a = false;
		if(col==7 || col==6) a = true;
		return a;
	}

	public void setValueAt(Object aValue, int row, int col){
		if(aValue != null){
			Document doc = data.get(row);
			if(col==7) doc.setEmprunte((Boolean)aValue);
		}
	}


	public int[] getSelect(){
		LinkedList<Integer> select = new LinkedList<Integer>();
		for(int i = 0; i<this.getRowCount(); i++){
			if((Boolean)this.getValueAt(i, 7)==true){
				select.add(i);
			}
		}
		int selection[] = new int[select.size()];
		for(int i = 0; i<select.size(); i++){
			selection[i] = select.get(i);
		}
		return selection;
	}

	public Document[] getSelectDoc(){
		LinkedList<Document> select = new LinkedList<Document>();
		for(int i = 0; i<this.getRowCount(); i++){
			if((Boolean)this.getValueAt(i, 7)==true){
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
		Statement stmt = null;
		try {
			stmt = Connexion.getInstance().createStatement();
			Connexion.getInstance().setAutoCommit(false);
			if(doc.getType(doc)=="Livre"){
				String add = "INSERT INTO LIVRE (TITRE,AUTEUR,ANNEE,CODE,PAGES,EDITEUR) " +
				"VALUES ('"+doc.getTitre()+"', '"+doc.getAuteur()+"', '"+doc.getAnnee()+"', '"+doc.getCode()+"', '"+doc.get1(doc)+"', '"+doc.get2(doc)+"'); ";
				stmt.executeUpdate(add);
				Connexion.getInstance().commit();
			}
			if(doc.getType(doc)=="Vidéo"){
				String add2 = "INSERT INTO VIDEO (TITRE,AUTEUR,ANNEE,CODE,DUREE,PAYS) " +
				"VALUES ('"+doc.getTitre()+"', '"+doc.getAuteur()+"', '"+doc.getAnnee()+"', '"+doc.getCode()+"', '"+doc.get1(doc)+"', '"+doc.get2(doc)+"'); ";
				stmt.executeUpdate(add2);
				Connexion.getInstance().commit();
			}
			if(doc.getType(doc)=="Audio"){
				String add3 = "INSERT INTO AUDIO (TITRE,AUTEUR,ANNEE,CODE,GENRE,PAYS) " +
				"VALUES ('"+doc.getTitre()+"', '"+doc.getAuteur()+"', '"+doc.getAnnee()+"', '"+doc.getCode()+"', '"+doc.get1(doc)+"', '"+doc.get2(doc)+"'); ";
				stmt.executeUpdate(add3);
				Connexion.getInstance().commit();
			}
			stmt.close();
		} catch ( Exception e ) {
			System.err.println( e.getClass().getName() + ": " + e.getMessage() );
			System.exit(0);
		}
		
	}

	public void removeDoc (Document doc){
		data.remove(doc);
		fireTableRowsDeleted(getRow(doc), getRow(doc));
		Statement stmt = null;
		try {
			stmt = Connexion.getInstance().createStatement();
			Connexion.getInstance().setAutoCommit(false);
			if(doc.getType(doc)=="Livre"){
				String remove = "DELETE from LIVRE where TITRE='"+doc.getTitre()+"';";
				stmt.executeUpdate(remove);
				Connexion.getInstance().commit();
			}
			if(doc.getType(doc)=="Vidéo"){
				String remove2 = "DELETE from VIDEO where AUTEUR='"+doc.getTitre()+"';";
				stmt.executeUpdate(remove2);
				Connexion.getInstance().commit();
			}
			if(doc.getType(doc)=="Audio"){
				String remove3 = "DELETE from AUDIO where TITRE='"+doc.getTitre()+"';";
				stmt.executeUpdate(remove3);
				Connexion.getInstance().commit();
			}
			stmt.close();
		} catch ( Exception e ) {
			System.err.println( e.getClass().getName() + ": " + e.getMessage() );
			System.exit(0);
		}
	}
}
