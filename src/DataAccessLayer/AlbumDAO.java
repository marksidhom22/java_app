package DataAccessLayer;

import DataAccessLayer.Album;
import DataAccessLayer.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * AlbumDAO class to handle database operations for Albums.
 */
public class AlbumDAO {

    /**
     * Retrieves an album by its identifier.
     *
     * @param albumIdentifier The identifier of the album to retrieve.
     * @return The Album object, or null if not found.
     */
    public Album getAlbumById(int albumIdentifier) {
        final String query = "SELECT * FROM Album_Producer WHERE albumIdentifier = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            
            pstmt.setInt(1, albumIdentifier);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return new Album(
                        rs.getInt("albumIdentifier"),
                        rs.getString("ssn") ,
                        rs.getDate("copyrightDate"),
                        rs.getInt("speed"),
                        rs.getString("title")

                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Retrieves all albums from the database.
     *
     * @return A list of all albums.
     */
    public List<Album> getAllAlbums() {
        List<Album> albums = new ArrayList<>();
        final String query = "SELECT * FROM Album_Producer";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            
            while (rs.next()) {
                albums.add(new Album(
                        rs.getInt("albumIdentifier"),
                        rs.getString("ssn") ,
                        rs.getDate("copyrightDate"),
                        rs.getInt("speed"),
                        rs.getString("title")

                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return albums;
    }

    /**
     * Adds a new album to the database.
     *
     * @param album The Album object to add.
     * @return true if the operation was successful.
     */
    public boolean addAlbum(Album album) {
        final String query = "INSERT INTO Album_Producer (albumIdentifier, title, copyrightDate, speed, ssn) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            
            pstmt.setInt(1, album.getAlbumIdentifier());
            pstmt.setString(2, album.getTitle());
            pstmt.setDate(3, new java.sql.Date(album.getCopyrightDate().getTime()));
            pstmt.setInt(4, album.getSpeed());
            pstmt.setString(5, album.getSsn());

            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Updates an existing album's details in the database.
     *
     * @param album The Album object to update.
     * @return true if the operation was successful.
     */
    public boolean updateAlbum(Album album) {
        final String query = "UPDATE Album_Producer SET title = ?, copyrightDate = ?, speed = ?, ssn = ? WHERE albumIdentifier = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            
            pstmt.setString(1, album.getTitle());
            pstmt.setDate(2, new java.sql.Date(album.getCopyrightDate().getTime()));
            pstmt.setInt(3, album.getSpeed());
            pstmt.setString(4, album.getSsn());
            pstmt.setInt(5, album.getAlbumIdentifier());

            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Deletes an album from the database by its identifier.
     *
     * @param albumIdentifier The identifier of the album to delete.
     * @return true if the operation was successful.
     */
    public boolean deleteAlbum(int albumIdentifier) {
        final String query = "DELETE FROM Album_Producer WHERE albumIdentifier = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            
            pstmt.setInt(1, albumIdentifier);
            
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
