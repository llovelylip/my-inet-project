package inet.util;

import java.util.Calendar;

/**
 * @author Nguyen Trong Tho
 * @version 1.0
 */

public class Today {
    private int dd,mm,yyyy,hh,mi,sec;
    private String dayOfWeek;

    public String getDayOfWeek() {return dayOfWeek; }
    public int getDay()    { return dd; }
    public int getMonth()  { return mm; }
    public int getYear()   { return yyyy; }
    public int getHour()   { return hh; }
    public int getMinute() { return mi; }
    public int getSecond() { return sec; }

    public Today(int day, int month, int year) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_MONTH, day);
        cal.set(Calendar.MONTH, month - 1);
        cal.set(Calendar.YEAR, year);
        init(cal);
    }


    public Today() {
        Calendar cal = Calendar.getInstance();
        init(cal);
    }

    private void init(Calendar cal) {
        int d = cal.get(Calendar.DAY_OF_WEEK);
        switch(d) {
            case Calendar.MONDAY:
                dayOfWeek = "Thu Hai";
                break;
            case Calendar.TUESDAY:
                dayOfWeek = "Thu Ba";
                break;
            case Calendar.WEDNESDAY:
                dayOfWeek = "Thu Tu";
                break;
            case Calendar.THURSDAY:
                dayOfWeek = "Thu Nam";
                break;
            case Calendar.FRIDAY:
                dayOfWeek = "Thu Sau";
                break;
            case Calendar.SATURDAY:
                dayOfWeek = "Thu Bay";
                break;
            case Calendar.SUNDAY:
                dayOfWeek = "Chu Nhat";
                break;
            default:dayOfWeek = "";
        }

        this.dd   = cal.get(Calendar.DAY_OF_MONTH);
        this.mm   = cal.get(Calendar.MONTH) + 1;
        this.yyyy = cal.get(Calendar.YEAR);

        this.hh = cal.get(Calendar.HOUR_OF_DAY);
        this.mi = cal.get(Calendar.MINUTE);
        this.sec= cal.get(Calendar.SECOND);
    }

    public static Today getInstance() {
        return new Today();
    }

    public static void main(String[] args) {
        //Now now = Now.getInstance();
        Today now = new Today();
        System.out.println(now.getDayOfWeek());
        System.out.println("day=" + now.getDay());
        System.out.println("Month=" + now.getMonth());
        System.out.println("hour=" + now.getHour());
        System.out.println("min=" + now.getMinute());

        Calendar cal = Calendar.getInstance();
        int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);//1: CN, 2: T2,...
    }
}
