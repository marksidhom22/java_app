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
        		+ "songs.author ,songs.title,Songs_Appears.albumIdentifier"
        		+ "  FROM Songs_Appears join songs "
        		+ "on Songs_Appears.songId =songs.songId ORDER BY songs.title ;\r\n"
        		+ "";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            
            while (rs.next()) {
                songs.add(new Song(
                        rs.getInt("songId"),
                        rs.getString("author"),
                        rs.getString("title"),
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
    	 int affectedRows=0;
        final String query = "INSERT INTO songs (songId, title, author) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            
            pstmt.setInt(1, song.getSongId());
            pstmt.setString(2, song.getTitle());
            pstmt.setString(3, song.getAuthor());

            affectedRows = pstmt.executeUpdate();
           
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        
        final String query2 = "INSERT INTO Songs_Appears (songId, albumIdentifier) VALUES (?, ?)";
        try (Connection conn2 = DatabaseConnection.getConnection();
             PreparedStatement pstmt2 = conn2.prepareStatement(query2)) {
            
            pstmt2.setInt(1, song.getSongId());
            pstmt2.setInt(2, song.getAlbumIdentifier());

            int affectedRows2 = pstmt2.executeUpdate();
            return affectedRows+ affectedRows2> 0;

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
        final String querySongs = "UPDATE songs SET title = ?, author = ? WHERE songId = ?";
        final String querySongsAppears = "UPDATE Songs_Appears SET albumIdentifier = ? WHERE songId = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmtSongs = conn.prepareStatement(querySongs);
             PreparedStatement pstmtSongsAppears = conn.prepareStatement(querySongsAppears)) {

            // Update the 'songs' table
            pstmtSongs.setString(1, song.getTitle());
            pstmtSongs.setString(2, song.getAuthor());
            pstmtSongs.setInt(3, song.getSongId());
            int affectedRowsSongs = pstmtSongs.executeUpdate();

            // Update the 'Songs_Appears' table
            pstmtSongsAppears.setInt(1, song.getAlbumIdentifier());
            pstmtSongsAppears.setInt(2, song.getSongId());
            int affectedRowsSongsAppears = pstmtSongsAppears.executeUpdate();

            return (affectedRowsSongs > 0) || (affectedRowsSongsAppears > 0);
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
