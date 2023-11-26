package DataAccessLayer;

import DataAccessLayer.Producer;
import DataAccessLayer.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * ProducerDAO class to handle database operations for Producers.
 */
public class ProducerDAO {

    /**
     * Retrieves a producer by their SSN.
     *
     * @param ssn The SSN of the producer to retrieve.
     * @return The Producer object, or null if not found.
     */
    public Producer getProducerBySSN(String ssn) {
        final String query = "SELECT * FROM musicians WHERE ssn = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            
            pstmt.setString(1, ssn);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return new Producer(
                        rs.getString("ssn"),
                        rs.getString("name")

                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Retrieves all producers from the database.
     *
     * @return A list of all producers.
     */
    public List<Producer> getAllProducers() {
        List<Producer> producers = new ArrayList<>();
        final String query = "SELECT * FROM musicians ORDER BY musicians.name";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            
            while (rs.next()) {
                producers.add(new Producer(
                        rs.getString("ssn"),
                        rs.getString("name")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return producers;
    }

    /**
     * Adds a new producer to the database.
     *
     * @param producer The Producer object to add.
     * @return true if the operation was successful.
     */
    public boolean addProducer(Producer producer) {
        final String query = "INSERT INTO Producers (ssn, name, phone, address) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            
            pstmt.setString(1, producer.getSsn());
            pstmt.setString(2, producer.getName());
            pstmt.setString(3, producer.getPhoneNumber());
            pstmt.setString(4, producer.getAddress());

            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Updates an existing producer's details in the database.
     *
     * @param producer The Producer object to update.
     * @return true if the operation was successful.
     */
    public boolean updateProducer(Producer producer) {
        final String query = "UPDATE Producers SET name = ?, phone = ?, address = ? WHERE ssn = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            
            pstmt.setString(1, producer.getName());
            pstmt.setString(2, producer.getPhoneNumber());
            pstmt.setString(3, producer.getAddress());
            pstmt.setString(4, producer.getSsn());

            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Deletes a producer from the database by their SSN.
     *
     * @param ssn The SSN of the producer to delete.
     * @return true if the operation was successful.
     */
    public boolean deleteProducer(String ssn) {
        final String query = "DELETE FROM musicians WHERE ssn = ?";
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
