import java.util.List;

/**
 * @author Guilherme Rodrigues e Rodrigo Pereira
 */

public class User {
    private String name;
    private String id;
    private String folder;
    private List<String> files;
    
    public User() {}
    
    public User(String aName, String aId, String aFolder, List<String> aFiles) {       
        name = aName;
        id = aId;
        folder = aFolder;
        files = aFiles;
    }
    

    //getters e setters
    
    public String getName() {
        return name;
    }
    
    public String getId() {
        return id;
    }
    
    public String getFolder() {
        return folder;
    }
    
    public List getFiles() {
        return files;
    }
    
     public void setName(String aName) {
        name = aName;
    }
    
    public void setId(String aId) {
        id = aId;
    }
    
    public void setFolder(String aFolder) {
        folder = aFolder;
    }
    
    public void setFiles(List<String> aFiles) {
        files = aFiles;
    }
}
