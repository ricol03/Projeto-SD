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
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Guilherme Rodrigues e Rodrigo Pereira
 */

@Path("api")
public class Calls {
    
    
    private UserManagement userManage;
    // lista de pedidos
    private List<FileRequest> pendingRequests = new ArrayList<>();
    File file = null;
    //private List<File> fileList = new ArrayList<>();

    
    // o JAX-RS requer um construtor vazio, caso contrário não funciona
    public Calls() {}
    
    
    public Calls(UserManagement aUserManagement) {
        userManage = aUserManagement;
    }
    
    public void deleteCompletedRequests() {
        for (int i = 0; i < pendingRequests.size(); i++)
            if (pendingRequests.get(i).getStatus().equals("download complete")) 
                pendingRequests.remove(i);
    }
    
    
    public boolean notifyClientToUpload(String aName, String aFileName) {
        FileRequest request = new FileRequest(aName, aFileName);

        synchronized (pendingRequests) {
            
            deleteCompletedRequests();

            // verifica se existe algum pedido do mesmo ficheiro e nome do cliente
            boolean exists = pendingRequests.stream()
                .anyMatch(r -> r.getName().equals(aName) && r.getFile().equals(aFileName));
            
            if (!exists) {
                pendingRequests.add(request);
                System.out.println("Added request: " + aName + " -> " + aFileName);
                adicionarLog("Ficheiro: " + aFileName + " a transferir para: " + aName);
                return true;
            } else {
                System.out.println("Request already exists.");
                return false;
            }
        }
    }
    
    // LOGS
    private static final List<Answer> logs = new ArrayList<>();

    private void adicionarLog(String mensagem) {
        Answer log = new Answer(LocalDateTime.now(), mensagem);
        logs.add(log);
        System.out.println("[" + log.getDatetime() + "] " + mensagem);
    }

    // endpoint dos logs
    @GET
    @Path("ads/logs")
    @Produces("application/json")
    public Response getLogs() {
        return Response.ok(logs).build();
    }
    
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
                adicionarLog("Login: " + aUser.getName());
                return Response.status(Response.Status.CREATED)
                        .entity(new Answer(LocalDateTime.now(), (Object) "Login efetuado com sucesso!")).build();
        }        
    }
    
    
    @DELETE
    @Path("ads/{Name}")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.TEXT_PLAIN)
    public Response logout(@PathParam("Name") String Name) {

        if (userManage.removeUser(Name)) {
            adicionarLog("Logout: " + Name);
            return Response.status(Response.Status.NO_CONTENT)
                    .entity("Logout efetuado com sucesso!").build();
        } else {
            adicionarLog("Tentativa de logout falhada - utilizador não existe - Nome: " + Name);
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("O utilizador não existe!").build();
        }
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
        for (User user : userManage.getUserList())
            if (user.getId().equals(aId))
                return Response.status(Response.Status.OK)
                        .entity(user.getFiles())
                        .build();
               
        return Response.status(Response.Status.OK)
                .entity("Utilizador sem Ficheiros")
                .build();    
    }
    
    
    // endpoint que verifica por pedidos
    @GET
    @Path("ads/checkrequests")
    @Produces("application/json")
    public Response checkRequests(@QueryParam("name") String aName) {
        
        deleteCompletedRequests();
        
        List<FileRequest> result = new ArrayList<>();

        for (FileRequest request : pendingRequests)
            if (request.getName().equals(aName)) 
                result.add(request);
            
        if (!result.isEmpty())
            return Response.status(Response.Status.OK)
                    .entity(result)
                    .build();
        else
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Sem uploads")
                    .build();
        
    }  
    
    
    // pedido para transferir o ficheiro do cliente pretendido
    @POST
    @Path("ads/requestfile")
    @Produces("application/json")
    public Response requestFile(FileRequest aFileRequest) {
        boolean success = notifyClientToUpload(aFileRequest.getName(), aFileRequest.getFile());
        
        file = null;
        
        if (success) {
            aFileRequest.setStatus("uploading");
            return Response.status(Response.Status.OK).entity("Waiting for upload...").build();
        }
            
        else
            return Response.status(Response.Status.BAD_REQUEST).entity("User not online or failed to notify").build();
    }
    
    // pedido para verificar o progresso do download    
    @POST
    @Path("ads/checkdownload")
    @Consumes("application/json")
    @Produces("application/json")
    public Response checkDownload(FileRequest aFileRequest) {
        for (FileRequest request : pendingRequests)
            if (request.getName().equals(aFileRequest.getName()) && request.getFile().equals(aFileRequest.getFile())) {
                System.out.println("Request status: " + request.getStatus());

                if (request.getStatus().equals("uploading"))
                    return Response.status(Response.Status.PARTIAL_CONTENT).build();
                else if (request.getStatus().equals("available for download")) 
                    return Response.status(Response.Status.CREATED).build();
                else 
                    return Response.status(Response.Status.BAD_REQUEST).build();
            }
        return Response.status(Response.Status.BAD_REQUEST).build();
    }

    
    // guarda o ficheiro temporariamente no servidor
    @POST
    @Path("ads/upload")
    @Consumes(MediaType.APPLICATION_OCTET_STREAM)
    public Response uploadFile(@QueryParam("filename") String aFileName,
                               @QueryParam("name") String aName,
                               InputStream inputStream) {
        
        
        for (FileRequest request : pendingRequests)
            if (request.getName().equals(aName) && request.getFile().equals(aFileName)) 
                request.setStatus("available for download");
        
        try {
            file = new File(aFileName);
            //fileList.add(file);
            
            /*int i = 0;
            
            for (i = 0; i < fileList.size(); i++)
                if (fileList.get(i).getName().equals(aFileName)) {
                    System.out.println("chegou pá");
                    break;
                }
            */
            Files.copy(inputStream, file.toPath(), StandardCopyOption.REPLACE_EXISTING);
            
            System.out.println("(upload) File size: " + file.length() + " bytes");
            
            return Response.status(Response.Status.OK).build();
        } catch (IOException e) {
            e.printStackTrace();
            return Response.status(Response.Status.FORBIDDEN).build();
        }
        
        
    }
    

    // busca o ficheiro guardado e devolve-o ao cliente que pediu
    @GET
    @Path("ads/download")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public Response downloadFile(@QueryParam("filename") String aFileName,
                                 @QueryParam("name") String aName) {
        
        /*File file = null;
        
        for (int i = 0; i < fileList.size(); i++)
            if (fileList.get(i).getName().equals(aFileName)) {
                System.out.println("chegou pá 2");
                file = fileList.get(i);
                fileList.remove(i);
                break;
            }    
        
        if (file == null || !file.exists()) {
            return Response.status(Response.Status.NOT_FOUND)
                           .entity("Ficheiro não foi encontrado: " + aName)
                           .build();
        }*/
        
        System.out.println("Tamanho do ficheiro: " + file.length() + " bytes");
        System.out.println(aName);
        System.out.println(aFileName);

        for (FileRequest request : pendingRequests) {
            if (request.getName().equals(aName) && request.getFile().equals(aFileName)) {
                request.setStatus("download complete");
            }
        }

        adicionarLog("Download Concluido");

        return Response.ok(file, MediaType.APPLICATION_OCTET_STREAM)
                .header("Content-Disposition", "attachment; filename=\"" + file.getName() + "\"")
                .build();           

        
    }
    
}
