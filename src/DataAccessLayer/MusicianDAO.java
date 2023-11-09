package DataAccessLayer;

import DataAccessLayer.Musician;
import DataAccessLayer.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import BusinessLogicLayer.InstrumentService;

/**
 * MusicianDAO class to handle database operations for Musicians.
 */
public class MusicianDAO {
    private InstrumentService instrumentService;

    public MusicianDAO() {
        this.instrumentService = new InstrumentService();
    }
    /**
     * Retrieves a musician by their identifier.
     *
     * @param musicianId The identifier of the musician to retrieve.
     * @return The Musician object, or null if not found.
     */
    public Musician getMusicianById(String ssn) {
        // Constructing the SQL query that joins the required tables to fetch musician details.
        final String query = "SELECT m.ssn, m.name, l.phone, l.address, p.instrId " +
                             "FROM notownrecords.musicians m " +
                             "JOIN lives l ON m.ssn = l.ssn " +
                             "JOIN plays p ON m.ssn = p.ssn " +
                             "WHERE m.ssn = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            
            pstmt.setString(1, ssn);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                // Assuming that you have an InstrumentService that can find an instrument by its ID
                // and return its name or the Instrument object itself.
                InstrumentService instrumentService = new InstrumentService();
                String instrumentId = rs.getString("instrId");
                Instrument instrument = instrumentService.findInstrumentById(instrumentId);
                
                // Creating the Musician object with all the retrieved details.
                return new Musician(
                    rs.getString("ssn"),
                    rs.getString("name"),
                    rs.getString("address"),
                    rs.getString("phone"),
                    instrumentId
                    );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }



