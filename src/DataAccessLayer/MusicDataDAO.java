package DataAccessLayer;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MusicDataDAO {

    public List<Object[]> getAllMusicData() {
        List<Object[]> musicDataList = new ArrayList<>();
        String sql = "SELECT m.name, s.title as \"song_title\", ap.title  as \"album_title\"\r\n"
        		+ "FROM musicians m\r\n"
        		+ "join perform p  on p.ssn  =m.ssn \r\n"
        		+ "JOIN songs s on s.songId =p.songId \r\n"
        		+ "JOIN songs_appears sa ON p.songId  = sa.songId \r\n"
        		+ "JOIN album_producer ap ON ap.albumIdentifier  = sa.albumIdentifier ORDER BY m.name; ";

        try (Connection conn = DatabaseConnection.getConnection();
                     Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                musicDataList.add(new Object[]{rs.getString(1), rs.getString(2), rs.getString(3)});
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return musicDataList;
    }

    public List<Object[]> searchMusicByAny(String searchQuery) {
        List<Object[]> searchResults = new ArrayList<>();
        // Query to search music by any criteria (e.g., musician name, album title, or song name)
        // This is just an example and should be tailored to your specific requirements.
        String sql = "SELECT m.name, s.title as \"song_title\", ap.title  as \"album_title\"\r\n"
        		+ "FROM musicians m\r\n"
        		+ "join perform p  on p.ssn  =m.ssn \r\n"
        		+ "JOIN songs s on s.songId =p.songId \r\n"
        		+ "JOIN songs_appears sa ON p.songId  = sa.songId \r\n"
        		+ "JOIN album_producer ap ON ap.albumIdentifier  = sa.albumIdentifier " +
                     "WHERE m.name LIKE ? OR s.title LIKE ? OR ap.title LIKE ? ORDER BY m.name";

                     try (Connection conn = DatabaseConnection.getConnection();
                     PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, "%" + searchQuery + "%");
            pstmt.setString(2, "%" + searchQuery + "%");
            pstmt.setString(3, "%" + searchQuery + "%");
            
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    searchResults.add(new Object[]{rs.getString(1), rs.getString(2), rs.getString(3)});
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return searchResults;
    }
}
