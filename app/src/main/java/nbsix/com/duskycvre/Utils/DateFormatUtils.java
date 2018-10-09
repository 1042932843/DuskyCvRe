/**
 * 
 */
package nbsix.com.duskycvre.Utils;

import android.annotation.SuppressLint;
import android.util.Log;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


/**
 * @ClassName: 	DateFormatter
 * @Description:TODO
 * @author 	JohnnyShieh
 * @date	Feb 14, 2014		5:07:44 PM
 */
@SuppressLint("SimpleDateFormat")
public class DateFormatUtils {

    private static final String format = "yyyy-MM-dd HH:mm:ss" ;
    private static final DateFormat formatter = new SimpleDateFormat ( format ) ;

    
    public static Date parse ( String str ) {
        Date date = null ;
        try {
            Log.d ( "parse", str ) ;
            date = formatter.parse ( str ) ;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date ;
    }
    
    public static String format ( Date date ) {
        return formatter.format ( date ) ;
    }
    

}
