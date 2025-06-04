import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.FormParam;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.GenericType;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.Map;

/**
 * @author Guilherme Rodrigues e Rodrigo Pereira
 */

@Path("api")
public class Calls {
    
    
    private UserManagement userManage;
    
    
    // o JAX-RS requer um construtor vazio, caso contrário não funciona
    public Calls() {}
    
    
    public Calls(UserManagement aUserManagement) {
        userManage = aUserManagement;
    }
    
    
    @POST
    @Path("ads")
    @Consumes("application/json")
    @Produces("application/json")
    public Response login(User aUser) {
        
        System.out.println(aUser.getName());
        
        
        switch(userManage.checkVariables(aUser.getName(), aUser.getId())) {
           
            // para cada caso devolvido é mostrada a mensagem correspondente
            case "no_values":
                return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Por favor introduza valores e tente novamente.").build();
                
            case "no_name":
                return Response.status(Response.Status.BAD_REQUEST)
                    .entity("O nome é nulo, tente novamente.").build();
                
            case "no_id":
                return Response.status(Response.Status.BAD_REQUEST)
                    .entity("O id é nulo, tente novamente.").build();
                
            case "both":
                return Response.status(Response.Status.BAD_REQUEST)
                    .entity("O utilizador já existe na lista, tente novamente.").build();
                
            case "id":
                return Response.status(Response.Status.BAD_REQUEST)
                    .entity("O id pertence a outro utilizador, tente novamente.").build();
                
            case "name":
                return Response.status(Response.Status.BAD_REQUEST)
                    .entity("O nome pertence a outro utilizador, tente novamente.").build();
                 
            default:
                userManage.addUser(aUser);

                return Response.status(Response.Status.CREATED)
                    .entity("Login efetuado com sucesso!").build();
        }        
    }
    
    
    @DELETE
    @Path("ads/{id}")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.TEXT_PLAIN)
    public Response logout(@PathParam("id") String aId) {
        
        if (userManage.removeUser(aId))
            return Response.status(Response.Status.NO_CONTENT)
                .entity("Logout efetuado com sucesso!").build();
        else
            return Response.status(Response.Status.NOT_FOUND)
                .entity("O utilizador não existe!").build();
    }
    
    
    @GET
    @Path("ads")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces("application/json")
    public Response listUsers() {   
        return Response.status(Response.Status.OK)
                .entity(userManage.getUserList())
                .build();
    }
    
    
    @GET
    @Path("ads/{id}")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces("application/json")
    public Response listFiles(@PathParam("id") String aId) {   
        for (User user : userManage.getUserList()) {
            if (user.getId().equals(aId)) {
                return Response.status(Response.Status.OK)
                        .entity(user.getFiles())
                        .build();
            }
        }
        return null;
    }
    
    
    @PUT
    @Path("ads")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces("application/json")
    public Response download(@FormParam("aName") String aName,
                             @FormParam("aFolder") String aFolder,
                             @FormParam("aFile") String aFile) {
        return Response.status(Response.Status.OK)
                 .entity("O ficheiro foi transferido com sucesso!")
                 .build();
    }
    
    //TODO: Adicionar os outros endpoints
    
    /*@POST
    @Path("stocks")
    @Consumes({"application/xml", "application/json"})
    @Produces("application/json")
    public Response addStock(Stock aStock) {

        switch(stockManage.checkVariables(aStock.getName(), aStock.getAcronym(), aStock.getValue())) {
            
            // para cada caso devolvido é mostrada a mensagem correspondente
            case "no_values":
                return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Por favor introduza valores e tente novamente.").build();
                
            case "no_name":
                return Response.status(Response.Status.BAD_REQUEST)
                    .entity("O nome é nulo, tente novamente.").build();
                
            case "no_acronym":
                return Response.status(Response.Status.BAD_REQUEST)
                    .entity("A sigla é nula, tente novamente.").build();
                
            case "both":
                return Response.status(Response.Status.BAD_REQUEST)
                    .entity("A ação já existe na lista, tente novamente.").build();
            
            case "name":
                return Response.status(Response.Status.BAD_REQUEST)
                    .entity("O nome pertence a outra ação, tente novamente.").build();
                
            case "acronym":
                return Response.status(Response.Status.BAD_REQUEST)
                    .entity("A sigla pertence a outra ação, tente novamente.").build();
                
            case "value":
                return Response.status(Response.Status.BAD_REQUEST)
                    .entity("O valor é zero, negativo ou tem menos/mais de duas casas decimais, tente novamente.").build();
                
            default:
                stockManage.addStock(aStock);
        
                return Response.status(Response.Status.OK)
                    .entity("A ação foi adicionada com sucesso!").build();
        }
    }
    
    
    @GET
    @Path("stocks")
    @Produces("application/json")
    public ArrayList<Stock> checkStocks() {
        return stockManage.checkStocks();
    }

    
    @GET
    @Path("stocks/{sigla}")
    @Produces("application/json")
    public Response getStock(@PathParam("sigla") String aAcronym) {
        
        Stock value = stockManage.getStock(aAcronym);
        
        if (value == null)
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("A ação selecionada não existe!").build();
        else
            return Response.status(Response.Status.OK)
                    .entity(value).build();
    }
    
    
    @PUT
    @Path("stocks")
    @Consumes({"application/xml", "application/json"})
    @Produces("application/json")
    public Response alterValue(Map<String, Object> payload) {
        
        String acronym = (String) payload.get("acronym");
        float value = ((Number) payload.get("value")).floatValue();
        
        if (stockManage.alterValue(acronym, value))
            return Response.status(Response.Status.OK)
                    .entity("O valor foi alterado com sucesso!").build();
        else
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Ocorreu um erro ao alterar o valor!\n"
                            + "Verifique os valores que introduziu e tente novamente.").build();
    }
    
    
    @DELETE
    @Path("stocks/{sigla}")
    @Produces("application/json")
    public Response removeStock(@PathParam("sigla") String aAcronym) {
        
        if (stockManage.removeStock(aAcronym))
            return Response.status(Response.Status.OK)
                    .entity("A ação foi eliminada com sucesso!").build();
        else
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Ocorreu um erro ao eliminar a ação!\n"
                            + "A ação não existe ou já foi eliminada. Verifique os valores que introduziu e tente novamente.").build();
    }*/
    
    
    
    
}
