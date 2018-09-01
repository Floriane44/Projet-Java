import javax.swing.*;

public class Video extends Document{
	private String duree;
	private String pays;
	private int nbSemEmprunt;

	public Video(String titre, String auteur, String annee, String code, String duree, String pays){
		super(titre, auteur, annee, code);
		this.duree = duree;
		this.pays = pays;
		nbSemEmprunt = 1;
	}

	public String getTitreV(){
		return super.getTitre();
	}

	public String getAuteurV(){
		return super.getAuteur();
	}

	public String getAnneeV(){
		return super.getAnnee();
	}

	public String getCodeV(){
		return super.getCode();
	}

	public String getDuree(){
		return duree;
	}

	public String getPays(){
		return pays;
	}

	public int getNbSemEmpruntV(){
		return nbSemEmprunt;
	}

	public String description(){
		return "\n" + super.getTitre() + "\t\t" + super.getAuteur() + "\t\t" + super.getAnnee() +"\t" +super.getCode() + "\t" + this.getDuree() + "\t" + this.getPays();
	}
}
