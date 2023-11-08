package DataAccessLayer;

import DataAccessLayer.Song;
import DataAccessLayer.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * SongDAO class to handle database operations for Songs.
 */
public class SongDAO {

    /**
     * Retrieves a song by its identifier.
     *
     * @param songId The identifier of the song to retrieve.
     * @return The Song object, or null if not found.
     */
    public Song getSongById(int songId) {
        final String query = "SELECT * FROM Songs_Appears WHERE songId = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            
            pstmt.setInt(1, songId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return new Song(
                        rs.getInt("songId"),
                        rs.getString("title"),
                        rs.getString("author"),
                        rs.getInt("albumIdentifier")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Retrieves all songs from the database.
     *
     * @return A list of all songs.
     */
    public List<Song> getAllSongs() {
        List<Song> songs = new ArrayList<>();
        final String query = "SELECT Songs_Appears.songId ,"
        		+ "Songs_Appears.albumIdentifier ,songs.author ,"
        		+ "songs.title  FROM Songs_Appears join songs "
        		+ "on Songs_Appears.songId =songs.songId ;\r\n"
        		+ "";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            
            while (rs.next()) {
                songs.add(new Song(
                        rs.getInt("songId"),
                        rs.getString("title"),
                        rs.getString("author"),
                        rs.getInt("albumIdentifier")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return songs;
    }

    /**
     * Adds a new song to the database.
     *
     * @param song The Song object to add.
     * @return true if the operation was successful.
     */
    public boolean addSong(Song song) {
        final String query = "INSERT INTO Songs_Appears (songId, title, author, albumIdentifier) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            
            pstmt.setInt(1, song.getSongId());
            pstmt.setString(2, song.getTitle());
            pstmt.setString(3, song.getAuthor());
            pstmt.setInt(4, song.getAlbumIdentifier());

            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Updates an existing song's details in the database.
     *
     * @param song The Song object to update.
     * @return true if the operation was successful.
     */
    public boolean updateSong(Song song) {
        final String query = "UPDATE Songs_Appears SET title = ?, author = ?, albumIdentifier = ? WHERE songId = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            
            pstmt.setString(1, song.getTitle());
            pstmt.setString(2, song.getAuthor());
            pstmt.setInt(3, song.getAlbumIdentifier());
            pstmt.setInt(4, song.getSongId());

            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Deletes a song from the database by its identifier.
     *
     * @param songId The identifier of the song to delete.
     * @return true if the operation was successful.
     */
    public boolean deleteSong(int songId) {
        final String query = "DELETE FROM Songs_Appears WHERE songId = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            
            pstmt.setInt(1, songId);
            
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
