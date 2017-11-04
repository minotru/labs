import java.io.UnsupportedEncodingException;
import java.util.Enumeration;
import java.util.Locale;
import java.util.ResourceBundle;

public class AppLocale {
    private static final String strMsg = "messages";
    private static Locale loc = Locale.getDefault();
    private static ResourceBundle res =
            ResourceBundle.getBundle( AppLocale.strMsg, AppLocale.loc );

    static Locale get() {
        return AppLocale.loc;
    }

    static public void set( Locale loc ) {
        AppLocale.loc = loc;
        res = ResourceBundle.getBundle( AppLocale.strMsg, AppLocale.loc );
    }

    static public ResourceBundle getBundle() {
        return AppLocale.res;
    }

    private static String translate(String key) {
        String value = "";
        try{
           value =  new String(res.getString(key).getBytes("ISO-8859-1"), "UTF-8");
        }  catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return value;
    }

    static String getString( String key ) {
        return translate(key);
    }

    // Resource keys:

    public static final String bus ="bus";
    public static final String trolleybus ="trolleybus";
    public static final String tram ="tram";
    public static final String number ="number";
    public static final String hasCome = "has_come";
    public static final String broken = "broken";
    public static final String repaired = "repaired";
    public static final String created = "created";
}
