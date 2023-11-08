package DataAccessLayer;

import DataAccessLayer.Musician;
import DataAccessLayer.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * MusicianDAO class to handle database operations for Musicians.
 */
public class MusicianDAO {

    /**
     * Retrieves a musician by SSN.
     *
     * @param ssn The SSN of the musician to retrieve.
     * @return The Musician object, or null if not found.
     */
    public Musician getMusicianBySSN(String ssn) {
        final String query = "SELECT * FROM Musicians WHERE ssn = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            
            pstmt.setString(1, ssn);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return new Musician(
                        rs.getString("ssn"),
                        rs.getString("name"),
                        rs.getString("address"),
                        rs.getString("phone")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Retrieves all musicians.
     *
     * @return A list of all Musicians.
     */
    public List<Musician> getAllMusicians() {
        List<Musician> musicians = new ArrayList<>();
        final String query = "SELECT * FROM Musicians";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            
            while (rs.next()) {
                musicians.add(new Musician(
                        rs.getString("ssn"),
                        rs.getString("name"),
                        rs.getString("address"),
                        rs.getString("phone")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return musicians;
    }

    /**
     * Adds a new musician to the database.
     *
     * @param musician The Musician object to add.
     * @return true if the operation was successful.
     */
    public boolean addMusician(Musician musician) {
        final String query = "INSERT INTO Musicians (ssn, name, address, phone) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            
            pstmt.setString(1, musician.getSsn());
            pstmt.setString(2, musician.getName());
            pstmt.setString(3, musician.getAddress());
            pstmt.setString(4, musician.getPhoneNumber());

            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Updates an existing musician's details in the database.
     *
     * @param musician The Musician object to update.
     * @return true if the operation was successful.
     */
    public boolean updateMusician(Musician musician) {
        final String query = "UPDATE Musicians SET name = ?, address = ?, phone = ? WHERE ssn = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            
            pstmt.setString(1, musician.getName());
            pstmt.setString(2, musician.getAddress());
            pstmt.setString(3, musician.getPhoneNumber());
            pstmt.setString(4, musician.getSsn());

            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Deletes a musician from the database by SSN.
     *
     * @param ssn The SSN of the musician to delete.
     * @return true if the operation was successful.
     */
    public boolean deleteMusician(String ssn) {
        final String query = "DELETE FROM Musicians WHERE ssn = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            
            pstmt.setString(1, ssn);
            
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
