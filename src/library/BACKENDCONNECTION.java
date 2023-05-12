package library;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class BACKENDCONNECTION {
  static Connection con = null;
    public static Connection getConnection() {
    try{
        Class.forName("com.mysql.cj.jdbc.Driver");
             con = DriverManager.getConnection("jdbc:mysql://localhost:3306/roy","root",""); 
    }
            catch(ClassNotFoundException | SQLException e){
                
            }return con;
}
}