package sa.student.service;

import com.novell.ldap.*;
import javax.faces.context.FacesContext;
import java.io.UnsupportedEncodingException;

public class LdapService {
    public LdapConnector connector = LdapConnector.getInstance();

//    private LDAPConnection lc = new LDAPConnection();
//
//    public LDAPConnection getLc() {
//        return lc;
//    }
//
//    public void setLc(LDAPConnection lc) {
//        this.lc = lc;
//    }

    public Boolean login(String user, String password){
        if (connector.isConnected()) {
            if (validateUser(user, password, "user")) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

//    public Boolean connect() {
//
//        String ldapHost = "54.166.103.169";
//        //String ldapHost = "192.168.99.101";
//        String dn = "cn=admin,dc=laundry,dc=unal,dc=edu,dc=co";
//        String password = "admin";
//
//        int ldapPort =  LDAPConnection.DEFAULT_PORT;
//        int ldapVersion = LDAPConnection.LDAP_V3;
//
//        try {
//            lc.connect(ldapHost, ldapPort);
//            System.out.println("Connecting to LDAP Server...");
//
//            LDAPResponseQueue queue = lc.bind(ldapVersion, dn, password.getBytes("UTF8"), (LDAPResponseQueue) null);
//            LDAPMessage message;
//
//            while(( message = queue.getResponse()) != null){
//                System.out.println(message.getClass().getName());
//
//                if(((LDAPResponse) message).getResultCode() == 0){
////                    System.out.println("Codigo de respuesta LDAPResponse: " + ((LDAPResponse) message).getResultCode());
////                    System.out.println("operacion bind para user correcto:" + dn);
//                    System.out.println("Authenticated in LDAP Server...");
//                }else
//                    return false;
//            }
//
//            //lc.bind(ldapVersion, dn, password.getBytes("UTF8"));
//            return true;
//        } catch (LDAPException | UnsupportedEncodingException ex) {
//            System.out.println("ERROR when connecting to LDAP Server...");
//            ex.printStackTrace();
//            return false;
//        }
//    }

    public Boolean validateUser(String username, String password, String type){

        String dn = "cn=" + username + ",ou=laundry,dc=laundry,dc=unal,dc=edu,dc=co";

        String dnOperator = "cn=" + username + ",ou=laundryOperator,dc=laundry,dc=unal,dc=edu,dc=co";

        Boolean bind_reponse = false;
        long startTime = System.currentTimeMillis();
        System.out.println("cn a conectar:" + dn);

        try {
            if(type.equalsIgnoreCase("operator")){
                LDAPResponseQueue queue = connector.getLc().bind(LDAPConnection.LDAP_V3, dnOperator , password.getBytes("UTF8"), (LDAPResponseQueue) null);
                LDAPMessage message;

                while(( message = queue.getResponse()) != null){
                    System.out.println(message.getClass().getName());

                    if(((LDAPResponse) message).getResultCode() == 0){
                        bind_reponse = true;
                        System.out.println("Codigo de respuesta LDAPResponse: " + ((LDAPResponse) message).getResultCode());
                        System.out.println("operacion bind para operator correcto:" + dn);
                    }
                }

            }
            else{
                LDAPResponseQueue queue = connector.getLc().bind(LDAPConnection.LDAP_V3, dn, password.getBytes("UTF8"), (LDAPResponseQueue) null);
                LDAPMessage message;

                while(( message = queue.getResponse()) != null){
                    System.out.println(message.getClass().getName());

                    if(((LDAPResponse) message).getResultCode() == 0){
                        bind_reponse = true;
                        System.out.println("Codigo de respuesta LDAPResponse: " + ((LDAPResponse) message).getResultCode());
                        System.out.println("operacion bind para user correcto:" + dn);
                    }
                }

                long endTime = System.currentTimeMillis();
                System.out.println( (startTime - endTime) );
            }
            return bind_reponse;
        } catch (LDAPException ex) {
            ex.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return false;
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
            connector.getLc().add(entry);
            return true;
        } catch (LDAPException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public Boolean insertOperator(String firstName, String lastName, String personalId, String pass, String room, String username){

        String dn = "cn=" + personalId + ",ou=laundryOperator,dc=laundry,dc=unal,dc=edu,dc=co";

        LDAPAttributeSet attribs = new LDAPAttributeSet();
        attribs.add(new LDAPAttribute("cn",username));
        attribs.add(new LDAPAttribute("givenName",firstName));
        attribs.add(new LDAPAttribute("sn",lastName));
        attribs.add(new LDAPAttribute("uid",personalId));
        attribs.add(new LDAPAttribute("userPassword",pass));
        attribs.add(new LDAPAttribute("departmentNumber",room));
        attribs.add(new LDAPAttribute("objectClass","inetOrgPerson"));

        try {
            LDAPEntry entry = new LDAPEntry(dn,attribs);
            connector.getLc().add(entry);
            return true;
        } catch (LDAPException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public String getData(String username, String type){
        String dn = "cn=" + username + ",ou=laundry,dc=laundry,dc=unal,dc=edu,dc=co";
        String dnOperator = "cn=" + username + ",ou=laundryOperator,dc=laundry,dc=unal,dc=edu,dc=co";
        LDAPEntry foundEntry = null;
        LDAPAttribute uid = null;
        String getAttrs[] = {"uid", "givenName", "sn", "uidNumber", "departmentNumber"};
        String values[] = {};
        String a  = "";
        try{
            if(type.equalsIgnoreCase("operator"))
                foundEntry = connector.getLc().read(dnOperator, getAttrs);
            else
                foundEntry = connector.getLc().read(dn, getAttrs);

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