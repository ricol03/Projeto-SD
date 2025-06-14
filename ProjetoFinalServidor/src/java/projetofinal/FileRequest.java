package projetofinal;

/**
 * @author ricol03
 */

public class FileRequest {
    private String name;
    private String file;
    private String status;
    
    public FileRequest() {}
    
    public FileRequest(String aName, String aFile) {
        name = aName;
        file = aFile;
        status = "requested";
    }
    
    public String getName() {
        return name;
    }
    
    public String getFile() {
        return file;
    }
    
    public String getStatus() {
        return status;
    }
    
    public void setName(String aName) {
        name = aName;
    }
    
    public void setFile(String aFile) {
        file = aFile;
    }
    
    public void setStatus(String aStatus) {
        status = aStatus;
    }
    
}
