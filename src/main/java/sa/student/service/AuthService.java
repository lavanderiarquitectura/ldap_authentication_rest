package sa.student.service;

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

        if (ldapService.connect()) {
            if (ldapService.validateUser(personalID, password)) {
                response = ldapService.getData(personalID);
            } else {
                response = "false";
            }
        } else {
            response = "false";
        }
        return response;
    }

    public String register(User student) {

        String personalID = student.getPersonal_id().toString();
        String password = student.getPassword();
        String firstName = student.getName();
        String lastName = student.getLast_name();
        String room = student.getRoom_id().toString();

        if (ldapService.connect()) {

            boolean success = ldapService.insertUser(firstName, lastName, personalID, password, room);

            if (success) {
                response = "user created";
            } else {
                response = "false";
            }
        } else {
            response = "false";
        }
        return response;
    }
}