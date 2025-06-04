package projetofinal;

/**
 * @author Guilherme Rodrigues e Rodrigo Pereira
 */

public class Ligacao {
    private String ip;
    private String port;
    private int id;
    
    public Ligacao() {}
    
    public Ligacao(String aIp, String aPort, int aId) {       
        ip = aIp;
        port = aPort;
        id = aId;
    }
    

    //getters e setters
    
    public String getIp() {
        return ip;
    }
    
    public String getPort() {
        return port;
    }
    
    public int getId() {
        return id;
    }
}
