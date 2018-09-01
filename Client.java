import java.util.*;
import java.text.*;
import javax.swing.*;
import java.sql.*;

public class Client{
	private String nom;
	private String prenom;
	private String adresse;
	private char[] password;
	private Calendar dateInscription;
	private Calendar deadline;
	//private Calendar dateRenouvellement;
	private SimpleDateFormat form;
	//private int nbEmpruntsDepasses;
	private int nbEmpruntsEncours;
	private JButton detail;
	private Boolean sel;
	private ArrayList<Document> docEmpr;

	public Client(String nom, String prenom, String adresse, char[] password){
		this.nom = nom;
		this.prenom = prenom;
		this.adresse = adresse;
		this.password = password;
		//dateInscription = this.getDateInscription(false);
		//nbEmpruntsDepasses = 0;
		nbEmpruntsEncours = 0;
		form = new SimpleDateFormat("d MMM y");
		sel = new Boolean(false);
		docEmpr = new ArrayList<Document>();
		detail = new JButton("Détail");
	}

	public Client(String nom, String prenom){
		this.nom = nom;
		this.prenom = prenom;
	}

	public String getDateInscription(){
		String resultat = "Not found";
		dateInscription = Calendar.getInstance();
		java.util.Date newDate = dateInscription.getTime();
		resultat = form.format(newDate);
		/*if(compt){
			dateInscription.add(dateInscription.WEEK_OF_MONTH, 3);
			newDate = dateInscription.getTime();
			resultat = form.format(newDate);
		}*/
		return resultat;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getNom() {
		return nom;
	}

	public void setPrenom(String prenom) {
		this.prenom=prenom;
	}

	public String getPrenom() {
		return prenom;
	}
 
	public void setAdresse(String adresse){
		this.adresse=adresse;
	}

	public String getAdresse () {
		return adresse;
	}

	public char[] getpassword(){
		return password;
	}

	public int getNbEmpruntsEncours(){
		return nbEmpruntsEncours;
	}

	public void addNbEmpruntsEncours(){
		nbEmpruntsEncours++;
	}

	public void minusNbEmpruntsEncours(){
		nbEmpruntsEncours--;
	}

	public JButton getDetails(){
		return detail;
	}

	public Boolean getSel(){
		return sel;
	}

	public void setSel(Boolean sel){
		this.sel = sel;
	}

	public void addDocEmpr(Document doc){
		docEmpr.add(doc);
		/*Statement stmt = null;
		try {
			stmt = Connexion.getInstance().createStatement();
			Connexion.getInstance().setAutoCommit(false);
			if(doc.getType(doc)=="Livre"){
				String add = "INSERT INTO LIVRERESV (TITRE,AUTEUR,ANNEE,CODE,PAGES,EDITEUR,POSSESSEUR) " +
				"VALUES ('"+doc.getTitre()+"', '"+doc.getAuteur()+"', '"+doc.getAnnee()+"', '"+doc.getCode()+"', '"+doc.get1(doc)+"', '"+doc.get2(doc)+"', '"+doc.getPossesseur().getNom()+"'); ";
				stmt.executeUpdate(add);
				Connexion.getInstance().commit();
			}
			if(doc.getType(doc)=="Vidéo"){
				String add2 = "INSERT INTO VIDEORESV (TITRE,AUTEUR,ANNEE,CODE,DUREE,PAYS,POSSESSEUR) " +
				"VALUES ('"+doc.getTitre()+"', '"+doc.getAuteur()+"', '"+doc.getAnnee()+"', '"+doc.getCode()+"', '"+doc.get1(doc)+"', '"+doc.get2(doc)+"', '"+doc.getPossesseur().getNom()+"'); ";
				stmt.executeUpdate(add2);
				Connexion.getInstance().commit();
			}
			if(doc.getType(doc)=="Audio"){
				String add3 = "INSERT INTO AUDIORESV (TITRE,AUTEUR,ANNEE,CODE,GENRE,PAYS,POSSESSEUR) " +
				"VALUES ('"+doc.getTitre()+"', '"+doc.getAuteur()+"', '"+doc.getAnnee()+"', '"+doc.getCode()+"', '"+doc.get1(doc)+"', '"+doc.get2(doc)+"', '"+doc.getPossesseur().getNom()+"'); ";
				stmt.executeUpdate(add3);
				Connexion.getInstance().commit();
			}
			stmt.close();
		} catch ( Exception e ) {
			System.err.println( e.getClass().getName() + ": " + e.getMessage() );
			System.exit(0);
		}*/
	}

	public void removeDocEmpr(Document doc){
		docEmpr.remove(doc);
		/*Statement stmt = null;
		try {
			stmt = Connexion.getInstance().createStatement();
			Connexion.getInstance().setAutoCommit(false);
			if(doc.getType(doc)=="Livre"){
				String remove = "DELETE from LIVRERESV where TITRE='"+doc.getTitre()+"';";
				stmt.executeUpdate(remove);
				Connexion.getInstance().commit();
			}
			if(doc.getType(doc)=="Vidéo"){
				String remove2 = "DELETE from VIDEORESV where TITRE='"+doc.getTitre()+"';";
				stmt.executeUpdate(remove2);
				Connexion.getInstance().commit();
			}
			if(doc.getType(doc)=="Audio"){
				String remove3 = "DELETE from AUDIORESV where TITRE='"+doc.getTitre()+"';";
				stmt.executeUpdate(remove3);
				Connexion.getInstance().commit();
			}
			stmt.close();
		} catch ( Exception e ) {
			System.err.println( e.getClass().getName() + ": " + e.getMessage() );
			System.exit(0);
		}*/
	}

	public ArrayList<Document> getDocEmpr(){
		return docEmpr;
	}

	/*public void setDocEmpr(LinkedList<Documents> list){
		this.docEmpr = list;
	}*/

	public String toString(){
	        return this.getNom()+" "+this.getPrenom();
	}
}
