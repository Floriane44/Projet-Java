import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class Mediatheque{

	public static void main (String[] args){
		//c = null;
		Statement stmt = null;
		try {
			
			stmt = Connexion.getInstance().createStatement();
			Connexion.getInstance().setAutoCommit(false);
			String sql = "CREATE TABLE IF NOT EXISTS LIVRE " +
				"(TITRE		 TEXT    NOT NULL," +
				" AUTEUR         TEXT    NOT NULL, " + 
				" ANNEE          TEXT    NOT NULL, " + 
				" CODE        	 TEXT	 NOT NULL, " +
				" PAGES		 TEXT	 NOT NULL, " +
				" EDITEUR	 TEXT	 NOT NULL)";
			stmt.executeUpdate(sql);
			sql = "CREATE TABLE IF NOT EXISTS VIDEO " +
				"(TITRE		 TEXT    NOT NULL," +
				" AUTEUR         TEXT    NOT NULL, " + 
				" ANNEE          TEXT    NOT NULL, " + 
				" CODE        	 TEXT	 NOT NULL, " +
				" DUREE		 TEXT	 NOT NULL, " +
				" PAYS		 TEXT	 NOT NULL, " +
			stmt.executeUpdate(sql);
			String sql3 = "CREATE TABLE IF NOT EXISTS AUDIO " +
				"(TITRE		 TEXT    NOT NULL," +
				" AUTEUR         TEXT    NOT NULL, " + 
				" ANNEE          TEXT    NOT NULL, " + 
				" CODE        	 TEXT	 NOT NULL, " +
				" GENRE		 TEXT	 NOT NULL, " +
				" PAYS		 TEXT	 NOT NULL)";
			stmt.executeUpdate(sql3);
			String sql4 = "CREATE TABLE IF NOT EXISTS CLIENT " +
				"(NOM		TEXT	NOT NULL," +
				"PRENOM		TEXT	NOT NULL, " +
				"ADRESSE	TEXT	NOT NULL, " +
				"PASS		TEXT	NOT NULL )";
			stmt.executeUpdate(sql4);
			stmt.close();
		} catch ( Exception e ) {
			System.err.println( e.getClass().getName() + ": " + e.getMessage() );
			System.exit(0);
		}
		new User();
	}
}
