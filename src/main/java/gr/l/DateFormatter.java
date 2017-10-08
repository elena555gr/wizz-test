package gr.l;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateFormatter {
    //   @SuppressWarnings("deprecation")
       public static String monthStrFormatter(String selectdata) {
           Date d = new Date(selectdata);
           SimpleDateFormat dt = new SimpleDateFormat("MMMM/dd/yyyy", Locale.ENGLISH);
           String date = dt.format(d);
           String[] split = date.split("/");
           String monthString = split[0];

           return monthString;
       }

    // @SuppressWarnings("deprecation")
       public static int monthIntFormatter(String selectdata) {
           Date d = new Date(selectdata);
           SimpleDateFormat dt = new SimpleDateFormat("MM/dd/yyyy", Locale.ENGLISH);
           String date = dt.format(d);
           String[] split = date.split("/");
           String monthStr = split[0];
           int monthInt = Integer.parseInt(monthStr);
           return monthInt;
       }

    //   @SuppressWarnings("deprecation")
       public static int yearIntFormatter(String selectdata) {
           Date d = new Date(selectdata);
           SimpleDateFormat dt = new SimpleDateFormat("MM/dd/yyyy", Locale.ENGLISH);
           String date = dt.format(d);
           String[] split = date.split("/");
           String yearStr = split[2];
           int yearInt = Integer.parseInt(yearStr);
           return yearInt;
       }

    //  @SuppressWarnings("deprecation")
       public static int dayIntFormatter(String selectdata) {
           Date d = new Date(selectdata);
           SimpleDateFormat dt = new SimpleDateFormat("MM/dd/yyyy", Locale.ENGLISH);
           String date = dt.format(d);
           String[] split = date.split("/");
           String dayStr = split[1];
           int dayInt = Integer.parseInt(dayStr);
           return dayInt;
       }

    //  @SuppressWarnings("deprecation")
       public static String dayFormatter(String selectdata) {
           Date d = new Date(selectdata);
           SimpleDateFormat dt = new SimpleDateFormat("MMMM/dd/yyyy", Locale.ENGLISH);
           String date = dt.format(d);
           String[] split = date.split("/");
           String month = split[0];
           //System.out.println(month);
           return month;
       }
}
