package sa.student.service;

import com.novell.ldap.*;

import java.io.UnsupportedEncodingException;

public class LdapConnector {

    private static LdapConnector ldapConnector;

    private LDAPConnection lc = new LDAPConnection();

    private boolean isConnected = false;

    public LDAPConnection getLc() {
        return lc;
    }

    public void setLc(LDAPConnection lc) {
        this.lc = lc;
    }

    public boolean isConnected() {
        return isConnected;
    }

    public void setConnected(boolean connected) {
        isConnected = connected;
    }

    private LdapConnector(){

    }

    public static LdapConnector getInstance(){
        if (ldapConnector == null){
            ldapConnector = new LdapConnector();
            ldapConnector.setConnected(ldapConnector.connect());
        }
        return ldapConnector;
    }


    public Boolean connect() {

        String ldapHost = "52.7.78.51";
        //String ldapHost = "192.168.99.101";
        String dn = "cn=admin,dc=laundry,dc=unal,dc=edu,dc=co";
        String password = "admin";

        int ldapPort =  LDAPConnection.DEFAULT_PORT;
        int ldapVersion = LDAPConnection.LDAP_V3;

        try {
            lc.connect(ldapHost, ldapPort);
            System.out.println("Connecting to LDAP Server...");

            LDAPResponseQueue queue = lc.bind(ldapVersion, dn, password.getBytes("UTF8"), (LDAPResponseQueue) null);
            LDAPMessage message;

            while(( message = queue.getResponse()) != null){
                System.out.println(message.getClass().getName());

                if(((LDAPResponse) message).getResultCode() == 0){
//                    System.out.println("Codigo de respuesta LDAPResponse: " + ((LDAPResponse) message).getResultCode());
//                    System.out.println("operacion bind para user correcto:" + dn);
                    System.out.println("Authenticated in LDAP Server...");
                }else
                    return false;
            }

            //lc.bind(ldapVersion, dn, password.getBytes("UTF8"));
            return true;
        } catch (LDAPException | UnsupportedEncodingException ex) {
            System.out.println("ERROR when connecting to LDAP Server...");
            ex.printStackTrace();
            return false;
        }
    }
}
