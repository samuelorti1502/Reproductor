package clases;

import com.mpatric.mp3agic.*;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;

/**
 *
 * @author Samuel David Ortiz
 */
public class MusicTags {
    Mp3File mp3File;
    private String Track, Artist, Title, Album, Year, Genre;
    ID3v1 id3v1Tag; 
    
    public MusicTags(String file) throws IOException, UnsupportedTagException, InvalidDataException{
        mp3File = new Mp3File(new File(file));
        id3v1Tag = mp3File.getId3v1Tag();
        
        System.out.println("Length of this mp3 is: " + mp3File.getLengthInSeconds() + " seconds");
        
        
    }

    public String getTrack() {
        return id3v1Tag.getTrack();
    }

    public String getArtist() {
        return id3v1Tag.getArtist();
    }

    public String getTitle() {
        return id3v1Tag.getTitle();
    }

    public String getAlbum() {
        return id3v1Tag.getAlbum();
    }

    public String getYear() {
        return id3v1Tag.getYear();
    }
    
}
