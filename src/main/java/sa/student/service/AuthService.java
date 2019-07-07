package sa.student.service;

import com.novell.ldap.LDAPException;
import sa.student.model.User;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class AuthService {

    @PersistenceContext
    EntityManager entityManager;

    String response = "";
    LdapService ldapService = new LdapService();

    public String login(User student) {

        String personalID = student.getPersonal_id().toString();
        String password = student.getPassword();

        if (ldapService.connector.isConnected()) {
            if (ldapService.validateUser(personalID, password, "user")) {
                response = ldapService.getData(personalID, "user");
            } else {
                response = "false";
            }
        } else {
            response = "false";
        }

//        try {
//            ldapService.getLc().disconnect();
//        } catch (LDAPException e) {
//            e.printStackTrace();
//        }

        return response;
    }

    public String loginOperator(User operator) {

        String username = operator.getUsername();
        String password = operator.getPassword();

        if (ldapService.connector.isConnected()) {
            if (ldapService.validateUser(username, password, "operator")) {
                response = ldapService.getData(username, "operator");
            } else {
                response = "false";
            }
        } else {
            response = "false";
        }
        return response;
    }

    public String getUser(String username) {
        if (ldapService.connector.isConnected()) {
                return ldapService.getData(username, "user");
        } else {
            return "false";
        }
    }

    public String register(User entry, String type) {

        String personalID = entry.getPersonal_id().toString();
        String password = entry.getPassword();
        String firstName = entry.getName();
        String lastName = entry.getLast_name();
        String room = entry.getRoom_id().toString();
        String username = entry.getUsername();

        boolean success;
        if (ldapService.connector.isConnected()) {

            if(getUser(personalID).equalsIgnoreCase("LDAPException found")){
                if(type.equalsIgnoreCase("operator"))
                    success = ldapService.insertOperator(firstName, lastName, personalID, password, room, username);
                else
                    success = ldapService.insertUser(firstName, lastName, personalID, password, room);

                if (success) {
                    response = "user created";
                } else {
                    response = "false";
                }
            } else {
                response = "user already exist!";
            }

        } else {
            response = "false";
        }

        return response;
    }
}