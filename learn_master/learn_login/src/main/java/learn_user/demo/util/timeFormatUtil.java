package learn_user.demo.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class timeFormatUtil {

    public  static String  getDatePatten(String patten , Date date)
    {
        SimpleDateFormat simpleDateFormat  =new SimpleDateFormat(patten) ;
        return simpleDateFormat .format(date);
    }
    public  static String  getDatePatten(Date date)
    {
        SimpleDateFormat simpleDateFormat  =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss") ;
        return simpleDateFormat .format(date);
    }


}
