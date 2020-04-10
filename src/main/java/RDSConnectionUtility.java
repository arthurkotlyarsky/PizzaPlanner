

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.mysql.jdbc.PreparedStatement;

public class RDSConnectionUtility {

	// JDBC driver name and database URL
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	static final String DB_URL = "jdbc:mysql://pizzadb.cggr05ybtkpi.us-east-2.rds.amazonaws.com:3306/pizza";

	// Database credentials
	static final String USER = "admin";
	static final String PASS = "admin_admin";

	public static void rdsJDBCConnection(Customer customerVO) {
		Connection conn = null;
		PreparedStatement preparedStatement = null;
		try {
			// STEP 2: Register JDBC driver
			Class.forName("com.mysql.jdbc.Driver");

			// STEP 3: Open a connection
			System.out.println("Connecting to a selected database...");
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
			System.out.println("Connected database successfully...");
			conn.setAutoCommit(true);
			System.out.println("Insert statement...");
			String query = " INSERT INTO pizza.customer_order (name,dob,card_number,card_expiry_date,toppings,order_number) "
					+ " VALUES(?,?,aes_encrypt(?,'privatekey123'),?,?,RAND() * 999999) ";
			preparedStatement = (PreparedStatement) conn.prepareStatement(query);

			preparedStatement.setString(1, customerVO.getName());
			preparedStatement.setString(2, customerVO.getDob());
			preparedStatement.setString(3, customerVO.getCard_number());
			preparedStatement.setString(4, customerVO.getCard_expiry_date());
			preparedStatement.setString(5, customerVO.getToppings());
			
			preparedStatement.execute(); //Executing insert statement
			System.out.println("Customer Order Record Inserted...");
		} catch (SQLException se) {
			se.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (preparedStatement != null)
					conn.close();
			} catch (SQLException se) {
			}
			try {
				if (conn != null)
					conn.close();
			} catch (SQLException se) {
				se.printStackTrace();
			}
		}
	}

}
