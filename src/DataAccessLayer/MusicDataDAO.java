package DataAccessLayer;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MusicDataDAO {

    public List<Object[]> getAllMusicData() {
        List<Object[]> musicDataList = new ArrayList<>();
        String sql = "SELECT m.name,  ap.title as \"song_title\", sa.title  as \"album_title\"\r\n"
        		+ "FROM musicians m\r\n"
        		+ "join perform p  on p.ssn  =m.ssn \r\n"
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

    public List<Object[]> searchMusicByAny(String searchQuery, boolean searchArtist, boolean searchAlbum, boolean searchSong) {
        List<Object[]> searchResults = new ArrayList<>();
        StringBuilder sql = new StringBuilder("SELECT m.name, ap.title as \"song_title\",  sa.title  as \"album_title\"\r\n" +
            "FROM musicians m\r\n" +
            "join perform p  on p.ssn  =m.ssn \r\n" +
            "JOIN songs_appears sa ON p.songId  = sa.songId \r\n" +
            "JOIN album_producer ap ON ap.albumIdentifier  = sa.albumIdentifier ");
    
        // Append WHERE clause based on checkbox states
        List<String> conditions = new ArrayList<>();
        if (searchArtist) 
        conditions.add("m.name LIKE ?");
        if (searchSong) 
        conditions.add("sa.title LIKE ?");
        if (searchAlbum) 
        conditions.add("ap.title LIKE ?");
    
        if (!conditions.isEmpty()) {
            sql.append(" WHERE ").append(String.join(" OR ", conditions));
        }
    
        sql.append(" ORDER BY m.name");
    
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql.toString())) {
    
            int index = 1;
            if (searchArtist) pstmt.setString(index++, "%" + searchQuery + "%");
            if (searchSong) pstmt.setString(index++, "%" + searchQuery + "%");
            if (searchAlbum) pstmt.setString(index, "%" + searchQuery + "%");
    
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
