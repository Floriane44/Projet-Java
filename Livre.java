import javax.swing.*;

public class Livre extends Document{
	private String nbPage;
	private String editeur;
	private int nbSemEmprunt;


	public Livre(String titre, String auteur, String annee, String code, String nbPage, String editeur){
		super(titre, auteur, annee, code);
		this.nbPage = nbPage;
		this.editeur = editeur;
		nbSemEmprunt = 3;
	}

	public String getTitreL(){
		return super.getTitre();
	}

	public String getAuteurL(){
		return super.getAuteur();
	}

	public String getAnneeL(){
		return super.getAnnee();
	}

	public String getCodeL(){
		return super.getCode();
	}

	public String getNbPage(){
		return nbPage;
	}

	public String getEditeur(){
		return editeur;
	}

	public int getNbSemEmprunt(){
		return nbSemEmprunt;
	}

	public String description(){
		return "\n" + super.getTitre() + "\t\t" + super.getAuteur() + "\t\t" + super.getAnnee() +"\t" +super.getCode() + "\t" + this.getEditeur() + "\t" + this.getNbPage();
	}
}
