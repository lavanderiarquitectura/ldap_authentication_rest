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

        String username = student.getUsername();
        String password = student.getPassword();

        if (ldapService.connect()) {
            if (ldapService.validateUser(username, password)) {
                response = ldapService.getData(username);
            } else {
                response = "false";
            }
        } else {
            response = "false";
        }
        return response;
    }
}