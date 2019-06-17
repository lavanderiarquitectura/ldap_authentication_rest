package sa.student.resource;

import com.google.gson.Gson;
import sa.student.model.User;
import sa.student.service.AuthService;

import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.UriInfo;


@Path("/auth")
public class AuthResource {

    ResponseBuilder response;

    @Context
    UriInfo uriInfo;

    @EJB
    AuthService authService = new AuthService();

    @GET
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{usr}/{psw}")

    public Response login(@PathParam("usr") String n1, @PathParam("psw") String n2){
        User student = new User();

        student.setUsername(n1);
        student.setPassword(n2);

        String reponse = authService.login(student);

        System.out.println(response);

        String[] userArray = reponse.split(";");

        if(userArray.length > 1){
            student.setName(userArray[1]);
            student.setLast_name(userArray[2]);
            student.setPersonal_id(Integer.parseInt(userArray[3]));
            student.setRoom_id(Integer.parseInt(userArray[4]));
        } else{
            student.setPersonal_id(-1);
        }

        Gson gson = new Gson();
        return Response.ok(gson.toJson(student))
                .status(200)
                .header("Access-Control-Allow-Origin", "*")
                .header("Access-Control-Allow-Headers", "origin, content-type, accept, authorization")
                .header("Access-Control-Allow-Credentials", "true")
                .header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD")
                .header("Access-Control-Max-Age", "1209600")
                .build();

//        response = Response.status(Response.Status.OK);
//        response.entity(reponse);
//        return student;
    }

}