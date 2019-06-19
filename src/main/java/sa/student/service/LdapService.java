package sa.student.service;

import com.novell.ldap.*;
import javax.faces.context.FacesContext;
import java.io.UnsupportedEncodingException;

public class LdapService {

    private LDAPConnection lc = new LDAPConnection();


    public Boolean login(String user, String password){
        if (connect()) {
            if (validateUser(user, password)) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    public Boolean connect() {

        //String ldapHost = "3.87.40.222";
        String ldapHost = "192.168.99.101";
        String dn = "cn=admin,dc=laundry,dc=unal,dc=edu,dc=co";
        String password = "admin";

        int ldapPort =  LDAPConnection.DEFAULT_PORT;
        int ldapVersion = LDAPConnection.LDAP_V3;

        try {
            lc.connect(ldapHost, ldapPort);
            System.out.println("Connecting to LDAP Server...");
            lc.bind(ldapVersion, dn, password.getBytes("UTF8"));
            System.out.println("Authenticated in LDAP Server...");
            return true;
        } catch (LDAPException | UnsupportedEncodingException ex) {
            System.out.println("ERROR when connecting to LDAP Server...");
            return false;
        }
    }

    public Boolean validateUser(String username, String password){

        String dn = "cn=" + username + ",ou=laundry,dc=laundry,dc=unal,dc=edu,dc=co";

        System.out.println("cn a conectar:" + dn);

        try {
            lc.bind(dn, password);
            return true;
        } catch (LDAPException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public Boolean insertUser(String firstName, String lastName, String personalId, String pass, String room){

        String dn = "cn=" + personalId + ",ou=laundry,dc=laundry,dc=unal,dc=edu,dc=co";

        LDAPAttributeSet attribs = new LDAPAttributeSet();
        attribs.add(new LDAPAttribute("cn",personalId));
        attribs.add(new LDAPAttribute("givenName",firstName));
        attribs.add(new LDAPAttribute("sn",lastName));
        attribs.add(new LDAPAttribute("uid",personalId));
        attribs.add(new LDAPAttribute("userPassword",pass));
        attribs.add(new LDAPAttribute("departmentNumber",room));
        attribs.add(new LDAPAttribute("objectClass","inetOrgPerson"));

        try {
            LDAPEntry entry = new LDAPEntry("cn="+personalId+",ou=laundry,dc=laundry,dc=unal,dc=edu,dc=co",attribs);
            lc.add(entry);
            return true;
        } catch (LDAPException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public String getData(String username){
        String dn = "cn=" + username + ",ou=laundry,dc=laundry,dc=unal,dc=edu,dc=co";
        LDAPEntry foundEntry = null;
        LDAPAttribute uid = null;
        String getAttrs[] = {"uid", "givenName", "sn", "uidNumber", "departmentNumber"};
        String values[] = {};
        String a  = "";
        try{
            foundEntry = lc.read(dn, getAttrs);

            uid = foundEntry.getAttribute("uid");
            values = uid.getStringValueArray();
            a = a + values[0] + ";";

            uid = foundEntry.getAttribute("givenName");
            values = uid.getStringValueArray();
            a = a + values[0] + ";";

            uid = foundEntry.getAttribute("sn");
            values = uid.getStringValueArray();
            a = a + values[0] + ";";

//            uid = foundEntry.getAttribute("uidNumber");
//            values = uid.getStringValueArray();
//            a = a + values[0] + ";";
            a = a + username + ";";

            uid = foundEntry.getAttribute("departmentNumber");
            values = uid.getStringValueArray();
            a = a + values[0] + ";";

            return a;
        } catch (LDAPException ex){
            ex.printStackTrace();
            return "LDAPException found";
        }
    }
}