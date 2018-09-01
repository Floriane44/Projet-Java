import java.util.*;
import java.text.*;
import javax.swing.*;
import java.sql.*;

public class Document{
	private String titre;
	private String auteur;
	private String annee;
	private String code;
	private boolean empruntable;
	private Boolean emprunte;
	private JButton detail;
	private int nbSemEmprunt;
	private Client possesseur;
	private Calendar deadline;
	private SimpleDateFormat form = new SimpleDateFormat("d MMM y");
	

	public Document(String titre, String auteur, String annee, String code){
		this.titre = titre;
		this.auteur = auteur;
		this.annee = annee;
		this.code = code;
		empruntable = true;
		emprunte = new Boolean(false);
		detail = new JButton("Détail");
		possesseur = new Client("Personne", " ");
	}

	public String getTitre(){
		return titre;
	}

	public String getAuteur(){
		return auteur;
	}

	public String getAnnee(){
		return annee;
	}

	public String getCode(){
		return code;
	}

	public String get1(Document o){
		if(o instanceof Livre) return ((Livre)o).getNbPage();
		else if(o instanceof Video) return ((Video)o).getDuree();
		else if(o instanceof Audio) return ((Audio)o).getGenre();
		else return "Not found";
	}

	public String get2(Document o){
		if(o instanceof Livre) return ((Livre)o).getEditeur();
		else if(o instanceof Video) return ((Video)o).getPays();
		else if(o instanceof Audio) return ((Audio)o).getPaysA();
		else return "Not found";
	}

	public int getNbSemEnmrunt(Document o){
		if(o instanceof Livre) return ((Livre)o).getNbSemEmprunt();
		else if(o instanceof Video) return ((Video)o).getNbSemEmpruntV();
		else if(o instanceof Audio) return ((Audio)o).getNbSemEmpruntA();
		else return 0;
	}

	public JButton getDetail(){
		return detail;
	}

	public String getType(Document o){
		String sc = new String();
		if(o instanceof Livre) sc = "Livre";
		else if(o instanceof Video) sc = "Vidéo";
		else if(o instanceof Audio) sc = "Audio";
		else sc = "Document";
		return sc;
	}

	public boolean getEmpruntable(){
		return empruntable;
	}

	public String getStringEmpruntable(){
		if(this.empruntable == true) return "Oui";
		if(this.empruntable == false) return "Non";
		else return "-";
	}

	public void setEmpruntable(boolean b){
		this.empruntable = b;
	}

	public Boolean getEmprunte(){
		return emprunte;
	}

	public void setEmprunte(Boolean emprunte){
		this.emprunte = emprunte;
		//System.out.println(emprunte.toString());
	}

	public String getDeadline(Document o){
		String resultat = "-";
		if(empruntable == false){
			deadline = Calendar.getInstance();
			deadline.add(deadline.WEEK_OF_MONTH, this.getNbSemEnmrunt(o));
			java.util.Date newDate = deadline.getTime();
			resultat = form.format(newDate);
		}
		return resultat;
	}

	public void setPossesseur(Client c){
		this.possesseur = c;
		//System.out.println(this.getPossesseur());
	}

	public Client getPossesseur(){
		return possesseur;
	}
}
