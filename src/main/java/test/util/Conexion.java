package test.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


	public class Conexion {

		private Connection con = null;
		private static Conexion db;
		private PreparedStatement preparedStatement;
		
		private final static String url= "jdbc:postgresql://: fanny.db.elephantsql.com/";
	    private final static String dbName = "jnvgnqqv";
	    private final static String driver = "org.postgresql.Driver";   /*org.postgresql.Driver*/
	    private final static String userName = "jnvgnqqv";
	    private final static String password = "aTo0Yykrx9nCmRavmYFsikv_usQtfOen";
	    
	    public Conexion() {
	    	
	    }

	    public Conexion(String driver, String url, String dbName, String userName, String password) {
			try {
				Class.forName(driver).newInstance();
				con = (Connection)DriverManager.getConnection(url+dbName,userName,password);
			} catch (InstantiationException | IllegalAccessException
					| ClassNotFoundException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	    
	    public static Conexion getConexion(){
			if ( db == null ) {
	            db = new Conexion();
	        }
			return db;
		}
		
		
		public PreparedStatement setPreparedStatement(String sql) throws SQLException {
			if ( db == null ) {
	            db = new Conexion();
	        }
			
			this.preparedStatement = con.prepareStatement(sql);
			return this.preparedStatement;
		}
	    
	    public Connection getCon() {
			return this.con;
		}
		
		public void cerrarConexion(){
			try {
				this.con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		public ResultSet query() throws SQLException{
	        ResultSet res = this.preparedStatement.executeQuery();
	        return res;
	    }
		
		public int execute() throws SQLException {
	        int result = this.preparedStatement.executeUpdate();
	        return result;
	 
	    }
			
			
		

}