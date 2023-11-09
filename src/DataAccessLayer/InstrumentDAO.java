package DataAccessLayer;

import DataAccessLayer.Instrument;
import DataAccessLayer.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * InstrumentDAO class to handle database operations for Instruments.
 */
public class InstrumentDAO {

    /**
     * Retrieves an instrument by its ID.
     *
     * @param instrId The ID of the instrument to retrieve.
     * @return The Instrument object, or null if not found.
     */
    public Instrument getInstrumentById(String instrId) {
        final String query = "SELECT * FROM Instruments WHERE instrId = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            
            pstmt.setString(1, instrId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return new Instrument(
                        rs.getString("instrId"),
                        rs.getString("dname"),
                        rs.getString("instrument_key")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Instrument getInstrumentByName(String instrName) {
        final String query = "SELECT * FROM Instruments WHERE dname = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            
            pstmt.setString(1, instrName);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return new Instrument(
                        rs.getString("instrId"),
                        rs.getString("dname"),
                        rs.getString("instrument_key")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }



    /**
     * Retrieves all instruments from the database.
     *
     * @return A list of all instruments.
     */
    public List<Instrument> getAllInstruments() {
        List<Instrument> instruments = new ArrayList<>();
        final String query = "SELECT * FROM Instruments";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            
            while (rs.next()) {
                instruments.add(new Instrument(
                        rs.getString("instrId"),
                        rs.getString("dname"),
                        rs.getString("instrument_key")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return instruments;
    }

    /**
     * Adds a new instrument to the database.
     *
     * @param instrument The Instrument object to add.
     * @return true if the operation was successful.
     */
    public boolean addInstrument(Instrument instrument) {
        final String query = "INSERT INTO Instruments (instrId, dname, instrument_key) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            
            pstmt.setString(1, instrument.getInstrId());
            pstmt.setString(2, instrument.getName());
            pstmt.setString(3, instrument.getKey());

            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Updates an existing instrument's details in the database.
     *
     * @param instrument The Instrument object to update.
     * @return true if the operation was successful.
     */
    public boolean updateInstrument(Instrument instrument) {
        final String query = "UPDATE Instruments SET dname = ?, instrument_key = ? WHERE instrId = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            
            pstmt.setString(1, instrument.getName());
            pstmt.setString(2, instrument.getKey());
            pstmt.setString(3, instrument.getInstrId());

            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Deletes an instrument from the database by its ID.
     *
     * @param instrId The ID of the instrument to delete.
     * @return true if the operation was successful.
     */
    public boolean deleteInstrument(String instrId) {
        final String query = "DELETE FROM Instruments WHERE instrId = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            
            pstmt.setString(1, instrId);
            
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
