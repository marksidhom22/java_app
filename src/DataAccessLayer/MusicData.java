package DataAccessLayer;

public class MusicData {
    private String musicianName;
    private String albumTitle;
    private String songName;

    // Constructor
    public MusicData(String musicianName, String albumTitle, String songName) {
        this.musicianName = musicianName;
        this.albumTitle = albumTitle;
        this.songName = songName;
    }

    // Getters and Setters
    public String getMusicianName() {
        return musicianName;
    }

    public void setMusicianName(String musicianName) {
        this.musicianName = musicianName;
    }

    public String getAlbumTitle() {
        return albumTitle;
    }

    public void setAlbumTitle(String albumTitle) {
        this.albumTitle = albumTitle;
    }

    public String getSongName() {
        return songName;
    }

    public void setSongName(String songName) {
        this.songName = songName;
    }
    
    // You might want to override the toString() method for easy printing, if necessary
}
