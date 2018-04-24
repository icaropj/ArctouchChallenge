package br.com.icaropinhoe.arctouch_challenge.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by icaro on 28/12/2017.
 */

public class DateUtil {

    public static String dateToString(Date date, String pattern){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        String formattedDate = simpleDateFormat.format(date);
        return formattedDate;
    }

}
