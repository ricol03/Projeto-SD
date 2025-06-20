package projetofinal;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author Guilherme Rodrigues e Rodrigo Pereira
 */

public class UserManagement {
    private final int NUM_USERS = 100;
    private List<User> userArray = new ArrayList<User>(NUM_USERS);
    
    public String checkVariables(String aName, String aId) {
        
        System.out.println(aName + " " + aId);
        
        if (aName == null && aId == null)
            return "no_values";
        else if (aName == null)
            return "no_name";
        else if (aId == null)
            return "no_id";
        else
            // para cada elemento do arraylist, verifica 
            // se a sigla ou o nome já existem
            for (User user : userArray) {
                if (user.getName().equalsIgnoreCase(aName) && user.getId().equalsIgnoreCase(aId))
                    return "both";
                else if (user.getId().equalsIgnoreCase(aId))
                    return "id";
                else if (user.getName().equalsIgnoreCase(aName))
                    return "name";
            }
        
        // devolve uma string vazia se as variáveis forem válidas
        // (tentei devolver nulo, mas no switch 
        // do método addStock da classe Calls dá um erro)
        return "";
    }
    
    public void addUser(User aUser) {
        userArray.add(aUser);
        
        for (User user : userArray) {
            System.out.println(user.getName() + " " + user.getId());
        }
    }
    
    public List<User> getUserList() {
        return userArray;
    }
    
    public List<String> getUserFiles(String aId) {
        for (User user : userArray) {
            if (user.getId().equals(aId)) {
                return user.getFiles();
            }
        }
        return null;
    }
    
    public boolean removeUser(String Name) {
        for (User user : userArray) {
            if (user.getName().equals(Name)) {
                userArray.remove(user);
                return true;
            }
        }
        return false;
    }
}
