package projetofinal;

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

import java.io.File;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
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
    
    /*@GET
    @Path("ads")
    @Produces("text/plain")
    public String test() {
        return "ads endpoint is alive";
    }*/
    
    @POST
    @Path("ads")
    @Consumes("application/json")
    @Produces("application/json")
    public Response login(User aUser) {
        
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
                    .entity(new Answer(LocalDateTime.now(), (Object)"Login efetuado com sucesso!")).build();
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
        return Response.status(Response.Status.OK)
                .entity("Utilizador sem Ficheiros")
                .build();    
    }
    
    
    // pedido para transferir o ficheiro do cliente pretendido
    @GET
    @Path("ads/requestfile")
    @Produces("application/json")
    public Response requestFile(@QueryParam("owner") String owner, @QueryParam("filename") String filename) {
        // Tell Client B (the owner) to upload the file | falta perceber como fazer isso ^^'
        boolean success = notifyClientToUpload(owner, filename);

        if (success) {
            return Response.ok("{\"message\":\"Waiting for upload...\"}").build();
        } else {
            return Response.status(Response.Status.BAD_REQUEST).entity("User not online or failed to notify").build();
        }
    }
    
    
    // guarda o ficheiro temporariamente no servidor
    @POST
    @Path("/upload")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response uploadFile(@FormDataParam("file") InputStream fileInputStream,
                               @FormDataParam("filename") String filename,
                               @FormDataParam("owner") String owner) {
        // Save file to server
        File file = new File("server_storage/" + filename);
        Files.copy(fileInputStream, file.toPath(), StandardCopyOption.REPLACE_EXISTING);
        return Response.ok("File received").build();
    }
    

    // busca o ficheiro guardado e devolve-o ao cliente que pediu
    @GET
    @Path("/download")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public Response downloadFile(@QueryParam("filename") String filename) {
        File file = new File("server_storage/" + filename);

        if (!file.exists()) {
            return Response.status(Response.Status.NOT_FOUND).entity("File not found").build();
        }

        return Response.ok(file, MediaType.APPLICATION_OCTET_STREAM)
            .header("Content-Disposition", "attachment; filename=\"" + file.getName() + "\"")
            .build();
    }
    
    /*@POST
    @Path("ads/download")
    @Consumes("application/json")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public Response download(FileRequest aFileRequest) {

        String folder = null;
        
        for (User user : userManage.getUserList()) {
            if (user.getName().equals(aFileRequest.getName())) {
                folder = user.getFolder();
                break;
            }
        
        //File file = new File(folder + "/" + aFileRequest.getFile());
        
        if (file.exists()) {
            return Response.status(Response.Status.OK)
                 .entity("O ficheiro foi transferido com sucesso!")
                 .build();
        } else {
            return Response.status(Response.Status.NOT_FOUND)
                 .entity("Ocorreu um erro!")
                 .build();
        }
    }*/
}
