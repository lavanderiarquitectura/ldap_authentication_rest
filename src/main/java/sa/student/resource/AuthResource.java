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
import java.util.ArrayList;


@Path("/auth")
public class AuthResource {

    ResponseBuilder response;

    @Context
    UriInfo uriInfo;

    @EJB
    AuthService authService = new AuthService();

     //private ArrayList<Token> tokens = new ArrayList<>();

//    private String generateTokenString() {
//		String ALPHA_NUMERIC_STRING = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
//
//		StringBuilder builder = new StringBuilder();
//		for (int i = 0; i <20; i++) {
//			int character = (int)(Math.random()*ALPHA_NUMERIC_STRING.length());
//			builder.append(ALPHA_NUMERIC_STRING.charAt(character));
//		}
//		return builder.toString();
//	}
	

    @GET
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/user/{usr}/{psw}")

    public Response login(@PathParam("usr") String n1, @PathParam("psw") String n2){
        User student = new User();

        student.setPersonal_id(Long.parseLong(n1));
        student.setPassword(n2);

	    //JSONObject response = new JSONObject();
        String response = "";

        Gson gson = new Gson();

        int responseStatus;
        String[] userArray = authService.login(student).split(";");

        if(userArray.length > 1){
//        	Integer id = Integer.parseInt(userArray[3]);
//	    	long dayInMillis = 1000 * 60 * 60 * 24;
//        	Token token = new Token(id, generateTokenString(),  System.currentTimeMillis() + dayInMillis);
//	    	tokens.add(token);

	    	response = "{\"login\" : \"success\"}";

	    	//response.put("login", token.getToken());
            responseStatus = 200;
        } else{
        	//response.put("login", "failure");
            response = "{\"login\" : \"failure\"}";
            responseStatus = 403;
        }

        return Response.ok(response)
                .status(responseStatus)
                .header("Access-Control-Allow-Origin", "*")
                .header("Access-Control-Allow-Headers", "origin, content-type, accept, authorization")
                .header("Access-Control-Allow-Credentials", "true")
                .header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD")
                .header("Access-Control-Max-Age", "1209600")
                .build();
    }

    @GET
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/operator/{usr}/{psw}")

    public Response loginOperator(@PathParam("usr") String username, @PathParam("psw") String password){

        User operator = new User();

        operator.setUsername(username);
        operator.setPassword(password);

        String response = "";

        int responseStatus;
        String[] userArray = authService.loginOperator(operator).split(";");
 
	    if(userArray.length > 1) {
//	    	long dayInMillis = 1000 * 60 * 60 * 24;
//	    	Token token = new Token(0, generateTokenString(), System.currentTimeMillis() + dayInMillis);
//	    	tokens.add(token);
	    	//response.put("login", token.getToken());
            response = "{\"login\" : \"success\"}";
	    	responseStatus = 200;
	    }
	    else {
            // response.put("login", "failure");
            response = "{\"login\" : \"failure\"}";
	    	responseStatus = 403;
	    }
        //Gson gson = new Gson();
        return Response.ok(response)
                .status(responseStatus)
                .header("Access-Control-Allow-Origin", "*")
                .header("Access-Control-Allow-Headers", "origin, content-type, accept, authorization")
                .header("Access-Control-Allow-Credentials", "true")
                .header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD")
                .header("Access-Control-Max-Age", "1209600")
                .build();
    }

    
//    @GET
//    @Consumes(MediaType.TEXT_PLAIN)
//    @Produces(MediaType.APPLICATION_JSON)
//    @Path("/token/{token}")
//    public Response validateToken(@PathParam("token") String token){
//	    //JSONObject response = new JSONObject();
//        String response = "";
//        int responseStatus = 403;
//
//		for(Token t : tokens) {
//			if(t.getToken().equals(token)) {
//				System.out.println("Token found!");
//				if(t.getExpiration() >= System.currentTimeMillis()) {
//					//response.put("user", t.getUserId());
//                    response = "{\"user\" : \""+ t.getUserId() +"\"}";
//					responseStatus = 200;
//				}
//				//Token expired
//				else {
//					tokens.remove(t);
//					//response.put("user", -1);
//                    response = "{\"user\" : -1}";
//					break;
//				}
//			}
//		}
//
//		//Gson gson = new Gson();
//
//        return Response.ok(response)
//                .status(responseStatus)
//                .header("Access-Control-Allow-Origin", "*")
//                .header("Access-Control-Allow-Headers", "origin, content-type, accept, authorization")
//                .header("Access-Control-Allow-Credentials", "true")
//                .header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD")
//                .header("Access-Control-Max-Age", "1209600")
//                .build();
//    }
    
    @GET
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/getuser/{usr}")

    public Response getUser(@PathParam("usr") String username){
        User student = new User();

        student.setUsername(username);

        String reponse = authService.getUser(username);

        System.out.println(response);
        int responseStatus;

        String[] userArray = reponse.split(";");

        if(userArray.length > 1){
            student.setName(userArray[1]);
            student.setLast_name(userArray[2]);
            student.setPersonal_id(Long.parseLong(userArray[3]));
            student.setRoom_id(Integer.parseInt(userArray[4]));
            responseStatus = 200;
        } else{
            student.setPersonal_id((long) -1);
            responseStatus = 403;
        }

        Gson gson = new Gson();
        return Response.ok(gson.toJson(student))
                .status(responseStatus)
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


    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.TEXT_PLAIN)
    @Path("/operator/register")
    public Response registerOperator( String user){
        String reponse = "";
        try {
            Gson gson = new Gson();
            User student = gson.fromJson(user, User.class);

            System.out.println(user);

            reponse = authService.register(student, "operator");

        }catch (Exception e){
            e.printStackTrace();
            return Response.noContent()
                    .status(415)
                    .header("Access-Control-Allow-Origin", "*")
                    .header("Access-Control-Allow-Headers", "origin, content-type, accept, authorization")
                    .header("Access-Control-Allow-Credentials", "true")
                    .header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD")
                    .header("Access-Control-Max-Age", "1209600")
                    .build();
        }

        return Response.ok(reponse)
                .status(200)
                .header("Access-Control-Allow-Origin", "*")
                .header("Access-Control-Allow-Headers", "origin, content-type, accept, authorization")
                .header("Access-Control-Allow-Credentials", "true")
                .header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD")
                .header("Access-Control-Max-Age", "1209600")
                .build();

    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.TEXT_PLAIN)
    @Path("/register")
    public Response register( String user){
        String reponse = "";
        try {
            Gson gson = new Gson();
            User student = gson.fromJson(user, User.class);

            System.out.println(user);

            reponse = authService.register(student, "user");

        }catch (Exception e){
            e.printStackTrace();
            return Response.noContent()
                    .status(415)
                    .header("Access-Control-Allow-Origin", "*")
                    .header("Access-Control-Allow-Headers", "origin, content-type, accept, authorization")
                    .header("Access-Control-Allow-Credentials", "true")
                    .header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD")
                    .header("Access-Control-Max-Age", "1209600")
                    .build();
        }

        return Response.ok(reponse)
                .status(200)
                .header("Access-Control-Allow-Origin", "*")
                .header("Access-Control-Allow-Headers", "origin, content-type, accept, authorization")
                .header("Access-Control-Allow-Credentials", "true")
                .header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD")
                .header("Access-Control-Max-Age", "1209600")
                .build();

    }

}