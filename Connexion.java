import java.sql.*;

public class Connexion{
	private static Connection c;

	private Connexion(){
		c = null;
		//Statement stmt = null;
		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:bibli.db");
			//c.setAutoCommit(false);
			
		} catch ( Exception e ) {
			System.err.println( e.getClass().getName() + ": " + e.getMessage() );
			System.exit(0);
		}
		//System.out.println("Table updated successfully");
	}

	public static Connection getInstance(){
		if(c == null){
			new Connexion();
		}
		return c;   
	}
}
