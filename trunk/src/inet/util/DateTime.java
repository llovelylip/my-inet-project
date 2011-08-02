package inet.util;

import java.sql.Timestamp;
import java.util.Calendar;

/**
 * @author Nguyen Trong Tho
 * @version 1.0
 */

public class DateTime {
    private Timestamp ts;
    private Calendar cal;

    private int dd,mm,yyyy,hh,mi,sec;

    private String dayOfWeek; //Thu Hai,..

    public String getDayOfWeek() {return dayOfWeek; }
    public int getDay()    { return dd; }
    public int getMonth()  { return mm; }
    public int getYear()   { return yyyy; }
    public int getHour()   { return hh; }
    public int getMinute() { return mi; }
    public int getSecond() { return sec; }
    public Calendar getCalendar() { return cal; }

    public Timestamp getTimestamp() {
        return this.ts;
    }
    public void setTimestamp(Timestamp ts) {
        if (ts != null) {
            this.ts = ts; //millisec = tu 1970
            this.cal.setTime(new java.util.Date(ts.getTime()));
            //this.extractInfo();
        }
    }


    /**
     *  Note: call refresh whenever you change values of DataTime object
     */
    public void refresh() {
        this.ts = new Timestamp((cal.getTime()).getTime());
        this.extractInfo();
    }

    public void setDay(int day)     {
        cal.set(Calendar.DAY_OF_MONTH, day);
    }
    public void setMonth(int month) {
        cal.set(Calendar.MONTH, month-1);
    }
    public void setYear(int year)   {
        cal.set(Calendar.YEAR, year);
    }
    public void setHour(int hour)   {
        cal.set(Calendar.HOUR_OF_DAY, hour);
    }
    public void setMinute(int min)  {
        cal.set(Calendar.MINUTE, min);
    }
    public void setSecond(int sec)  {
        cal.set(Calendar.SECOND, sec);
    }

    public DateTime() { // use current date and time
        this.cal = Calendar.getInstance();
        this.ts  = new Timestamp((cal.getTime()).getTime());
        this.extractInfo();
    }

    public DateTime(Timestamp ts) {
        this.ts  = ts;
        this.cal = Calendar.getInstance();
        this.cal.setTime(new java.util.Date(ts.getTime()));
        this.extractInfo();
    }

    public DateTime(Calendar cal) {
        this.cal = cal;
        this.ts  = new Timestamp((cal.getTime()).getTime());
        this.extractInfo();
    }


    public static DateTime getInstance() {
        return new DateTime();
    }

    public static DateTime getInstance(Timestamp ts) {
        return new DateTime(ts);
    }

    public static DateTime getInstance(int year, int month, int day) {
        if (month > 0) month -= 1; // Month begin from 0 value
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);
        return new DateTime(calendar);
    }


    // next day from day of this object
    public DateTime getNextDay() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new java.util.Date(ts.getTime()));
        int iDay = calendar.get(calendar.DAY_OF_MONTH);
        calendar.set(Calendar.DAY_OF_MONTH, iDay + 1);
        return new DateTime(calendar);
    }


    // next day from day/month/year
    public static DateTime getNextDay(int day, int month, int year) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month-1, day+1);
        return new DateTime(calendar);
    }

    public DateTime getPreviousDay() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new java.util.Date(ts.getTime()));
        int iDay = calendar.get(calendar.DAY_OF_MONTH);
        calendar.set(Calendar.DAY_OF_MONTH, iDay - 1);
        return new DateTime(calendar);
    }

    // next day from day/month/year
    public static DateTime getPreviousDay(int day, int month, int year) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month-1, day-1);
        return new DateTime(calendar);
    }

    private void extractInfo() {
        int d = cal.get(Calendar.DAY_OF_WEEK);
        switch (d) {
            case Calendar.MONDAY:
                dayOfWeek = "Thu 2";
                break;
            case Calendar.TUESDAY:
                dayOfWeek = "Thu 3";
                break;
            case Calendar.WEDNESDAY:
                dayOfWeek = "Thu 4";
                break;
            case Calendar.THURSDAY:
                dayOfWeek = "Thu 5";
                break;
            case Calendar.FRIDAY:
                dayOfWeek = "Thu 6";
                break;
            case Calendar.SATURDAY:
                dayOfWeek = "Thu 7";
                break;
            case Calendar.SUNDAY:
                dayOfWeek = "CN";
                break;
            default:
                dayOfWeek = "";
        }
        this.dd = cal.get(Calendar.DAY_OF_MONTH);
        this.mm = cal.get(Calendar.MONTH) + 1;
        this.yyyy = cal.get(Calendar.YEAR);

        this.hh = cal.get(Calendar.HOUR_OF_DAY);
        this.mi = cal.get(Calendar.MINUTE);
        this.sec = cal.get(Calendar.SECOND);
    }

    public static void main(String[] args) {
        DateTime dt = DateTime.getInstance(DateProc.createTimestamp());
        System.out.println(dt.getDayOfWeek());
        System.out.println(dt.getDay());
        System.out.println(dt.getMonth());
    }
}
