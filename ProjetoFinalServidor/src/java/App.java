import jakarta.ws.rs.core.Application;
import jakarta.ws.rs.ApplicationPath;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Guilherme Rodrigues e Rodrigo Pereira
 */

@ApplicationPath("app")
public class App extends Application {
    
    private Set<Object> singletons = new HashSet<Object>();
    
    private UserManagement userManage = new UserManagement();
    private Calls calls = new Calls(userManage);
    
    public App() {
        singletons.add(new Calls(userManage));
    }    
    
    @Override
    public Set<Object> getSingletons() {
        return singletons;
    }
}