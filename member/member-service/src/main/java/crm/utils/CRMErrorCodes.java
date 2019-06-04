package crm.utils;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public final class CRMErrorCodes {
    public static final String ATLEASTONEOFTHEFIELDSMUSTBEAMENDED =
            "COM.CRM.EXCEPTION.SUBSCRIPTIONS.ATLEASTONEOFTHEFIELDSMUSTBEAMENDEDEXCEPTION";
    public static final String SERVICEALREADYEXISTSONSUBSCRIPTION =
            "COM.CRM.EXCEPTION.SUBSCRIPTIONS.SERVICEALREADYEXISTSONSUBSCRIPTIONEXCEPTION";
    
    private static final Set<String> IGNORED_CRM_MESSAGES = new HashSet<>(Arrays.asList(
            ATLEASTONEOFTHEFIELDSMUSTBEAMENDED,
            SERVICEALREADYEXISTSONSUBSCRIPTION));
    
    public static Set<String> getAllIgnoredCrmMessages() {
        return IGNORED_CRM_MESSAGES;
    }
}
