package projetofinal;

/**
 * @author ricol03
 */

public class FileRequest {
    private String name;
    private String file;
    
    public FileRequest() {}
    
    public FileRequest(String aName, String aFile) {
        name = aName;
        file = aFile;
    }
    
    public String getName() {
        return name;
    }
    
    public String getFile() {
        return file;
    }
    
    public void setName(String aName) {
        name = aName;
    }
    
    public void setFile(String aFile) {
        file = aFile;
    }
    
}
