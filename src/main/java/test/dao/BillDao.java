package test.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import test.modelo.Bill;
import test.util.Conexion;

public class BillDao {

private String jdbcURL = "jdbc:postgresql://: fanny.db.elephantsql.com/jnvgnqqv";
	
    private String jdbcUsername = "jnvgnqqv";
    
    private String jdbcPassword = "aTo0Yykrx9nCmRavmYFsikv_usQtfOen";
    
    
    private Conexion conexion;
    
    private static final String INSERT_USERS_SQL = "INSERT INTO bill (date_bill, user_id, value, type, observation) VALUES (?, ?, ?, ?, ?);";
    private static final String SELECT_USER_BY_ID = "SELECT * FROM bill WHERE id =?";
    private static final String SELECT_ALL_USERS = "SELECT * FROM bill";
    private static final String DELETE_USERS_SQL = "DELETE FROM bill WHERE id = ?;";
    private static final String UPDATE_USERS_SQL = "UPDATE bill SET date_bill = ?, user_id = ?, value = ?, type = ?, observation = ?;";
    
    protected Connection getConnection() {
        Connection connection = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return connection;
    }
    
    public void insertBill(Bill bill) throws SQLException {
        System.out.println(INSERT_USERS_SQL);

        // try-with-resource statement will auto close the connection.
        try (Connection connection = getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(INSERT_USERS_SQL)) {
            preparedStatement.setDate(1, bill.getDate_bill());
            preparedStatement.setInt(2, bill.getUser_id());
            preparedStatement.setInt(3, bill.getValue());
            preparedStatement.setInt(4, bill.getType());
            preparedStatement.setString(5, bill.getObservation());
            System.out.println(preparedStatement);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            //printSQLException(e);
        }
    }
    
    public Bill selectBill(int id) {
    	Bill bill = null;
        // Step 1: Establishing a Connection
        try (Connection connection = getConnection();
            // Step 2:Create a statement using connection object
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_USER_BY_ID);) {
            preparedStatement.setInt(1, id);
            System.out.println(preparedStatement);
            // Step 3: Execute the query or update query
            ResultSet rs = preparedStatement.executeQuery();

            // Step 4: Process the ResultSet object.
            while (rs.next()) {
                Date date_bill = rs.getDate("date_bill");
                Integer user_id = rs.getInt("user_id");
                Integer value = rs.getInt("value");
                Integer type = rs.getInt("type");
                String observation = rs.getString("observation");
                bill = new Bill(id, date_bill, user_id, value, type, observation);
            }
        } catch (SQLException e) {
            printSQLException(e);
        }
        return bill;
    }

    public List < Bill > selectAllUsers() {

        // using try-with-resources to avoid closing resources (boiler plate code)
        List < Bill > bills = new ArrayList < > ();
        // Step 1: Establishing a Connection
        try (Connection connection = getConnection();

            // Step 2:Create a statement using connection object
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_USERS);) {
            System.out.println(preparedStatement);
            // Step 3: Execute the query or update query
            ResultSet rs = preparedStatement.executeQuery();

            // Step 4: Process the ResultSet object.
            while (rs.next()) {
                int id = rs.getInt("id");
                Date date_bill = rs.getDate("date_bill");
                Integer user_id = rs.getInt("user_id");
                Integer value = rs.getInt("value");
                Integer type = rs.getInt("type");
                String observation = rs.getString("observation");
                bills.add(new Bill(id, date_bill, user_id, value, type, observation));
            }
        } catch (SQLException e) {
            printSQLException(e);
        }
        return bills;
    }

    public boolean deleteBill(int id) throws SQLException {
        boolean rowDeleted;
        try (Connection connection = getConnection(); PreparedStatement statement = connection.prepareStatement(DELETE_USERS_SQL);) {
            statement.setInt(1, id);
            rowDeleted = statement.executeUpdate() > 0;
        }
        return rowDeleted;
    }

    public boolean updateBill(Bill bill) throws SQLException {
        boolean rowUpdated;
        try (Connection connection = getConnection(); PreparedStatement statement = connection.prepareStatement(UPDATE_USERS_SQL);) {
            statement.setDate(1, bill.getDate_bill());
            statement.setInt(2, bill.getUser_id());
            statement.setInt(3, bill.getValue());
            statement.setInt(4, bill.getType());
            statement.setString(5, bill.getObservation());

            rowUpdated = statement.executeUpdate() > 0;
        }
        return rowUpdated;
    }
    
    private void printSQLException(SQLException ex) {
        for (Throwable e: ex) {
            if (e instanceof SQLException) {
                e.printStackTrace(System.err);
                System.err.println("SQLState: " + ((SQLException) e).getSQLState());
                System.err.println("Error Code: " + ((SQLException) e).getErrorCode());
                System.err.println("Message: " + e.getMessage());
                Throwable t = ex.getCause();
                while (t != null) {
                    System.out.println("Cause: " + t);
                    t = t.getCause();
                }
            }
        }
    }
}
