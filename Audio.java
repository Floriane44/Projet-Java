import javax.swing.*;

public class Audio extends Document{
	private String genre;
	private String pays;
	private int nbSemEmprunt;

	public Audio(String titre, String auteur, String annee, String code, String genre, String pays){
		super(titre, auteur, annee, code);
		this.genre = genre;
		this.pays = pays;
		nbSemEmprunt = 1;
	}

	public String getTitreA(){
		return super.getTitre();
	}

	public String getAuteurA(){
		return super.getAuteur();
	}

	public String getAnneeA(){
		return super.getAnnee();
	}

	public String getCodeA(){
		return super.getCode();
	}

	public String getGenre(){
		return genre;
	}

	public String getPaysA(){
		return pays;
	}

	public int getNbSemEmpruntA(){
		return nbSemEmprunt;
	}

	public String descriptionA(){
		return "\n" + super.getTitre() + "\t\t" + super.getAuteur() + "\t\t" + super.getAnnee() +"\t" +super.getCode() + "\t" + this.getGenre() + "\t" + this.getPaysA();
	}
}
