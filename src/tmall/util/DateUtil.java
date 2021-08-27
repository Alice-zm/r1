package tmall.util;

import java.util.Date;

public class DateUtil {
    public static java.sql.Timestamp d2t(Date d){
        if(null==d)
            return null;
        return new java.sql.Timestamp(d.getTime());
    }
    public static Date t2d(java.sql.Timestamp t){
        if(null==t)
            return null;
        return new Date(t.getTime());
    }
}
