package clases;

/**
 *
 * @author Majey
 */
import java.io.File;
import javax.swing.filechooser.FileFilter;

/**
 *
 * @author Majey
 */
public class FilterType extends FileFilter{
    
    private final String extension;
    private final String description;
    
    public FilterType(String extension, String description){
        // Set Contructor Values
        this.extension = extension;
        this.description = description;
    }
    
    @Override
    public boolean accept(File file) {
        // If File Is Returning Directory
        if(file.isDirectory()){
            return true;
        }
        // Return File Name with Extension
        return file.getName().endsWith(extension);
    }

    @Override
    public String getDescription() {
        // Return To Display File Type and Description
        return description + String.format(" (*%s)", extension);
    }
    
}
