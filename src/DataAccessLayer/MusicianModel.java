//package DataAccessLayer;
//import java.sql.*;
//import java.util.ArrayList;
//
//public class MusicianModel {
//    // Assuming DBConnection is a singleton utility class that handles database connections.
//    private Connection conn = DBConnection.getInstance().getConnection();
//
//    public ArrayList<Musician> getAllMusicians() throws SQLException {
//        ArrayList<Musician> musicians = new ArrayList<>();
//        String sql = "SELECT * FROM Musicians";
//        Statement statement = conn.createStatement();
//        ResultSet resultSet = statement.executeQuery(sql);
//        
//        while (resultSet.next()) {
//            String ssn = resultSet.getString("ssn");
//            String name = resultSet.getString("name");
//            // ... extract other fields
//            musicians.add(new Musician(ssn, name /*, ... other fields */));
//        }
//        return musicians;
//    }
//    
//    // ... additional methods for insert, update, delete
//}
