import java.sql.*;
import javax.swing.JOptionPane;
public class PostgresConn {
	Connection conn=null;
	public static Connection dbConnection()
	{
		try
		{
			Class.forName("org.postgresql.Driver");
			Connection conn=DriverManager.getConnection("jdbc:postgresql://localhost:5432/foodorderingsystem", "postgres","1021");
			return conn;
		}
		catch(Exception e)
		{
			JOptionPane.showMessageDialog(null, e);
			return null;
		}
	}
}