     public Musician getMusicianBySSN(String ssn) {
        final String query = "SELECT m.ssn, m.name, l.phone, l.address, p.instrId " +
                             "FROM notownrecords.musicians m " +
                             "JOIN lives l ON m.ssn = l.ssn " +
                             "JOIN plays p ON m.ssn = p.ssn " +
                             "WHERE m.ssn = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            
            pstmt.setString(1, ssn);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                String instrId = rs.getString("instrId");
                // String instrName = this.instrumentService.findInstrumentById(instrId).getName();
                return new Musician(
                        rs.getString("ssn"),
                        rs.getString("name"),
                        rs.getString("address"),
                        rs.getString("phone"),
                        instrId
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }



    /**
     * Adds a new musician to the database.
     *
     * @param musician The Musician object to add.
     * @return true if the operation was successful.
     */

     public boolean addMusician(Musician musician) {
        Connection conn = null;
        PreparedStatement pstmt1 = null, pstmt2 = null, pstmt3 = null;
        boolean success = false;

        final String insertMusicianQuery = "INSERT INTO notownrecords.musicians (ssn, name) VALUES (?, ?)";
        final String insertLivesQuery = "INSERT INTO lives (ssn, phone, address) VALUES (?, ?, ?)";
        final String insertPlaysQuery = "INSERT INTO plays (ssn, instrId) VALUES (?, ?)";

        try {
            conn = DatabaseConnection.getConnection();
            conn.setAutoCommit(false);

            // Insert into notownrecords.musicians
            pstmt1 = conn.prepareStatement(insertMusicianQuery);
            pstmt1.setString(1, musician.getSsn());
            pstmt1.setString(2, musician.getName());
            pstmt1.executeUpdate();

            // Insert into lives
            pstmt2 = conn.prepareStatement(insertLivesQuery);
            pstmt2.setString(1, musician.getSsn());
            pstmt2.setString(2, musician.getPhoneNumber());
            pstmt2.setString(3, musician.getAddress());
            pstmt2.executeUpdate();

            // Insert into plays
            pstmt3 = conn.prepareStatement(insertPlaysQuery);
            pstmt3.setString(1, musician.getSsn());
            pstmt3.setString(2, musician.getIntsrument_id());
            pstmt3.executeUpdate();

            conn.commit();
            success = true;
        } catch (SQLException e) {
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            e.printStackTrace();
        } finally {
            try {
                if (pstmt1 != null) pstmt1.close();
                if (pstmt2 != null) pstmt2.close();
                if (pstmt3 != null) pstmt3.close();
                if (conn != null) {
                    conn.setAutoCommit(true);
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return success;
    }


    /**
     * Updates an existing musician's details in the database.
     *
     * @param musician The Musician object to update.
     * @return true if the operation was successful.
     */

     public boolean updateMusician(Musician musician) {
        Connection conn = null;
        PreparedStatement pstmt1 = null, pstmt2 = null, pstmt3 = null;
        boolean success = false;

        final String updateMusicianQuery = "UPDATE notownrecords.musicians SET name = ? WHERE ssn = ?";
        final String updateLivesQuery = "UPDATE lives SET phone = ?, address = ? WHERE ssn = ?";
        final String updatePlaysQuery = "UPDATE plays SET instrId = ? WHERE ssn = ?";

        try {
            conn = DatabaseConnection.getConnection();
            conn.setAutoCommit(false);

            // Update notownrecords.musicians
            pstmt1 = conn.prepareStatement(updateMusicianQuery);
            pstmt1.setString(1, musician.getName());
            pstmt1.setString(2, musician.getSsn());
            pstmt1.executeUpdate();

            // Update lives
            pstmt2 = conn.prepareStatement(updateLivesQuery);
            pstmt2.setString(1, musician.getPhoneNumber());
            pstmt2.setString(2, musician.getAddress());
            pstmt2.setString(3, musician.getSsn());
            pstmt2.executeUpdate();

            // Update plays
            pstmt3 = conn.prepareStatement(updatePlaysQuery);
            pstmt3.setString(1, musician.getIntsrument_id());
            pstmt3.setString(2, musician.getSsn());
            pstmt3.executeUpdate();

            conn.commit();
            success = true;
        } catch (SQLException e) {
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            e.printStackTrace();
        } finally {
            try {
                if (pstmt1 != null) pstmt1.close();
                if (pstmt2 != null) pstmt2.close();
                if (pstmt3 != null) pstmt3.close();
                if (conn != null) {
                    conn.setAutoCommit(true);
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return success;
    }



    /**
     * Deletes a musician from the database by their identifier.
     *
     * @param musicianId The identifier of the musician to delete.
     * @return true if the operation was successful.
     */
    
     public boolean deleteMusician(String ssn) {
        Connection conn = null;
        PreparedStatement pstmt1 = null, pstmt2 = null, pstmt3 = null;
        boolean success = false;

        final String deletePlaysQuery = "DELETE FROM plays WHERE ssn = ?";
        final String deleteLivesQuery = "DELETE FROM lives WHERE ssn = ?";
        final String deleteMusicianQuery = "DELETE FROM notownrecords.musicians WHERE ssn = ?";

        try {
            conn = DatabaseConnection.getConnection();
            conn.setAutoCommit(false);

            // Delete from plays
            pstmt1 = conn.prepareStatement(deletePlaysQuery);
            pstmt1.setString(1, ssn);
            pstmt1.executeUpdate();

            // Delete from lives
            pstmt2 = conn.prepareStatement(deleteLivesQuery);
            pstmt2.setString(1, ssn);
            pstmt2.executeUpdate();

            // Delete from notownrecords.musicians
            pstmt3 = conn.prepareStatement(deleteMusicianQuery);
            pstmt3.setString(1, ssn);
            pstmt3.executeUpdate();

            conn.commit();
            success = true;
        } catch (SQLException e) {
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            e.printStackTrace();
        } finally {
            try {
                if (pstmt1 != null) pstmt1.close();
                if (pstmt2 != null) pstmt2.close();
                if (pstmt3 != null) pstmt3.close();
                if (conn != null) {
                    conn.setAutoCommit(true);
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return success;
    }
     public List<Musician> getAllMusicians() {
    	    List<Musician> musicians = new ArrayList<>();
			  final String query = "SELECT m.ssn, m.name, l.phone, l.address, p.instrId " +
			  "FROM notownrecords.musicians m " +
			  "JOIN lives l ON m.ssn = l.ssn " +
			  "JOIN plays p ON m.ssn = p.ssn";
            
    	    try (Connection conn = DatabaseConnection.getConnection();
    	         Statement stmt = conn.createStatement();
    	         ResultSet rs = stmt.executeQuery(query)) {

    	        while (rs.next()) {
    	            musicians.add(new Musician(
    	                    rs.getString("ssn"),
    	                    rs.getString("name"),
    	                    rs.getString("address"),
    	                    rs.getString("phone"),
    	                    rs.getString("instrId") // Assuming you have an instrument name field in your Musician class
    	            ));
    	        }
    	    } catch (SQLException e) {
    	        e.printStackTrace();
    	    }
            // Loop on each musician and set instrument name using setInstrNameUsingInstrId
            for (Musician musician : musicians) {
                musician.setInstrNameUsingInstrId(musician.getIntsrument_id());
            }    	   
    
            return musicians;
    	}


//    public List<Musician> getAllMusicians() {
//        List<Musician> musicians = new ArrayList<>();
//        final String query = "SELECT m.ssn, m.name, l.phone, l.address, p.instrId " +
//                             "FROM notownrecords.musicians m " +
//                             "JOIN lives l ON m.ssn = l.ssn " +
//                             "JOIN plays p ON m.ssn = p.ssn";
//    
//        try (Connection conn = DatabaseConnection.getConnection();
//             PreparedStatement pstmt = conn.prepareStatement(query)) {
//            
//            ResultSet rs = pstmt.executeQuery();
//    
//            while (rs.next()) {
//                String instrumentId = rs.getString("instrId");
//                // Here you can retrieve the instrument name or the entire Instrument object,
//                // depending on how you wish to use it. For simplicity, only the ID is used.
//                musicians.add(new Musician(
//                    rs.getString("ssn"),
//                    rs.getString("name"),
//                    rs.getString("address"),
//                    rs.getString("phone"),
//                    instrumentId
//                ));
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return musicians;
//    }
    
}
